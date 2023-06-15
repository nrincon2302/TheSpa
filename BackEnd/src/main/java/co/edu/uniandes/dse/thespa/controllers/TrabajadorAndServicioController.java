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

import co.edu.uniandes.dse.thespa.services.TrabajadorAndServicioService;
import co.edu.uniandes.dse.thespa.dto.ServicioDTO;
import co.edu.uniandes.dse.thespa.dto.ServicioDetailDTO;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;

@RestController
@RequestMapping("/trabajadores")
public class TrabajadorAndServicioController {

	@Autowired
	private TrabajadorAndServicioService trabajadorServicioService;

	@Autowired
	private ModelMapper modelMapper;

	// Asocia un servicio a un trabajador
	@PostMapping(value = "/{trabajadorId}/servicios/{servicioId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ServicioDTO addServicioToTrabajador(@PathVariable("servicioId") Long servicioId,
			@PathVariable("trabajadorId") Long trabajadorId)
			throws EntityNotFoundException, IllegalOperationException {
		ServicioEntity servicioEntity = trabajadorServicioService.addServicioToTrabajador(trabajadorId, servicioId);
		return modelMapper.map(servicioEntity, ServicioDTO.class);
	}

	// Busca un servicio dentro de los asociados a un trabajador
	@GetMapping(value = "/{trabajadorId}/servicios/{servicioId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ServicioDetailDTO getServicio(@PathVariable("trabajadorId") Long trabajadorId,
			@PathVariable("servicioId") Long servicioId)
			throws EntityNotFoundException, IllegalOperationException {
		ServicioEntity servicioEntity = trabajadorServicioService.getServicio(trabajadorId, servicioId);
		return modelMapper.map(servicioEntity, ServicioDetailDTO.class);
	}

	// Busca todos los servicios asociados a un trabajador
	@GetMapping(value = "/{trabajadorId}/servicios")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ServicioDetailDTO> getServicios(@PathVariable("trabajadorId") Long trabajadorId)
			throws EntityNotFoundException {
		List<ServicioEntity> servicioEntity = trabajadorServicioService.getServicios(trabajadorId);
		return modelMapper.map(servicioEntity, new TypeToken<List<ServicioDetailDTO>>() {
		}.getType());
	}

	// Reemplaza/Actualiza los servicios asociados a un trabajador
	@PutMapping(value = "/{trabajadorId}/servicios")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ServicioDetailDTO> replaceBooks(@PathVariable("trabajadorId") Long trabajadorId,
			@RequestBody List<ServicioDTO> servicios)
			throws EntityNotFoundException {
		List<ServicioEntity> entities = modelMapper.map(servicios, new TypeToken<List<ServicioEntity>>() {
		}.getType());
		List<ServicioEntity> serviciosList = trabajadorServicioService.replaceServicios(trabajadorId, entities);
		return modelMapper.map(serviciosList, new TypeToken<List<ServicioDetailDTO>>() {
		}.getType());
	}

	// Elimina la conexión entre un trabajador y un servicio
	@DeleteMapping(value = "/{trabajadorId}/servicios/{servicioId}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteServicioFromTrabajador(@PathVariable("servicioId") Long servicioId,
			@PathVariable("trabajadorId") Long trabajadorId)
			throws EntityNotFoundException, IllegalOperationException {
		trabajadorServicioService.deleteServicioTrabajador(trabajadorId, servicioId);
	}

}
