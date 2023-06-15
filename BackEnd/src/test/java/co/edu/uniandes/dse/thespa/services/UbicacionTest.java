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
import co.edu.uniandes.dse.thespa.entities.UbicacionEntity;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;

@DataJpaTest
@Transactional
@Import(UbicacionService.class)
public class UbicacionTest {

    @Autowired
    private UbicacionService ubicacionService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<UbicacionEntity> ubicacionList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    // Limpia las tablas que están implicadas en la prueba
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from UbicacionEntity");
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
    }

    // Inserta los datos de prueba en la lista de Servicio Extra
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            // crea nuevas ubicaciones con datos aleatorios
            // su latitud debe ser un numero entre -90 y 90
            // su longitud debe ser un numero entre -180 y 180

            UbicacionEntity ubicacion = factory.manufacturePojo(UbicacionEntity.class);
            ubicacion.setLatitud((double) (Math.random() * 180 - 90));
            ubicacion.setLongitud((double) (Math.random() * 360 - 180));
            entityManager.persist(ubicacion);
            ubicacionList.add(ubicacion);
        }
        // crea una sede para asociarla a las ubicaciones
        SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
        entityManager.persist(sede);
        // Asocia la sede a las ubicaciones
        for (UbicacionEntity ubi : ubicacionList) {
            ubi.setSede(sede);
        }
    }

    @Test
    // crea un ubicacion con todas las condiciones necesarias
    void testCreateUbicacion() throws EntityNotFoundException, IllegalOperationException {
        UbicacionEntity newEntity = ubicacionList.get(0);
        // añade una sede al ubicacion
        newEntity.setSede(ubicacionList.get(0).getSede());
        UbicacionEntity result = ubicacionService.createUbicacion(newEntity);
        assertNotNull(result);
        UbicacionEntity entity = entityManager.find(UbicacionEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        // compara los atributos del ubicacion
        assertEquals(newEntity.getLatitud(), entity.getLatitud());
        assertEquals(newEntity.getLongitud(), entity.getLongitud());
        assertEquals(newEntity.getCiudad(), entity.getCiudad());
        assertEquals(newEntity.getDireccion(), entity.getDireccion());
        assertEquals(newEntity.getSede(), newEntity.getSede());
    }

    // Crea una ubicacion cuya latitud es null, espera una excepción
    // IllegalOperationException
    @Test
    void testCreateUbicacionLatitudNull() {
        UbicacionEntity newEntity = ubicacionList.get(0);
        newEntity.setLatitud(null);
        assertThrows(IllegalOperationException.class, () -> {
            ubicacionService.createUbicacion(newEntity);
        });
    }

    // crea una ubicacion cuya latitud es menor a -90, espera una excepción
    // IllegalOperationException
    @Test
    void testCreateUbicacionLatitudMenor() {
        UbicacionEntity newEntity = ubicacionList.get(0);
        newEntity.setLatitud(-91.0);
        assertThrows(IllegalOperationException.class, () -> {
            ubicacionService.createUbicacion(newEntity);
        });
    }

    // crea una ubicacion cuya latitud es mayor a 90, espera una excepción
    // IllegalOperationException
    @Test
    void testCreateUbicacionLatitudMayor() {
        UbicacionEntity newEntity = ubicacionList.get(0);
        newEntity.setLatitud(91.0);
        assertThrows(IllegalOperationException.class, () -> {
            ubicacionService.createUbicacion(newEntity);
        });
    }

    // crea una ubicacion cuya longitud es menor a -180, espera una excepción
    // IllegalOperationException
    @Test
    void testCreateUbicacionLongitudMenor() {
        UbicacionEntity newEntity = ubicacionList.get(0);
        newEntity.setLongitud(-181.0);
        assertThrows(IllegalOperationException.class, () -> {
            ubicacionService.createUbicacion(newEntity);
        });
    }

    // crea una ubicacion cuya longitud es mayor a 180, espera una excepción
    // IllegalOperationException
    @Test
    void testCreateUbicacionLongitudMayor() {
        UbicacionEntity newEntity = ubicacionList.get(0);
        newEntity.setLongitud(181.0);
        assertThrows(IllegalOperationException.class, () -> {
            ubicacionService.createUbicacion(newEntity);
        });
    }

    // Crea una ubicacion cuya longitud es null, espera una excepción
    // IllegalOperationException
    @Test
    void testCreateUbicacionLongitudNull() {
        UbicacionEntity newEntity = ubicacionList.get(0);
        newEntity.setLongitud(null);
        assertThrows(IllegalOperationException.class, () -> {
            ubicacionService.createUbicacion(newEntity);
        });
    }

    // Crea una ubicacion cuya ciudad es null, espera una excepción
    // IllegalOperationException
    @Test
    void testCreateUbicacionCiudadNull() {
        UbicacionEntity newEntity = ubicacionList.get(0);
        newEntity.setCiudad(null);
        assertThrows(IllegalOperationException.class, () -> {
            ubicacionService.createUbicacion(newEntity);
        });
    }

    // Crea una ubicacion cuya cuidad es " ", espera una excepción
    // IllegalOperationException
    @Test
    void testCreateUbicacionCiudadVacio() {
        UbicacionEntity newEntity = ubicacionList.get(0);
        newEntity.setCiudad(" ");
        assertThrows(IllegalOperationException.class, () -> {
            ubicacionService.createUbicacion(newEntity);
        });
    }

    // crea una ubicacion cuya sede es null, espera que se cree la ubicacion
    // correctamente
    @Test
    void testCreateUbicacionSedeNull() throws EntityNotFoundException, IllegalOperationException {
        UbicacionEntity newEntity = ubicacionList.get(0);
        newEntity.setSede(null);
        UbicacionEntity result = ubicacionService.createUbicacion(newEntity);
        assertNotNull(result);
    }

    // Crea una ubicacion cuya direccion es null, espera una excepción
    // IllegalOperationException
    @Test
    void testCreateUbicacionDireccionNull() {
        UbicacionEntity newEntity = ubicacionList.get(0);
        newEntity.setDireccion(null);
        assertThrows(IllegalOperationException.class, () -> {
            ubicacionService.createUbicacion(newEntity);
        });
    }

    // Crea una ubicacion cuya direccion es " ", espera una excepción
    // IllegalOperationException
    @Test
    void testCreateUbicacionDireccionVacio() {
        UbicacionEntity newEntity = ubicacionList.get(0);
        newEntity.setDireccion(" ");
        assertThrows(IllegalOperationException.class, () -> {
            ubicacionService.createUbicacion(newEntity);
        });
    }

    // Obtiene todas las ubicaciones
    @Test
    void testGetServiciosExtra() {
        List<UbicacionEntity> list = ubicacionService.getUbicaciones();
        assertEquals(ubicacionList.size(), list.size());
        for (UbicacionEntity entity : list) {
            boolean found = false;
            for (UbicacionEntity storedEntity : ubicacionList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    // Obtiene una ubicacion por su ID
    @Test
    void testGetUbicacion() throws EntityNotFoundException {
        UbicacionEntity entity = ubicacionList.get(0);
        UbicacionEntity resultEntity = ubicacionService.getUbicacion(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        // compara los atributos del ubicacion
        assertEquals(entity.getLatitud(), resultEntity.getLatitud());
        assertEquals(entity.getLongitud(), resultEntity.getLongitud());
        assertEquals(entity.getCiudad(), resultEntity.getCiudad());
        assertEquals(entity.getDireccion(), resultEntity.getDireccion());
        assertEquals(entity.getSede(), resultEntity.getSede());
    }

    // Obtiene una ubicacion por su ID, espera una excepción EntityNotFoundException
    @Test
    void testGetUbicacionNoExistente() {
        assertThrows(EntityNotFoundException.class, () -> {
            ubicacionService.getUbicacion(0L);
        });
    }

    // Actualiza una ubicacion
    @Test
    void testUpdateUbicacion() throws EntityNotFoundException, IllegalOperationException {
        UbicacionEntity entity = ubicacionList.get(0);
        UbicacionEntity pojoEntity = factory.manufacturePojo(UbicacionEntity.class);
        // la latitud debe ser un número entre -90 y 90
        pojoEntity.setLatitud((double) (Math.random() * 180 - 90));
        // la longitud debe ser un número entre -180 y 180
        pojoEntity.setLongitud((double) (Math.random() * 360 - 180));
        pojoEntity.setId(entity.getId());
        // añade una sede a la entidad
        pojoEntity.setSede(ubicacionList.get(0).getSede());
        ubicacionService.updateUbicacion(entity.getId(), pojoEntity);
        UbicacionEntity resp = entityManager.find(UbicacionEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        // compara los atributos del ubicacion
        assertEquals(pojoEntity.getLatitud(), resp.getLatitud());
        assertEquals(pojoEntity.getLongitud(), resp.getLongitud());
        assertEquals(pojoEntity.getCiudad(), resp.getCiudad());
        assertEquals(pojoEntity.getDireccion(), resp.getDireccion());
        assertEquals(pojoEntity.getSede(), resp.getSede());
    }

    // actualiza ubicacion con ID inválido
    @Test
    void testUpdateUbicacionIDInvalido() {
        assertThrows(EntityNotFoundException.class, () -> {
            UbicacionEntity pojoEntity = ubicacionList.get(0);
            pojoEntity.setId(0L);
            ubicacionService.updateUbicacion(0L, pojoEntity);
        });
    }

    // actualiza ubicacion con latitud nula
    @Test
    void testUpdateUbicacionLatitudNull() {
        assertThrows(IllegalOperationException.class, () -> {
            UbicacionEntity entity = ubicacionList.get(0);
            UbicacionEntity pojoEntity = ubicacionList.get(0);
            pojoEntity.setId(entity.getId());
            pojoEntity.setLatitud(null);
            ubicacionService.updateUbicacion(entity.getId(), pojoEntity);
        });
    }

    // actualiza ubicacion con longitud nula
    @Test
    void testUpdateUbicacionLongitudNull() {
        assertThrows(IllegalOperationException.class, () -> {
            UbicacionEntity entity = ubicacionList.get(0);
            UbicacionEntity pojoEntity = ubicacionList.get(0);
            pojoEntity.setId(entity.getId());
            pojoEntity.setLongitud(null);
            ubicacionService.updateUbicacion(entity.getId(), pojoEntity);
        });
    }

    // actualiza ubicacion con latitud menor a -90
    @Test
    void testUpdateUbicacionLatitudMenor() {
        assertThrows(IllegalOperationException.class, () -> {
            UbicacionEntity entity = ubicacionList.get(0);
            UbicacionEntity pojoEntity = ubicacionList.get(0);
            pojoEntity.setId(entity.getId());
            pojoEntity.setLatitud(-91.0);
            ubicacionService.updateUbicacion(entity.getId(), pojoEntity);
        });
    }

    // actualiza ubicacion con latitud mayor a 90
    @Test
    void testUpdateUbicacionLatitudMayor() {
        assertThrows(IllegalOperationException.class, () -> {
            UbicacionEntity entity = ubicacionList.get(0);
            UbicacionEntity pojoEntity = ubicacionList.get(0);
            pojoEntity.setId(entity.getId());
            pojoEntity.setLatitud(91.0);
            ubicacionService.updateUbicacion(entity.getId(), pojoEntity);
        });
    }

    // actualiza ubicacion con longitud menor a -180
    @Test
    void testUpdateUbicacionLongitudMenor() {
        assertThrows(IllegalOperationException.class, () -> {
            UbicacionEntity entity = ubicacionList.get(0);
            UbicacionEntity pojoEntity = ubicacionList.get(0);
            pojoEntity.setId(entity.getId());
            pojoEntity.setLongitud(-181.0);
            ubicacionService.updateUbicacion(entity.getId(), pojoEntity);
        });
    }

    // actualiza ubicacion con longitud mayor a 180
    @Test
    void testUpdateUbicacionLongitudMayor() {
        assertThrows(IllegalOperationException.class, () -> {
            UbicacionEntity entity = ubicacionList.get(0);
            UbicacionEntity pojoEntity = ubicacionList.get(0);
            pojoEntity.setId(entity.getId());
            pojoEntity.setLongitud(181.0);
            ubicacionService.updateUbicacion(entity.getId(), pojoEntity);
        });
    }

    // actualiza ubicacion con sede nula, espera que se actualice correctamente
    @Test
    void testUpdateUbicacionSedeNull() throws EntityNotFoundException, IllegalOperationException {
        UbicacionEntity entity = ubicacionList.get(0);
        UbicacionEntity pojoEntity = ubicacionList.get(0);
        pojoEntity.setId(entity.getId());
        pojoEntity.setSede(null);
        ubicacionService.updateUbicacion(entity.getId(), pojoEntity);
        UbicacionEntity resp = entityManager.find(UbicacionEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        // compara los atributos del ubicacion
        assertEquals(pojoEntity.getLatitud(), resp.getLatitud());
        assertEquals(pojoEntity.getLongitud(), resp.getLongitud());
        assertEquals(pojoEntity.getCiudad(), resp.getCiudad());
        assertEquals(pojoEntity.getDireccion(), resp.getDireccion());
        assertEquals(pojoEntity.getSede(), resp.getSede());

    }

    // actualiza ubicacion con ciudad nula
    @Test
    void testUpdateUbicacionCiudadNull() {
        assertThrows(IllegalOperationException.class, () -> {
            UbicacionEntity entity = ubicacionList.get(0);
            UbicacionEntity pojoEntity = ubicacionList.get(0);
            pojoEntity.setId(entity.getId());
            pojoEntity.setCiudad(null);
            ubicacionService.updateUbicacion(entity.getId(), pojoEntity);
        });
    }

    // actualiza ubicacion con dirección nula
    @Test
    void testUpdateUbicacionDireccionNull() {
        assertThrows(IllegalOperationException.class, () -> {
            UbicacionEntity entity = ubicacionList.get(0);
            UbicacionEntity pojoEntity = ubicacionList.get(0);
            pojoEntity.setId(entity.getId());
            pojoEntity.setDireccion(null);
            ubicacionService.updateUbicacion(entity.getId(), pojoEntity);
        });
    }

    // actualiza ubicacion con dirección vacía
    @Test
    void testUpdateUbicacionDireccionVacio() {
        assertThrows(IllegalOperationException.class, () -> {
            UbicacionEntity entity = ubicacionList.get(0);
            UbicacionEntity pojoEntity = ubicacionList.get(0);
            pojoEntity.setId(entity.getId());
            pojoEntity.setDireccion(" ");
            ubicacionService.updateUbicacion(entity.getId(), pojoEntity);
        });
    }

    // actualiza ubicacion con cuidad vacía
    @Test
    void testUpdateUbicacionCiudadVacio() {
        assertThrows(IllegalOperationException.class, () -> {
            UbicacionEntity entity = ubicacionList.get(0);
            UbicacionEntity pojoEntity = ubicacionList.get(0);
            pojoEntity.setId(entity.getId());
            pojoEntity.setCiudad(" ");
            ubicacionService.updateUbicacion(entity.getId(), pojoEntity);
        });
    }

    // elimina un ubicacion
    @Test
    void testDeleteUbicacion() throws EntityNotFoundException, IllegalOperationException {
        UbicacionEntity entity = ubicacionList.get(0);
        ubicacionService.deleteUbicacion(entity.getId());
        UbicacionEntity deleted = entityManager.find(UbicacionEntity.class, entity.getId());
        assertNull(deleted);
    }

    // elimina un ubicacion con ID inválido
    @Test
    void testDeleteUbicacionIDInvalido() {
        assertThrows(EntityNotFoundException.class, () -> {
            ubicacionService.deleteUbicacion(0L);
        });
    }
}
