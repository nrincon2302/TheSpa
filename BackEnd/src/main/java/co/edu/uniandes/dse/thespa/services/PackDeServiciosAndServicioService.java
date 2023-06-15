package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import co.edu.uniandes.dse.thespa.entities.PackDeServiciosEntity;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;

import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.PackDeServiciosRepository;
import co.edu.uniandes.dse.thespa.repositories.ServicioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PackDeServiciosAndServicioService {
    // String estático para eliminar el code smell en el mensaje de excepción y
    // reporte
    private static final String MENSAJE_PACK_NO_EXISTE = "El pack de servicios con el id = {0} no existe";
    private static final String MENSAJE_SERVICIO_NO_EXISTE = "El servicio con el id = {0} no existe";
    private static final String MENSAJE_SERVICIO_NOTIN_PACK = "El servicio con el id = {0} no esta en el pack de servicios con el id = {1}";

    // Inyeccion de dependencias -> Repositorio PackDeServicios
    @Autowired
    private PackDeServiciosRepository packDeServiciosRepository;

    // Inyeccion de dependencias -> Servicio Servicio
    @Autowired
    private ServicioRepository servicioRepository;

    // Obtiene todos los servicios de un pack de servicios
    @Transactional
    public List<ServicioEntity> getServicios(Long id) throws EntityNotFoundException {
        log.info("Consultando los servicios del pack de servicios con id = {}", id);
        Optional<PackDeServiciosEntity> packsBuscados = packDeServiciosRepository.findById(id);
        if (packsBuscados.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_PACK_NO_EXISTE, id));
        }
        log.info("Servicios del pack de servicios encontrados");
        return packsBuscados.get().getServicios();
    }

    // obtiene un servicio de un pack de servicios dado el id del pack de servicios
    // y el id del servicio
    @Transactional
    public ServicioEntity getServicio(Long packid, Long servicioID)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Consultando el servicio con id = {} del pack de servicios con id = {}", servicioID, packid);

        // Busca el pack de servicios
        Optional<PackDeServiciosEntity> packsBuscados = packDeServiciosRepository.findById(packid);
        if (packsBuscados.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_PACK_NO_EXISTE, packid));
        }

        // Busca el servicio
        Optional<ServicioEntity> serviciosBuscados = servicioRepository.findById(servicioID);
        if (serviciosBuscados.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_SERVICIO_NO_EXISTE, servicioID));
        }

        // Verifica que el servicio este en el pack de servicios
        if (!packsBuscados.get().getServicios().contains(serviciosBuscados.get())) {
            throw new IllegalOperationException(String.format(MENSAJE_SERVICIO_NOTIN_PACK, servicioID, packid));
        }

        log.info("Servicio encontrado");

        // Retorna el servicio
        return serviciosBuscados.get();
    }

    // Agrega un servicio a un pack de servicios
    @Transactional
    public ServicioEntity addServicio(Long packid, Long servicioID)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Agregando el servicio con id = {} al pack de servicios con id = {}", servicioID, packid);

        // Busca el pack de servicios
        Optional<PackDeServiciosEntity> packsBuscados = packDeServiciosRepository.findById(packid);
        if (packsBuscados.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_PACK_NO_EXISTE, packid));
        }

        // Busca el servicio
        Optional<ServicioEntity> serviciosBuscados = servicioRepository.findById(servicioID);
        if (serviciosBuscados.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_SERVICIO_NO_EXISTE, servicioID));
        }

        // Verifica que el servicio no este ya en el pack de servicios
        if (packsBuscados.get().getServicios().contains(serviciosBuscados.get())) {
            throw new IllegalOperationException("El servicio con el id = " + servicioID
                    + " ya se encuentra en el pack de servicios con el id = " + packid);
        }

        // Agrega el servicio al pack de servicios
        packsBuscados.get().getServicios().add(serviciosBuscados.get());

        log.info("Servicio agregado al pack de servicios");

        // Retorna el servicio

        return serviciosBuscados.get();

    }

    // Elimina un servicio de un pack de servicios
    @Transactional
    public ServicioEntity removeServicio(Long packid, Long servicioID)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("eliminando el servicio con id = {} del pack de servicios con id = {}", servicioID, packid);

        // Busca el pack de servicios
        Optional<PackDeServiciosEntity> packsBuscados = packDeServiciosRepository.findById(packid);
        if (packsBuscados.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_PACK_NO_EXISTE, packid));
        }

        // Busca el servicio
        Optional<ServicioEntity> serviciosBuscados = servicioRepository.findById(servicioID);
        if (serviciosBuscados.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_SERVICIO_NO_EXISTE, servicioID));
        }

        // Verifica que el servicio este en el pack de servicios
        if (!packsBuscados.get().getServicios().contains(serviciosBuscados.get())) {
            throw new IllegalOperationException(String.format(MENSAJE_SERVICIO_NOTIN_PACK, servicioID, packid));
        }

        // Elimina el servicio del pack de servicios
        packsBuscados.get().getServicios().remove(serviciosBuscados.get());

        log.info("Servicio eliminado del pack de servicios");

        return serviciosBuscados.get();
    }

    // actualiza un pack de servicios con una nueva lista de servicios
    @Transactional
    public List<ServicioEntity> updateServicios(Long packid, List<ServicioEntity> servicios)
            throws EntityNotFoundException, IllegalOperationException {

        log.info("Actualizando los servicios del pack de servicios con id = {}", packid);

        // Busca el pack de servicios
        Optional<PackDeServiciosEntity> packsBuscados = packDeServiciosRepository.findById(packid);
        if (packsBuscados.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_PACK_NO_EXISTE, packid));
        }

        // por cada servicio en la lista de servicios, verifica que exista
        for (ServicioEntity servicio : servicios) {
            Optional<ServicioEntity> serviciosBuscados = servicioRepository.findById(servicio.getId());
            if (serviciosBuscados.isEmpty()) {
                throw new EntityNotFoundException(String.format(MENSAJE_SERVICIO_NO_EXISTE, servicio.getId()));
            }
        }

        // actualiza la lista de servicios del pack de servicios
        packsBuscados.get().setServicios(servicios);

        log.info("Servicios del pack de servicios actualizados");

        return packsBuscados.get().getServicios();
    }

}
