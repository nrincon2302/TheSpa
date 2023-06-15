package co.edu.uniandes.dse.thespa.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.entities.PackDeServiciosEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.ServicioRepository;
import co.edu.uniandes.dse.thespa.repositories.PackDeServiciosRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ServicioAndPackDeServiciosService {
    // String estático para eliminar el code smell en el mensaje de excepción y
    // reporte
    private static final String MENSAJE_PACK_NO_EXISTE = "El pack de servicios con el id = {0} no existe";
    private static final String MENSAJE_SERVICIO_NO_EXISTE = "El servicio con el id = {0} no existe";
    private static final String MENSAJE_PACK_NOTIN_SERVICIO = "El pack de servicios con el id = {1} no esta en el servicio con el id = {0}";

    // Inyeccion de dependencias -> Repositorio Servicios
    @Autowired
    ServicioRepository servicioRepository;

    // Inyeccion de dependencias -> Repositorio PackDeServicios
    @Autowired
    PackDeServiciosRepository packDeServiciosRepository;

    // Creación de packs de servicios
    @Transactional
    public PackDeServiciosEntity addPackDeServicios(Long servicioID, long packDeServiciosID)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de agregar un pack de servicios al servicio con id = {0}", servicioID);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioID);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_SERVICIO_NO_EXISTE, servicioID));
        }
        Optional<PackDeServiciosEntity> packDeServiciosEntity = packDeServiciosRepository.findById(packDeServiciosID);
        if (packDeServiciosEntity.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_PACK_NO_EXISTE, packDeServiciosID));
        }

        if (servicioEntity.get().getPacksDeServicios().contains(packDeServiciosEntity.get())) {
            throw new IllegalOperationException("El pack de servicios con el id = " + packDeServiciosID
                    + " ya está asociado al servicio con el id = " + servicioID);
        }
        servicioEntity.get().getPacksDeServicios().add(packDeServiciosEntity.get());
        log.info("Termina proceso de agregar un pack de servicios al servicio con id = {0}", servicioID);

        return packDeServiciosEntity.get();
    }

    // Obtener todos los packs de servicios
    @Transactional
    public PackDeServiciosEntity getPack(Long servicioID, Long packid)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Consultando el pack de servicios con id = {} del servicio con id = {}", packid, servicioID);

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

        if (!serviciosBuscados.get().getPacksDeServicios().contains(packsBuscados.get())) {
            throw new IllegalOperationException(String.format(MENSAJE_PACK_NOTIN_SERVICIO, servicioID, packid));
        }

        log.info("Pack de Servicios encontrado");

        // Retorna el servicio
        return packsBuscados.get();
    }

    // Obtener todos los packs de servicios
    @Transactional
    public List<PackDeServiciosEntity> getPacksDeServicios(Long servicioID) throws EntityNotFoundException {
        log.info("Inicia proceso de consultar los packs de servicios del servicio con id = {0}", servicioID);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioID);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_SERVICIO_NO_EXISTE, servicioID));
        }
        log.info("Termina proceso de consultar los packs de servicios del servicio con id = {0}", servicioID);
        return servicioEntity.get().getPacksDeServicios();
    }

    // Eliminar un pack de servicios
    @Transactional
    public PackDeServiciosEntity removePackDeServicios(Long servicioID, long packDeServiciosID)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de remover un pack de servicios del servicio con id = {0}", servicioID);
        Optional<ServicioEntity> servicioEntity = servicioRepository.findById(servicioID);
        if (servicioEntity.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_SERVICIO_NO_EXISTE, servicioID));
        }
        Optional<PackDeServiciosEntity> packDeServiciosEntity = packDeServiciosRepository.findById(packDeServiciosID);
        if (packDeServiciosEntity.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_PACK_NO_EXISTE, packDeServiciosID));
        }

        if (!servicioEntity.get().getPacksDeServicios().contains(packDeServiciosEntity.get())) {
            throw new IllegalOperationException("El pack de servicios con el id = " + packDeServiciosID
                    + " no está asociado al servicio con el id = " + servicioID);
        }
        servicioEntity.get().getPacksDeServicios().remove(packDeServiciosEntity.get());
        log.info("Termina proceso de remover un pack de servicios del servicio con id = {0}", servicioID);

        return packDeServiciosEntity.get();
    }

    // Actualizar los packs de servicios de un servicio
    @Transactional
    public List<PackDeServiciosEntity> updatePacks(Long servicioID, List<PackDeServiciosEntity> packs)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Actualizando los packs de servicios del servicio con id = {}", servicioID);

        // Busca el servicio
        Optional<ServicioEntity> serviciosBuscados = servicioRepository.findById(servicioID);
        if (serviciosBuscados.isEmpty()) {
            throw new EntityNotFoundException(String.format(MENSAJE_SERVICIO_NO_EXISTE, servicioID));
        }

        // por cada pack en la lista de packs, verifica que exista
        for (PackDeServiciosEntity pack : packs) {
            Optional<PackDeServiciosEntity> packsBuscados = packDeServiciosRepository.findById(pack.getId());
            if (packsBuscados.isEmpty()) {
                throw new EntityNotFoundException(String.format(MENSAJE_PACK_NO_EXISTE, pack.getId()));
            }
        }

        // crea una lista de packs de servicios
        List<PackDeServiciosEntity> packsDeServicios = new ArrayList<>();
        // por cada pack en la lista de packs, lo agrega a la lista de packs de
        // servicios
        for (PackDeServiciosEntity pack : packs) {
            Optional<PackDeServiciosEntity> packsBuscados = packDeServiciosRepository.findById(pack.getId());
            if (packsBuscados.isPresent()) {
                packsDeServicios.add(packsBuscados.get());
            }
        }

        // actualiza la lista de packs del servicio
        serviciosBuscados.get().setPacksDeServicios(packsDeServicios);

        log.info("Packs de servicios del servicio actualizados");

        return serviciosBuscados.get().getPacksDeServicios();
    }

}
