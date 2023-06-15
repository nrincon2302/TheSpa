package co.edu.uniandes.dse.thespa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UbicacionDTO {
    private long id;
    private Double latitud;
    private Double longitud;
    private String ciudad;
    private String direccion;
}
