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
import co.edu.uniandes.dse.thespa.services.ArticuloDeRopaService;
import co.edu.uniandes.dse.thespa.dto.ArticuloDeRopaDetailDTO;
import co.edu.uniandes.dse.thespa.entities.ArticuloDeRopaEntity;

@RestController
@RequestMapping("/articulosDeRopa")
public class ArticuloDeRopaController {

    // inyectar el servicio de articulos de ropa
    @Autowired
    private ArticuloDeRopaService articuloDeRopaService;

    // inyecta el model mapper
    @Autowired
    private ModelMapper modelMapper;

    // metodo para encontrar todos los articulos de ropa
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ArticuloDeRopaDetailDTO> findAll() {
        List<ArticuloDeRopaEntity> articulos = articuloDeRopaService.getArticulosDeRopa();
        return modelMapper.map(articulos, new TypeToken<List<ArticuloDeRopaDetailDTO>>() {
        }.getType());
    }

    // metodo para encontrar un articulo de ropa dado su id
    @GetMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ArticuloDeRopaDetailDTO findOne(@PathVariable("id") Long id) throws EntityNotFoundException {
        ArticuloDeRopaEntity articulo = articuloDeRopaService.getArticuloDeRopa(id);
        return modelMapper.map(articulo, ArticuloDeRopaDetailDTO.class);
    }

    // metodo para crear una entidad de un articulo de ropa dado un DTO, retorna el
    // DTO de la entidad creada
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ArticuloDeRopaDetailDTO create(@RequestBody ArticuloDeRopaDetailDTO articuloDTO)
            throws IllegalOperationException {

        ArticuloDeRopaEntity articulo = articuloDeRopaService
                .createArticuloDeRopa(modelMapper.map(articuloDTO, ArticuloDeRopaEntity.class));
        return modelMapper.map(articulo, ArticuloDeRopaDetailDTO.class);
    }

    // metodo para eliminar un articulo de ropa dado su id
    @DeleteMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
        articuloDeRopaService.deleteArticuloDeRopa(id);
    }

    // metodo para actualizar un articulo de ropa dado su id y un DTO, retorna el
    // DTO
    // de la entidad actualizada
    @PutMapping(value = "{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ArticuloDeRopaDetailDTO update(@PathVariable("id") Long id, @RequestBody ArticuloDeRopaDetailDTO articuloDTO)
            throws EntityNotFoundException {
        ArticuloDeRopaEntity articulo = articuloDeRopaService.updateArticuloDeRopaEntity(id,
                modelMapper.map(articuloDTO, ArticuloDeRopaEntity.class));
        return modelMapper.map(articulo, ArticuloDeRopaDetailDTO.class);
    }

}
