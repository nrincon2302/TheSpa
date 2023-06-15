package co.edu.uniandes.dse.thespa.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import co.edu.uniandes.dse.thespa.entities.UbicacionEntity;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;

import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import({ SedeService.class, UbicacionAndSedeService.class })
public class UbicacionAndSedeTest {

    // Servicio a probar
    @Autowired
    private UbicacionAndSedeService ubicacionAndSedeService;

    // TestEntityManager para crear entidades
    @Autowired
    private TestEntityManager entityManager;

    // PodamFactory para crear entidades
    private PodamFactory factory = new PodamFactoryImpl();

    // Lista de Ubicaciones
    private List<UbicacionEntity> dataUbicacion = new ArrayList<>();

    // Lista de Sedes
    private List<SedeEntity> dataSede = new ArrayList<>();

    // Configuracion inicial
    @BeforeEach
    public void setUp() {
        clearData();
        // Crear 3 Ubicaciones
        for (int i = 0; i < 3; i++) {
            // crea nuevas ubicaciones con datos aleatorios
            // su latitud debe ser un numero entre -90 y 90
            // su longitud debe ser un numero entre -180 y 180

            UbicacionEntity ubicacion = factory.manufacturePojo(UbicacionEntity.class);
            ubicacion.setLatitud((double) (Math.random() * 180 - 90));
            ubicacion.setLongitud((double) (Math.random() * 360 - 180));
            entityManager.persist(ubicacion);
            dataUbicacion.add(ubicacion);
        }

        // Crear 3 Sedes
        for (int i = 0; i < 3; i++) {
            SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
            entityManager.persist(sede);
            dataSede.add(sede);
        }
    }

    // Limpia la base de datos
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from UbicacionEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from SedeEntity").executeUpdate();
    }

    // Prueba para asignar una sede a una ubicacion
    @Test
    public void addSedeTest() throws EntityNotFoundException, IllegalOperationException {
        // Seleccionar una ubicacion
        UbicacionEntity ubicacion = dataUbicacion.get(0);
        // Seleccionar una sede
        SedeEntity sede = dataSede.get(0);
        // Asignar la sede a la ubicacion
        ubicacionAndSedeService.asignarSede(ubicacion.getId(), sede.getId());
        // Verificar que la sede se haya asignado correctamente
        assertEquals(sede, ubicacionAndSedeService.obtenerSede(ubicacion.getId()));
    }

    // Prueba para obtener una sede de una ubicacion
    @Test
    public void getSedeTest() throws EntityNotFoundException, IllegalOperationException {
        // Seleccionar una ubicacion
        UbicacionEntity ubicacion = dataUbicacion.get(0);
        // Seleccionar una sede
        SedeEntity sede = dataSede.get(0);
        // Asignar la sede a la ubicacion
        ubicacionAndSedeService.asignarSede(ubicacion.getId(), sede.getId());
        // Verificar que la sede se haya asignado correctamente
        assertEquals(sede, ubicacionAndSedeService.obtenerSede(ubicacion.getId()));
    }

    // Prueba para obtener una sede de una ubicacion que no tiene sede, espera una
    // EntityNotFoundException
    @Test
    public void getSedeNullTest() throws EntityNotFoundException, IllegalOperationException {
        // Seleccionar una ubicacion
        UbicacionEntity ubicacion = dataUbicacion.get(0);
        // Verificar que la sede se haya asignado correctamente
        assertThrows(EntityNotFoundException.class, () -> {
            ubicacionAndSedeService.obtenerSede(ubicacion.getId());
        });
    }

    // Prueba para obtener una sede de una ubicacion que no existe
    @Test
    public void getSedeNullTest2() throws EntityNotFoundException, IllegalOperationException {
        // Seleccionar una ubicacion
        UbicacionEntity ubicacion = dataUbicacion.get(0);
        // Seleccionar una sede
        SedeEntity sede = dataSede.get(0);
        // Asignar la sede a la ubicacion
        ubicacionAndSedeService.asignarSede(ubicacion.getId(), sede.getId());
        // Verificar que la sede se haya asignado correctamente
        assertThrows(EntityNotFoundException.class, () -> {
            ubicacionAndSedeService.obtenerSede(ubicacion.getId() + 100);
        });
    }

    // Prueba para eliminar una sede de una ubicacion
    @Test
    public void deleteSedeTest() throws EntityNotFoundException, IllegalOperationException {
        // Seleccionar una ubicacion
        UbicacionEntity ubicacion = dataUbicacion.get(0);
        // Seleccionar una sede
        SedeEntity sede = dataSede.get(0);
        // Asignar la sede a la ubicacion
        ubicacionAndSedeService.asignarSede(ubicacion.getId(), sede.getId());
        // Verificar que la sede se haya asignado correctamente
        assertEquals(sede, ubicacionAndSedeService.obtenerSede(ubicacion.getId()));
        // Eliminar la sede de la ubicacion
        ubicacionAndSedeService.eliminarSede(ubicacion.getId());
        // Verificar que la sede se haya eliminado correctamente, espera una
        // EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> {
            ubicacionAndSedeService.obtenerSede(ubicacion.getId());
        });
    }

    // Prueba para eliminar una sede de una ubicacion que no tiene sede
    @Test
    public void deleteSedeNullTest() throws EntityNotFoundException, IllegalOperationException {
        // Seleccionar una ubicacion
        UbicacionEntity ubicacion = dataUbicacion.get(0);
        // Verificar que la ubicacion no tenga sede
        assertThrows(EntityNotFoundException.class, () -> {
            ubicacionAndSedeService.obtenerSede(ubicacion.getId());
        });
        // Eliminar la sede de la ubicacion, espera una IllegalOperationException
        assertThrows(IllegalOperationException.class, () -> {
            ubicacionAndSedeService.eliminarSede(ubicacion.getId());
        });
    }

    // Prueba para eliminar una sede de una ubicacion que no existe
    @Test
    public void deleteSedeNullTest2() throws EntityNotFoundException, IllegalOperationException {
        // Seleccionar una ubicacion
        UbicacionEntity ubicacion = dataUbicacion.get(0);
        // Seleccionar una sede
        SedeEntity sede = dataSede.get(0);
        // Asignar la sede a la ubicacion
        ubicacionAndSedeService.asignarSede(ubicacion.getId(), sede.getId());
        // Verificar que la sede se haya asignado correctamente
        assertEquals(sede, ubicacionAndSedeService.obtenerSede(ubicacion.getId()));
        // Eliminar la sede de la ubicacion
        ubicacionAndSedeService.eliminarSede(ubicacion.getId());
        // Verificar que la sede se haya eliminado correctamente
        assertThrows(EntityNotFoundException.class, () -> {
            ubicacionAndSedeService.eliminarSede(ubicacion.getId() + 100);
        });
    }

    // prueba para asignar una sede a una ubicacion que no existe
    @Test
    public void addSedeNullTest() throws EntityNotFoundException, IllegalOperationException {
        // Seleccionar una ubicacion
        UbicacionEntity ubicacion = dataUbicacion.get(0);
        // Seleccionar una sede
        SedeEntity sede = dataSede.get(0);
        // Asignar la sede a la ubicacion
        ubicacionAndSedeService.asignarSede(ubicacion.getId(), sede.getId());
        // Verificar que la sede se haya asignado correctamente
        assertEquals(sede, ubicacionAndSedeService.obtenerSede(ubicacion.getId()));
        // Eliminar la sede de la ubicacion
        ubicacionAndSedeService.eliminarSede(ubicacion.getId());
        // Verificar que la sede se haya eliminado correctamente
        assertThrows(EntityNotFoundException.class, () -> {
            ubicacionAndSedeService.asignarSede(ubicacion.getId() + 100, sede.getId());
        });
    }

    // prueba para asignar una sede que no existe a una ubicacion
    @Test
    public void addSedeNullTest2() throws EntityNotFoundException, IllegalOperationException {
        // Seleccionar una ubicacion
        UbicacionEntity ubicacion = dataUbicacion.get(0);
        // Seleccionar una sede
        SedeEntity sede = dataSede.get(0);
        // Asignar la sede a la ubicacion
        ubicacionAndSedeService.asignarSede(ubicacion.getId(), sede.getId());
        // Verificar que la sede se haya asignado correctamente
        assertEquals(sede, ubicacionAndSedeService.obtenerSede(ubicacion.getId()));
        // Eliminar la sede de la ubicacion
        ubicacionAndSedeService.eliminarSede(ubicacion.getId());
        // Verificar que la sede se haya eliminado correctamente
        assertThrows(EntityNotFoundException.class, () -> {
            ubicacionAndSedeService.asignarSede(ubicacion.getId(), sede.getId() + 100);
        });
    }

}
