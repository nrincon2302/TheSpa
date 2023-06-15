package co.edu.uniandes.dse.thespa.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.thespa.entities.ArticuloDeRopaEntity;
import co.edu.uniandes.dse.thespa.entities.PackDeServiciosEntity;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.entities.ServicioExtraEntity;
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.SedeRepository;
import lombok.extern.slf4j.Slf4j;

//Author -> @Juan Coronel

@Slf4j
@Service
public class SedeService {
    // String estático para eliminar el code smell en el mensaje de excepción y
    // reporte
    private static final String SEDE_NOT_FOUND = "SEDE_NOT_FOUND";

    // Inyeccion de dependencias -> Repositorio Sede
    @Autowired
    SedeRepository sedeRepo;

    // Revisa que la sede sea legal
    @Transactional
    public void sedeLegal(SedeEntity sede) throws IllegalOperationException {
        // Assert 1: el nombre no debe ser null
        String nombreSede = sede.getNombre();
        if (nombreSede == null) {
            throw new IllegalOperationException("La sede tiene que tener un nombre.");
        }

        // Assert 2: el nombre debe ser unico
        List<SedeEntity> allSedes = getSedes();
        for (SedeEntity sed : allSedes) {
            if (sed.getNombre().equals(sede.getNombre())) {
                throw new IllegalOperationException("El nombre de la sede debe ser unico.");
            }
        }

        // Assert 3: la sede no debe de existir en la base de datos
        for (SedeEntity sed : allSedes) {
            if (sed.getId().equals(sede.getId())) {
                throw new IllegalOperationException("La sede ya existe en la base de datos.");
            }
        }

        // Assert 4: el nombre no debe ser un string vacio
        if (nombreSede.equals("")) {
            throw new IllegalOperationException("La sede tiene que tener un nombre no vacio.");
        }

        // Assert 5: debe tener una ubicacion la sede
        if (sede.getUbicacion() == null) {
            throw new IllegalOperationException("La sede tiene que tener una ubicacion.");
        }

        // dos sedes no pueden tener la misma ubicacion
        for (SedeEntity sed : allSedes) {
            // revisa que la ubicación de sed no sea null
            if (sed.getUbicacion() != null && sede.getUbicacion().getId().equals(sed.getUbicacion().getId())) {
                throw new IllegalOperationException("La sede no puede tener la misma ubicacion que otra sede.");
            }
        }
    }

    // Crear Sede
    @Transactional
    public SedeEntity createSede(SedeEntity sede) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de creacion de Sede.");

        // revisa que la sede sea legal
        sedeLegal(sede);

        // revisa que la lista de trabajadores no sea null

        if (sede.getTrabajadores() == null) {
            sede.setTrabajadores(new ArrayList<TrabajadorEntity>());
        }

        // revisa que la lista de articulos de ropa no sea null
        if (sede.getArticulosDeRopa() == null) {
            sede.setArticulosDeRopa(new ArrayList<ArticuloDeRopaEntity>());
        }

        // revisa que la lista de servicios no sea null
        if (sede.getServicios() == null) {
            sede.setServicios(new ArrayList<ServicioEntity>());
        }

        // revisa que la lista de servicios extra no sea null
        if (sede.getServiciosExtra() == null) {
            sede.setServiciosExtra(new ArrayList<ServicioExtraEntity>());
        }

        // revisa que la lista de packs de servicios no sea null
        if (sede.getPacksDeServicios() == null) {
            sede.setPacksDeServicios(new ArrayList<PackDeServiciosEntity>());
        }

        log.info("Finaliza proceso de creacion de Sede.");
        return sedeRepo.save(sede);

    }

    // Obtener todas las sedes
    @Transactional
    public List<SedeEntity> getSedes() {
        log.info("Inicia proceso de encontrar todas las sedes");
        return sedeRepo.findAll();
    }

    // Obtener una sede segun su Id
    @Transactional
    public SedeEntity getSede(Long sedeId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar la Sede con id = {0}", sedeId);
        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty())
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        log.info("Termina proceso de consultar la Sede con id = {0}", sedeId);
        return sedeEntity.get();
    }

    // Actualizar una sede
    @Transactional
    public SedeEntity updateSede(Long sedeId, SedeEntity sede)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar la Sede con id = {0}", sedeId);
        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty())
            throw new EntityNotFoundException(SEDE_NOT_FOUND);

        sedeEntity.get().setNombre(sede.getNombre());
        sedeEntity.get().setImagen(sede.getImagen());
        log.info("Termina proceso de actualizar la Sede con id = {0}", sedeId);
        return sedeRepo.save(sedeEntity.get());
    }

    // Eliminar una Sede
    @Transactional
    public void deleteSede(Long sedeId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de borrar la sede con id = {0}", sedeId);
        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }

        sedeRepo.deleteById(sedeId);
        log.info("Termina proceso de borrar la sede con id = {0}", sedeId);
    }

}
