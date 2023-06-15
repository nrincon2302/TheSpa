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

import co.edu.uniandes.dse.thespa.entities.ArticuloDeRopaEntity;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;

@DataJpaTest
@Transactional
@Import(ArticuloDeRopaService.class)
public class ArticuloDeRopaServiceTest {

    // Servicio que se va a probar
    @Autowired
    private ArticuloDeRopaService articuloDeRopaService;

    //
    @Autowired
    private TestEntityManager entityManager;

    // PodamFactory para generar datos aleatorios
    private PodamFactory factory = new PodamFactoryImpl();

    // Lista de articulos de ropa
    private List<ArticuloDeRopaEntity> listaArticulosDeRopa = new ArrayList<>();

    // Limpia la base de datos
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    // Limpia la base de datos de los datos de prueba
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ArticuloDeRopaEntity");
        entityManager.getEntityManager().createQuery("delete from SedeEntity");
    }

    // Inserta los datos de prueba en la lista de articulos de ropa
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ArticuloDeRopaEntity entity = factory.manufacturePojo(ArticuloDeRopaEntity.class);
            // entity.setSede(null);
            entityManager.persist(entity);
            listaArticulosDeRopa.add(entity);
        }
        // crea una sede para asociarla a los articulos de ropa
        SedeEntity sede = factory.manufacturePojo(SedeEntity.class);
        entityManager.persist(sede);
        // Asocia la sede a los articulos de ropa
        for (ArticuloDeRopaEntity articuloDeRopa : listaArticulosDeRopa) {
            articuloDeRopa.setSede(sede);
        }

    }

    // Crea un articulo de ropa
    @Test
    void testCreateArticuloDeRopa() throws IllegalOperationException {
        ArticuloDeRopaEntity newEntity = factory.manufacturePojo(ArticuloDeRopaEntity.class);
        // añade una sede a la entidad
        newEntity.setSede(listaArticulosDeRopa.get(0).getSede());
        ArticuloDeRopaEntity result = articuloDeRopaService.createArticuloDeRopa(newEntity);
        assertNotNull(result);
        ArticuloDeRopaEntity entity = entityManager.find(ArticuloDeRopaEntity.class, result.getId());
        assertEquals(newEntity.getTalla(), entity.getTalla());
        assertEquals(newEntity.getColor(), entity.getColor());
        assertEquals(newEntity.getNumDisponible(), entity.getNumDisponible());
        assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getDescripcion(), entity.getDescripcion());
        assertEquals(newEntity.getPrecio(), entity.getPrecio());
        assertEquals(newEntity.getImagen(), entity.getImagen());
        assertEquals(newEntity.getSede(), entity.getSede());

    }

    // hace un test que espera un error al crear un articulo de ropa sin nombre
    @Test
    void testCreateArticuloDeRopaSinNombre() {
        ArticuloDeRopaEntity newEntity = factory.manufacturePojo(ArticuloDeRopaEntity.class);
        newEntity.setNombre(null);
        assertThrows(IllegalOperationException.class, () -> {
            articuloDeRopaService.createArticuloDeRopa(newEntity);
        });
    }

    // hace un test que espera un error al crear un articulo de ropa sin precio
    @Test
    void testCreateArticuloDeRopaSinPrecio() {
        ArticuloDeRopaEntity newEntity = factory.manufacturePojo(ArticuloDeRopaEntity.class);
        newEntity.setPrecio(null);
        assertThrows(IllegalOperationException.class, () -> {
            articuloDeRopaService.createArticuloDeRopa(newEntity);
        });
    }

    // hace un test que espera un error al crear un articulo de ropa sin sede
    @Test
    void testCreateArticuloDeRopaSinSede() {
        ArticuloDeRopaEntity newEntity = factory.manufacturePojo(ArticuloDeRopaEntity.class);
        newEntity.setSede(null);
        assertThrows(IllegalOperationException.class, () -> {
            articuloDeRopaService.createArticuloDeRopa(newEntity);
        });
    }

    // Obtiene todos los articulos de ropa
    @Test
    void testGetAllArticulosDeRopa() {
        List<ArticuloDeRopaEntity> list = articuloDeRopaService.getArticulosDeRopa();
        assertEquals(listaArticulosDeRopa.size(), list.size());
        for (ArticuloDeRopaEntity entity : list) {
            boolean found = false;
            for (ArticuloDeRopaEntity storedEntity : listaArticulosDeRopa) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    // Obtiene un articulo de ropa
    @Test
    void testGetArticuloDeRopa() throws EntityNotFoundException {
        ArticuloDeRopaEntity entity = listaArticulosDeRopa.get(0);
        ArticuloDeRopaEntity resultEntity = articuloDeRopaService.getArticuloDeRopa(entity.getId());
        assertNotNull(resultEntity);
        assertEquals(entity.getTalla(), resultEntity.getTalla());
        assertEquals(entity.getColor(), resultEntity.getColor());
        assertEquals(entity.getNumDisponible(), resultEntity.getNumDisponible());
        assertEquals(entity.getNombre(), resultEntity.getNombre());
        assertEquals(entity.getDescripcion(), resultEntity.getDescripcion());
        assertEquals(entity.getPrecio(), resultEntity.getPrecio());
        assertEquals(entity.getImagen(), resultEntity.getImagen());
        // assertEquals(entity.getSede(), resultEntity.getSede());
    }

    // Obtiene un articulo de ropa que no existe
    @Test
    void testGetArticuloDeRopaNoExiste() {
        assertThrows(EntityNotFoundException.class, () -> {
            articuloDeRopaService.getArticuloDeRopa(0L);
        });
    }

    // Actualiza un articulo de ropa
    @Test
    void testUpdateArticuloDeRopa() throws EntityNotFoundException {
        ArticuloDeRopaEntity entity = listaArticulosDeRopa.get(0);
        ArticuloDeRopaEntity pojoEntity = factory.manufacturePojo(ArticuloDeRopaEntity.class);
        pojoEntity.setId(entity.getId());
        articuloDeRopaService.updateArticuloDeRopaEntity(pojoEntity.getId(), pojoEntity);
        ArticuloDeRopaEntity resp = entityManager.find(ArticuloDeRopaEntity.class, entity.getId());
        assertEquals(pojoEntity.getTalla(), resp.getTalla());
        assertEquals(pojoEntity.getColor(), resp.getColor());
        assertEquals(pojoEntity.getNumDisponible(), resp.getNumDisponible());
        assertEquals(pojoEntity.getNombre(), resp.getNombre());
        assertEquals(pojoEntity.getDescripcion(), resp.getDescripcion());
        assertEquals(pojoEntity.getPrecio(), resp.getPrecio());
        assertEquals(pojoEntity.getImagen(), resp.getImagen());
        // assertEquals(pojoEntity.getSede(), resp.getSede());
    }

    // Actualiza un articulo de ropa que no existe
    @Test
    void testUpdateArticuloDeRopaNoExiste() {
        assertThrows(EntityNotFoundException.class, () -> {
            ArticuloDeRopaEntity pojoEntity = factory.manufacturePojo(ArticuloDeRopaEntity.class);
            articuloDeRopaService.updateArticuloDeRopaEntity(0L, pojoEntity);
        });
    }

    // Elimina un articulo de ropa
    @Test
    void testDeleteArticuloDeRopa() throws EntityNotFoundException {
        ArticuloDeRopaEntity entity = listaArticulosDeRopa.get(0);
        articuloDeRopaService.deleteArticuloDeRopa(entity.getId());
        ArticuloDeRopaEntity deleted = entityManager.find(ArticuloDeRopaEntity.class, entity.getId());
        assertNull(deleted);
    }

    // Elimina un articulo de ropa que no existe
    @Test
    void testDeleteArticuloDeRopaNoExiste() {
        assertThrows(EntityNotFoundException.class, () -> {
            articuloDeRopaService.deleteArticuloDeRopa(0L);
        });
    }

}
