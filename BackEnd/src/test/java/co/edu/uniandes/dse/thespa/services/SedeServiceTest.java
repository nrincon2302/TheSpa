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
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.entities.UbicacionEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
//@Autor -> Juan Coronel

@DataJpaTest
@Transactional
@Import(SedeService.class)
public class SedeServiceTest {

    // Servicio que se va a probar
    @Autowired
    private SedeService SedeService;

    // TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // PodamFactory para generar datos aleatorios
    private PodamFactory factory = new PodamFactoryImpl();

    // Lista de sedes
    private List<SedeEntity> sedes = new ArrayList<>();

    // Configuracion inicial de la prueba
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    // Limpia las tablas que están implicadas en la prueba
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from SedeEntity");

    }

    // Inserta los datos de prueba en la lista de Sedes
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            SedeEntity entity = factory.manufacturePojo(SedeEntity.class);
            entityManager.persist(entity);
            sedes.add(entity);
        }
    }

    // Prueba 1: Crear una sede
    @Test
    void testCreateSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);

        newEntity.setNombre("Sede Ficticia 1");
        UbicacionEntity ubicacion = factory.manufacturePojo(UbicacionEntity.class);
        newEntity.setUbicacion(ubicacion);

        List<PackDeServiciosEntity> packServiciosFicticios = new ArrayList<>();
        packServiciosFicticios.add(new PackDeServiciosEntity());
        newEntity.setPacksDeServicios(packServiciosFicticios);

        List<ServicioEntity> serviciosFicticios = new ArrayList<>();
        serviciosFicticios.add(new ServicioEntity());
        newEntity.setServicios(serviciosFicticios);

        List<TrabajadorEntity> trabajadoresFicticios = new ArrayList<>();
        trabajadoresFicticios.add(new TrabajadorEntity());
        newEntity.setTrabajadores(trabajadoresFicticios);

        SedeEntity result = SedeService.createSede(newEntity);
        sedes.add(result);
        assertNotNull(result);
        SedeEntity entity = entityManager.find(SedeEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getImagen(), entity.getImagen());

    }

    // Prueba 2: Crear una sede con un nombre incorrecto (Vacio)
    @Test
    void testCreateSedeWithNoValidName() {
        assertThrows(IllegalOperationException.class, () -> {
            SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
            newEntity.setNombre("");
            SedeService.createSede(newEntity);
        });
    }

    // Prueba 3: Crear una sede con un nombre incorrecto (Nulo)
    @Test
    void testCreateSedeWithNoValidName2() {
        assertThrows(IllegalOperationException.class, () -> {
            SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
            newEntity.setNombre(null);
            SedeService.createSede(newEntity);
        });
    }

    // Prueba 3.1: Crear una sede con un nombre repetido
    @Test
    void testCreateSedeWithRepeatedName() {
        assertThrows(IllegalOperationException.class, () -> {
            SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
            newEntity.setNombre(sedes.get(0).getNombre());
            SedeService.createSede(newEntity);
        });
    }

    // Prueba 3.2: Crear una sede ya existente en la base de datos
    @Test
    void testCreateSedeAlreadyExists() {
        assertThrows(IllegalOperationException.class, () -> {
            SedeEntity newEntity = sedes.get(0);
            SedeService.createSede(newEntity);
        });
    }

    // Prueba 3.3: Crear una sede con la misma ubicación que otra sede
    @Test
    void testCreateSedeWithRepeatedLocation() {
        assertThrows(IllegalOperationException.class, () -> {
            SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
            newEntity.setUbicacion(sedes.get(0).getUbicacion());
            SedeService.createSede(newEntity);
        });
    }

    // Prueba 3.4: Crear una sede con ubicación incorrecta (Nula)
    @Test
    void testCreateSedeWithNullLocation() {
        assertThrows(IllegalOperationException.class, () -> {
            SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
            newEntity.setUbicacion(null);
            SedeService.createSede(newEntity);
        });
    }

    // Prueba 3.5: Crear una sede con trabajadores incorrectos (Nula)
    @Test
    void testCreateSedeWithNullWorkers() {
        assertThrows(IllegalOperationException.class, () -> {
            SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
            newEntity.setTrabajadores(null);
            SedeService.createSede(newEntity);
        });
    }

    // Prueba 3.6: Crear una sede con articulos de ropa incorrectos (Nula)
    @Test
    void testCreateSedeWithNullArticles() {
        assertThrows(IllegalOperationException.class, () -> {
            SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
            newEntity.setArticulosDeRopa(null);
            SedeService.createSede(newEntity);
        });
    }

    // Prueba 3.7: Crear una sede con servicios incorrectos (Nula)
    @Test
    void testCreateSedeWithNullServices() {
        assertThrows(IllegalOperationException.class, () -> {
            SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
            newEntity.setServicios(null);
            SedeService.createSede(newEntity);
        });
    }

    // Prueba 3.8: Crear una sede con servicios extra incorrectos (Nula)
    @Test
    void testCreateSedeWithNullExtraServices() {
        assertThrows(IllegalOperationException.class, () -> {
            SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
            newEntity.setServiciosExtra(null);
            SedeService.createSede(newEntity);
        });
    }

    // Prueba 3.9: Crear una sede con packs incorrectos (Nula)
    @Test
    void testCreateSedeWithNullPacks() {
        assertThrows(IllegalOperationException.class, () -> {
            SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);
            newEntity.setPacksDeServicios(null);
            SedeService.createSede(newEntity);
        });
    }

    // Prueba 4: getSedes
    @Test
    void testGetSedes() {
        List<SedeEntity> list = SedeService.getSedes();
        assertEquals(sedes.size(), list.size());
        for (SedeEntity entity : list) {
            boolean found = false;
            for (SedeEntity storedEntity : sedes) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    // Prueba 5: getSede
    @Test
    void testGetSede() throws EntityNotFoundException {
        SedeEntity entity = sedes.get(0);
        SedeEntity resultEntity = SedeService.getSede(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getImagen(), resultEntity.getImagen());
    }

    // Prueba 6: getSede (sede no existente)
    @Test
    void testGetInvalidSede() {
        assertThrows(EntityNotFoundException.class, () -> {
            SedeService.getSede(0L);
        });
    }

    // Prueba 7: Update Sede ()
    @Test
    void testUpdateSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity entity = sedes.get(0);
        SedeEntity pojoEntity = factory.manufacturePojo(SedeEntity.class);
        pojoEntity.setId(entity.getId());
        SedeService.updateSede(entity.getId(), pojoEntity);

        SedeEntity resp = entityManager.find(SedeEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getImagen(), resp.getImagen());
    }

    // Prueba 8: Update Sede (No existe Sede)
    @Test
    void testUpdateSedeInvalid() {
        assertThrows(EntityNotFoundException.class, () -> {
            SedeEntity pojoEntity = factory.manufacturePojo(SedeEntity.class);
            pojoEntity.setId(0L);
            SedeService.updateSede(0L, pojoEntity);
        });
    }

    // Prueba 9: deleteSede
    @Test
    void testDeleteSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity entity = sedes.get(1);
        SedeService.deleteSede(entity.getId());
        SedeEntity deleted = entityManager.find(SedeEntity.class, entity.getId());
        assertNull(deleted);
    }

    // Prueba 10: deleteSede (No existe la sede)
    @Test
    void testDeleteInvalidSede() {
        assertThrows(EntityNotFoundException.class, () -> {
            SedeService.deleteSede(0L);
        });
    }

    // Prueba 11: crear una sede sin ubicacion
    @Test
    void testCreateSedeWithoutUbicacion() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);

        newEntity.setNombre("Sede Ficticia 1");

        List<PackDeServiciosEntity> packServiciosFicticios = new ArrayList<>();
        packServiciosFicticios.add(new PackDeServiciosEntity());
        newEntity.setPacksDeServicios(packServiciosFicticios);

        List<ServicioEntity> serviciosFicticios = new ArrayList<>();
        serviciosFicticios.add(new ServicioEntity());
        newEntity.setServicios(serviciosFicticios);

        List<TrabajadorEntity> trabajadoresFicticios = new ArrayList<>();
        trabajadoresFicticios.add(new TrabajadorEntity());
        newEntity.setTrabajadores(trabajadoresFicticios);

        assertThrows(IllegalOperationException.class, () -> {
            SedeService.createSede(newEntity);
        });

    }

    // Prueba 12: crear una sede con ubicación repetida
    @Test
    void testCreateSedeWithRepeatedUbicacion() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity newEntity = factory.manufacturePojo(SedeEntity.class);

        newEntity.setNombre("Sede Ficticia 1");

        List<PackDeServiciosEntity> packServiciosFicticios = new ArrayList<>();
        packServiciosFicticios.add(new PackDeServiciosEntity());
        newEntity.setPacksDeServicios(packServiciosFicticios);

        List<ServicioEntity> serviciosFicticios = new ArrayList<>();
        serviciosFicticios.add(new ServicioEntity());
        newEntity.setServicios(serviciosFicticios);

        List<TrabajadorEntity> trabajadoresFicticios = new ArrayList<>();
        trabajadoresFicticios.add(new TrabajadorEntity());
        newEntity.setTrabajadores(trabajadoresFicticios);

        newEntity.setUbicacion(sedes.get(0).getUbicacion());

        assertThrows(IllegalOperationException.class, () -> {
            SedeService.createSede(newEntity);
        });

    }

}
