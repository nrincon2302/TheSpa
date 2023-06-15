package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import co.edu.uniandes.dse.thespa.entities.UbicacionEntity;

import co.edu.uniandes.dse.thespa.repositories.UbicacionRepository;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UbicacionService {
    // String estático para eliminar el code smell en el mensaje de excepción y
    // reporte
    private static final String MENSAJE_UBICACION_NO_EXISTE = "La ubicación con el id = {0} no existe";
    private static final String UBICACION_ENCONTRADA = "Ubicación encontrada";

    // Inyeccion de dependencias -> Repositorio Ubicacion
    @Autowired
    private UbicacionRepository ubicacionRepository;

    // creación de ubicaciones
    @Transactional
    public UbicacionEntity createUbicacion(UbicacionEntity ubicacionEntity)
            throws IllegalOperationException {
        log.info("Creando una ubicacion nueva");
        // revisa que la latitud y longitud no esten vacias
        if (ubicacionEntity.getLatitud() == null || ubicacionEntity.getLongitud() == null) {
            throw new IllegalOperationException("La latitud y longitud no pueden ser nulas");
        }
        // revisa que la latitud y longitud sean validas
        if (ubicacionEntity.getLatitud() < -90 || ubicacionEntity.getLatitud() > 90) {
            throw new IllegalOperationException("La latitud debe estar entre -90 y 90");
        }
        // revisa que la latitud y longitud sean validas
        if (ubicacionEntity.getLongitud() < -180 || ubicacionEntity.getLongitud() > 180) {
            throw new IllegalOperationException("La longitud debe estar entre -180 y 180");
        }
        // revisa que la ciudad no este vacia
        if (ubicacionEntity.getCiudad() == null) {
            throw new IllegalOperationException("La ciudad no puede ser nula");
        }
        // revisa que la cuidad no sea " "
        if (ubicacionEntity.getCiudad().equals(" ")) {
            throw new IllegalOperationException("La ciudad no puede ser vacia");
        }
        // revisa que la direccion no este vacia
        if (ubicacionEntity.getDireccion() == null) {
            throw new IllegalOperationException("La direccion no puede ser nula");
        }
        // revisa que la direccion no sea " "
        if (ubicacionEntity.getDireccion().equals(" ")) {
            throw new IllegalOperationException("La direccion no puede ser vacia");
        }
        log.info("Ubicacion creada");
        return ubicacionRepository.save(ubicacionEntity);
    }

    // obtener todas las ubicaciones
    @Transactional
    public List<UbicacionEntity> getUbicaciones() {
        log.info("Consultando todas las ubicaciones");
        return ubicacionRepository.findAll();
    }

    // obtener una ubicacion por id
    @Transactional
    public UbicacionEntity getUbicacion(Long id) throws EntityNotFoundException {
        log.info("Consultando la ubicacion con id = {}", id);
        Optional<UbicacionEntity> ubicacionesBuscadas = ubicacionRepository.findById(id);
        if (ubicacionesBuscadas.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_UBICACION_NO_EXISTE, id));
        }
        log.info(UBICACION_ENCONTRADA);
        return ubicacionesBuscadas.get();
    }

    // actualizar una ubicacion, dada un id y una ubicacion
    @Transactional
    public UbicacionEntity updateUbicacion(Long id, UbicacionEntity ubicacion)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Actualizando la ubicacion con id = {}", id);
        Optional<UbicacionEntity> ubicacionesBuscadas = ubicacionRepository.findById(id);
        if (ubicacionesBuscadas.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_UBICACION_NO_EXISTE, id));
        }
        log.info(UBICACION_ENCONTRADA);
        // revisa que la latitud y longitud no esten vacias
        if (ubicacion.getLatitud() == null || ubicacion.getLongitud() == null) {
            throw new IllegalOperationException("La latitud y longitud no pueden ser nulas");
        }
        // revisa que la latitud y longitud sean validas
        if (ubicacion.getLatitud() < -90 || ubicacion.getLatitud() > 90) {
            throw new IllegalOperationException("La latitud debe estar entre -90 y 90");
        }
        // revisa que la latitud y longitud sean validas
        if (ubicacion.getLongitud() < -180 || ubicacion.getLongitud() > 180) {
            throw new IllegalOperationException("La longitud debe estar entre -180 y 180");
        }
        // revisa que la ciudad no este vacia
        if (ubicacion.getCiudad() == null) {
            throw new IllegalOperationException("La ciudad no puede ser nula");
        }
        // revisa que la cuidad no sea " "
        if (ubicacion.getCiudad().equals(" ")) {
            throw new IllegalOperationException("La ciudad no puede ser vacia");
        }
        // revisa que la direccion no este vacia
        if (ubicacion.getDireccion() == null) {
            throw new IllegalOperationException("La direccion no puede ser nula");
        }
        // revisa que la direccion no sea " "
        if (ubicacion.getDireccion().equals(" ")) {
            throw new IllegalOperationException("La direccion no puede ser vacia");
        }

        ubicacion.setId(id);
        log.info("Actualizando la ubicacion con id = {}", id);
        return ubicacionRepository.save(ubicacion);
    }

    // eliminar una ubicacion
    @Transactional
    public void deleteUbicacion(long id) throws EntityNotFoundException {
        log.info("Eliminando la ubicacion con id = {}", id);
        Optional<UbicacionEntity> ubicacionesBuscadas = ubicacionRepository.findById(id);
        if (ubicacionesBuscadas.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_UBICACION_NO_EXISTE, id));
        }
        log.info(UBICACION_ENCONTRADA);
        ubicacionRepository.deleteById(id);
    }

}
