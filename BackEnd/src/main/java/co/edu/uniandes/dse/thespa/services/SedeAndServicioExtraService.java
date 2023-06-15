package co.edu.uniandes.dse.thespa.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.thespa.entities.ServicioExtraEntity;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.SedeRepository;
import lombok.extern.slf4j.Slf4j;
import co.edu.uniandes.dse.thespa.repositories.ServicioExtraRepository;

//Author -> @Juan Coronel

@Slf4j
@Service
public class SedeAndServicioExtraService {

    private static final String SEDE_NOT_FOUND = "SEDE_NOT_FOUND";
    private static final String EXTRA_SERVICE_NOT_FOUND = "EXTRA_SERVICE_NOT_FOUND";

    // Inyeccion de dependencias -> Repositorio Sede
    @Autowired
    SedeRepository sedeRepo;

    // Inyeccion de dependencias -> Repositorio Servicio Extra
    @Autowired
    ServicioExtraRepository servicioExtraRepo;

    // Obtener todos los servicios extra de una sede
    @Transactional
    public List<ServicioExtraEntity> obtenerAllServicios(Long sedeId) {
        return servicioExtraRepo.findAll();
    }

    // añadir un servicio extra a la sede
    @Transactional
    public ServicioExtraEntity addSedeExtraService(Long sedeId, Long sedeExtraServiceId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de añadir a la sede un servicio extra con con id = {0}", sedeExtraServiceId);
        Optional<ServicioExtraEntity> servEntity = servicioExtraRepo.findById(sedeExtraServiceId);
        if (servEntity.isEmpty()) {
            throw new EntityNotFoundException(EXTRA_SERVICE_NOT_FOUND);
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }

        // revisa si el servicio extra ya esta en la sede, si esta lanza una
        // IllegalOperationException
        if (sedeEntity.get().getServiciosExtra().contains(servEntity.get())) {
            throw new IllegalOperationException("EXTRA_SERVICE_ALREADY_EXISTS");
        }

        List<ServicioExtraEntity> servs = sedeEntity.get().getServiciosExtra();
        servs.add(servEntity.get());

        sedeEntity.get().setServiciosExtra(servs);

        log.info("Termina proceso de añadir a la sede un servicio extra con con id = {0}", sedeId);

        return servEntity.get();
    }

    // Eliminar un servicio extra de la sede
    @Transactional
    public ServicioExtraEntity deleteSedeExtraService(Long sedeId, Long sedeExtraServiceId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de remover a la sede un servicio extra con con id = {0}", sedeExtraServiceId);
        Optional<ServicioExtraEntity> servEntity = servicioExtraRepo.findById(sedeExtraServiceId);
        if (servEntity.isEmpty()) {
            throw new EntityNotFoundException(EXTRA_SERVICE_NOT_FOUND);
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }

        // revisa si el servicio extra no esta en la sede, si no esta lanza una
        // IllegalOperationException
        if (!sedeEntity.get().getServiciosExtra().contains(servEntity.get())) {
            throw new IllegalOperationException("EXTRA_SERVICE_NOT_FOUND_IN_CURRENT_SEDE");
        }

        List<ServicioExtraEntity> servs = sedeEntity.get().getServiciosExtra();
        servs.remove(servEntity.get());

        sedeEntity.get().setServiciosExtra(servs);

        log.info("Termina proceso de eliminar de la sede un servicio extra con con id = {0}", sedeId);

        return servEntity.get();

    }

    // actualizar la lista de serviios extra de la sede
    @Transactional
    public List<ServicioExtraEntity> updateSedeExtraService(Long sedeId, List<ServicioExtraEntity> sedeExtraServiceId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar la lista de servicios extra de la sede con con id = {0}", sedeId);
        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }

        // revisa si los servicios extra existen
        for (ServicioExtraEntity serv : sedeExtraServiceId) {
            Optional<ServicioExtraEntity> servEntity = servicioExtraRepo.findById(serv.getId());
            if (servEntity.isEmpty()) {
                throw new EntityNotFoundException(EXTRA_SERVICE_NOT_FOUND);
            }
        }

        // crea una lista de servicios extra
        List<ServicioExtraEntity> servs = new ArrayList<>();

        for (ServicioExtraEntity serv : sedeExtraServiceId) {
            Optional<ServicioExtraEntity> servEntity = servicioExtraRepo.findById(serv.getId());
            if (servEntity.isPresent()) {
                servs.add(servEntity.get());
            }
        }

        sedeEntity.get().setServiciosExtra(servs);

        log.info("Termina proceso de actualizar la lista de servicios extra de la sede con con id = {0}", sedeId);

        return sedeEntity.get().getServiciosExtra();

    }

}
