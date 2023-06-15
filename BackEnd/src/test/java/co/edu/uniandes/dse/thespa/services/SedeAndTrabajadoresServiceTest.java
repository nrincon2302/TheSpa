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
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
//@Autor -> Juan Coronel

@DataJpaTest
@Transactional
@Import({ SedeAndTrabajadorService.class, TrabajadorService.class })
public class SedeAndTrabajadoresServiceTest {

    // Servicio que se va a probar
    @Autowired
    private SedeAndTrabajadorService sedeTrabajadorService;

    // TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // PodamFactory para generar datos aleatorios
    private PodamFactory factory = new PodamFactoryImpl();

    // Lista de sedes
    private List<SedeEntity> sedes = new ArrayList<>();

    // Lista de trabajadores
    private List<TrabajadorEntity> trabajadores = new ArrayList<>();

    // Configuracion inicial de la prueba
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    // Limpia las tablas que están implicadas en la prueba
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
        entityManager.getEntityManager().createQuery("delete from TrabajadorEntity");

    }

    // Inserta los datos de prueba en la lista de Sedes
    private void insertData() {
        List<TrabajadorEntity> t = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            SedeEntity sEntity = factory.manufacturePojo(SedeEntity.class);
            TrabajadorEntity tEntity = factory.manufacturePojo(TrabajadorEntity.class);
            entityManager.persist(tEntity);
            trabajadores.add(tEntity);

            t.add(tEntity);
            sEntity.setTrabajadores(t);
            entityManager.persist(sEntity);
            sedes.add(sEntity);
        }
    }

    // Prueba 1: Agregar un Trabajador a una sede
    @Test
    void testAddTrabajadorToSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        TrabajadorEntity trabaj = factory.manufacturePojo(TrabajadorEntity.class);
        entityManager.persist(trabaj);

        TrabajadorEntity answer = sedeTrabajadorService.addSedeTrabajador(sede.getId(), trabaj.getId());
        assertNotNull(answer);
        assertEquals(trabaj.getId(), answer.getId());

    }

    // Prueba 2: Agregar un Trabajador que ya existe en una sede, esperando una
    // excepcion de tipo IllegalOperationException
    @Test
    void testAddTrabajadorToSedeAlreadyExists() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        TrabajadorEntity trabaj = sede.getTrabajadores().get(0);

        assertThrows(IllegalOperationException.class, () -> {
            sedeTrabajadorService.addSedeTrabajador(sede.getId(), trabaj.getId());
        });
    }

    // Prueba 2.1: Intentar agregar un trabajador que no existe en una sede.
    // Espera una EntityNotFoundException
    @Test
    void testAddTrabajadorToSedeNotExist() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);

        assertThrows(EntityNotFoundException.class, () -> {
            sedeTrabajadorService.addSedeTrabajador(sede.getId(), 0L);
        });
    }

    // Prueba 2.2: Intentar agregar un trabajador a una sede que no existe.
    // Espera una EntityNotFoundException
    @Test
    void testAddTrabajadorToSedeNotExist2() throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity trabajador = trabajadores.get(0);

        assertThrows(EntityNotFoundException.class, () -> {
            sedeTrabajadorService.addSedeTrabajador(0L, trabajador.getId());
        });
    }

    // Prueba 2.3: Obtener los trabajadores de una sede
    @Test
    void testGetTrabajadoresFromSede() throws EntityNotFoundException {
        SedeEntity sede = sedes.get(0);
        List<TrabajadorEntity> trabajadorEntities = sedeTrabajadorService.obtenerTrabajadroes(sede.getId());

        //assertEquals(sede.getServicios().size(), servicioEntities.size());
		for (int i = 0; i < trabajadorEntities.size(); i++) {
			assertTrue(trabajadorEntities.contains(trabajadores.get(i)));
		}
    }

    // Prueba 2.4: Obtener los trabajadores de una sede no existente
    @Test
    void testGetTrabajadoresFromSedeNotExist() throws EntityNotFoundException {
        assertThrows(EntityNotFoundException.class, () -> {
            sedeTrabajadorService.obtenerTrabajadroes(0L);
        });
    }

    // Prueba 3: Eliminar un Trabajador a una sede
    @Test
    void testdeleteTrabajadorToSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        TrabajadorEntity trabaj = sede.getTrabajadores().get(0);

        TrabajadorEntity answer = sedeTrabajadorService.deleteSedeTrabajador(sede.getId(), trabaj.getId());
        assertNotNull(answer);
        assertEquals(trabaj.getId(), answer.getId());

    }

    // Prueba para eliminar un trabajador de una sede que no existe
    // Se espera que se lance una excepción de tipo IllegalOperationException
    @Test
    void deleteTrabajadorNotExistsTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene un trabajador extra aleatorio
        TrabajadorEntity trabajador = factory.manufacturePojo(TrabajadorEntity.class);
        entityManager.persist(trabajador);
        // Se elimina el servicio de la sede
        assertThrows(EntityNotFoundException.class, () -> {
            sedeTrabajadorService.deleteSedeTrabajador(0L, trabajador.getId());
        });
    }

    // Prueba para eliminar un trabajador que no existe de una sede
    // Se espera que se lance una excepción de tipo IllegalOperationException
    @Test
    void deleteTrabajadorNotExistsTest2() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene una sede aleatoria
        SedeEntity sede = sedes.get(0);
        // Se elimina el servicio de la sede
        assertThrows(EntityNotFoundException.class, () -> {
            sedeTrabajadorService.deleteSedeTrabajador(sede.getId(), 0L);
        });
    }

    // Prueba 4: Eliminar un Trabajador que no existe en una sede, esperando una
    // excepcion de tipo IllegalOperationException
    @Test
    void testdeleteTrabajadorToSedeNotExists() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        TrabajadorEntity trabajador = factory.manufacturePojo(TrabajadorEntity.class);
        entityManager.persist(trabajador);

        assertThrows(IllegalOperationException.class, () -> {
            sedeTrabajadorService.deleteSedeTrabajador(sede.getId(), trabajador.getId());
        });

    }

    // Prueba 5: Actualizar un trabajador de una sede
    @Test
    void testUpdateTrabajadorToSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        List<TrabajadorEntity> nuevaLista = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            TrabajadorEntity entity = factory.manufacturePojo(TrabajadorEntity.class);
            entityManager.persist(entity);
            nuevaLista.add(entity);
        }

        sedeTrabajadorService.updateSedeTrabajadores(sede.getId(), nuevaLista);
        List<TrabajadorEntity> tEntities = entityManager.find(SedeEntity.class, sede.getId()).getTrabajadores();
        for (TrabajadorEntity item : tEntities) {
            assertTrue(nuevaLista.contains(item));
        }
    }

    // Prueba 5.1: Intentar actualizar un trabajador en una sede que no existe.
    // Espera una EntityNotFoundException
    @Test
    void testUpdateTInvalidSede() {
        assertThrows(EntityNotFoundException.class, () -> {
            List<TrabajadorEntity> nuevaLista = new ArrayList<>();
        
            for (int i = 0; i < 3; i++) {
                TrabajadorEntity entity = factory.manufacturePojo(TrabajadorEntity.class);
                entityManager.persist(entity);
                nuevaLista.add(entity);
            }
            
            sedeTrabajadorService.updateSedeTrabajadores(0L, nuevaLista);
        });
    }

}
