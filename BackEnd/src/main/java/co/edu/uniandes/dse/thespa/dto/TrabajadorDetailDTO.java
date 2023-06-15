package co.edu.uniandes.dse.thespa.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrabajadorDetailDTO extends TrabajadorDTO {
    private List<SedeDTO> sedes = new ArrayList<>();
    private List<ServicioDTO> servicios = new ArrayList<>();

}
