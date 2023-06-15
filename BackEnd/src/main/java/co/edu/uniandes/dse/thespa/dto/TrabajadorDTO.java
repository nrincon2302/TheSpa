package co.edu.uniandes.dse.thespa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrabajadorDTO {
    private long id;
    private String nombre;
    private String foto;
    private Integer calificacion;
    private Boolean enHallOfFame;
    
}
