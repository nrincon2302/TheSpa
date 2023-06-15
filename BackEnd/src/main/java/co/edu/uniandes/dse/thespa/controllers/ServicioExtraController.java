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

import co.edu.uniandes.dse.thespa.services.ServicioExtraService;
import co.edu.uniandes.dse.thespa.dto.ServicioExtraDetailDTO;
import co.edu.uniandes.dse.thespa.entities.ServicioExtraEntity;

@RestController
@RequestMapping("/serviciosExtra")
public class ServicioExtraController {

    // Inyección del servicio de servicios extra
    @Autowired
    private ServicioExtraService servicioExtraService;

    // Inyección del ModelMapper
    @Autowired
    private ModelMapper modelMapper;

    // Método para encontrar todos los servicios extra
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ServicioExtraDetailDTO> findAll() {
        List<ServicioExtraEntity> serviciosExtra = servicioExtraService.getServiciosExtras();
        return modelMapper.map(serviciosExtra, new TypeToken<List<ServicioExtraDetailDTO>>() {
        }.getType());
    }

    // Método para encontrar un servicio extra dado su id
    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ServicioExtraDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        ServicioExtraEntity servicioExtraEntity = servicioExtraService.getServicioExtra(id);
        return modelMapper.map(servicioExtraEntity, ServicioExtraDetailDTO.class);
    }

    // Método para crear un servicio extra a partir de un DTO
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServicioExtraDetailDTO create(@RequestBody ServicioExtraDetailDTO servicioExtraDetailDTO)
            throws IllegalOperationException, EntityNotFoundException {
        ServicioExtraEntity servicioExtraEntity = servicioExtraService
                .createServicioExtra(modelMapper.map(servicioExtraDetailDTO, ServicioExtraEntity.class));
        return modelMapper.map(servicioExtraEntity, ServicioExtraDetailDTO.class);
    }

    // Método para actualizar la información de un servicio extra dado su id
    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ServicioExtraDetailDTO update(@PathVariable("id") Long id,
            @RequestBody ServicioExtraDetailDTO servicioExtraDetailDTO)
            throws EntityNotFoundException, IllegalOperationException {
        ServicioExtraEntity servicioExtraEntity = servicioExtraService.updateServicioExtra(id,
                modelMapper.map(servicioExtraDetailDTO, ServicioExtraEntity.class));
        return modelMapper.map(servicioExtraEntity, ServicioExtraDetailDTO.class);
    }

    // Método para borrar la información de un servicio extra dado su id
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        servicioExtraService.deleteServicioExtra(id);
    }

}
