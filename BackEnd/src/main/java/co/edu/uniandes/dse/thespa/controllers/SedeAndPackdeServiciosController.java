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
import co.edu.uniandes.dse.thespa.dto.PackDeServiciosDetailDTO;
import co.edu.uniandes.dse.thespa.services.SedeAndPackServicesService;
import co.edu.uniandes.dse.thespa.entities.PackDeServiciosEntity;

@RestController
@RequestMapping("/sedes")
public class SedeAndPackdeServiciosController {

    // inyectar el servicio de sedes y packs de servicios
    @Autowired
    private SedeAndPackServicesService saP;

    // inyecta el model mapper
    @Autowired
    private ModelMapper modelMapper;

    // metodo para encontrar todos los packs de servicios dentro de una sede dado su
    // id
    @GetMapping(value = "{id}/packs")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackDeServiciosDetailDTO> findAll(@PathVariable("id") Long id) {
        List<PackDeServiciosEntity> servicios = saP.obtenerAllPacks(id);
        return modelMapper.map(servicios, new TypeToken<List<PackDeServiciosDetailDTO>>() {
        }.getType());
    }

    // Busca un pack de servicios dentro de los asociados a una sede
    @GetMapping(value = "{id}/packs/{idPack}")
    @ResponseStatus(code = HttpStatus.OK)
    public PackDeServiciosDetailDTO getServicio(@PathVariable("id") Long sedeId,
            @PathVariable("idPack") Long packId)
            throws EntityNotFoundException, IllegalOperationException {
        PackDeServiciosEntity pack = saP.getPack(sedeId, packId);
        return modelMapper.map(pack, PackDeServiciosDetailDTO.class);
    }

    // metodo para agregar un pack de servicios a una sede dado su id
    @PostMapping(value = "{id}/packs/{idPack}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PackDeServiciosDTO create(@PathVariable("id") Long id, @PathVariable("idPack") Long idPack)
            throws IllegalOperationException, EntityNotFoundException {

        PackDeServiciosEntity servicio = saP.addSedePackDeServicios(id, idPack);
        return modelMapper.map(servicio, PackDeServiciosDTO.class);
    }

    // metodo para eliminar un pack servicio de una sede dado su id
    @DeleteMapping(value = "{id}/packs/{idPack}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public PackDeServiciosDTO delete(@PathVariable("id") Long id, @PathVariable("idPack") Long idPack)
            throws IllegalOperationException, EntityNotFoundException {

        PackDeServiciosEntity servicioEliminado = saP.deleteSedePackDeServicios(id, idPack);
        return modelMapper.map(servicioEliminado, PackDeServiciosDTO.class);
    }

    // metodo para actualizar la lista de packs de servicios de una sede
    @PutMapping(value = "{id}/packs")
    @ResponseStatus(code = HttpStatus.OK)
    public List<PackDeServiciosDetailDTO> update(@PathVariable("id") Long id,
            @RequestBody List<PackDeServiciosDetailDTO> packs)
            throws IllegalOperationException, EntityNotFoundException {

        List<PackDeServiciosEntity> packsEntity = modelMapper.map(packs,
                new TypeToken<List<PackDeServiciosEntity>>() {
                }.getType());
        List<PackDeServiciosEntity> packsActualizados = saP.updateSedePackDeServicios(id, packsEntity);
        return modelMapper.map(packsActualizados, new TypeToken<List<PackDeServiciosDetailDTO>>() {
        }.getType());
    }

}
