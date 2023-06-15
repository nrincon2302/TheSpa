package co.edu.uniandes.dse.thespa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import co.edu.uniandes.dse.thespa.entities.ServicioExtraEntity;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
//@Autor -> Juan Coronel

@DataJpaTest
@Transactional
@Import({ SedeAndServicioExtraService.class, ServicioExtraService.class })
public class SedeAndServicioExtraServiceTest {

    // Servicio que se va a probar
    @Autowired
    private SedeAndServicioExtraService sedeServicioExtraService;

    // TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // PodamFactory para generar datos aleatorios
    private PodamFactory factory = new PodamFactoryImpl();

    // Lista de sedes
    private List<SedeEntity> sedes = new ArrayList<>();

    // Lista de servicios extra
    private List<ServicioExtraEntity> serviciosExtra = new ArrayList<>();

    // Configuracion inicial de la prueba
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    // Limpia las tablas que están implicadas en la prueba
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
        entityManager.getEntityManager().createQuery("delete from ServicioExtraEntity");

    }

    // Inserta los datos de prueba en la lista de Sedes

    private void insertData() {
        List<ServicioExtraEntity> se = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            SedeEntity sEntity = factory.manufacturePojo(SedeEntity.class);
            ServicioExtraEntity seEntity = factory.manufacturePojo(ServicioExtraEntity.class);
            entityManager.persist(seEntity);
            serviciosExtra.add(seEntity);

            se.add(seEntity);
            sEntity.setServiciosExtra(se);
            entityManager.persist(sEntity);
            sedes.add(sEntity);
        }

    }

    // Prueba para agregar un servicio extra a una sede
    @Test
    void addServicioExtraTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene una sede aleatoria
        SedeEntity sede = sedes.get(0);
        // Se obtiene un servicio extra aleatorio
        ServicioExtraEntity servicioExtra = factory.manufacturePojo(ServicioExtraEntity.class);
        entityManager.persist(servicioExtra);
        // Se agrega el servicio extra a la sede
        ServicioExtraEntity answer = sedeServicioExtraService.addSedeExtraService(sede.getId(), servicioExtra.getId());
        // Se verifica que el servicio extra se haya agregado a la sede
        assertNotNull(answer);
        assertEquals(answer.getId(), servicioExtra.getId());
    }

    // Prueba para agregar un servicio ya existente a una sede
    // Se espera que se lance una excepción de tipo IllegalOperationException
    @Test
    void addServicioExtraAlreadyExistsTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene una sede aleatoria
        SedeEntity sede = sedes.get(0);
        // Se obtiene un servicio extra aleatorio
        ServicioExtraEntity servicioExtra = sede.getServiciosExtra().get(0);
        // Se agrega el servicio extra a la sede
        assertThrows(IllegalOperationException.class, () -> {
            sedeServicioExtraService.addSedeExtraService(sede.getId(), servicioExtra.getId());
        });
    }

    // Prueba 2.1: Intentar agregar un servicioExtra que no existe en una sede.
    // Espera una EntityNotFoundException
    @Test
    void testAddSEToSedeNotExist() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);

        assertThrows(EntityNotFoundException.class, () -> {
            sedeServicioExtraService.addSedeExtraService(sede.getId(), 0L);
        });
    }

    // Prueba 2.2: Intentar agregar un servicioExtra a una sede que no existe.
    // Espera una EntityNotFoundException
    @Test
    void testAddSEToSedeNotExist2() throws EntityNotFoundException, IllegalOperationException {
        ServicioExtraEntity servicioExtra = serviciosExtra.get(0);

        assertThrows(EntityNotFoundException.class, () -> {
            sedeServicioExtraService.addSedeExtraService(0L, servicioExtra.getId());
        });
    }

    // Prueba 3: Obtener los serviciosExtra de una sede
    @Test
    void testGetSEFromSede() throws EntityNotFoundException {
        SedeEntity sede = sedes.get(0);
        List<ServicioExtraEntity> servicioExtraEntities = sedeServicioExtraService.obtenerAllServicios(sede.getId());

        //assertEquals(sede.getServiciosExtra().size(), servicioExtraEntities.size());
		for (int i = 0; i < servicioExtraEntities.size(); i++) {
			assertTrue(servicioExtraEntities.contains(serviciosExtra.get(i)));
		}
    }

    // Prueba para eliminar un servicio extra de una sede
    @Test
    void deleteServicioExtraTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene una sede aleatoria
        SedeEntity sede = sedes.get(0);
        // Se obtiene un servicio extra aleatorio
        ServicioExtraEntity servicioExtra = sede.getServiciosExtra().get(0);
        // Se elimina el servicio extra de la sede
        sedeServicioExtraService.deleteSedeExtraService(sede.getId(), servicioExtra.getId());
        // Se verifica que el servicio extra se haya eliminado de la sede
        assertEquals(2, sede.getServiciosExtra().size());
    }

    // Prueba para eliminar un servicio extra que no está en una sede
    // Se espera que se lance una excepción de tipo IllegalOperationException
    @Test
    void deleteServicioExtraNotContainedTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene una sede aleatoria
        SedeEntity sede = sedes.get(0);
        // Se obtiene un servicio extra aleatorio
        ServicioExtraEntity servicioExtra = factory.manufacturePojo(ServicioExtraEntity.class);
        entityManager.persist(servicioExtra);
        // Se elimina el servicio extra de la sede
        assertThrows(IllegalOperationException.class, () -> {
            sedeServicioExtraService.deleteSedeExtraService(sede.getId(), servicioExtra.getId());
        });
    }

    // Prueba para eliminar un servicio extra que no existe de una sede
    // Se espera que se lance una excepción de tipo IllegalOperationException
    @Test
    void deleteServicioExtraNotExistsTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene un servicio extra aleatorio
        ServicioExtraEntity servicioExtra = factory.manufacturePojo(ServicioExtraEntity.class);
        entityManager.persist(servicioExtra);
        // Se elimina el servicio extra de la sede
        assertThrows(EntityNotFoundException.class, () -> {
            sedeServicioExtraService.deleteSedeExtraService(0L, servicioExtra.getId());
        });
    }

    // Prueba para eliminar un servicio extra de una sede que no existe
    // Se espera que se lance una excepción de tipo IllegalOperationException
    @Test
    void deleteServicioExtraNotExistsTest2() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene una sede aleatoria
        SedeEntity sede = sedes.get(0);
        // Se elimina el servicio extra de la sede
        assertThrows(EntityNotFoundException.class, () -> {
            sedeServicioExtraService.deleteSedeExtraService(sede.getId(), 0L);
        });
    }

    // Prueba 5: Actualizar un servicioExtra de una sede
    @Test
    void testUpdateSEToSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        List<ServicioExtraEntity> nuevaLista = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ServicioExtraEntity entity = factory.manufacturePojo(ServicioExtraEntity.class);
            entityManager.persist(entity);
            nuevaLista.add(entity);
        }

        sedeServicioExtraService.updateSedeExtraService(sede.getId(), nuevaLista);
        List<ServicioExtraEntity> seEntities = entityManager.find(SedeEntity.class, sede.getId()).getServiciosExtra();
        for (ServicioExtraEntity item : seEntities) {
            assertTrue(nuevaLista.contains(item));
        }
    }

    // Prueba 5.1: Intentar actualizar un SE en una sede que no existe.
    // Espera una EntityNotFoundException
	@Test
	void testUpdateSEInvalidSede() {
		assertThrows(EntityNotFoundException.class, () -> {
            List<ServicioExtraEntity> nuevaLista = new ArrayList<>();
		
            for (int i = 0; i < 3; i++) {
                ServicioExtraEntity entity = factory.manufacturePojo(ServicioExtraEntity.class);
                entityManager.persist(entity);
                nuevaLista.add(entity);
            }
            
            sedeServicioExtraService.updateSedeExtraService(0L, nuevaLista);
		});
	}

}
