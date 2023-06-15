package co.edu.uniandes.dse.thespa.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.SedeRepository;
import co.edu.uniandes.dse.thespa.repositories.TrabajadorRepository;
import lombok.extern.slf4j.Slf4j;

//Author -> @Juan Coronel

@Slf4j
@Service
public class SedeAndTrabajadorService {

    // String estático para eliminar el code smell en el mensaje de excepción y
    // reporte
    private static final String TRABAJADOR_NOT_FOUND = "TRABAJADOR_NOT_FOUND";
    private static final String SEDE_NOT_FOUND = "SEDE_NOT_FOUND";
    private static final String TRABAJADOR_ALREADY_EXISTS = "TRABAJADOR_ALREADY_EXISTS";
    private static final String TRABAJADOR_NOT_FOUND_IN_CURRENT_SEDE = "TRABAJADOR_NOT_FOUND_IN_CURRENT_SEDE";

    // Inyeccion de dependencias -> Repositorio Sede
    @Autowired
    SedeRepository sedeRepo;

    // Inyeccion de dependencias -> Repositorio Trabajadores
    @Autowired
    TrabajadorRepository trabajadoresRepo;

    // Obtener todos los trabajadores de una sede
    @Transactional
    public List<TrabajadorEntity> obtenerTrabajadroes(Long sedeId) throws EntityNotFoundException {

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }

        return sedeEntity.get().getTrabajadores();

    }

    // Añadir un trabajador a la sede
    @Transactional
    public TrabajadorEntity addSedeTrabajador(Long sedeId, Long trabajadorId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de añadir a la sede un Trabajador con con id = {0}", trabajadorId);
        Optional<TrabajadorEntity> trabEntity = trabajadoresRepo.findById(trabajadorId);
        if (trabEntity.isEmpty()) {
            throw new EntityNotFoundException(TRABAJADOR_NOT_FOUND);
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }

        // revisa si el trabajador ya esta en la sede, si esta lanza una
        // IllegalOperationException
        if (sedeEntity.get().getTrabajadores().contains(trabEntity.get())) {
            throw new IllegalOperationException(TRABAJADOR_ALREADY_EXISTS);
        }

        List<TrabajadorEntity> trabajs = sedeEntity.get().getTrabajadores();
        trabajs.add(trabEntity.get());

        sedeEntity.get().setTrabajadores(trabajs);

        log.info("Termina proceso de añadir a la sede un Trabajador con con id = {0}", sedeId);

        return trabEntity.get();
    }

    // Eliminar un trabajador de la sede
    @Transactional
    public TrabajadorEntity deleteSedeTrabajador(Long sedeId, Long trabajadorId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de eliminar de la sede un Trabajador con con id = {0}", trabajadorId);
        Optional<TrabajadorEntity> trabEntity = trabajadoresRepo.findById(trabajadorId);
        if (trabEntity.isEmpty()) {
            throw new EntityNotFoundException(TRABAJADOR_NOT_FOUND);
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }

        // revisa si el trabajador no esta en la sede, si no esta lanza una
        // IllegalOperationException
        if (!sedeEntity.get().getTrabajadores().contains(trabEntity.get())) {
            throw new IllegalOperationException(TRABAJADOR_NOT_FOUND_IN_CURRENT_SEDE);
        }

        List<TrabajadorEntity> trabajs = sedeEntity.get().getTrabajadores();
        trabajs.remove(trabEntity.get());

        sedeEntity.get().setTrabajadores(trabajs);

        log.info("Termina proceso de añadir a la sede un Trabajador con con id = {0}", sedeId);

        return trabEntity.get();
    }

    // actualizar la lista de serviios extra de la sede
    @Transactional
    public List<TrabajadorEntity> updateSedeTrabajadores(Long sedeId, List<TrabajadorEntity> sedeExtraServiceId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar la lista de servicios extra de la sede con con id = {0}", sedeId);
        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }

        // revisa si los servicios extra existen
        for (TrabajadorEntity serv : sedeExtraServiceId) {
            Optional<TrabajadorEntity> servEntity = trabajadoresRepo.findById(serv.getId());
            if (servEntity.isEmpty()) {
                throw new EntityNotFoundException(TRABAJADOR_NOT_FOUND);
            }
        }

        // crea una lista de servicios extra
        List<TrabajadorEntity> servs = new ArrayList<>();

        for (TrabajadorEntity serv : sedeExtraServiceId) {
            Optional<TrabajadorEntity> servEntity = trabajadoresRepo.findById(serv.getId());
            if (servEntity.isPresent()) {
                servs.add(servEntity.get());
            }
        }

        sedeEntity.get().setTrabajadores(servs);

        log.info("Termina proceso de actualizar la lista de servicios extra de la sede con con id = {0}", sedeId);

        return sedeEntity.get().getTrabajadores();

    }
}
