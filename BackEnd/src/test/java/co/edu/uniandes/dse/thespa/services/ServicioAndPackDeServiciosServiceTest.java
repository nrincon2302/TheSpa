package co.edu.uniandes.dse.thespa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.thespa.entities.PackDeServiciosEntity;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import({ ServicioAndPackDeServiciosService.class })
public class ServicioAndPackDeServiciosServiceTest {

    @Autowired
    private ServicioAndPackDeServiciosService servicioService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<ServicioEntity> servicios = new ArrayList<>();

    private List<PackDeServiciosEntity> packs = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.clear();
        entityManager.getEntityManager().createQuery("delete from ServicioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from PackDeServiciosEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
            entityManager.persist(servicio);
            servicios.add(servicio);
            for (int j = 0; j < 3; j++) {
                PackDeServiciosEntity pack = factory.manufacturePojo(PackDeServiciosEntity.class);
                entityManager.persist(pack);
                packs.add(pack);
                servicio.getPacksDeServicios().add(pack);
            }
        }

    }

    @Test
    void addPackTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        PackDeServiciosEntity pack = factory.manufacturePojo(PackDeServiciosEntity.class);
        entityManager.persist(pack);
        packs.add(pack);

        PackDeServiciosEntity result = servicioService.addPackDeServicios(servicio.getId(), pack.getId());

        assertNotNull(result);
        assertEquals(pack, result);
    }

    @Test
    void addPackEmptyServicioTest() throws EntityNotFoundException, IllegalOperationException {
        Long id = 0L;
        PackDeServiciosEntity pack = factory.manufacturePojo(PackDeServiciosEntity.class);
        entityManager.persist(pack);
        packs.add(pack);

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.addPackDeServicios(id, pack.getId());
        });
    }

    @Test
    void addPackEmptyPackTest() throws EntityNotFoundException, IllegalOperationException {
        Long id = 0L;
        ServicioEntity servicio = servicios.get(0);

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.addPackDeServicios(servicio.getId(), id);
        });
    }

    @Test
    void addPackAlreadyInServicioTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        PackDeServiciosEntity pack = packs.get(0);

        assertThrows(IllegalOperationException.class, () -> {
            servicioService.addPackDeServicios(servicio.getId(), pack.getId());
        });
    }

    @Test
    void getPackEmptyPackTest() throws EntityNotFoundException, IllegalOperationException {
        Long id = 0L;
        ServicioEntity servicio = servicios.get(0);

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.addPackDeServicios(servicio.getId(), id);
        });
    }

    @Test
    void getPackEmptyServicioTest() throws EntityNotFoundException, IllegalOperationException {
        Long id = 0L;
        PackDeServiciosEntity pack = packs.get(0);

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.addPackDeServicios(id, pack.getId());
        });
    }

    @Test
    void getPackNotInServicioTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        PackDeServiciosEntity pack = factory.manufacturePojo(PackDeServiciosEntity.class);
        entityManager.persist(pack);
        packs.add(pack);

        assertThrows(IllegalOperationException.class, () -> {
            servicioService.getPack(servicio.getId(), pack.getId());
        });
    }

    @Test
    void getPackTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        PackDeServiciosEntity pack = packs.get(0);

        PackDeServiciosEntity result = servicioService.getPack(servicio.getId(), pack.getId());

        assertNotNull(result);
        assertEquals(pack, result);
    }

    @Test
    void getPacksEmptyServicioTest() throws EntityNotFoundException {
        Long id = 0L;

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.getPacksDeServicios(id);
        });
    }

    @Test
    void getPacksTest() throws EntityNotFoundException {
        ServicioEntity servicio = servicios.get(0);

        List<PackDeServiciosEntity> result = servicioService.getPacksDeServicios(servicio.getId());

        assertNotNull(result);
        assertEquals(servicio.getPacksDeServicios(), result);
    }

    @Test
    void removePackEmptyServicioTest() throws EntityNotFoundException, IllegalOperationException {
        Long id = 0L;
        PackDeServiciosEntity pack = packs.get(0);

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.removePackDeServicios(id, pack.getId());
        });
    }

    @Test
    void removePackEmptyPackTest() throws EntityNotFoundException, IllegalOperationException {
        Long id = 0L;
        ServicioEntity servicio = servicios.get(0);

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.removePackDeServicios(servicio.getId(), id);
        });
    }

    @Test
    void removePackNotInServicioTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        PackDeServiciosEntity pack = factory.manufacturePojo(PackDeServiciosEntity.class);
        entityManager.persist(pack);
        packs.add(pack);

        assertThrows(IllegalOperationException.class, () -> {
            servicioService.removePackDeServicios(servicio.getId(), pack.getId());
        });
    }

    @Test
    void removePackTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        PackDeServiciosEntity pack = packs.get(0);

        servicioService.removePackDeServicios(servicio.getId(), pack.getId());

        List<PackDeServiciosEntity> result = servicioService.getPacksDeServicios(servicio.getId());
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void updatePackEmptyServiceTest() throws EntityNotFoundException, IllegalOperationException {
        Long id = 0L;

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.updatePacks(id, packs);
        });
    }

    // @Test
    // NO PUEDO HACER EL TEST DE LOS PACKS
    // void updatePackEmptyPacksTest() throws EntityNotFoundException, IllegalOperationException {
    //     ServicioEntity servicio = servicios.get(0);

    //     List<PackDeServiciosEntity> result = new ArrayList<PackDeServiciosEntity>();
    //     result.add(factory.manufacturePojo(PackDeServiciosEntity.class));

    //     assertThrows(IllegalOperationException.class, () -> {
    //         servicioService.updatePacks(servicio.getId(), result);
    //     });
    // }


    @Test
    void updatePackTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);

        servicioService.updatePacks(servicio.getId(), packs);

        List<PackDeServiciosEntity> packs2 = servicioService.getPacksDeServicios(servicio.getId());
        assertNotNull(packs2);
        assertEquals(packs.size(), packs2.size());
    }
}
