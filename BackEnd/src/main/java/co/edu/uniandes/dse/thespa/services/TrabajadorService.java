package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.repositories.TrabajadorRepository;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TrabajadorService {
    // String estático para eliminar el code smell en el mensaje de excepción y
    // reporte
    private static final String MENSAJE_TRABAJADOR_NO_EXISTE = "El trabajador con el id = {0} no existe";

    // Inyeccion de dependencias -> Repositorio Trabajador
    @Autowired
    TrabajadorRepository trabajadorRepository;

    // Método para la Creación de un trabajador
    @Transactional
    public TrabajadorEntity createTrabajador(TrabajadorEntity trabajadorEntity)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia el proceso de creación del trabajador");

        if ((trabajadorEntity.getNombre() == null) || (trabajadorEntity.getNombre().equals("")))
            throw new IllegalOperationException("El nombre del trabajador no puede estar vacío");

        if ((trabajadorEntity.getSedes() == null) || (trabajadorEntity.getSedes().isEmpty()))
            throw new IllegalOperationException("El trabajador debe estar en al menos una sede");

        if (trabajadorEntity.getServicios() == null)
            throw new IllegalOperationException("El trabajador debe tener relacionado al menos un servicio");

        log.info("Termina proceso de creación del trabajador");
        return trabajadorRepository.save(trabajadorEntity);
    }

    // Método para obtener todos los trabajadores
    @Transactional
    public List<TrabajadorEntity> getTrabajadores() {
        log.info("Inicia proceso de consultar todos los trabajadores");
        return trabajadorRepository.findAll();
    }

    // Método para obtener un trabajador por ID
    @Transactional
    public TrabajadorEntity getTrabajador(Long trabajadorId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar el trabajador con id = {0}", trabajadorId);
        Optional<TrabajadorEntity> trabajadorEntity = trabajadorRepository.findById(trabajadorId);
        if (trabajadorEntity.isEmpty())
            throw new EntityNotFoundException(String.format(MENSAJE_TRABAJADOR_NO_EXISTE, trabajadorId));
        log.info("Termina proceso de consultar el trabajador con id = {0}", trabajadorId);
        return trabajadorEntity.get();
    }

    // Método para actualizar un trabajador
    @Transactional
    public TrabajadorEntity updateTrabajador(Long trabajadorId, TrabajadorEntity trabajador)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar el trabajador con id = {0}", trabajadorId);
        Optional<TrabajadorEntity> trabajadorEntity = trabajadorRepository.findById(trabajadorId);
        if (trabajadorEntity.isEmpty())
            throw new EntityNotFoundException(String.format(MENSAJE_TRABAJADOR_NO_EXISTE, trabajadorId));

        if ((trabajador.getNombre() == null) || (trabajador.getNombre().equals("")))
            throw new IllegalOperationException("El nombre del trabajador no puede estar vacío");

        if (trabajador.getSedes() == null)
            throw new IllegalOperationException("El trabajador debe estar en al menos una sede");

        if (trabajador.getServicios() == null)
            throw new IllegalOperationException("El trabajador debe tener relacionado al menos un servicio");

        trabajador.setId(trabajadorEntity.get().getId());
        log.info("Termina proceso de actualizar el trabajador con id = {0}", trabajadorId);
        return trabajadorRepository.save(trabajador);
    }

    // Método para borrar un trabajador
    @Transactional
    public void deleteTrabajador(Long trabajadorId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de borrar el trabajador con id = {0}", trabajadorId);
        Optional<TrabajadorEntity> trabajadorEntity = trabajadorRepository.findById(trabajadorId);
        if (trabajadorEntity.isEmpty())
            throw new EntityNotFoundException("El trabajador con id = " + trabajadorId + " no existe");

        trabajadorRepository.deleteById(trabajadorId);
        log.info("Termina proceso de borrar el trabajador con id = {0}", trabajadorId);
    }
}
