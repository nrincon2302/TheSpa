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
import co.edu.uniandes.dse.thespa.dto.PackDeServiciosDetailDTO;
import co.edu.uniandes.dse.thespa.services.PackDeServiciosService;
import co.edu.uniandes.dse.thespa.entities.PackDeServiciosEntity;

@RestController
@RequestMapping("/packsDeServicios")
public class PackDeServiciosController {

    // inyectar el servicio de packs de servicios
    @Autowired
    private PackDeServiciosService packDeServiciosService;

    // inyecta el model mapper
    @Autowired
    private ModelMapper modelMapper;

    // metodo para encontrar todos los packs de servicios
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackDeServiciosDetailDTO> findAll() {
        List<PackDeServiciosEntity> packs = packDeServiciosService.getPacksDeServicios();
        return modelMapper.map(packs, new TypeToken<List<PackDeServiciosDetailDTO>>() {
        }.getType());
    }

    // metodo para encontrar un pack dado su id
    @GetMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PackDeServiciosDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        PackDeServiciosEntity pack = packDeServiciosService.getPackDeServicios(id);
        return modelMapper.map(pack, PackDeServiciosDetailDTO.class);
    }

    // metodo para crear una entidad de un pack dado un DTO, retorna el DTO de la
    // entidad creada
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public PackDeServiciosDetailDTO create(@RequestBody PackDeServiciosDetailDTO packDTO)
            throws IllegalOperationException {

        PackDeServiciosEntity pack = packDeServiciosService
                .createPackDeServicios(modelMapper.map(packDTO, PackDeServiciosEntity.class));
        return modelMapper.map(pack, PackDeServiciosDetailDTO.class);
    }

    // metodo para actualizar un pack dado su id y un DTO, retorna el DTO de la
    // entidad actualizada
    @PutMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public PackDeServiciosDetailDTO update(@PathVariable("id") Long id, @RequestBody PackDeServiciosDetailDTO packDTO)
            throws EntityNotFoundException {

        PackDeServiciosEntity pack = packDeServiciosService
                .updatePackDeServicios(id, modelMapper.map(packDTO, PackDeServiciosEntity.class));
        return modelMapper.map(pack, PackDeServiciosDetailDTO.class);
    }

    // metodo para eliminar un pack dado su id
    @DeleteMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
        packDeServiciosService.deletePackDeServicios(id);
    }

}
