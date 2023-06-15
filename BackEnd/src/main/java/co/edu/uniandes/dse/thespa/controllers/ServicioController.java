package co.edu.uniandes.dse.thespa.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

import co.edu.uniandes.dse.thespa.services.ServicioService;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;
import co.edu.uniandes.dse.thespa.dto.ServicioDetailDTO;

import org.modelmapper.TypeToken;

import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;

@RestController
@RequestMapping("/servicios")

public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ServicioDetailDTO> findAll() {
        List<ServicioEntity> servicios = servicioService.getServicios();
        return modelMapper.map(servicios, new TypeToken<List<ServicioDetailDTO>>() {
        }.getType());

    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ServicioDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        ServicioEntity servicio = servicioService.getServicio(id);
        return modelMapper.map(servicio, ServicioDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServicioDetailDTO create(@RequestBody ServicioDetailDTO servicioDTO)
            throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = servicioService.createServicio(modelMapper.map(servicioDTO, ServicioEntity.class));

        return modelMapper.map(servicio, ServicioDetailDTO.class);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ServicioDetailDTO update(@PathVariable("id") Long id, @RequestBody ServicioDetailDTO servicioDTO)
            throws EntityNotFoundException {
        ServicioEntity servicio = servicioService.updateServicio(id,
                modelMapper.map(servicioDTO, ServicioEntity.class));
        return modelMapper.map(servicio, ServicioDetailDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
        servicioService.deleteServicio(id);
    }
}
