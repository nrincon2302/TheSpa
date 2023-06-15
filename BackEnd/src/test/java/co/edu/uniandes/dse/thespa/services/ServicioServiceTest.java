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
import co.edu.uniandes.dse.thespa.entities.*;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
@Import(ServicioService.class)

public class ServicioServiceTest {

    // Servicio que se va a probar
    @Autowired
    private ServicioService servicioService;

    // TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // PodamFactory para generar datos aleatorios
    private PodamFactory factory = new PodamFactoryImpl();

    // Listas de servicios, packs, sedes y trabajadores
    private List<ServicioEntity> servicioList = new ArrayList<>();
    private List<PackDeServiciosEntity> packList = new ArrayList<>();
    private List<SedeEntity> sedeList = new ArrayList<>();
    private List<TrabajadorEntity> trabajadorList = new ArrayList<>();

    /**
     * Configuración inicial de la prueba.
     */
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    /*
     * Limpia las tablas que están implicadas en la prueba.
     */

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ServicioEntity");
    }

    /*
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
            entityManager.persist(servicio);
            servicioList.add(servicio);
        }

        for (int i = 0; i < 3; i++) {
            PackDeServiciosEntity pack = factory.manufacturePojo(PackDeServiciosEntity.class);
            entityManager.persist(pack);
            packList.add(pack);
            servicioList.get(i).getPacksDeServicios().add(pack);
        }

        for (int i = 0; i < 3; i++) {
            SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
            entityManager.persist(sede);
            sedeList.add(sede);
            servicioList.get(i).setSede(sede);
        }

        for (int i = 0; i < 3; i++) {
            TrabajadorEntity trabajador = factory.manufacturePojo(TrabajadorEntity.class);
            entityManager.persist(trabajador);
            trabajadorList.add(trabajador);
            servicioList.get(i).getTrabajadores().add(trabajador);
        }

    }

    /*
     * Prueba para crear un servicio.
     */
    @Test
    void testCreateServicio() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity newEntity = servicioList.get(0);
        ServicioEntity result = servicioService.createServicio(newEntity);
        assertNotNull(result);

        ServicioEntity entity = entityManager.find(ServicioEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getDuracion(), entity.getDuracion());
        assertEquals(newEntity.getRestricciones(), entity.getRestricciones());
        assertEquals(newEntity.getDisponible(), entity.getDisponible());
        assertEquals(newEntity.getHoraInicio(), entity.getHoraInicio());
        assertEquals(newEntity.getPrecio(), entity.getPrecio());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getSede(), entity.getSede());
        assertEquals(newEntity.getTrabajadores(), entity.getTrabajadores());
        assertEquals(newEntity.getPacksDeServicios(), entity.getPacksDeServicios());

    }

    /*
     * Prueba para crear un servicio sin nombre.
     */
    @Test
    void testCreateServicioSinNombre() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            newEntity.setNombre(null);
            servicioService.createServicio(newEntity);
        });
    }

    @Test
    void testCreateServicioSinNombre2() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            newEntity.setNombre("");
            servicioService.createServicio(newEntity);
        });
    }

    @Test
    void testCreateServicioSinDescripcion() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            newEntity.setDescripcion(null);
            servicioService.createServicio(newEntity);
        });
    }

    @Test
    void testCreateServicioSinDescripcion2() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            newEntity.setDescripcion("");
            servicioService.createServicio(newEntity);
        });
    }

    @Test
    void testCreateServicioSinPrecio() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            newEntity.setPrecio(null);
            servicioService.createServicio(newEntity);
        });
    }

    /*
     * Prueba para crear un servicio sin descripcion.
     */
    @Test
    void testCreateServicioSinSede() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            newEntity.setSede(null);
            servicioService.createServicio(newEntity);
        });
    }

    /*
     * Prueba para crear un servicio sin trabajadores.
     */
    @Test
    void testCreateServicioSinTrabajadores() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            newEntity.setTrabajadores(null);
            servicioService.createServicio(newEntity);
        });
    }

    /*
     * Prueba para crear un servicio sin packs.
     */
    @Test
    void testCreateServicioSinPacks() {
        ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
        newEntity.setPacksDeServicios(null);
        assertThrows(IllegalOperationException.class, () -> {
            servicioService.createServicio(newEntity);
        });
    }

    /*
     * Prueba para crear un servicio sin precio.
     */
    @Test
    void testCreateServicioSinDuracion() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            newEntity.setDuracion(null);
            servicioService.createServicio(newEntity);
        });
    }

    /*
     * Prueba para crear un servicio sin precio.
     */
    @Test
    void testCreateServicioSinRestricciones() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            newEntity.setRestricciones(null);
            servicioService.createServicio(newEntity);
        });
    }

    @Test
    void testCreateServicioSinRestricciones2() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            newEntity.setRestricciones("");
            servicioService.createServicio(newEntity);
        });
    }

    /*
     * Prueba para crear un servicio sin precio.
     */
    @Test
    void testCreateServicioSinDisponible() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            newEntity.setDisponible(null);
            servicioService.createServicio(newEntity);
        });
    }

    /*
     * Prueba para crear un servicio sin precio.
     */
    @Test
    void testCreateServicioSinHoraInicio() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioEntity newEntity = factory.manufacturePojo(ServicioEntity.class);
            newEntity.setHoraInicio(null);
            servicioService.createServicio(newEntity);
        });
    }

    /*
     * Prueba para crear un servicio sin precio.
     */
    @Test
    void testGetServicios() {
        List<ServicioEntity> list = servicioService.getServicios();
        assertEquals(servicioList.size(), list.size());
        for (ServicioEntity entity : list) {
            boolean found = false;
            for (ServicioEntity storedEntity : servicioList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    /*
     * Prueba para consultar un servicio.
     */
    @Test
    void testGetServicio() throws EntityNotFoundException {
        ServicioEntity entity = servicioList.get(0);
        ServicioEntity resultEntity = servicioService.getServicio(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        assertEquals(entity.getPrecio(), resultEntity.getPrecio());
        assertEquals(entity.getSede(), resultEntity.getSede());
        assertEquals(entity.getPacksDeServicios(), resultEntity.getPacksDeServicios());
        assertEquals(entity.getTrabajadores(), resultEntity.getTrabajadores());
        assertEquals(entity.getDuracion(), resultEntity.getDuracion());
        assertEquals(entity.getRestricciones(), resultEntity.getRestricciones());
        assertEquals(entity.getDisponible(), resultEntity.getDisponible());
        assertEquals(entity.getHoraInicio(), resultEntity.getHoraInicio());
    }

    /*
     * Prueba para consultar un servicio que no existe.
     */
    @Test
    void testGetInvalidServicio() {
        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.getServicio(0L);
        });
    }

    /*
     * Prueba para actualizar un servicio.
     */
    @Test
    void testUpdateServicio() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity entity = servicioList.get(0);
        ServicioEntity pojoEntity = factory.manufacturePojo(ServicioEntity.class);
        pojoEntity.setId(entity.getId());
        servicioService.updateServicio(entity.getId(), pojoEntity);

        ServicioEntity resp = entityManager.find(ServicioEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        assertEquals(pojoEntity.getPrecio(), resp.getPrecio());
        assertEquals(pojoEntity.getSede(), resp.getSede());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getPacksDeServicios(), resp.getPacksDeServicios());
        assertEquals(pojoEntity.getTrabajadores(), resp.getTrabajadores());
        assertEquals(pojoEntity.getDuracion(), resp.getDuracion());
        assertEquals(pojoEntity.getRestricciones(), resp.getRestricciones());
        assertEquals(pojoEntity.getDisponible(), resp.getDisponible());
        assertEquals(pojoEntity.getHoraInicio(), resp.getHoraInicio());
    }

    /*
     * Prueba para actualizar un servicio sin nombre.
     */
    @Test
    void testUpdateInvalidServicio() {
        ServicioEntity pojoEntity = factory.manufacturePojo(ServicioEntity.class);
        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.updateServicio(0L, pojoEntity);
        });
    }

    /*
     * Prueba para actualizar un servicio sin nombre.
     */
    @Test
    void testDeleteServicio() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity entity = servicioList.get(1);
        servicioService.deleteServicio(entity.getId());
        ServicioEntity deleted = entityManager.find(ServicioEntity.class, entity.getId());
        assertNull(deleted);
    }

    /*
     * Prueba para actualizar un servicio sin nombre.
     */
    @Test
    void testDeleteInvalidServicio() {
        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.deleteServicio(0L);
        });
    }
}
