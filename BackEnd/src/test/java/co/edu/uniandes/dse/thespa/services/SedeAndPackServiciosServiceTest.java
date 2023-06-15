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
import co.edu.uniandes.dse.thespa.entities.PackDeServiciosEntity;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
//@Autor -> Juan Coronel

@DataJpaTest
@Transactional
@Import({ SedeAndPackServicesService.class, PackDeServiciosService.class })
public class SedeAndPackServiciosServiceTest {

    // Servicio que se va a probar
    @Autowired
    private SedeAndPackServicesService sedeAndPackService;

    // TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // PodamFactory para generar datos aleatorios
    private PodamFactory factory = new PodamFactoryImpl();

    // Lista de sedes
    private List<SedeEntity> sedes = new ArrayList<>();

    // Lista de pack de servicios
    private List<PackDeServiciosEntity> packs = new ArrayList<>();

    // Configuracion inicial de la prueba
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    // Limpia las tablas que están implicadas en la prueba
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
        entityManager.getEntityManager().createQuery("delete from PackDeServiciosEntity");

    }

    // Inserta los datos de prueba en la lista de Sedes
    private void insertData() {
        List<PackDeServiciosEntity> p = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            SedeEntity sEntity = factory.manufacturePojo(SedeEntity.class);
            PackDeServiciosEntity pEntity = factory.manufacturePojo(PackDeServiciosEntity.class);
            entityManager.persist(pEntity);
            packs.add(pEntity);

            p.add(pEntity);
            sEntity.setPacksDeServicios(p);
            entityManager.persist(sEntity);
            sedes.add(sEntity);
        }
    }

    // Prueba 1: Agregar un Pack de servicios a una sede
    @Test
    void testAddPackDeServiciosToSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        PackDeServiciosEntity packDeServicios = factory.manufacturePojo(PackDeServiciosEntity.class);
        entityManager.persist(packDeServicios);

        PackDeServiciosEntity answer = sedeAndPackService.addSedePackDeServicios(sede.getId(), packDeServicios.getId());
        assertNotNull(answer);
        assertEquals(packDeServicios.getId(), answer.getId());

    }

    // Prueba 2: Intentar agregar un Pack de servicios ya existente en una sede.
    // Espera una IllegalOperationException
    @Test
    void testAddPackDeServiciosToSedeAlreadyExist() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        PackDeServiciosEntity packDeServicios = sede.getPacksDeServicios().get(0);

        assertThrows(IllegalOperationException.class, () -> {
            sedeAndPackService.addSedePackDeServicios(sede.getId(), packDeServicios.getId());
        });
    }

    // Prueba 2.1: Intentar agregar un Pack de servicios que no existe en una sede.
    // Espera una EntityNotFoundException
    @Test
    void testAddPackDeServiciosToSedeNotExist() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);

        assertThrows(EntityNotFoundException.class, () -> {
            sedeAndPackService.addSedePackDeServicios(sede.getId(), 0L);
        });
    }

    // Prueba 2.2: Intentar agregar un Pack de servicios a una sede que no existe.
    // Espera una EntityNotFoundException
    @Test
    void testAddPackDeServiciosToSedeNotExist2() throws EntityNotFoundException, IllegalOperationException {
        PackDeServiciosEntity packDeServicios = packs.get(0);

        assertThrows(EntityNotFoundException.class, () -> {
            sedeAndPackService.addSedePackDeServicios(0L, packDeServicios.getId());
        });
    }

    // Prueba 3: Obtener los Packs de servicios de una sede
    @Test
    void testGetPacksDeServiciosFromSede() throws EntityNotFoundException {
        SedeEntity sede = sedes.get(0);
        List<PackDeServiciosEntity> packsEntities = sedeAndPackService.obtenerAllPacks(sede.getId());

        assertEquals(sede.getPacksDeServicios().size(), packsEntities.size());
		for (int i = 0; i < packsEntities.size(); i++) {
			assertTrue(packsEntities.contains(packs.get(i)));
		}
    }

    // Prueba 3.0: Obtener un pack de servicios de una sede
    @Test
    void testGetPackDeServiciosFromSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        PackDeServiciosEntity packDeServicios = sede.getPacksDeServicios().get(0);

        PackDeServiciosEntity answer = sedeAndPackService.getPack(sede.getId(), packDeServicios.getId());
        assertNotNull(answer);
        assertEquals(packDeServicios.getId(), answer.getId());
    }

    // Prueba 3.1: Intentar obtener un Packs de servicios de una sede que no existe.
    // Espera una EntityNotFoundException
    @Test
    void testGetPacksDeServiciosFromSedeNotExist() throws EntityNotFoundException {
        PackDeServiciosEntity packDeServicios = packs.get(0);

        assertThrows(EntityNotFoundException.class, () -> {
            sedeAndPackService.getPack(0L, packDeServicios.getId());
        });
    }

    // Prueba 3.2: Intentar obtener los Packs de servicios de una sede que no tiene packs.
    // Espera una IllegalOperationException
    @Test
    void testGetPacksDeServiciosNotInSede() throws EntityNotFoundException {
        SedeEntity sede = sedes.get(0);
        PackDeServiciosEntity packDeServicios = factory.manufacturePojo(PackDeServiciosEntity.class);
        entityManager.persist(packDeServicios);

        assertThrows(IllegalOperationException.class, () -> {
            sedeAndPackService.getPack(sede.getId(), packDeServicios.getId());
        });
    }

    // Prueba 3.3: Intentar obtener un Packs de servicios que no existe de una sede.
    // Espera una EntityNotFoundException
    @Test
    void testGetPacksDeServiciosNotExistFromSede() throws EntityNotFoundException {
        SedeEntity sede = sedes.get(0);

        assertThrows(EntityNotFoundException.class, () -> {
            sedeAndPackService.getPack(sede.getId(), 0L);
        });
    }

    // Prueba 4: Eliminar un Pack de servicios de una sede
    @Test
    void testdeletePackDeServiciosToSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        PackDeServiciosEntity packDeServicios = sede.getPacksDeServicios().get(0);

        PackDeServiciosEntity answer = sedeAndPackService.deleteSedePackDeServicios(sede.getId(), packDeServicios.getId());
        assertNotNull(answer);
        assertEquals(packDeServicios.getId(), answer.getId());

    }

    // Prueba 4.1: Intentar eliminar un Pack de servicios que no existe en una sede.
    // Espera una EntityNotFoundException
    @Test
    void testdeletePackDeServiciosToSedeNotExist() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);

        assertThrows(EntityNotFoundException.class, () -> {
            sedeAndPackService.deleteSedePackDeServicios(sede.getId(), 0L);
        });
    }

    // Prueba 4.2: Intentar eliminar un Pack de servicios de una sede que no existe.
    // Espera una EntityNotFoundException
    @Test
    void testdeletePackDeServiciosToSedeNotExist2() throws EntityNotFoundException, IllegalOperationException {
        PackDeServiciosEntity packDeServicios = packs.get(0);

        assertThrows(EntityNotFoundException.class, () -> {
            sedeAndPackService.deleteSedePackDeServicios(0L, packDeServicios.getId());
        });
    }

    // Prueba 5: Actualizar un Pack de servicios de una sede
    @Test
    void testUpdatePackDeServiciosToSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        List<PackDeServiciosEntity> nuevaLista = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            PackDeServiciosEntity entity = factory.manufacturePojo(PackDeServiciosEntity.class);
            entityManager.persist(entity);
            nuevaLista.add(entity);
        }

        sedeAndPackService.updateSedePackDeServicios(sede.getId(), nuevaLista);
        List<PackDeServiciosEntity> packEntities = entityManager.find(SedeEntity.class, sede.getId()).getPacksDeServicios();
        for (PackDeServiciosEntity item : packEntities) {
            assertTrue(nuevaLista.contains(item));
        }
    }

    // Prueba 5.1: Intentar actualizar un Pack de servicios en una sede que no existe.
    // Espera una EntityNotFoundException
	@Test
	void testUpdatePackInvalidSede() {
		assertThrows(EntityNotFoundException.class, () -> {
            List<PackDeServiciosEntity> nuevaLista = new ArrayList<>();
		
            for (int i = 0; i < 3; i++) {
                PackDeServiciosEntity entity = factory.manufacturePojo(PackDeServiciosEntity.class);
                entityManager.persist(entity);
                nuevaLista.add(entity);
            }
            
            sedeAndPackService.updateSedePackDeServicios(0L, nuevaLista);
		});
	}

}