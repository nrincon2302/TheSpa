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

import co.edu.uniandes.dse.thespa.services.UbicacionService;

import co.edu.uniandes.dse.thespa.dto.UbicacionDTO;
import co.edu.uniandes.dse.thespa.dto.UbicacionDetailDTO;
import co.edu.uniandes.dse.thespa.entities.UbicacionEntity;

@RestController
@RequestMapping("/ubicaciones")
public class UbicacionController {

    // Inyección del servicio de ubicaciones
    @Autowired
    private UbicacionService ubicacionService;

    // Inyección del ModelMapper
    @Autowired
    private ModelMapper modelMapper;

    // Método para encontrar todas las ubicaciones
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<UbicacionDTO> findAll() {
        List<UbicacionEntity> ubicaciones = ubicacionService.getUbicaciones();
        return modelMapper.map(ubicaciones, new TypeToken<List<UbicacionDTO>>() {
        }.getType());
    }

    // Método para encontrar una ubicacion dado su id
    @GetMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public UbicacionDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        UbicacionEntity ubicacionEntity = ubicacionService.getUbicacion(id);
        return modelMapper.map(ubicacionEntity, UbicacionDetailDTO.class);
    }

    // Método para crear una ubicacion a partir de un DTO
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UbicacionDetailDTO create(@RequestBody UbicacionDetailDTO ubicacionDTO)
            throws IllegalOperationException {
        UbicacionEntity ubicacionEntity = ubicacionService
                .createUbicacion(modelMapper.map(ubicacionDTO, UbicacionEntity.class));
        return modelMapper.map(ubicacionEntity, UbicacionDetailDTO.class);
    }

    // Método para actualizar una ubicacion a partir de un DTO
    @PutMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public UbicacionDTO update(@PathVariable("id") Long id, @RequestBody UbicacionDTO ubicacionDTO)
            throws IllegalOperationException, EntityNotFoundException {
        UbicacionEntity ubicacionEntity = ubicacionService
                .updateUbicacion(id, modelMapper.map(ubicacionDTO, UbicacionEntity.class));
        return modelMapper.map(ubicacionEntity, UbicacionDTO.class);
    }

    // Método para eliminar una ubicacion dado su id
    @DeleteMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
        ubicacionService.deleteUbicacion(id);
    }

}
