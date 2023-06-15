package co.edu.uniandes.dse.thespa.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServicioDTO extends ProductoDTO {
    private Integer duracion;
    private String restricciones;
    private Boolean disponible;
    private Integer horaInicio;

}
