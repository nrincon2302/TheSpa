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
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;

@DataJpaTest
@Transactional
@Import(TrabajadorService.class)
public class TrabajadorServiceTest {

    @Autowired
    private TrabajadorService trabajadorService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<TrabajadorEntity> trabajadorList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    // Limpia las tablas que están implicadas en la prueba
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from TrabajadorEntity");
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
    }

    // Inserta los datos de prueba en la lista de Trabajadores
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            TrabajadorEntity entity = factory.manufacturePojo(TrabajadorEntity.class);
            entityManager.persist(entity);
            trabajadorList.add(entity);
        }
        // crea una sede para asociarla a los trabajadores
        SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
        entityManager.persist(sede);
        // Asocia la sede a los trabajadores
        for (TrabajadorEntity trabajador : trabajadorList) {
            List<SedeEntity> listaSedes = new ArrayList<SedeEntity>();
            listaSedes.add(sede);
            trabajador.setSedes(listaSedes);
        }
        // crea un servicio para asociarlo a los trabajadores
        ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
        entityManager.persist(servicio);
        // Asocia el servicio a los trabajadores
        for (TrabajadorEntity trabajador : trabajadorList) {
            List<ServicioEntity> listaServicios = new ArrayList<ServicioEntity>();
            listaServicios.add(servicio);
            trabajador.setServicios(listaServicios);
        }
    }

    @Test
    // crea un trabajador con todas las condiciones necesarias
    void testCreateTrabajador() throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity newEntity = factory.manufacturePojo(TrabajadorEntity.class);
        // añade una sede a la entidad
        newEntity.setSedes(trabajadorList.get(0).getSedes());
        // añade un servicio a la entidad
        newEntity.setServicios(trabajadorList.get(0).getServicios());
        TrabajadorEntity result = trabajadorService.createTrabajador(newEntity);
        result.setNombre("nombre de prueba");
        assertNotNull(result);
        TrabajadorEntity entity = entityManager.find(TrabajadorEntity.class, result.getId());
        assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getFoto(), entity.getFoto());
        assertEquals(newEntity.getCalificacion(), entity.getCalificacion());
        assertEquals(newEntity.getEnHallOfFame(), entity.getEnHallOfFame());
        assertEquals(newEntity.getSedes(), entity.getSedes());
        assertEquals(newEntity.getServicios(), newEntity.getServicios());
    }

    @Test
    // Crea un trabajador cuyo nombre es una cadena vacía -> Operación ilegal
    void testCreateTrabajadorSinNombre() {
        assertThrows(IllegalOperationException.class, () -> {
            TrabajadorEntity newEntity = factory.manufacturePojo(TrabajadorEntity.class);
            // añade una sede a la entidad
            newEntity.setSedes(trabajadorList.get(0).getSedes());
            // añade un servicio a la entidad
            newEntity.setServicios(trabajadorList.get(0).getServicios());
            // setear el nombre al posible error
            newEntity.setNombre("");
            trabajadorService.createTrabajador(newEntity);
        });
    }

    @Test
    // Crea un trabajador cuyo nombre es nulo -> Operación ilegal
    void testCreateTrabajadorSinNombre2() {
        assertThrows(IllegalOperationException.class, () -> {
            TrabajadorEntity newEntity = factory.manufacturePojo(TrabajadorEntity.class);
            // añade una sede a la entidad
            newEntity.setSedes(trabajadorList.get(0).getSedes());
            // añade un servicio a la entidad
            newEntity.setServicios(trabajadorList.get(0).getServicios());
            // setear el nombre al posible error
            newEntity.setNombre(null);
            trabajadorService.createTrabajador(newEntity);
        });
    }

    @Test
    // Crea un trabajador sin una sede asociada -> Operación ilegal
    void testCreateTrabajadorSinSede() {
        assertThrows(IllegalOperationException.class, () -> {
            TrabajadorEntity newEntity = factory.manufacturePojo(TrabajadorEntity.class);
            // añade un servicio a la entidad
            newEntity.setServicios(trabajadorList.get(0).getServicios());
            // setear las sedes al posible error
            newEntity.setSedes(null);
            trabajadorService.createTrabajador(newEntity);
        });
    }

    @Test
    // Crea un trabajador con una lista vacía de sedes -> Operación ilegal
    void testCreateTrabajadorSinSede2() {
        assertThrows(IllegalOperationException.class, () -> {
            TrabajadorEntity newEntity = factory.manufacturePojo(TrabajadorEntity.class);
            // añade un servicio a la entidad
            newEntity.setServicios(trabajadorList.get(0).getServicios());
            // setear las sedes al posible error
            newEntity.setSedes(new ArrayList<>());
            trabajadorService.createTrabajador(newEntity);
        });
    }

    @Test
    // Crea un trabajador sin un servicio asociado -> Operación ilegal
    void testCreateTrabajadorSinServicios() {
        assertThrows(IllegalOperationException.class, () -> {
            TrabajadorEntity newEntity = factory.manufacturePojo(TrabajadorEntity.class);
            // añade una sede a la entidad
            newEntity.setSedes(trabajadorList.get(0).getSedes());
            // setear los servicios al posible error
            newEntity.setServicios(null);
            trabajadorService.createTrabajador(newEntity);
        });
    }

    @Test
    // Obtiene todos los trabajadores de la base de datos
    void testGetTrabajadores() {
        List<TrabajadorEntity> list = trabajadorService.getTrabajadores();
        assertEquals(trabajadorList.size(), list.size());
        for (TrabajadorEntity entity : list) {
            boolean found = false;
            for (TrabajadorEntity storedEntity : trabajadorList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    // Obtiene un trabajador con ID dado, asumiendo todas las condiciones de un trabajador
    void testGetTrabajador() throws EntityNotFoundException {
        TrabajadorEntity entity = trabajadorList.get(0);
        TrabajadorEntity resultEntity = trabajadorService.getTrabajador(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getId(), resultEntity.getId());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getFoto(), resultEntity.getFoto());
        assertEquals(entity.getCalificacion(), resultEntity.getCalificacion());
        assertEquals(entity.getEnHallOfFame(), resultEntity.getEnHallOfFame());
        assertEquals(entity.getSedes(), resultEntity.getSedes());
        assertEquals(entity.getServicios(), resultEntity.getServicios());
    }

    @Test
    // Obtiene un trabajador con ID inválido -> Operación inválida
    void testGetTrabajadorNoExistente() {
        assertThrows(EntityNotFoundException.class, () -> {
            trabajadorService.getTrabajador(0L);
        });
    }

    @Test
    // Actualiza el trabajador con ID dado, asumiendo todas las condiciones de un trabajador
    void testUpdateTrabajador() throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity entity = trabajadorList.get(0);
        TrabajadorEntity pojoEntity = factory.manufacturePojo(TrabajadorEntity.class);
        pojoEntity.setId(entity.getId());
        // añade una sede a la entidad
        pojoEntity.setSedes(trabajadorList.get(0).getSedes());
        // añade un servicio a la entidad
        pojoEntity.setServicios(trabajadorList.get(0).getServicios());
        trabajadorService.updateTrabajador(entity.getId(), pojoEntity);
        TrabajadorEntity resp = entityManager.find(TrabajadorEntity.class, entity.getId());
        assertEquals(pojoEntity.getId(), resp.getId());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getFoto(), resp.getFoto());
        assertEquals(pojoEntity.getCalificacion(), resp.getCalificacion());
        assertEquals(pojoEntity.getEnHallOfFame(), resp.getEnHallOfFame());
        assertEquals(pojoEntity.getSedes(), resp.getSedes());
        assertEquals(pojoEntity.getServicios(), resp.getServicios());
    }

    @Test
    // actualiza trabajador con ID inválido -> Operación inválida
    void testUpdateTrabajadorIDInvalido() {
        assertThrows(EntityNotFoundException.class, () -> {
            TrabajadorEntity pojoEntity = factory.manufacturePojo(TrabajadorEntity.class);
            pojoEntity.setId(0L);
            trabajadorService.updateTrabajador(0L, pojoEntity);
        });
    }

    @Test
    // actualiza trabajador con nombre cadena vacía -> Operación inválida
    void testUpdateTrabajadorSinNombre() {
        assertThrows(IllegalOperationException.class, () -> {
            TrabajadorEntity entity = trabajadorList.get(0);
            TrabajadorEntity pojoEntity = factory.manufacturePojo(TrabajadorEntity.class);
            // añade una sede a la entidad
            pojoEntity.setSedes(trabajadorList.get(0).getSedes());
            // añade un servicio a la entidad
            pojoEntity.setServicios(trabajadorList.get(0).getServicios());
            // setear el nombre al posible error
            pojoEntity.setNombre("");
            pojoEntity.setId(entity.getId());
            trabajadorService.updateTrabajador(entity.getId(), pojoEntity);
        });
    }

    @Test
    // actualiza trabajador con nombre nulo -> Operación inválida
    void testUpdateTrabajadorSinNombre2() {
        assertThrows(IllegalOperationException.class, () -> {
            TrabajadorEntity entity = trabajadorList.get(0);
            TrabajadorEntity pojoEntity = factory.manufacturePojo(TrabajadorEntity.class);
            // añade una sede a la entidad
            pojoEntity.setSedes(trabajadorList.get(0).getSedes());
            // añade un servicio a la entidad
            pojoEntity.setServicios(trabajadorList.get(0).getServicios());
            // setear el nombre al posible error
            pojoEntity.setNombre(null);
            pojoEntity.setId(entity.getId());
            trabajadorService.updateTrabajador(entity.getId(), pojoEntity);
        });
    }

    @Test
    // actualiza trabajador sin sedes -> Operación inválida
    void testUpdateTrabajadorSinSedes() {
        assertThrows(IllegalOperationException.class, () -> {
            TrabajadorEntity entity = trabajadorList.get(0);
            TrabajadorEntity pojoEntity = factory.manufacturePojo(TrabajadorEntity.class);
            // añade un servicio a la entidad
            pojoEntity.setServicios(trabajadorList.get(0).getServicios());
            // setear las sedes al posible error
            pojoEntity.setSedes(null);
            pojoEntity.setId(entity.getId());
            trabajadorService.updateTrabajador(entity.getId(), pojoEntity);
        });
    }

    @Test
    // actualiza trabajador sin servicios -> Operación inválida
    void testUpdateTrabajadorSinServicios() {
        assertThrows(IllegalOperationException.class, () -> {
            TrabajadorEntity entity = trabajadorList.get(0);
            TrabajadorEntity pojoEntity = factory.manufacturePojo(TrabajadorEntity.class);
            // añade una sede a la entidad
            pojoEntity.setSedes(trabajadorList.get(0).getSedes());
            // setear los servicios al posible error
            pojoEntity.setServicios(null);
            pojoEntity.setId(entity.getId());
            trabajadorService.updateTrabajador(entity.getId(), pojoEntity);
        });
    }

    @Test
    // Elimina un trabajador con ID dado, asumiendo todas las condiciones de un trabajador
    void testDeleteTrabajador() throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity entity = trabajadorList.get(0);
        trabajadorService.deleteTrabajador(entity.getId());
        TrabajadorEntity deleted = entityManager.find(TrabajadorEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    // Eliminar un trabajador con un índice incorrecto/no existente -> Operación inválida
    void testDeleteTrabajadorIDInvalido() {
        assertThrows(EntityNotFoundException.class, () -> {
            trabajadorService.deleteTrabajador(0L);
        });
    }
}
