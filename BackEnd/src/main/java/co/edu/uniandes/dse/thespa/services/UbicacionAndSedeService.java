package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.entities.UbicacionEntity;
import co.edu.uniandes.dse.thespa.repositories.SedeRepository;
import co.edu.uniandes.dse.thespa.repositories.UbicacionRepository;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UbicacionAndSedeService {
    // String estático para eliminar el code smell en el mensaje de excepción y
    // reporte
    private static final String UBICACION_NOT_FOUND = "UBICACION_NOT_FOUND";
    private static final String SEDE_NOT_FOUND = "SEDE_NOT_FOUND";

    // Inyeccion de dependencias -> Repositorio Ubicacion
    @Autowired
    private UbicacionRepository ubicacionRepository;

    // Inyeccion de dependencias -> Repositorio Sede
    @Autowired
    private SedeRepository sedeRepository;

    // Obtener la sede de una ubicacion
    @Transactional
    public SedeEntity obtenerSede(Long ubicacionId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de consultar la sede de la ubicacion con id = {0}", ubicacionId);
        Optional<UbicacionEntity> ubicacionEntity = ubicacionRepository.findById(ubicacionId);
        if (ubicacionEntity.isEmpty()) {
            throw new EntityNotFoundException(UBICACION_NOT_FOUND);
        }
        SedeEntity sedeEntity = ubicacionEntity.get().getSede();
        if (sedeEntity == null) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }
        log.info("Termina proceso de consultar la sede de la ubicacion con id = {0}", ubicacionId);
        return sedeEntity;
    }

    // asignar una sede a una ubicacion
    @Transactional
    public SedeEntity asignarSede(Long ubicacionId, Long sedeId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de asignar la sede con id = {0} a la ubicacion con id = {1}", sedeId, ubicacionId);
        Optional<UbicacionEntity> ubicacionEntity = ubicacionRepository.findById(ubicacionId);
        if (ubicacionEntity.isEmpty()) {
            throw new EntityNotFoundException(UBICACION_NOT_FOUND);
        }
        Optional<SedeEntity> sedeEntity = sedeRepository.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }
        ubicacionEntity.get().setSede(sedeEntity.get());
        log.info("Termina proceso de asignar la sede con id = {0} a la ubicacion con id = {1}", sedeId, ubicacionId);
        return sedeEntity.get();
    }

    // Eliminar la sede de una ubicacion
    @Transactional
    public void eliminarSede(Long ubicacionId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de eliminar la sede de la ubicacion con id = {0}", ubicacionId);
        Optional<UbicacionEntity> ubicacionEntity = ubicacionRepository.findById(ubicacionId);
        if (ubicacionEntity.isEmpty()) {
            throw new EntityNotFoundException(UBICACION_NOT_FOUND);
        }
        // revisar si la ubicacion tiene una sede
        if (ubicacionEntity.get().getSede() == null) {
            throw new IllegalOperationException(SEDE_NOT_FOUND);
        }
        ubicacionEntity.get().setSede(null);
        log.info("Termina proceso de eliminar la sede de la ubicacion con id = {0}", ubicacionId);
    }

}
