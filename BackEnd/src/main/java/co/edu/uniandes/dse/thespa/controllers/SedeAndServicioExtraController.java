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
import co.edu.uniandes.dse.thespa.dto.ServicioExtraDTO;
import co.edu.uniandes.dse.thespa.services.SedeAndServicioExtraService;
import co.edu.uniandes.dse.thespa.services.ServicioExtraService;
import co.edu.uniandes.dse.thespa.entities.ServicioExtraEntity;

@RestController
@RequestMapping("/sedes")
public class SedeAndServicioExtraController {

    // inyectar el servicio de sedes y servicios extras
    @Autowired
    private SedeAndServicioExtraService saE;

    // inyecta el model mapper
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ServicioExtraService seS;

    // metodo para encontrar todos los servicios extras dentro de una sede dado su
    // id
    @GetMapping(value = "{id}/serviciosExtra")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ServicioExtraEntity> findAll(@PathVariable("id") Long id) {
        List<ServicioExtraEntity> servicios = saE.obtenerAllServicios(id);
        return modelMapper.map(servicios, new TypeToken<List<ServicioExtraDTO>>() {
        }.getType());
    }

    // metodo para encontrar un servicio extra dentro de una sede dado su id
    @GetMapping(value = "/{id}/serviciosExtra/{idServicio}")
    @ResponseStatus(code = HttpStatus.OK)
    public ServicioExtraDTO getServicioExtra(@PathVariable("id") Long id, @PathVariable("idServicio") Long idServicio)
            throws EntityNotFoundException {

        ServicioExtraEntity servicio = seS.getServicioExtra(idServicio);
        return modelMapper.map(servicio, ServicioExtraDTO.class);
    }

    // metodo para agregar un servicio Extra a una sede dado su id
    @PostMapping(value = "{id}/serviciosExtra/{idServicio}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServicioExtraDTO create(@PathVariable("id") Long id, @PathVariable("idServicio") Long idServicio)
            throws IllegalOperationException, EntityNotFoundException {

        ServicioExtraEntity servicio = saE.addSedeExtraService(id, idServicio);
        return modelMapper.map(servicio, ServicioExtraDTO.class);
    }

    // metodo para eliminar un servicio extra de una sede dado su id
    @DeleteMapping(value = "{id}/serviciosExtra/{idServicio}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ServicioExtraDTO delete(@PathVariable("id") Long id, @PathVariable("idServicio") Long idServicio)
            throws IllegalOperationException, EntityNotFoundException {

        ServicioExtraEntity servicioEliminado = saE.deleteSedeExtraService(id, idServicio);
        return modelMapper.map(servicioEliminado, ServicioExtraDTO.class);
    }

    // metodo para actualizar la lista de servicios extras de una sede dado su id
    @PutMapping(value = "{id}/serviciosExtra")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ServicioExtraDTO> update(@PathVariable("id") Long id, @RequestBody List<ServicioExtraDTO> idServicio)
            throws IllegalOperationException, EntityNotFoundException {

        List<ServicioExtraEntity> serviciosEntity = modelMapper.map(idServicio,
                new TypeToken<List<ServicioExtraEntity>>() {
                }.getType());
        List<ServicioExtraEntity> servicios = saE.updateSedeExtraService(id, serviciosEntity);
        return modelMapper.map(servicios, new TypeToken<List<ServicioExtraDTO>>() {
        }.getType());
    }

}
