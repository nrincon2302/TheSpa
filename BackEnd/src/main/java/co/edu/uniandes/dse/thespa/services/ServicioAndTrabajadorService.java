package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.TrabajadorRepository;
import co.edu.uniandes.dse.thespa.repositories.ServicioRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ServicioAndTrabajadorService {
    // String estático para eliminar el code smell en el mensaje de excepción y
    // reporte
    private static final String MENSAJE_TRABAJADOR_NO_EXISTE = "El trabajador con el id = {} no existe";
    private static final String MENSAJE_SERVICIO_NO_EXISTE = "El servicio con el id = {} no existe";
    private static final String MENSAJE_TRABAJADOR_NOTIN_SERVICIO = "El trabajador con el id = {} no esta en el servicio con el id = {}";

    // Inyeccion de dependencias -> Repositorio Servicios
    @Autowired
    ServicioRepository servicioRepository;

    // Inyeccion de dependencias -> Repositorio Trabajador
    @Autowired
    TrabajadorRepository trabajadorRepository;

    // Creación de trabajadores
    @Transactional
    public TrabajadorEntity addTrabajador(Long servicioID, long trabajadorID)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de agregar un trabajador al servicio con id = {}", servicioID);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioID);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_SERVICIO_NO_EXISTE, servicioID));
        }
        Optional<TrabajadorEntity> trabajadorEntity = trabajadorRepository.findById(trabajadorID);
        if (trabajadorEntity.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_TRABAJADOR_NO_EXISTE, trabajadorID));
        }

        if (servicioEntity.get().getTrabajadores().contains(trabajadorEntity.get())) {
            throw new IllegalOperationException("El trabajador con el id = " + trabajadorID
                    + " ya está asociado al servicio con el id = " + servicioID);
        }
        servicioEntity.get().getTrabajadores().add(trabajadorEntity.get());
        log.info("Termina proceso de agregar un trabajador al servicio con id = {}", servicioID);

        return trabajadorEntity.get();
    }

    // Obtener todos los trabajadores de un servicio
    @Transactional
    public List<TrabajadorEntity> getTrabajadores(Long servicioID) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar los trabajadores del servicio con id = {}", servicioID);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioID);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_SERVICIO_NO_EXISTE, servicioID));
        }
        log.info("Termina proceso de consultar los trabajadores del servicio con id = {}", servicioID);
        return servicioEntity.get().getTrabajadores();
    }

    // Obtener un trabajador de un servicio
    @Transactional
    public TrabajadorEntity getTrabajador(Long servicioID, Long trabajadorid)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Consultando el trabajador con id = {} del servicio con id = {}", trabajadorid, servicioID);

        // Busca el trabajador
        Optional<TrabajadorEntity> trabajadorBuscado = trabajadorRepository.findById(trabajadorid);
        if (trabajadorBuscado.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_TRABAJADOR_NO_EXISTE, trabajadorid));
        }

        // Busca el servicio
        Optional<ServicioEntity> serviciosBuscados = servicioRepository.findById(servicioID);
        if (serviciosBuscados.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_SERVICIO_NO_EXISTE, servicioID));
        }

        if (!serviciosBuscados.get().getTrabajadores().contains(trabajadorBuscado.get())) {
            throw new IllegalOperationException(
                    String.format(MENSAJE_TRABAJADOR_NOTIN_SERVICIO, servicioID, trabajadorid));
        }

        log.info("Pack de Servicios encontrado");

        // Retorna el servicio
        return trabajadorBuscado.get();
    }

    // Eliminar un trabajador de un servicio
    @Transactional
    public TrabajadorEntity removeTrabajador(Long servicioID, long trabajadorID)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de remover un trabajador del servicio con id = {}", servicioID);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioID);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_SERVICIO_NO_EXISTE, servicioID));
        }
        Optional<TrabajadorEntity> trabajadorEntity = trabajadorRepository.findById(trabajadorID);
        if (trabajadorEntity.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_TRABAJADOR_NO_EXISTE, trabajadorID));
        }

        if (!servicioEntity.get().getTrabajadores().contains(trabajadorEntity.get())) {
            throw new IllegalOperationException("El trabajador con el id = " + trabajadorID
                    + " no está asociado al servicio con el id = " + servicioID);
        }
        servicioEntity.get().getTrabajadores().remove(trabajadorEntity.get());
        log.info("Termina proceso de remover un trabajador del servicio con id = {}", servicioID);

        return trabajadorEntity.get();
    }

    // Actualizar los trabajadores de un servicio
    @Transactional
    public List<TrabajadorEntity> updateTrabajadores(Long servicioID, List<TrabajadorEntity> trabajadores)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Actualizando los trabajadores del servicio con id = {}", servicioID);

        // Busca el servicio
        Optional<ServicioEntity> serviciosBuscados = servicioRepository.findById(servicioID);
        if (serviciosBuscados.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_SERVICIO_NO_EXISTE, servicioID));
        }

        // por cada pack en la lista de packs, verifica que exista
        for (TrabajadorEntity trabajador : trabajadores) {
            Optional<TrabajadorEntity> trabajadoresBuscados = trabajadorRepository.findById(trabajador.getId());
            if (trabajadoresBuscados.isEmpty()) {
                throw new EntityNotFoundException(String.format(MENSAJE_TRABAJADOR_NO_EXISTE, trabajador.getId()));
            }
        }

        // crea una lista de packs de servicios
        List<TrabajadorEntity> trabajadoresF = new ArrayList<>();
        // por cada pack en la lista de packs, lo agrega a la lista de packs de
        // servicios
        for (TrabajadorEntity trabajador : trabajadores) {
            Optional<TrabajadorEntity> trabajadoresBuscados = trabajadorRepository.findById(trabajador.getId());
            if (trabajadoresBuscados.isPresent()) {
                trabajadoresF.add(trabajadoresBuscados.get());
            }
        }

        // actualiza la lista de packs del servicio
        serviciosBuscados.get().setTrabajadores(trabajadoresF);

        log.info("Packs de servicios del servicio actualizados");

        return serviciosBuscados.get().getTrabajadores();
    }
}
