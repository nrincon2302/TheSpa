package co.edu.uniandes.dse.thespa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(PackDeServiciosService.class)
public class PackDeServiciosTest {

    // Servicio que se va a probar
    @Autowired
    private PackDeServiciosService PackDeServiciosService;

    // TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // PodamFactory para generar datos aleatorios
    private PodamFactory factory = new PodamFactoryImpl();

    // Lista de sedes
    private List<SedeEntity> sedes = new ArrayList<>();

    // lista de packs de servicios
    private List<PackDeServiciosEntity> packs = new ArrayList<>();

    // lista de listas de servicios
    private List<List<ServicioEntity>> servicios = new ArrayList<>();

    // Configuracion inicial de la prueba
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    // Limpia las tablas que están implicadas en la prueba
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from PackDeServiciosEntity");
        entityManager.getEntityManager().createQuery("delete from SedeEntity");

    }

    // Inserta los datos de prueba en la lista de Sedes
    private void insertData() {
        // crea varios packs de servicios (packs)
        for (int i = 0; i < 3; i++) {
            PackDeServiciosEntity entity = factory.manufacturePojo(PackDeServiciosEntity.class);
            entityManager.persist(entity);
            packs.add(entity);
        }
        // se crea una lista de sedes
        for (int i = 0; i < 3; i++) {
            SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
            entityManager.persist(entity);
            sedes.add(entity);
        }
        // se le añaade una sede a cada pack de servicios
        for (int i = 0; i < packs.size(); i++) {
            packs.get(i).setSede(sedes.get(i));
        }
        // se crea una lista de listas de servicios
        for (int i = 0; i < 3; i++) {
            List<ServicioEntity> serviciosPack = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
                entityManager.persist(entity);
                serviciosPack.add(entity);
            }
            servicios.add(serviciosPack);
        }
        // se le añade una lista de servicios a cada pack de servicios
        for (int i = 0; i < packs.size(); i++) {
            packs.get(i).setServicios(servicios.get(i));
        }

    }

    // Prueba para crear un pack de servicios
    @Test
    void createPackDeServiciosTest() throws IllegalOperationException {
        PackDeServiciosEntity newEntity = packs.get(0);
        PackDeServiciosEntity result = PackDeServiciosService.createPackDeServicios(newEntity);
        assertNotNull(result);
        PackDeServiciosEntity entity = entityManager.find(PackDeServiciosEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getServicios(), entity.getServicios());
        assertEquals(newEntity.getSede(), entity.getSede());
        assertEquals(newEntity.getDescuento(), entity.getDescuento());
    }

    // prueba para crear un pack de servicios sin nombre
    @Test
    void createPackDeServiciosSinNombreTest() {
        PackDeServiciosEntity newEntity = packs.get(0);
        newEntity.setNombre(null);
        assertThrows(IllegalOperationException.class, () -> {
            PackDeServiciosService.createPackDeServicios(newEntity);
        });
    }

    // prueba para crear un pack de servicios sin sede
    @Test
    void createPackDeServiciosSinSedeTest() {
        PackDeServiciosEntity newEntity = packs.get(0);
        newEntity.setSede(null);
        assertThrows(IllegalOperationException.class, () -> {
            PackDeServiciosService.createPackDeServicios(newEntity);
        });
    }

    // prueba para crear un pack de servicios sin servicios
    @Test
    void createPackDeServiciosSinServiciosTest() {
        PackDeServiciosEntity newEntity = packs.get(0);
        newEntity.setServicios(null);
        assertThrows(IllegalOperationException.class, () -> {
            PackDeServiciosService.createPackDeServicios(newEntity);
        });
    }

    // prueba para crear un pack de servicios con servicios null
    @Test
    void createPackDeServiciosConServiciosNullTest() {
        PackDeServiciosEntity newEntity = packs.get(0);
        newEntity.setServicios(null);
        assertThrows(IllegalOperationException.class, () -> {
            PackDeServiciosService.createPackDeServicios(newEntity);
        });
    }

    // prueba obtener todos los packs de servicios
    @Test
    void getPacksDeServiciosTest() {
        List<PackDeServiciosEntity> list = PackDeServiciosService.getPacksDeServicios();
        assertEquals(packs.size(), list.size());
        for (PackDeServiciosEntity entity : list) {
            boolean found = false;
            for (PackDeServiciosEntity storedEntity : packs) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    // prueba para obtener un pack de servicios
    @Test
    void getPackDeServiciosTest() throws EntityNotFoundException {
        PackDeServiciosEntity entity = packs.get(0);
        PackDeServiciosEntity resultEntity = PackDeServiciosService.getPackDeServicios(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getServicios(), resultEntity.getServicios());
        assertEquals(entity.getSede(), resultEntity.getSede());
        assertEquals(entity.getDescuento(), resultEntity.getDescuento());
    }

    // prueba para obtener un pack de servicios que no existe
    @Test
    void getPackDeServiciosNoExisteTest() {
        assertThrows(EntityNotFoundException.class, () -> {
            PackDeServiciosService.getPackDeServicios(100L);
        });
    }

    // prueba para actualizar un pack de servicios
    @Test
    void updatePackDeServiciosTest() throws EntityNotFoundException {
        PackDeServiciosEntity entity = packs.get(0);
        PackDeServiciosEntity pojoEntity = factory.manufacturePojo(PackDeServiciosEntity.class);
        // le añade una sede al pojoEntity
        pojoEntity.setSede(sedes.get(1));
        // le añade una lista de servicios al pojoEntity
        pojoEntity.setServicios(servicios.get(1));

        pojoEntity.setId(entity.getId());

        PackDeServiciosService.updatePackDeServicios(pojoEntity.getId(), pojoEntity);

        PackDeServiciosEntity resp = entityManager.find(PackDeServiciosEntity.class, entity.getId());

        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getServicios(), resp.getServicios());
        assertEquals(pojoEntity.getSede(), resp.getSede());
        assertEquals(pojoEntity.getDescuento(), resp.getDescuento());
    }

    // prueba para actualizar un pack de servicios que no existe
    @Test
    void updatePackDeServiciosNoExisteTest() {
        PackDeServiciosEntity pojoEntity = factory.manufacturePojo(PackDeServiciosEntity.class);
        // le añade una sede al pojoEntity
        pojoEntity.setSede(sedes.get(1));
        // le añade una lista de servicios al pojoEntity
        pojoEntity.setServicios(servicios.get(1));
        assertThrows(EntityNotFoundException.class, () -> {
            PackDeServiciosService.updatePackDeServicios(100L, pojoEntity);
        });
    }

    // prueba para eliminar un pack de servicios
    @Test
    void deletePackDeServiciosTest() throws EntityNotFoundException {
        PackDeServiciosEntity entity = packs.get(0);
        PackDeServiciosService.deletePackDeServicios(entity.getId());
        PackDeServiciosEntity deleted = entityManager.find(PackDeServiciosEntity.class, entity.getId());
        assertNull(deleted);
    }

    // prueba para eliminar un pack de servicios que no existe
    @Test
    void deletePackDeServiciosNoExisteTest() {
        assertThrows(EntityNotFoundException.class, () -> {
            PackDeServiciosService.deletePackDeServicios(100L);
        });
    }

    // prueba para crear un pack con menos de 2 servicios, espera una
    // illegalOperationException
    @Test
    void createPackDeServiciosConMenosDeDosServiciosTest() {

        // crea un nuevo pack de servicios
        PackDeServiciosEntity newEntity = factory.manufacturePojo(PackDeServiciosEntity.class);
        entityManager.persist(newEntity);

        // le añade una sede al pojoEntity
        newEntity.setSede(sedes.get(1));

        // crea una lista de servicios con un solo servicio
        List<ServicioEntity> newServicio = new ArrayList<>();
        ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
        entityManager.persist(servicio);
        newServicio.add(servicio);

        // le añade un servicio al pojoEntity
        newEntity.setServicios(newServicio);

        assertThrows(IllegalOperationException.class, () -> {
            PackDeServiciosService.createPackDeServicios(newEntity);
        });
    }

    // prueba para revisar que el descuento de un pack no sea null
    @Test
    void createPackDeServiciosConDescuentoNullTest() {
        PackDeServiciosEntity newEntity = packs.get(0);
        newEntity.setDescuento(null);
        assertThrows(IllegalOperationException.class, () -> {
            PackDeServiciosService.createPackDeServicios(newEntity);
        });
    }

}
