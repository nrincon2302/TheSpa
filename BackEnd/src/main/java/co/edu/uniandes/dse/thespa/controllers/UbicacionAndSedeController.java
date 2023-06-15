package co.edu.uniandes.dse.thespa.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import co.edu.uniandes.dse.thespa.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.thespa.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.thespa.dto.SedeDTO;
import co.edu.uniandes.dse.thespa.services.UbicacionAndSedeService;

@RestController
@RequestMapping("/ubicaciones")
public class UbicacionAndSedeController {

    // Injección de dependencias
    @Autowired
    private UbicacionAndSedeService ubicacionAndSedeService;

    // Injección de dependencias
    @Autowired
    private ModelMapper modelMapper;

    // Obtiene la sede de una ubicación
    @GetMapping("/{id}/sedes")
    @ResponseStatus(code = HttpStatus.OK)
    public SedeDTO getSede(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        return modelMapper.map(ubicacionAndSedeService.obtenerSede(id), SedeDTO.class);
    }

    // asigna una sede a una ubicación
    @PostMapping("/{id}/sedes/{idSede}")
    @ResponseStatus(code = HttpStatus.OK)
    public SedeDTO asignarSede(@PathVariable("id") Long id, @PathVariable("idSede") Long idSede)
            throws EntityNotFoundException, IllegalOperationException {
        return modelMapper.map(ubicacionAndSedeService.asignarSede(id, idSede), SedeDTO.class);
    }

}
