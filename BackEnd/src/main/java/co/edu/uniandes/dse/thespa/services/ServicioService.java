package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.TrabajadorRepository;
import co.edu.uniandes.dse.thespa.repositories.SedeRepository;
import co.edu.uniandes.dse.thespa.repositories.ServicioRepository;
import co.edu.uniandes.dse.thespa.repositories.PackDeServiciosRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service

public class ServicioService {
    // String estático para eliminar el code smell en el mensaje de excepción y reporte
    private static final String SERVICE_NOT_FOUND = "Servicio not found";

    // Inyeccion de dependencias -> Repositorio Servicio
    @Autowired
    ServicioRepository servicioRepository;

    // Inyeccion de dependencias -> Repositorio PackDeServicios
    @Autowired
    PackDeServiciosRepository packDeServiciosRepository;

    // Inyeccion de dependencias -> Repositorio Sede
    @Autowired
    SedeRepository sedeRepository;

    // Inyeccion de dependencias -> Repositorio Trabajador
    @Autowired
    TrabajadorRepository trabajadorRepository;

    // Crear un servicio
    @Transactional
    public ServicioEntity createServicio(ServicioEntity servicioEntity)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de creación del servicio");

        if (servicioEntity.getNombre() == null || servicioEntity.getNombre().equals("")) {
            throw new IllegalOperationException("El nombre del servicio no es válido");
        }

        if (servicioEntity.getDescripcion() == null || servicioEntity.getDescripcion().equals("")) {
            throw new IllegalOperationException("La descripción del servicio no es válida");
        }

        if (servicioEntity.getPrecio() == null) {
            throw new IllegalOperationException("El precio del servicio no es válido");
        }

        if (servicioEntity.getDuracion() == null) {
            throw new IllegalOperationException("La duración del servicio no es válida");
        }

        if (servicioEntity.getRestricciones() == null || servicioEntity.getRestricciones().equals("")) {
            throw new IllegalOperationException("Las restricciones no son válidas");
        }

        if (servicioEntity.getDisponible() == null) {
            throw new IllegalOperationException("El servicio no tiene un estado de disponibilidad válido");
        }

        if (servicioEntity.getHoraInicio() == null) {
            throw new IllegalOperationException("La hora de inicio del servicio no es válida");
        }

        if (servicioEntity.getSede() == null) {
            throw new IllegalOperationException("La sede del servicio no es válida");
        }

        if (servicioEntity.getTrabajadores() == null) {
            throw new IllegalOperationException("Los trabajadores del servicio no son válidos");
        }

        if (servicioEntity.getPacksDeServicios() == null) {
            throw new IllegalOperationException("Los de servicios del servicio no son válidos");
        }

        log.info("Termina proceso de creación del Servicio");

        return servicioRepository.save(servicioEntity);
    }

    // Obtener todos los servicios
    @Transactional
    public List<ServicioEntity> getServicios() {
        log.info("Inicia proceso de consultar todos los servicios");
        return servicioRepository.findAll();
    }

    // Obtener un servicio
    @Transactional
    public ServicioEntity getServicio(Long servicioId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar el servicio con id = {0}", servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException(SERVICE_NOT_FOUND);
        }
        log.info("Termina proceso de consultar el servicio con id = {0}", servicioId);
        return servicioEntity.get();
    }

    // Actualizar un servicio
    @Transactional
    public ServicioEntity updateServicio(Long servicioId, ServicioEntity servicio) throws EntityNotFoundException {
        log.info("Inicia proceso de actualizar el servicio con id = {0}", servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException(SERVICE_NOT_FOUND);
        }
        servicio.setId(servicioEntity.get().getId());
        log.info("Termina proceso de actualizar el servicio con id = {0}", servicioId);
        return servicioRepository.save(servicio);
    }

    // Eliminar un servicio
    @Transactional
    public void deleteServicio(Long servicioId) throws EntityNotFoundException {
        log.info("Inicia proceso de borrar el servicio con id = {0}", servicioId);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioId);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException(SERVICE_NOT_FOUND);
        }
        servicioRepository.deleteById(servicioId);
        log.info("Termina proceso de borrar el servicio con id = {0}", servicioId);
    }
}
