package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import co.edu.uniandes.dse.thespa.entities.PackDeServiciosEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.PackDeServiciosRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PackDeServiciosService {
    // String estático para eliminar el code smell en el mensaje de excepción y
    // reporte
    private static final String MENSAJE_PACK_NO_EXISTE = "El pack de servicios con el id = {0} no existe";

    // Inyeccion de dependencias -> Repositorio PackDeServicios
    @Autowired
    private PackDeServiciosRepository packDeServiciosRepository;

    // creación de packs de servicios
    @Transactional
    public PackDeServiciosEntity createPackDeServicios(PackDeServiciosEntity packDeServicios)
            throws IllegalOperationException {
        log.info("Creando un pack de servicios nuevo");
        // revisa que el nombre del pack de servicios no sea null
        if (packDeServicios.getNombre() == null) {
            throw new IllegalOperationException("El nombre del pack de servicios no puede ser null");
        }
        // revisa que la sede del pack de servicios no sea null
        if (packDeServicios.getSede() == null) {
            throw new IllegalOperationException("La sede del pack de servicios no puede ser null");
        }
        // revia que los servicios del pack de servicios no sean null
        if (packDeServicios.getServicios() == null) {
            throw new IllegalOperationException("Los servicios del pack de servicios no pueden ser null");
        }
        // revisa que el pack de servicios tenga al menos 2 servicios
        if (packDeServicios.getServicios().size() < 2) {
            throw new IllegalOperationException("El pack de servicios debe tener al menos 2 servicios");
        }

        // revisa que el descuento del pack de servicios no sea null
        if (packDeServicios.getDescuento() == null) {
            throw new IllegalOperationException("El descuento del pack de servicios no puede ser null");
        }

        return packDeServiciosRepository.save(packDeServicios);
    }

    // obtener todos los packs de servicios
    @Transactional
    public List<PackDeServiciosEntity> getPacksDeServicios() {
        log.info("Consultando todos los packs de servicios");
        return packDeServiciosRepository.findAll();
    }

    // obtener un pack de servicios
    @Transactional
    public PackDeServiciosEntity getPackDeServicios(Long id) throws EntityNotFoundException {
        log.info("Consultando el pack de servicios con id = {}", id);
        Optional<PackDeServiciosEntity> packsBuscados = packDeServiciosRepository.findById(id);
        if (packsBuscados.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_PACK_NO_EXISTE, id));
        }
        log.info("Pack de servicios encontrado");
        return packsBuscados.get();

    }

    // actualizar un pack de servicios
    @Transactional
    public PackDeServiciosEntity updatePackDeServicios(Long id, PackDeServiciosEntity packDeServicios)
            throws EntityNotFoundException {

        log.info("Actualizando el pack de servicios con id = {}", id);
        Optional<PackDeServiciosEntity> packsBuscados = packDeServiciosRepository.findById(id);
        if (packsBuscados.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_PACK_NO_EXISTE, id));
        }

        packDeServicios.setId(id);
        log.info("Pack de servicios actualizado");
        return packDeServiciosRepository.save(packDeServicios);

    }

    // borrar un pack de servicios
    @Transactional
    public void deletePackDeServicios(Long id) throws EntityNotFoundException {
        log.info("Borrando el pack de servicios con id = {}", id);
        Optional<PackDeServiciosEntity> packsBuscados = packDeServiciosRepository.findById(id);
        if (packsBuscados.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_PACK_NO_EXISTE, id));
        }
        packDeServiciosRepository.deleteById(id);
        log.info("Pack de servicios borrado");
    }

}