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

import co.edu.uniandes.dse.thespa.services.ServicioAndPackDeServiciosService;
import co.edu.uniandes.dse.thespa.entities.PackDeServiciosEntity;
import co.edu.uniandes.dse.thespa.dto.PackDeServiciosDTO;

import org.modelmapper.TypeToken;

import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;

@RestController
@RequestMapping("/servicios")
public class ServicioAndPackDeServiciosController {

    @Autowired
    private ServicioAndPackDeServiciosService service;

    @Autowired
    private ModelMapper modelMapper;

    // Encuentra todos los packs de servicios de un servicio
    @GetMapping("/{id}/packsdeservicios")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackDeServiciosEntity> findAll(@PathVariable("id") Long id) throws EntityNotFoundException {

        List<PackDeServiciosEntity> packsDeServicios = service.getPacksDeServicios(id);
        return modelMapper.map(packsDeServicios, new TypeToken<List<PackDeServiciosDTO>>() {
        }.getType());
    }

    // Agrega un pack de servicios a un servicio
    @PostMapping("/{id}/packsdeservicios/{idPack}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PackDeServiciosDTO create(@PathVariable("id") Long id, @PathVariable("idPack") Long idPack)
            throws EntityNotFoundException, IllegalOperationException {
        PackDeServiciosEntity packDeServicios = service.addPackDeServicios(id, idPack);
        return modelMapper.map(packDeServicios, PackDeServiciosDTO.class);
    }

    @GetMapping("/{id}/packsdeservicios/{idPack}")
    @ResponseStatus(code = HttpStatus.OK)
    public PackDeServiciosDTO findOne(@PathVariable("id") Long id, @PathVariable("idPack") Long idPack) throws EntityNotFoundException, IllegalOperationException {
        PackDeServiciosEntity packDeServicios = service.getPack(id, idPack);
        return modelMapper.map(packDeServicios, PackDeServiciosDTO.class);
    }

    // Elimina un pack de servicios de un servicio
    @DeleteMapping(value = "/{id}/packsdeservicios/{idPack}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public PackDeServiciosDTO delete(@PathVariable("id") Long id, @PathVariable("idPack") Long idPack)
            throws EntityNotFoundException, IllegalOperationException {
        PackDeServiciosEntity packEliminado = service.removePackDeServicios(id, idPack);
        return modelMapper.map(packEliminado, PackDeServiciosDTO.class);
    }

    @PutMapping(value = "/{id}/packsdeservicios")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackDeServiciosDTO> update(@PathVariable("id") Long id, @RequestBody List<PackDeServiciosDTO> packsDeServicios)
            throws IllegalOperationException, EntityNotFoundException {

        List<PackDeServiciosEntity> entities = modelMapper.map(packsDeServicios, new TypeToken<List<PackDeServiciosEntity>>() {
        }.getType());
        List<PackDeServiciosEntity> packsDeServiciosActualizados = service.updatePacks(id, entities);
        return modelMapper.map(packsDeServiciosActualizados, new TypeToken<List<PackDeServiciosDTO>>() {
        }.getType());
    }
   
}