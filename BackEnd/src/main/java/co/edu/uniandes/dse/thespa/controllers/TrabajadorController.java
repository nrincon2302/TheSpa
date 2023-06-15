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

import co.edu.uniandes.dse.thespa.services.TrabajadorService;
import co.edu.uniandes.dse.thespa.dto.TrabajadorDetailDTO;
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;

@RestController
@RequestMapping("/trabajadores")
public class TrabajadorController {

    // Inyección del servicio de trabajadores
    @Autowired
    private TrabajadorService trabajadorService;

    // Inyección del ModelMapper
    @Autowired
    private ModelMapper modelMapper;

    // Método para encontrar todos los trabajadores
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<TrabajadorDetailDTO> findAll() {
        List<TrabajadorEntity> trabajadores = trabajadorService.getTrabajadores();
        return modelMapper.map(trabajadores, new TypeToken<List<TrabajadorDetailDTO>>() {
        }.getType());
    }

    // Método para encontrar un trabajador dado su id
    @GetMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public TrabajadorDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        TrabajadorEntity trabajadorEntity = trabajadorService.getTrabajador(id);
        return modelMapper.map(trabajadorEntity, TrabajadorDetailDTO.class);
    }

    // Método para crear un trabajador a partir de un DTO
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public TrabajadorDetailDTO create(@RequestBody TrabajadorDetailDTO trabajadorDTO)
            throws IllegalOperationException, EntityNotFoundException {

        TrabajadorEntity trabajador = trabajadorService
                .createTrabajador(modelMapper.map(trabajadorDTO, TrabajadorEntity.class));
        return modelMapper.map(trabajador, TrabajadorDetailDTO.class);
    }

    // Método para actualizar la información de un trabajador dado su id
    @PutMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public TrabajadorDetailDTO update(@PathVariable("id") Long id, @RequestBody TrabajadorDetailDTO trabajadorDTO)
            throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity trabajadorEntity = trabajadorService.updateTrabajador(id,
                modelMapper.map(trabajadorDTO, TrabajadorEntity.class));
        return modelMapper.map(trabajadorEntity, TrabajadorDetailDTO.class);
    }

    // Método para borrar la información de un trabajador dado su id
    @DeleteMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        trabajadorService.deleteTrabajador(id);
    }

}
