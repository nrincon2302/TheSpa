package co.edu.uniandes.dse.thespa.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PackDeServiciosDetailDTO extends PackDeServiciosDTO {
    private SedeDTO sede;
    private List<ServicioDTO> servicios = new ArrayList<>();

}
