package co.edu.uniandes.dse.thespa.controllers;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.services.SedeAndArticuloRopaService;
import co.edu.uniandes.dse.thespa.dto.ArticuloDeRopaDTO;
import co.edu.uniandes.dse.thespa.dto.ArticuloDeRopaDetailDTO;
import co.edu.uniandes.dse.thespa.entities.ArticuloDeRopaEntity;

@RestController
@RequestMapping("/sedes")
public class SedeAndArticuloRopaController {

	@Autowired
	private SedeAndArticuloRopaService saR;

	@Autowired
	private ModelMapper modelMapper;

	// Asocia un articulo a una sede
	@PostMapping(value = "{sedeId}/articulosDeRopa/{articuloId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ArticuloDeRopaDTO addArticuloToSede(@PathVariable("articuloId") Long articuloId,
			@PathVariable("sedeId") Long sedeId)
			throws EntityNotFoundException, IllegalOperationException {
		ArticuloDeRopaEntity articuloEntity = saR.addSedeArticuloDeRopa(sedeId, articuloId);
		return modelMapper.map(articuloEntity, ArticuloDeRopaDTO.class);
	}

	// Busca un articulo de ropa dentro de los asociados a una sede
	@GetMapping(value = "{sedeId}/articulosDeRopa/{articuloId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ArticuloDeRopaDetailDTO getServicio(@PathVariable("sedeId") Long sedeId,
			@PathVariable("articuloId") Long articuloId)
			throws EntityNotFoundException, IllegalOperationException {
		ArticuloDeRopaEntity articulo = saR.getArticulo(sedeId, articuloId);
		return modelMapper.map(articulo, ArticuloDeRopaDetailDTO.class);
	}

	// Busca todos los articulos asociados a una sede
	@GetMapping(value = "{sedeId}/articulosDeRopa")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ArticuloDeRopaDetailDTO> getArticulos(@PathVariable("sedeId") Long sedeId) throws EntityNotFoundException {
		List<ArticuloDeRopaEntity> articulos = saR.obtenerAllArticulos(sedeId);
		return modelMapper.map(articulos, new TypeToken<List<ArticuloDeRopaDetailDTO>>() {
		}.getType());
	}

	// Elimina la conexión entre una sede y un articulo
	@DeleteMapping(value = "{sedeId}/articulosDeRopa/{articuloId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteArticuloFromSede(@PathVariable("sedeId") Long sedeId,
			@PathVariable("articuloId") Long articuloId)
			throws EntityNotFoundException, IllegalOperationException {
		saR.deleteSedeArticuloDeRopa(sedeId, articuloId);
	}

	// actualiza los articulos de ropa de una sede
	@PutMapping(value = "{sedeId}/articulosDeRopa")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ArticuloDeRopaDetailDTO> updateArticulos(@PathVariable("sedeId") Long sedeId,
			@RequestBody List<ArticuloDeRopaDetailDTO> articulos)
			throws EntityNotFoundException, IllegalOperationException {
		List<ArticuloDeRopaEntity> articulosEntity = modelMapper.map(articulos,
				new TypeToken<List<ArticuloDeRopaEntity>>() {
				}.getType());
		List<ArticuloDeRopaEntity> articulosUpdated = saR.updateArticulos(sedeId, articulosEntity);
		return modelMapper.map(articulosUpdated, new TypeToken<List<ArticuloDeRopaDetailDTO>>() {
		}.getType());
	}
}
