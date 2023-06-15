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
import co.edu.uniandes.dse.thespa.entities.ArticuloDeRopaEntity;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import({ SedeAndArticuloRopaService.class, ArticuloDeRopaService.class })
public class SedeAndArticuloDeRopaServiceTest {
    
    // Servicio que se va a probar
    @Autowired
    private SedeAndArticuloRopaService sedeArticuloDeRopaService;

    // TestEntityManager
    @Autowired
    private TestEntityManager entityManager;

    // PodamFactory para generar datos aleatorios
    private PodamFactory factory = new PodamFactoryImpl();

    // Lista de sedes
    private List<SedeEntity> sedes = new ArrayList<>();

    // Lista de servicios extra
    private List<ArticuloDeRopaEntity> articulosDeRopa = new ArrayList<>();

    // Configuracion inicial de la prueba
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    // Limpia las tablas que están implicadas en la prueba
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
        entityManager.getEntityManager().createQuery("delete from ArticuloDeRopaEntity");
    }

    // Inserta los datos de prueba en la lista de Sedes
    private void insertData() {
        List<ArticuloDeRopaEntity> s = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            SedeEntity sEntity = factory.manufacturePojo(SedeEntity.class);
            ArticuloDeRopaEntity aEntity = factory.manufacturePojo(ArticuloDeRopaEntity.class);
            entityManager.persist(aEntity);
            articulosDeRopa.add(aEntity);
    
            s.add(aEntity);
            sEntity.setArticulosDeRopa(s);
            entityManager.persist(sEntity);
            sedes.add(sEntity);
        }

        ArticuloDeRopaEntity articulo = factory.manufacturePojo(ArticuloDeRopaEntity.class);
        entityManager.persist(articulo);
        articulosDeRopa.add(articulo);
    }
    
    // Prueba para agregar un servicio extra a una sede
    @Test
    void addArticuloDeRopaTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene una sede aleatoria
        SedeEntity sede = sedes.get(0);
        // Se obtiene un articulo aleatorio
        ArticuloDeRopaEntity articuloDeRopa = articulosDeRopa.get(3);
        // Se agrega el articulo a la sede
        ArticuloDeRopaEntity answer = sedeArticuloDeRopaService.addSedeArticuloDeRopa(sede.getId(), articuloDeRopa.getId());
        // Se verifica que el articulo se haya agregado a la sede
        assertNotNull(answer);
        assertEquals(answer.getId(), articuloDeRopa.getId());
    }

    @Test
    // agregar un articulo a una sede no existente -> Entidad no encontrada
    void testAddArticuloToSedeNotExist() throws EntityNotFoundException, IllegalOperationException {
        ArticuloDeRopaEntity articuloDeRopa = articulosDeRopa.get(3);
        assertThrows(EntityNotFoundException.class, () -> {
            sedeArticuloDeRopaService.addSedeArticuloDeRopa(0L, articuloDeRopa.getId());
        });
    }

    @Test
    // agregar un articulo no existente a una sede -> Entidad no encontrada
    void testAddArticuloNotExistToSede() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            SedeEntity sede = sedes.get(0);
            sedeArticuloDeRopaService.addSedeArticuloDeRopa(sede.getId(), 0L);
        });
    }

    @Test
    // agregar un articulo ya agregado a una sede -> Entidad no encontrada
    void testAddArticuloAlreadyExistToSede() throws EntityNotFoundException, IllegalOperationException {
       // Se obtiene una sede aleatoria
       SedeEntity sede = sedes.get(0);
       // Se obtiene un articulo aleatorio
       ArticuloDeRopaEntity articuloDeRopa = articulosDeRopa.get(3);
       // Se agrega el articulo a la sede
       sedeArticuloDeRopaService.addSedeArticuloDeRopa(sede.getId(), articuloDeRopa.getId());
       // Verificar que no se puede agregar el mismo artículo otra vez
       assertThrows(IllegalOperationException.class, () -> {
        sedeArticuloDeRopaService.addSedeArticuloDeRopa(sede.getId(), articuloDeRopa.getId());
        });
    }

	@Test
	void testGetArticulos() throws EntityNotFoundException {
        SedeEntity sede = sedes.get(0);
		List<ArticuloDeRopaEntity> articuloEntities = sedeArticuloDeRopaService.obtenerAllArticulos(sede.getId());

		assertEquals(sede.getArticulosDeRopa().size(), articuloEntities.size());

		for (int i = 0; i < articuloEntities.size(); i++) {
			assertTrue(articuloEntities.contains(articulosDeRopa.get(i)));
		}
	}

	// consultar todos los articulos de una sede que no existe
	@Test
	void testGetAllArticulosInvalidSede() {
		assertThrows(EntityNotFoundException.class, () -> {
			sedeArticuloDeRopaService.obtenerAllArticulos(0L);
		});
	}

    // Consultar un artículo de una sede
    @Test
	void testGetArticulo() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        ArticuloDeRopaEntity articuloEntity = articulosDeRopa.get(0);
        ArticuloDeRopaEntity articulo = sedeArticuloDeRopaService.getArticulo(sede.getId(), articuloEntity.getId());
		assertNotNull(articulo);

        assertEquals(articuloEntity.getId(), articulo.getId());
        assertEquals(articuloEntity.getNombre(), articulo.getNombre());
        assertEquals(articuloEntity.getDescripcion(), articulo.getDescripcion());
        assertEquals(articuloEntity.getPrecio(), articulo.getPrecio());
        assertEquals(articuloEntity.getImagen(), articulo.getImagen());
        assertEquals(articuloEntity.getTalla(), articulo.getTalla());
        assertEquals(articuloEntity.getColor(), articulo.getColor());
        assertEquals(articuloEntity.getNumDisponible(), articulo.getNumDisponible());        
	}

	// consultar un a de un s que no existe
	@Test
	void testGetArticuloInvalidSede() {
		assertThrows(EntityNotFoundException.class, () -> {
			ArticuloDeRopaEntity articuloEntity = articulosDeRopa.get(0);
			sedeArticuloDeRopaService.getArticulo(0L, articuloEntity.getId());
		});
	}

	// consultar un a que no existe de un s
	@Test
	void testGetInvalidArticulo() {
        SedeEntity sede = sedes.get(0);

		assertThrows(EntityNotFoundException.class, () -> {
			sedeArticuloDeRopaService.getArticulo(sede.getId(), 0L);
		});
	}

	// consultar un a no asociado con un s
	@Test
	void testGetArticuloNotAssociatedSede() {
		assertThrows(IllegalOperationException.class, () -> {
			SedeEntity sedeEntity = sedes.get(0);

			ArticuloDeRopaEntity aEntity = factory.manufacturePojo(ArticuloDeRopaEntity.class);
			entityManager.persist(aEntity);

			sedeArticuloDeRopaService.getArticulo(sedeEntity.getId(), aEntity.getId());
		});
	}

	// reemplazar/actualizar los a de un s
	@Test
	void testReplaceArticulosInSede() throws EntityNotFoundException, IllegalOperationException {
		List<ArticuloDeRopaEntity> nuevaLista = new ArrayList<>();
        SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
        entityManager.persist(sede);
		
		for (int i = 0; i < 3; i++) {
			ArticuloDeRopaEntity entity = factory.manufacturePojo(ArticuloDeRopaEntity.class);
			entityManager.persist(entity);
			nuevaLista.add(entity);
		}
		
		sedeArticuloDeRopaService.updateArticulos(sede.getId(), nuevaLista);
		
		List<ArticuloDeRopaEntity> aEntities = entityManager.find(SedeEntity.class, sede.getId()).getArticulosDeRopa();
		for (ArticuloDeRopaEntity item : aEntities) {
			assertTrue(nuevaLista.contains(item));
		}
	}

    // reemplazar/actualizar los a de una s que no existe
    @Test
    void testReplaceArticulosInInvalidSede() {
        assertThrows(EntityNotFoundException.class, () -> {
            List<ArticuloDeRopaEntity> nuevaLista = new ArrayList<>();
		
            for (int i = 0; i < 3; i++) {
                ArticuloDeRopaEntity entity = factory.manufacturePojo(ArticuloDeRopaEntity.class);
                entityManager.persist(entity);
                nuevaLista.add(entity);
            }
            
            sedeArticuloDeRopaService.updateArticulos(0L, nuevaLista);
		});
    }

    // reemplazar/actualizar los a de una s con una lista vacia
    @Test
    void testReplaceArticulosInSedeEmptyList() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        List<ArticuloDeRopaEntity> nuevaLista = new ArrayList<>();
        sedeArticuloDeRopaService.updateArticulos(sede.getId(), nuevaLista);
        List<ArticuloDeRopaEntity> aEntities = entityManager.find(SedeEntity.class, sede.getId()).getArticulosDeRopa();
        assertEquals(0, aEntities.size());
    }


    @Test
    // eliminar un articulo de una sede
    void testDeleteArticuloFromSede() throws EntityNotFoundException, IllegalOperationException {
        SedeEntity sede = sedes.get(0);
        ArticuloDeRopaEntity articuloDeRopa = sede.getArticulosDeRopa().get(0);

        ArticuloDeRopaEntity answer = sedeArticuloDeRopaService.deleteSedeArticuloDeRopa(sede.getId(),
                articuloDeRopa.getId());
        assertNotNull(answer);
        assertEquals(articuloDeRopa.getId(), answer.getId());
    }

    @Test
    // eliminar un articulo a un sede que no existe -> Entidad no encontrada
    void testDeleteArticuloFromSedeNotExist() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            ArticuloDeRopaEntity articulo = articulosDeRopa.get(0);
            sedeArticuloDeRopaService.deleteSedeArticuloDeRopa(0L, articulo.getId());
        });
    }

    @Test
    // eliminar un articulo que no existe a un sede -> Entidad no encontrada
    void testDeleteArticuloNotExistFromSede() throws EntityNotFoundException, IllegalOperationException {
        assertThrows(EntityNotFoundException.class, () -> {
            SedeEntity sede = sedes.get(0);
            sedeArticuloDeRopaService.deleteSedeArticuloDeRopa(sede.getId(), 0L);
        });
    }

    // Prueba para eliminar un articulo de ropa que no está agregado de una sede
    // Se espera que se lance una excepción de tipo IllegalOperationException
    @Test
    void deleteArticuloNotContainedTest() throws EntityNotFoundException, IllegalOperationException {
        // Se obtiene una sede aleatoria
        SedeEntity sede = sedes.get(0);
        // Se obtiene un articulo aleatorio
        ArticuloDeRopaEntity articulo = factory.manufacturePojo(ArticuloDeRopaEntity.class);
        entityManager.persist(articulo);
        // Se elimina el articulo de la sede
        assertThrows(IllegalOperationException.class, () -> {
            sedeArticuloDeRopaService.deleteSedeArticuloDeRopa(sede.getId(), articulo.getId());
        });
    }
}
