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
import co.edu.uniandes.dse.thespa.dto.SedeDTO;
import co.edu.uniandes.dse.thespa.dto.SedeDetailDTO;
import co.edu.uniandes.dse.thespa.entities.SedeEntity;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.services.SedeService;

@RestController
@RequestMapping("/sedes")
public class SedeController {

    // inyectar el servicio de sede
    @Autowired
    private SedeService sedeService;

    // inyecta el model mapper
    @Autowired
    private ModelMapper modelMapper;

    // metodo para encontrar todas las sedes
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<SedeDetailDTO> findAll() {
        List<SedeEntity> sedes = sedeService.getSedes();
        return modelMapper.map(sedes, new TypeToken<List<SedeDetailDTO>>() {
        }.getType());
    }

    // metodo para encontrar una sede dado su id
    @GetMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public SedeDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        SedeEntity sede = sedeService.getSede(id);
        return modelMapper.map(sede, SedeDetailDTO.class);
    }

    // metodo para crear una entidad de una sede dado un DTO, retorna el
    // DTO de la entidad creada
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public SedeDetailDTO create(@RequestBody SedeDetailDTO sedeDTO)
            throws IllegalOperationException, EntityNotFoundException {

        SedeEntity sede = sedeService.createSede(modelMapper.map(sedeDTO, SedeEntity.class));
        return modelMapper.map(sede, SedeDetailDTO.class);
    }

    // metodo para editar una entidad de sede
    @PutMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public SedeDTO update(@PathVariable("id") Long id, @RequestBody SedeDTO sedeDTO)
            throws IllegalOperationException, EntityNotFoundException {
        SedeEntity sede = sedeService.updateSede(id,
                modelMapper.map(sedeDTO, SedeEntity.class));
        return modelMapper.map(sede, SedeDTO.class);
    }

    // metodo para eliminar una sede dado su id
    @DeleteMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        sedeService.deleteSede(id);
    }
}
