package co.edu.uniandes.dse.thespa.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

import co.edu.uniandes.dse.thespa.services.ServicioAndTrabajadorService;
import co.edu.uniandes.dse.thespa.entities.TrabajadorEntity;
import co.edu.uniandes.dse.thespa.dto.ServicioDTO;
import co.edu.uniandes.dse.thespa.dto.TrabajadorDTO;

import org.modelmapper.TypeToken;

import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;

@RestController
@RequestMapping("/servicios")
public class ServicioAndTrabajadorController {

    @Autowired
    private ServicioAndTrabajadorService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TrabajadorAndServicioController trabajadorAndServicioController;

    // añade un trabajador a un servicio
    @PostMapping(value = "{id}/trabajadores/{idTrabajador}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServicioDTO create(@PathVariable("id") Long id, @PathVariable("idTrabajador") Long idTrabajador)
            throws IllegalOperationException, EntityNotFoundException {

        return trabajadorAndServicioController.addServicioToTrabajador(idTrabajador, id);
    }

    // Encuentra todos los trabajadores de un servicio
    @GetMapping("/{id}/trabajadores")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TrabajadorEntity> findAll(@PathVariable("id") Long id) throws EntityNotFoundException {

        List<TrabajadorEntity> trabajadores = service.getTrabajadores(id);
        return modelMapper.map(trabajadores, new TypeToken<List<TrabajadorDTO>>() {
        }.getType());
    }

    @GetMapping("/{id}/trabajadores/{idTrabajador}")
    @ResponseStatus(code = HttpStatus.OK)
    public TrabajadorDTO findOne(@PathVariable("id") Long id,
            @PathVariable("idTrabajador") Long idTrabajador) throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity trabajador = service.getTrabajador(id, idTrabajador);
        return modelMapper.map(trabajador, TrabajadorDTO.class);
    }

    // Elimina un trabajador de un servicio
    @DeleteMapping(value = "/{id}/trabajadores/{idTrabajador}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public TrabajadorDTO delete(@PathVariable("id") Long id,
            @PathVariable("idTrabajador") Long idTrabajador)
            throws EntityNotFoundException, IllegalOperationException {
        TrabajadorEntity trabajadorEliminado = service.removeTrabajador(id,
                idTrabajador);
        return modelMapper.map(trabajadorEliminado, TrabajadorDTO.class);
    }

    @PutMapping(value = "/{id}/trabajadores")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TrabajadorDTO> update(@PathVariable("id") Long id, @RequestBody List<TrabajadorDTO> trabajadores)
            throws IllegalOperationException, EntityNotFoundException {

        List<TrabajadorEntity> entities = modelMapper.map(trabajadores, new TypeToken<List<TrabajadorEntity>>() {
        }.getType());
        List<TrabajadorEntity> trabajadoresActualizados = service.updateTrabajadores(id, entities);
        return modelMapper.map(trabajadoresActualizados, new TypeToken<List<TrabajadorDTO>>() {
        }.getType());
    }
}