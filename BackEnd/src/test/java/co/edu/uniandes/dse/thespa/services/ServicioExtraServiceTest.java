package co.edu.uniandes.dse.thespa.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.entities.ServicioExtraEntity;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;

@DataJpaTest
@Transactional
@Import(ServicioExtraService.class)
public class ServicioExtraServiceTest {

    @Autowired
    private ServicioExtraService servicioExtraService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<ServicioExtraEntity> servicioExtraList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    // Limpia las tablas que están implicadas en la prueba
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ServicioExtraEntity");
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
    }

    // Inserta los datos de prueba en la lista de Servicio Extra
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ServicioExtraEntity entity = factory.manufacturePojo(ServicioExtraEntity.class);
            entityManager.persist(entity);
            servicioExtraList.add(entity);
        }
        // crea una sede para asociarla a los servicios extra
        SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
        entityManager.persist(sede);
        // Asocia la sede a los servicios extra
        for (ServicioExtraEntity sExtra : servicioExtraList) {
            sExtra.setSede(sede);
        }
    }

    @Test
    // crea un servicio extra con todas las condiciones necesarias
    void testCreateServicioExtra() throws EntityNotFoundException, IllegalOperationException {
        ServicioExtraEntity newEntity = factory.manufacturePojo(ServicioExtraEntity.class);
        // añade una sede al servicio extra
        newEntity.setSede(servicioExtraList.get(0).getSede());
        ServicioExtraEntity result = servicioExtraService.createServicioExtra(newEntity);
        result.setNombre("nombre de prueba");
        assertNotNull(result);
        ServicioExtraEntity entity = entityManager.find(ServicioExtraEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        assertEquals(newEntity.getPrecio(), entity.getPrecio());
        assertEquals(newEntity.getImagen(), entity.getImagen());
        assertEquals(newEntity.getDisponible(), entity.getDisponible());
        assertEquals(newEntity.getSede(), newEntity.getSede());
    }

    @Test
    // Crea un servicio extra cuyo nombre es una cadena vacía -> Operación ilegal
    void testCreateServicioExtraSinNombre() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioExtraEntity newEntity = factory.manufacturePojo(ServicioExtraEntity.class);
            newEntity.setNombre("");
            servicioExtraService.createServicioExtra(newEntity);
        });
    }

    @Test
    // Crea un servicio extra cuyo nombre es nulo -> Operación ilegal
    void testCreateServicioExtraSinNombre2() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioExtraEntity newEntity = factory.manufacturePojo(ServicioExtraEntity.class);
            newEntity.setNombre(null);
            servicioExtraService.createServicioExtra(newEntity);
        });
    }

    @Test
    // Crea un servicio extra cuyo precio es negativo -> Operación ilegal
    void testCreateServicioExtraPrecioNegativo() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioExtraEntity newEntity = factory.manufacturePojo(ServicioExtraEntity.class);
            newEntity.setPrecio(-7.0);
            servicioExtraService.createServicioExtra(newEntity);
        });
    }

    @Test
    // Crea un servicio extra sin sede -> Operación ilegal
    void testCreateServicioExtraSinSede() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioExtraEntity newEntity = factory.manufacturePojo(ServicioExtraEntity.class);
            newEntity.setSede(null);
            servicioExtraService.createServicioExtra(newEntity);
        });
    }

    @Test
    // Crea un servicio extra cuya descripción es nula -> Operación ilegal
    void testCreateServicioExtraSinDescripcion() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioExtraEntity newEntity = factory.manufacturePojo(ServicioExtraEntity.class);
            newEntity.setDescripcion(null);
            servicioExtraService.createServicioExtra(newEntity);
        });
    }

    @Test
    // Crea un servicio extra cuya descripcion es vacía -> Operación ilegal
    void testCreateServicioExtraSinDescripcion2() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioExtraEntity newEntity = factory.manufacturePojo(ServicioExtraEntity.class);
            newEntity.setDescripcion("");
            servicioExtraService.createServicioExtra(newEntity);
        });
    }

    @Test
    // Crea un servicio extra cuya disponibilidad es nulo -> Operación ilegal
    void testCreateServicioExtraSinDisponibilidad() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioExtraEntity newEntity = factory.manufacturePojo(ServicioExtraEntity.class);
            newEntity.setDisponible(null);
            servicioExtraService.createServicioExtra(newEntity);
        });
    }

    @Test
    // Obtiene todos los servicios extra existentes en la base de datos, asumiendo que hay 3 servicios extra creados
    void testGetServiciosExtra() {
        List<ServicioExtraEntity> list = servicioExtraService.getServiciosExtras();
        assertEquals(servicioExtraList.size(), list.size());
        for (ServicioExtraEntity entity : list) {
            boolean found = false;
            for (ServicioExtraEntity storedEntity : servicioExtraList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    // Obtiene un servicio extra con ID dado, asumiendo todas las condiciones de un servicio extra
    void testGetServicioExtra() throws EntityNotFoundException {
        ServicioExtraEntity entity = servicioExtraList.get(0);
        ServicioExtraEntity resultEntity = servicioExtraService.getServicioExtra(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        assertEquals(entity.getPrecio(), resultEntity.getPrecio());
        assertEquals(entity.getImagen(), resultEntity.getImagen());
        assertEquals(entity.getDisponible(), resultEntity.getDisponible());
        assertEquals(entity.getSede(), resultEntity.getSede());
    }

    @Test
    // Obtiene un servicio extra con ID inválido -> Operación inválida
    void testGetServicioExtraNoExistente() {
        assertThrows(EntityNotFoundException.class, () -> {
            servicioExtraService.getServicioExtra(0L);
        });
    }

    @Test
    // Actualiza el servicio extra con ID dado, asumiendo todas las condiciones de un servicio extra
    void testUpdateServicioExtra() throws EntityNotFoundException, IllegalOperationException {
        ServicioExtraEntity entity = servicioExtraList.get(0);
        ServicioExtraEntity pojoEntity = factory.manufacturePojo(ServicioExtraEntity.class);
        pojoEntity.setId(entity.getId());
        // añade una sede a la entidad
        pojoEntity.setSede(servicioExtraList.get(0).getSede());
        servicioExtraService.updateServicioExtra(entity.getId(), pojoEntity);
        ServicioExtraEntity resp = entityManager.find(ServicioExtraEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        assertEquals(pojoEntity.getPrecio(), resp.getPrecio());
        assertEquals(pojoEntity.getImagen(), resp.getImagen());
        assertEquals(pojoEntity.getDisponible(), resp.getDisponible());
        assertEquals(pojoEntity.getSede(), resp.getSede());
    }

    @Test
    // Actualiza un servicio extra con ID inválido -> Operación inválida
    void testUpdateServicioExtraIDInvalido() {
        assertThrows(EntityNotFoundException.class, () -> {
            ServicioExtraEntity pojoEntity = factory.manufacturePojo(ServicioExtraEntity.class);
            pojoEntity.setId(0L);
            servicioExtraService.updateServicioExtra(0L, pojoEntity);
        });
    }

    @Test
    // Actualiza servicio extra con nombre cadena vacía -> Operación inválida
    void testUpdateServicioExtraSinNombre() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioExtraEntity entity = servicioExtraList.get(0);
            ServicioExtraEntity pojoEntity = factory.manufacturePojo(ServicioExtraEntity.class);
            pojoEntity.setNombre("");
            pojoEntity.setId(entity.getId());
            servicioExtraService.updateServicioExtra(entity.getId(), pojoEntity);
        });
    }

    @Test
    // actualiza servicio extra con nombre nulo -> Operación inválida
    void testUpdateServicioExtraSinNombre2() {
        assertThrows(IllegalOperationException.class, () -> {
            ServicioExtraEntity entity = servicioExtraList.get(0);
            ServicioExtraEntity pojoEntity = factory.manufacturePojo(ServicioExtraEntity.class);
            pojoEntity.setNombre(null);
            pojoEntity.setId(entity.getId());
            servicioExtraService.updateServicioExtra(entity.getId(), pojoEntity);
        });
    }

    @Test
    // Elimina un servicio extra con ID dado, asumiendo todas las condiciones de un servicio extra
    void testDeleteServicioExtra() throws EntityNotFoundException, IllegalOperationException {
        ServicioExtraEntity entity = servicioExtraList.get(0);
        servicioExtraService.deleteServicioExtra(entity.getId());
        ServicioExtraEntity deleted = entityManager.find(ServicioExtraEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    // eliminar un servicio extra con un índice incorrecto/no existente -> Operación inválida
    void testDeleteServicioExtraIDInvalido() {
        assertThrows(EntityNotFoundException.class, () -> {
            servicioExtraService.deleteServicioExtra(0L);
        });
    }
}
