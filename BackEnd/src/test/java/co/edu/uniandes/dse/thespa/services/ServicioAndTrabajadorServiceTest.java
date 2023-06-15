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

import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import({ ServicioAndTrabajadorService.class })

public class ServicioAndTrabajadorServiceTest {

    /*
     * Instancia de la clase que se quiere probar
     */
    @Autowired
    private ServicioAndTrabajadorService servicioService;

    /*
     * Instancia para manejar la base de datos
     */
    @Autowired
    private TestEntityManager entityManager;

    /*
     * Creación de objetos aleatorios de una manera muy sencilla.
     */
    private PodamFactory factory = new PodamFactoryImpl();

    /*
     * Lista de servicios y trabajadores que se van a utilizar en las pruebas
     */
    private List<ServicioEntity> servicios = new ArrayList<>();
    private List<TrabajadorEntity> trabajadores = new ArrayList<>();

    /*
     * Método que se ejecuta antes de cada método de prueba
     */
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    /*
     * Método que limpia la base de datos
     */
    private void clearData() {
        entityManager.clear();
        entityManager.getEntityManager().createQuery("delete from ServicioEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from TrabajadorEntity").executeUpdate();
    }

    /*
     * Método que inserta datos en la base de datos
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
            entityManager.persist(servicio);
            servicios.add(servicio);
            servicio.setTrabajadores(new ArrayList<>());
            for (int j = 0; j < 3; j++) {
                TrabajadorEntity trabajador = factory.manufacturePojo(TrabajadorEntity.class);
                entityManager.persist(trabajador);
                trabajadores.add(trabajador);
                servicio.getTrabajadores().add(trabajador);
            }
        }

    }

    /*
     * Prueba para agregar un trabajador a un servicio
     */
    @Test
    void addTrabajadorTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        TrabajadorEntity trabajador = factory.manufacturePojo(TrabajadorEntity.class);
        entityManager.persist(trabajador);
        trabajadores.add(trabajador);

        TrabajadorEntity result = servicioService.addTrabajador(servicio.getId(), trabajador.getId());

        assertNotNull(result);
        assertEquals(trabajador, result);
    }

    /*
     * Prueba para agregar un trabajador a un servicio que no existe
     */
    @Test
    void addTrabajadorEmptyServicioTest() throws EntityNotFoundException, IllegalOperationException {
        Long id = 0L;
        TrabajadorEntity trabajador = factory.manufacturePojo(TrabajadorEntity.class);

        entityManager.persist(trabajador);
        trabajadores.add(trabajador);

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.addTrabajador(id, trabajador.getId());
        });
    }

    /*
     * Prueba para agregar un trabajador que no existe a un servicio
     */
    @Test
    void addTrabajadorEmptyTrabajadorTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        Long id = 0L;

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.addTrabajador(servicio.getId(), id);
        });
    }

    /*
     * Prueba para agregar un trabajador que ya está en un servicio
     */
    @Test
    void addTrabajadorAlreadyInServicioTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        TrabajadorEntity trabajador = trabajadores.get(0);

        assertThrows(IllegalOperationException.class, () -> {
            servicioService.addTrabajador(servicio.getId(), trabajador.getId());
        });
    }

    /*
     * Prueba para obtener los trabajadores de un servicio
     */
    @Test
    void getTrabajadoresTest() throws EntityNotFoundException {
        ServicioEntity servicio = servicios.get(0);

        List<TrabajadorEntity> result = servicioService.getTrabajadores(servicio.getId());

        assertNotNull(result);
        assertEquals(servicio.getTrabajadores(), result);
    }

    /*
     * Prueba para obtener los trabajadores de un servicio que no existe
     */
    @Test
    void getTrabajadoresEmptyServicioTest() throws EntityNotFoundException {
        Long id = 0L;

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.getTrabajadores(id);
        });
    }

    /*
     * Prueba para obtener un trabajador de un servicio
     */
    @Test
    void getTrabajadorEmptyTrabajadorTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        Long id = 0L;

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.getTrabajador(servicio.getId(), id);
        });
    }

    /*
     * Prueba para obtener un trabajador de un servicio que no existe
     */
    @Test
    void getTrabajadorEmptyServicioTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        Long id = 0L;

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.getTrabajador(id, servicio.getId());
        });
    }

    /*
     * Prueba para obtener un trabajador que no está en un servicio
     */
    @Test
    void getTrabajadorNotInServicioTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        TrabajadorEntity trabajador = factory.manufacturePojo(TrabajadorEntity.class);
        entityManager.persist(trabajador);
        trabajadores.add(trabajador);

        assertThrows(IllegalOperationException.class, () -> {
            servicioService.getTrabajador(servicio.getId(), trabajador.getId());
        });
    }

    /*
     * Prueba para remover un trabajador de un servicio
     */
    @Test
    void getTrabajadorTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        TrabajadorEntity trabajador = trabajadores.get(0);

        TrabajadorEntity result = servicioService.getTrabajador(servicio.getId(), trabajador.getId());

        assertNotNull(result);
        assertEquals(trabajador, result);
    }

    /*
     * Prueba para remover un trabajador de un servicio que no existe
     */
    @Test
    void removeTrabajadorTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        TrabajadorEntity trabajador = trabajadores.get(0);

        servicioService.removeTrabajador(servicio.getId(), trabajador.getId());

        List<TrabajadorEntity> result = servicioService.getTrabajadores(servicio.getId());
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    /*
     * Prueba para remover un trabajador de un servicio que no existe
     */
    @Test
    void removeTrabajadorEmptyServicioTest() throws EntityNotFoundException, IllegalOperationException {
        Long id = 0L;
        TrabajadorEntity trabajador = factory.manufacturePojo(TrabajadorEntity.class);

        entityManager.persist(trabajador);
        trabajadores.add(trabajador);

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.removeTrabajador(id, trabajador.getId());
        });
    }

    /*
     * Prueba para remover un trabajador que no existe de un servicio
     */
    @Test
    void removeTrabajadorEmptyTrabajadorTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        Long id = 0L;

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.removeTrabajador(servicio.getId(), id);
        });
    }

    /*
     * Prueba para remover un trabajador que no está en un servicio
     */
    @Test
    void removeTrabajadorNotInServicioTest() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);
        TrabajadorEntity trabajador = factory.manufacturePojo(TrabajadorEntity.class);
        entityManager.persist(trabajador);
        trabajadores.add(trabajador);

        assertThrows(IllegalOperationException.class, () -> {
            servicioService.removeTrabajador(servicio.getId(), trabajador.getId());
        });
    }

    /*
     * Prueba para actualizar los trabajadores de un servicio
     */
    @Test
    void updateTrabajadores() throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicios.get(0);

        servicioService.updateTrabajadores(servicio.getId(), trabajadores);

        List<TrabajadorEntity> trabajadores2 = servicioService.getTrabajadores(servicio.getId());
        assertNotNull(trabajadores2);
        assertEquals(trabajadores.size(), trabajadores2.size());
    }

    /*
     * Prueba para actualizar los trabajadores de un servicio que no existe
     */
    @Test
    void updateTrabajadoresEmptyServicio() throws EntityNotFoundException, IllegalOperationException {
        Long id = 0L;

        assertThrows(EntityNotFoundException.class, () -> {
            servicioService.updateTrabajadores(id, trabajadores);
        });
    }
}
