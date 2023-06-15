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
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
//@Autor -> Juan Coronel

@DataJpaTest
@Transactional
@Import({ SedeAndServicioService.class, ServicioService.class })
public class SedeAndServiciosServiceTest {

    // Servicio que se va a probar
    @Autowired
    private SedeAndServicioService sedeServicioService;

    // TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // PodamFactory para generar datos aleatorios
    private PodamFactory factory = new PodamFactoryImpl();

    // Lista de sedes
    private List<SedeEntity> sedes = new ArrayList<>();

    // Lista de servicios
    private List<ServicioEntity> servicios = new ArrayList<>();

    // Configuracion inicial de la prueba
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    // Limpia las tablas que están implicadas en la prueba
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
        entityManager.getEntityManager().createQuery("delete from ServicioEntity");

    }

    // Inserta los datos de prueba en la lista de Sedes
    private void insertData() {
        List<ServicioEntity> s = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            SedeEntity sEntity = factory.manufacturePojo(SedeEntity.class);
            ServicioEntity seEntity = factory.manufacturePojo(ServicioEntity.class);
            entityManager.persist(seEntity);
            servicios.add(seEntity);

            s.add(seEntity);
            sEntity.setServicios(s);
            entityManager.persist(sEntity);
            sedes.add(sEntity);
        }
    }

    // Prueba 1: Agregar un servicio a una sede
    @Test
    void testAddServiceToSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        ServicioEntity service = factory.manufacturePojo(ServicioEntity.class);
        entityManager.persist(service);

        ServicioEntity answer = sedeServicioService.addSedeServicio(sede.getId(), service.getId());
        assertNotNull(answer);
        assertEquals(service.getId(), answer.getId());

    }

    // Prueba 2: Agregar un servicio ya existente en una sede
    @Test
    void testAddServiceToSedeAlreadyExist() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        ServicioEntity service = sede.getServicios().get(0);

        assertThrows(IllegalOperationException.class, () -> {
            sedeServicioService.addSedeServicio(sede.getId(), service.getId());
        });
    }

    // Prueba 2.1: Intentar agregar un servicio que no existe en una sede.
    // Espera una EntityNotFoundException
    @Test
    void testAddSEToSedeNotExist() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);

        assertThrows(EntityNotFoundException.class, () -> {
            sedeServicioService.addSedeServicio(sede.getId(), 0L);
        });
    }

    // Prueba 2.2: Intentar agregar un servicio a una sede que no existe.
    // Espera una EntityNotFoundException
    @Test
    void testAddSEToSedeNotExist2() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);

        assertThrows(EntityNotFoundException.class, () -> {
            sedeServicioService.addSedeServicio(0L, servicio.getId());
        });
    }

    // Prueba 2.3: Obtener los servicios de una sede
    @Test
    void testGetSEFromSede() throws EntityNotFoundException {
        SedeEntity sede = sedes.get(0);
        List<ServicioEntity> servicioEntities = sedeServicioService.obtenerAllServicios(sede.getId());

        //assertEquals(sede.getServicios().size(), servicioEntities.size());
		for (int i = 0; i < servicioEntities.size(); i++) {
			assertTrue(servicioEntities.contains(servicios.get(i)));
		}
    }

    // Prueba 2.4: Obtener los servicios de una sede no existente
    @Test
    void testGetSEFromSedeNotExist() throws EntityNotFoundException {
        assertThrows(EntityNotFoundException.class, () -> {
            sedeServicioService.obtenerAllServicios(0L);
        });
    }

    // Prueba 3: eliminar un servicio de una sede
    @Test
    void testDeleteServiceToSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        ServicioEntity serv = sede.getServicios().get(0);

        ServicioEntity answer = sedeServicioService.deleteSedeServicio(sede.getId(), serv.getId());

        assertNotNull(answer);
        assertEquals(serv.getId(), answer.getId());
    }

        // Prueba para eliminar un servicio de una sede que no existe
    // Se espera que se lance una excepción de tipo IllegalOperationException
    @Test
    void deleteServicioNotExistsTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene un servicio extra aleatorio
        ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
        entityManager.persist(servicio);
        // Se elimina el servicio de la sede
        assertThrows(EntityNotFoundException.class, () -> {
            sedeServicioService.deleteSedeServicio(0L, servicio.getId());
        });
    }

    // Prueba para eliminar un servicio que no existe de una sede
    // Se espera que se lance una excepción de tipo IllegalOperationException
    @Test
    void deleteServicioExtraNotExistsTest2() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene una sede aleatoria
        SedeEntity sede = sedes.get(0);
        // Se elimina el servicio de la sede
        assertThrows(EntityNotFoundException.class, () -> {
            sedeServicioService.deleteSedeServicio(sede.getId(), 0L);
        });
    }

    // Prueba 4: eliminar un servicio que no está en una sede
    @Test
    void testDeleteServiceToSedeNotExist() throws EntityNotFoundException, IllegalOperationException {
         // Se obtiene una sede aleatoria
         SedeEntity sede = sedes.get(0);
         // Se obtiene un servicio extra aleatorio
         ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
         entityManager.persist(servicio);
         // Se elimina el servicio de la sede
         assertThrows(IllegalOperationException.class, () -> {
             sedeServicioService.deleteSedeServicio(sede.getId(), servicio.getId());
         });
    }

    // Prueba 5: Actualizar un servicio de una sede
    @Test
    void testUpdateSEToSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        List<ServicioEntity> nuevaLista = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
            entityManager.persist(entity);
            nuevaLista.add(entity);
        }

        sedeServicioService.updateSedeServicios(sede.getId(), nuevaLista);
        List<ServicioEntity> seEntities = entityManager.find(SedeEntity.class, sede.getId()).getServicios();
        for (ServicioEntity item : seEntities) {
            assertTrue(nuevaLista.contains(item));
        }
    }

    // Prueba 5.1: Intentar actualizar un SErvicio en una sede que no existe.
    // Espera una EntityNotFoundException
    @Test
    void testUpdateSEInvalidSede() {
        assertThrows(EntityNotFoundException.class, () -> {
            List<ServicioEntity> nuevaLista = new ArrayList<>();
        
            for (int i = 0; i < 3; i++) {
                ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
                entityManager.persist(entity);
                nuevaLista.add(entity);
            }
            
            sedeServicioService.updateSedeServicios(0L, nuevaLista);
        });
    }

}
