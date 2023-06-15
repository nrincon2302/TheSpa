package co.edu.uniandes.dse.thespa.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

@Getter
@Setter
public class ServicioDetailDTO extends ServicioDTO {
    private SedeDTO sede;
    private List<PackDeServiciosDTO> packsDeServicios = new ArrayList<>();
    private List<TrabajadorDTO> trabajadores = new ArrayList<>();

}
