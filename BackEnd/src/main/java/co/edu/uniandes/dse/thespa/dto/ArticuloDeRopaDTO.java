package co.edu.uniandes.dse.thespa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticuloDeRopaDTO extends ProductoDTO {
    private Integer numDisponible;
    private Integer talla;
    private String color;

}
