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
import co.edu.uniandes.dse.thespa.dto.PackDeServiciosDTO;
import co.edu.uniandes.dse.thespa.dto.ServicioDTO;

import co.edu.uniandes.dse.thespa.services.PackDeServiciosAndServicioService;
import co.edu.uniandes.dse.thespa.entities.ServicioEntity;

@RestController
@RequestMapping("/packsDeServicios")
public class PackDeServiciosAndServiciosController {

    // inyectar el servicio de packs de servicios
    @Autowired
    private PackDeServiciosAndServicioService packDeServiciosAndServicioService;

    // inyecta el model mapper
    @Autowired
    private ModelMapper modelMapper;

    // metodo para encontrar todos los servicios dentro de un pack de servicios dado
    // su id
    @GetMapping(value = "{id}/servicios")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ServicioEntity> findAll(@PathVariable("id") Long id) throws EntityNotFoundException {
        List<ServicioEntity> packs = packDeServiciosAndServicioService.getServicios(id);
        return modelMapper.map(packs, new TypeToken<List<PackDeServiciosDTO>>() {
        }.getType());
    }

    // metodo para encontrar un servicio dentro de un pack de servicios dado su id
    @GetMapping(value = "{id}/servicios/{idServicio}")
    @ResponseStatus(code = HttpStatus.OK)
    public ServicioDTO find(@PathVariable("id") Long id, @PathVariable("idServicio") Long idServicio)
            throws EntityNotFoundException, IllegalOperationException {
        ServicioEntity servicio = packDeServiciosAndServicioService.getServicio(id, idServicio);
        return modelMapper.map(servicio, ServicioDTO.class);
    }

    // metodo para agregar un servicio a un pack de servicios dado su id
    @PostMapping(value = "{id}/servicios/{idServicio}")
    @ResponseStatus(code = HttpStatus.OK)
    public ServicioDTO create(@PathVariable("id") Long id, @PathVariable("idServicio") Long idServicio)
            throws IllegalOperationException, EntityNotFoundException {

        ServicioEntity servicio = packDeServiciosAndServicioService.addServicio(id, idServicio);
        return modelMapper.map(servicio, ServicioDTO.class);
    }

    // metodo para eliminar un servicio de un pack de servicios dado su id
    @DeleteMapping(value = "{id}/servicios/{idServicio}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ServicioDTO delete(@PathVariable("id") Long id, @PathVariable("idServicio") Long idServicio)
            throws IllegalOperationException, EntityNotFoundException {

        ServicioEntity servicioEliminado = packDeServiciosAndServicioService.removeServicio(id, idServicio);
        return modelMapper.map(servicioEliminado, ServicioDTO.class);
    }

    // metodo para actualizar los servicios de un pack de servicios dado el el del
    // pack y una lista de servicios
    @PutMapping(value = "{id}/servicios")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ServicioDTO> update(@PathVariable("id") Long id, @RequestBody List<ServicioDTO> servicios)
            throws IllegalOperationException, EntityNotFoundException {

        List<ServicioEntity> entities = modelMapper.map(servicios, new TypeToken<List<ServicioEntity>>() {
        }.getType());
        List<ServicioEntity> serviciosActualizados = packDeServiciosAndServicioService.updateServicios(id, entities);
        return modelMapper.map(serviciosActualizados, new TypeToken<List<ServicioDTO>>() {
        }.getType());
    }

}
