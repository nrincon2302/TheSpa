package co.edu.uniandes.dse.thespa.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.thespa.entities.ArticuloDeRopaEntity;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.repositories.ArticuloDeRopaRepository;
import co.edu.uniandes.dse.thespa.repositories.SedeRepository;
import lombok.extern.slf4j.Slf4j;

//Author -> @Juan Coronel

@Slf4j
@Service
public class SedeAndArticuloRopaService {

    private static final String SEDE_NOT_FOUND = "SEDE_NOT_FOUND";
    private static final String ARTICULO_NOT_FOUND = "ARTICULO_NOT_FOUND";

    // Inyeccion de dependencias -> Repositorio Sede
    @Autowired
    SedeRepository sedeRepo;

    // Inyeccion de dependencias -> Repositorio Pack de Servicios
    @Autowired
    ArticuloDeRopaRepository articuloRepo;

    // Obtener todos los articulos de ropa de una sede
    @Transactional
    public List<ArticuloDeRopaEntity> obtenerAllArticulos(Long sedeId) throws EntityNotFoundException {
		Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
		if (sedeEntity.isEmpty())
			throw new EntityNotFoundException(SEDE_NOT_FOUND);

		return sedeEntity.get().getArticulosDeRopa();
	}

    // Añadir un Pack de servicios a la sede
    @Transactional
    public ArticuloDeRopaEntity addSedeArticuloDeRopa(Long sedeId, Long articuloDeRopaId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de añadir a la sede un ArticuloDeRopa con con id = {0}", articuloDeRopaId);
        Optional<ArticuloDeRopaEntity> packEntity = articuloRepo.findById(articuloDeRopaId);
        if (packEntity.isEmpty()) {
            throw new EntityNotFoundException(ARTICULO_NOT_FOUND);
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }

        // revisa si el pack ya esta en la sede, si esta lanza una
        // IllegalOperationException
        if (sedeEntity.get().getArticulosDeRopa().contains(packEntity.get())) {
            throw new IllegalOperationException("ARTICULO_ALREADY_EXISTS");
        }

        List<ArticuloDeRopaEntity> articuloDeRopaS = sedeEntity.get().getArticulosDeRopa();
        articuloDeRopaS.add(packEntity.get());

        sedeEntity.get().setArticulosDeRopa(articuloDeRopaS);

        log.info("Termina proceso de añadir a la sede un Articulo con con id = {0}", sedeId);

        return packEntity.get();
    }

    // obtiene un articulo de una sede dado el id de la sede y el id del articulo
    @Transactional
    public ArticuloDeRopaEntity getArticulo(Long sedeid, Long articuloID)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Consultando el articulo con id = {} de la sede con id = {}", articuloID, sedeid);

        // Busca la sede
        Optional<SedeEntity> sedeBuscado = sedeRepo.findById(sedeid);
        if (sedeBuscado.isEmpty()) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }

        // Busca el articulo
        Optional<ArticuloDeRopaEntity> articulo = articuloRepo.findById(articuloID);
        if (articulo.isEmpty()) {
            throw new EntityNotFoundException(ARTICULO_NOT_FOUND);
        }

        // Verifica que el articulo este en la sede
        if (!sedeBuscado.get().getArticulosDeRopa().contains(articulo.get())) {
            throw new IllegalOperationException("ARTICULO_NOT_FOUND_IN_SEDE");
        }

        log.info("Articulo encontrado");

        // Retorna el articulo
        return articulo.get();
    }

    // Eliminar un articulo de ropa de la sede
    @Transactional
    public ArticuloDeRopaEntity deleteSedeArticuloDeRopa(Long sedeId, Long articuloDeRopaId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de remover a la sede un ArticuloDeRopa con con id = {0}", articuloDeRopaId);
        Optional<ArticuloDeRopaEntity> articuloEntity = articuloRepo.findById(articuloDeRopaId);
        if (articuloEntity.isEmpty()) {
            throw new EntityNotFoundException(ARTICULO_NOT_FOUND);
        }

        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }

        // revisa si el articulo no esta en la sede, si no esta lanza una
        // IllegalOperationException
        if (!sedeEntity.get().getArticulosDeRopa().contains(articuloEntity.get())) {
            throw new IllegalOperationException("ARTICULO_NOT_FOUND_IN_CURRENT_SEDE");
        }

        List<ArticuloDeRopaEntity> articulos = sedeEntity.get().getArticulosDeRopa();
        articulos.remove(articuloEntity.get());

        sedeEntity.get().setArticulosDeRopa(articulos);

        log.info("Termina proceso de eliminar de la sede un ArticuloDeRopa con con id = {0}", sedeId);

        return articuloEntity.get();
    }

    // actualiza la lista de articulos de ropa de una sede
    @Transactional
    public List<ArticuloDeRopaEntity> updateArticulos(Long sedeId, List<ArticuloDeRopaEntity> articulos)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de actualizar la lista de articulos de ropa de la sede con id = {0}", sedeId);
        Optional<SedeEntity> sedeEntity = sedeRepo.findById(sedeId);
        if (sedeEntity.isEmpty()) {
            throw new EntityNotFoundException(SEDE_NOT_FOUND);
        }

        // revisa que todos los articulos existan
        for (ArticuloDeRopaEntity articulo : articulos) {
            Optional<ArticuloDeRopaEntity> articuloEntity = articuloRepo.findById(articulo.getId());
            if (articuloEntity.isEmpty()) {
                throw new EntityNotFoundException(ARTICULO_NOT_FOUND);
            }
        }

        // obtiene todos los articulos en articulos, y los pone en una lista
        List<ArticuloDeRopaEntity> articulosActuales = new ArrayList<>();
        for (ArticuloDeRopaEntity articulo : articulos) {
            Optional<ArticuloDeRopaEntity> articuloEntity = articuloRepo.findById(articulo.getId());
            if (articuloEntity.isPresent()) {
                articulosActuales.add(articuloEntity.get());
            }
        }

        sedeEntity.get().setArticulosDeRopa(articulosActuales);

        log.info("Termina proceso de actualizar la lista de articulos de ropa de la sede con id = {0}", sedeId);

        return sedeEntity.get().getArticulosDeRopa();
    }
}
