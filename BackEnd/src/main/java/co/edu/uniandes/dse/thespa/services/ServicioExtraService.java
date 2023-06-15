package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import co.edu.uniandes.dse.thespa.entities.ServicioExtraEntity;
import co.edu.uniandes.dse.thespa.repositories.ServicioExtraRepository;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServicioExtraService {
    // String estático para eliminar el code smell en el mensaje de excepción y
    // reporte
    private static final String MENSAJE_SERVICIOEXTRA_NO_EXISTE = "El servicio extra con el id = {0} no existe";

    // Inyeccion de dependencias -> Repositorio ServicioExtra
    @Autowired
    ServicioExtraRepository servicioExtraRepository;

    // Método para la Creación de un servicio extra
    @Transactional
    public ServicioExtraEntity createServicioExtra(ServicioExtraEntity servicioExtraEntity)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia el proceso de creación del servicio extra");

        // Verifica que el nombre del servicio extra no esté vacío
        if ((servicioExtraEntity.getNombre() == null) || (servicioExtraEntity.getNombre().equals("")))
            throw new IllegalOperationException("El nombre del servicio extra no puede estar vacío");

        // Verifica que el precio del servicio extra no sea negativo
        if (servicioExtraEntity.getPrecio() < 0)
            throw new IllegalOperationException("El precio del servicio extra no puede ser negativo");

        // Verifica que la sede del servicio extra no sea null
        if (servicioExtraEntity.getSede() == null)
            throw new IllegalOperationException("La sede del servicio extra no puede ser null");

        // verifica que la descripcion del servicio extra no sea null ni vacia
        if (servicioExtraEntity.getDescripcion() == null || servicioExtraEntity.getDescripcion().equals(""))
            throw new IllegalOperationException("La descripcion del servicio extra no puede ser null ni vacia");

        // verifica que la disponibilidad del servicio extra no sea null
        if (servicioExtraEntity.getDisponible() == null)
            throw new IllegalOperationException("La disponibilidad del servicio extra no puede ser null");

        log.info("Termina proceso de creación del servicio extra");
        return servicioExtraRepository.save(servicioExtraEntity);
    }

    // Método para obtener todos los servicios extra
    @Transactional
    public List<ServicioExtraEntity> getServiciosExtras() {
        log.info("Inicia proceso de consultar todos los servicios extra");
        return servicioExtraRepository.findAll();
    }

    // Método para obtener un servicio extra por ID
    @Transactional
    public ServicioExtraEntity getServicioExtra(Long servicioExtraId) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar el servicio extra con id = {0}", servicioExtraId);
        Optional<ServicioExtraEntity> servicioExtraEntity = servicioExtraRepository.findById(servicioExtraId);
        if (servicioExtraEntity.isEmpty())
            throw new EntityNotFoundException(String.format(MENSAJE_SERVICIOEXTRA_NO_EXISTE, servicioExtraId));
        log.info("Termina proceso de consultar el servicio extra con id = {0}", servicioExtraId);
        return servicioExtraEntity.get();
    }

    // Método para actualizar un servicio extra
    @Transactional
    public ServicioExtraEntity updateServicioExtra(Long servicioExtraId, ServicioExtraEntity servicioExtra)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar el servicio extra con id = {0}", servicioExtraId);
        Optional<ServicioExtraEntity> servicioExtraEntity = servicioExtraRepository.findById(servicioExtraId);
        if (servicioExtraEntity.isEmpty())
            throw new EntityNotFoundException(String.format(MENSAJE_SERVICIOEXTRA_NO_EXISTE, servicioExtraId));

        if ((servicioExtra.getNombre() == null) || (servicioExtra.getNombre().equals("")))
            throw new IllegalOperationException("El nombre del servicio extra no puede estar vacío");

        servicioExtra.setId(servicioExtraEntity.get().getId());
        log.info("Termina proceso de actualizar el servicio extra con id = {0}", servicioExtraId);
        return servicioExtraRepository.save(servicioExtra);
    }

    // Método para borrar un servicio extra
    @Transactional
    public void deleteServicioExtra(Long servicioExtraId) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de borrar el servicio extra con id = {0}", servicioExtraId);
        Optional<ServicioExtraEntity> servicioExtraEntity = servicioExtraRepository.findById(servicioExtraId);
        if (servicioExtraEntity.isEmpty())
            throw new EntityNotFoundException("No se encontró el servicio extra con id = " + servicioExtraId);

        servicioExtraRepository.deleteById(servicioExtraId);
        log.info("Termina proceso de borrar el servicio extra con id = {0}", servicioExtraId);
    }
}
