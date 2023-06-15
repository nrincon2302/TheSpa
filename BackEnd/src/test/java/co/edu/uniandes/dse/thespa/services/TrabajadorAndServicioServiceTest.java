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
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import({ TrabajadorAndServicioService.class, ServicioService.class })
public class TrabajadorAndServicioServiceTest {
    // Servicio que se va a probar
    @Autowired
    private TrabajadorAndServicioService trabajadorandServicioService;

    // TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // PodamFactory para generar datos aleatorios
    private PodamFactory factory = new PodamFactoryImpl();

    // Lista de trabajadores
    private List<TrabajadorEntity> trabajadores = new ArrayList<>();

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
        entityManager.getEntityManager().createQuery("delete from TrabajadorEntity");
        entityManager.getEntityManager().createQuery("delete from ServicioEntity");

    }

    // Inserta los datos de prueba en la lista de Trabajadores
    private void insertData() {
        List<ServicioEntity> s = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            TrabajadorEntity tEntity = factory.manufacturePojo(TrabajadorEntity.class);
            ServicioEntity sEntity = factory.manufacturePojo(ServicioEntity.class);
            entityManager.persist(sEntity);
            servicios.add(sEntity);

            s.add(sEntity);
            tEntity.setServicios(s);
            entityManager.persist(tEntity);
            trabajadores.add(tEntity);
        }

        ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
        entityManager.persist(servicio);
        servicios.add(servicio);
    }

    @Test
    // agregar un servicios a un trabajador
    void testAddServiciosToTrabajador() throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity trabajador = trabajadores.get(0);
        ServicioEntity servicio = servicios.get(3);

        ServicioEntity answer = trabajadorandServicioService.addServicioToTrabajador(trabajador.getId(),
                servicio.getId());
        assertNotNull(answer);
        assertEquals(servicio.getId(), answer.getId());
    }

    @Test
    // agregar un servicio a un trabajador que no existe -> Entidad no encontrada
    void testAddServicioToTrabajadorNotExist() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            ServicioEntity servicio = servicios.get(0);
            trabajadorandServicioService.addServicioToTrabajador(0L, servicio.getId());
        });
    }

    @Test
    // agregar un servicio no existente a un trabajador -> Entidad no encontrada
    void testAddServicioNotExistToTrabajador() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            TrabajadorEntity trabajador = trabajadores.get(0);
            trabajadorandServicioService.addServicioToTrabajador(trabajador.getId(), 0L);
        });
    }

    @Test
    // agregar un servicio ya existente en un trabajador -> Operación ilegal
    void testAddServicioToTrabajadorAlreadyExist() throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity trabajador = trabajadores.get(0);
        ServicioEntity servicio = trabajador.getServicios().get(0);

        assertThrows(IllegalOperationException.class, () -> {
            trabajadorandServicioService.addServicioToTrabajador(trabajador.getId(), servicio.getId());
        });
    }

    // consultar todos los servicios de un trabajador
	@Test
	void testGetServicios() throws EntityNotFoundException {
        TrabajadorEntity trabajador = trabajadores.get(0);
		List<ServicioEntity> servicioEntities = trabajadorandServicioService.getServicios(trabajador.getId());

		assertEquals(trabajador.getServicios().size(), servicioEntities.size());

		for (int i = 0; i < servicioEntities.size(); i++) {
			assertTrue(servicioEntities.contains(servicios.get(i)));
		}
	}

	// consultar los servicios de un trabajador que no existe
	@Test
	void testGetServiciosInvalidTrabajador() {
		assertThrows(EntityNotFoundException.class, () -> {
			trabajadorandServicioService.getServicios(0L);
		});
	}

	// consultar un servicio de un trabajador
	@Test
	void testGetServicio() throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity trabajador = trabajadores.get(0);
        ServicioEntity servicioEntity = servicios.get(0);
		ServicioEntity servicio = trabajadorandServicioService.getServicio(trabajador.getId(), servicioEntity.getId());
		assertNotNull(servicio);

        assertEquals(servicioEntity.getId(), servicio.getId());
        assertEquals(servicioEntity.getDuracion(), servicio.getDuracion());
        assertEquals(servicioEntity.getRestricciones(), servicio.getRestricciones());
        assertEquals(servicioEntity.getDisponible(), servicio.getDisponible());
        assertEquals(servicioEntity.getHoraInicio(), servicio.getHoraInicio());
        assertEquals(servicioEntity.getPrecio(), servicio.getPrecio());
        assertEquals(servicioEntity.getNombre(), servicio.getNombre());
        assertEquals(servicioEntity.getSede(), servicio.getSede());
        assertEquals(servicioEntity.getTrabajadores(), servicio.getTrabajadores());
        assertEquals(servicioEntity.getPacksDeServicios(), servicio.getPacksDeServicios());
	}

	// consultar un servicio de un trabajador que no existe
	@Test
	void testGetServicioInvalidTrabajador() {
		assertThrows(EntityNotFoundException.class, () -> {
			ServicioEntity servicioEntity = servicios.get(0);
			trabajadorandServicioService.getServicio(0L, servicioEntity.getId());
		});
	}

	// consultar un servicio que no existe de un trabajador
	@Test
	void testGetInvalidServicio() {
        TrabajadorEntity trabajador = trabajadores.get(0);

		assertThrows(EntityNotFoundException.class, () -> {
			trabajadorandServicioService.getServicio(trabajador.getId(), 0L);
		});
	}

	// consultar un servicio no asociado con un trabajador
	@Test
	void testGetServicioNotAssociatedTrabajador() {
		assertThrows(IllegalOperationException.class, () -> {
			TrabajadorEntity trabajadorEntity = trabajadores.get(0);

			ServicioEntity servicioEntity = factory.manufacturePojo(ServicioEntity.class);
			entityManager.persist(servicioEntity);

			trabajadorandServicioService.getServicio(trabajadorEntity.getId(), servicioEntity.getId());
		});
	}

	// reemplazar/actualizar los servicios de un trabajador
	@Test
	void testReplaceServiciosTrabajadorNotInServicio() throws EntityNotFoundException, IllegalOperationException {
		List<ServicioEntity> nuevaLista = new ArrayList<>();
        // Crear un trabajador y persistirlo
        TrabajadorEntity trabajador = factory.manufacturePojo(TrabajadorEntity.class);
        entityManager.persist(trabajador);
		// Crear servicios nuevos y agregarlos a la lista
		for (int i = 0; i < 3; i++) {
			ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
			entityManager.persist(entity);
			nuevaLista.add(entity);
		}
		// Llamar al servicio para reemplazar la lista de servicios
		trabajadorandServicioService.replaceServicios(trabajador.getId(), nuevaLista);
		
		List<ServicioEntity> servicioEntities = entityManager.find(TrabajadorEntity.class, trabajador.getId()).getServicios();
		for (ServicioEntity item : servicioEntities) {
			assertTrue(nuevaLista.contains(item));
		}
	}

 	// reemplazar/actualizar un servicios no asociado a un trabajador
     @Test
     void testReplaceServicios() throws EntityNotFoundException, IllegalOperationException {
         List<ServicioEntity> nuevaLista = new ArrayList<>();
         // Acceder a un trabajador existente
         TrabajadorEntity trabajador = trabajadores.get(0);
         // Crear servicios nuevos y agregarlos a la lista
         for (int i = 0; i < 3; i++) {
             ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
             entityManager.persist(entity);
             nuevaLista.add(entity);
         }
         // Llamar al servicio para reemplazar la lista de servicios
         trabajadorandServicioService.replaceServicios(trabajador.getId(), nuevaLista);
         
         List<ServicioEntity> servicioEntities = entityManager.find(TrabajadorEntity.class, trabajador.getId()).getServicios();
         for (ServicioEntity item : servicioEntities) {
             assertTrue(nuevaLista.contains(item));
         }
     }
	
	// actualizar los servicios de un trabajador que no existe
	@Test
	void testReplaceServiciosInvalidTrabajador() {
		assertThrows(EntityNotFoundException.class, () -> {
            List<ServicioEntity> nuevaLista = new ArrayList<>();
            // Crear servicios nuevos y agregarlos a la lista
            for (int i = 0; i < 3; i++) {
                ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
                entityManager.persist(entity);
                nuevaLista.add(entity);
            }
            // Llamar al servicio para reemplazar la lista de servicios
            trabajadorandServicioService.replaceServicios(0L, nuevaLista);
		});
	}

	// actualizar los servicios no existentes de un trabajador
	@Test
	void testReplaceInvalidServicios() {
        TrabajadorEntity trabajador = trabajadores.get(0);

		assertThrows(EntityNotFoundException.class, () -> {
			List<ServicioEntity> nuevaLista = new ArrayList<>();
			ServicioEntity entity = factory.manufacturePojo(ServicioEntity.class);
            // Setear el id de la entidad para que sea 0L y no sea válido
			entity.setId(0L);
			nuevaLista.add(entity);
			trabajadorandServicioService.replaceServicios(trabajador.getId(), nuevaLista);
		});
	}

    @Test
    // eliminar un servicio de un trabajador
    void testDeleteServicioFromTrabajador() throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity trabajador = trabajadores.get(0);
        ServicioEntity servicio = trabajador.getServicios().get(0);

        ServicioEntity answer = trabajadorandServicioService.deleteServicioTrabajador(trabajador.getId(),
                servicio.getId());
        assertNotNull(answer);
        assertEquals(servicio.getId(), answer.getId());
    }

    @Test
    // eliminar un servicio a un trabajador que no existe -> Entidad no encontrada
    void testDeleteServicioFromTrabajadorNotExist() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            ServicioEntity servicio = servicios.get(0);
            trabajadorandServicioService.deleteServicioTrabajador(0L, servicio.getId());
        });
    }

    @Test
    // eliminar un servicio no existente a un trabajador -> Entidad no encontrada
    void testDeleteServicioNotExistFromTrabajador() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            TrabajadorEntity trabajador = trabajadores.get(0);
            trabajadorandServicioService.deleteServicioTrabajador(trabajador.getId(), 0L);
        });
    }

    @Test
    // eliminar un servicio que no está asociado a un trabajador -> Operación ilegal
    void testDeleteNotRelatedServicioFromTrabajador() throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity trabajador = trabajadores.get(0);
        ServicioEntity servicio = factory.manufacturePojo(ServicioEntity.class);
        entityManager.persist(servicio);

        assertThrows(IllegalOperationException.class, () -> {
            trabajadorandServicioService.deleteServicioTrabajador(trabajador.getId(), servicio.getId());
        });
    }

}
