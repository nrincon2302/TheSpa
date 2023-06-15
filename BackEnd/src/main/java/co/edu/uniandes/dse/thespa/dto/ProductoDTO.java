package co.edu.uniandes.dse.thespa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO {
    private long id;
    private String nombre;
    private String descripcion;
    private Float precio;
    private String imagen;

}
