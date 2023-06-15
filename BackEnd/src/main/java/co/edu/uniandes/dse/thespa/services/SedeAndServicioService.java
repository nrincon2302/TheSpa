package co.edu.uniandes.dse.thespa.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.SedeRepository;
import co.edu.uniandes.dse.thespa.repositories.ServicioRepository;
import lombok.extern.slf4j.Slf4j;

//Author -> @Juan Coronel

@Slf4j
@Service
public class SedeAndServicioService {
    // String estático para eliminar el code smell en el mensaje de excepción y
    // reporte
    private static final String SEDE_NOT_FOUND = "SEDE_NOT_FOUND";
    private static final String SERVICE_NOT_FOUND = "SERVICE_NOT_FOUND";
    private static final String SERVICE_ALREADY_EXISTS = "SERVICE_ALREADY_EXISTS";
    private static final String SERVICIO_NOT_FOUND_IN_CURRENT_SEDE = "SERVICIO_NOT_FOUND_IN_CURRENT_SEDE";

    // Inyeccion de dependencias -> Repositorio Sede
    @Autowired
    SedeRepository sedeRepo;

    // Inyeccion de dependencias -> Repositorio Servicio
    @Autowired
    ServicioRepository servicioRepo;

    // Obtener todos los servicios de una sede
    @Transactional
    public List<ServicioEntity> obtenerAllServicios(Long sedeId)
            throws EntityNotFoundException {

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }

        List<ServicioEntity> servicios = servicioRepo.findAll();
        if (servicios.isEmpty()) {
            throw new EntityNotFoundException(SERVICE_NOT_FOUND);
        }

        return servicios;
    }

    // Añadir un servicio a la sede
    @Transactional
    public ServicioEntity addSedeServicio(Long sedeId, Long servicioId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de añadir a la sede un servicio con con id = {0}", servicioId);
        Optional<ServicioEntity> servEntity = servicioRepo.findById(servicioId);
        if (servEntity.isEmpty()) {
            throw new EntityNotFoundException(SERVICE_NOT_FOUND);
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }

        // revisa si el servicio ya esta en la sede, si esta lanza una
        // IllegalOperationException
        if (sedeEntity.get().getServicios().contains(servEntity.get())) {
            throw new IllegalOperationException(SERVICE_ALREADY_EXISTS);
        }

        List<ServicioEntity> servicios = sedeEntity.get().getServicios();
        servicios.add(servEntity.get());

        sedeEntity.get().setServicios(servicios);

        log.info("Termina proceso de añadir a la sede un servicio con con id = {0}", sedeId);

        return servEntity.get();
    }

    // Eliminar un servicio a la sede
    @Transactional
    public ServicioEntity deleteSedeServicio(Long sedeId, Long servicioId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de eliminar de la sede un servicio con con id = {0}", servicioId);
        Optional<ServicioEntity> servEntity = servicioRepo.findById(servicioId);
        if (servEntity.isEmpty()) {
            throw new EntityNotFoundException(SERVICE_NOT_FOUND);
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }

        List<ServicioEntity> servicios = sedeEntity.get().getServicios();

        // revisa si el servicio no esta en la sede, si no esta lanza una
        // IllegalOperationException
        if (!servicios.contains(servEntity.get())) {
            throw new IllegalOperationException(SERVICIO_NOT_FOUND_IN_CURRENT_SEDE);
        }
        servicios.remove(servEntity.get());

        sedeEntity.get().setServicios(servicios);

        log.info("Termina proceso de elimnar de la sede un servicio con con id = {0}", sedeId);

        return servEntity.get();
    }

    // actualiza la lista de servicios de una sede
    @Transactional
    public List<ServicioEntity> updateSedeServicios(Long sedeId, List<ServicioEntity> servicios)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar la lista de servicios de la sede con id = {0}", sedeId);
        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }

        // revisa que todos los servicios existan
        for (ServicioEntity servicio : servicios) {
            Optional<ServicioEntity> servEntity = servicioRepo.findById(servicio.getId());
            if (servEntity.isEmpty()) {
                throw new EntityNotFoundException(SERVICE_NOT_FOUND);
            }
        }

        // crea una lista de servicios
        List<ServicioEntity> serviciosActuales = new ArrayList<>();
        for (ServicioEntity servicio : servicios) {
            Optional<ServicioEntity> servEntity = servicioRepo.findById(servicio.getId());
            if (servEntity.isPresent()) {
                serviciosActuales.add(servEntity.get());
            }
        }

        sedeEntity.get().setServicios(serviciosActuales);

        log.info("Termina proceso de actualizar la lista de servicios de la sede con id = {0}", sedeId);

        return sedeEntity.get().getServicios();
    }

}
