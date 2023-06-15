package co.edu.uniandes.dse.thespa.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SedeDetailDTO extends SedeDTO {
    private UbicacionDTO ubicacion;
    private List<ServicioExtraDTO> serviciosExtra = new ArrayList<>();
    private List<PackDeServiciosDTO> packsDeServicios = new ArrayList<>();
    private List<ArticuloDeRopaDTO> articulos = new ArrayList<>();
    private List<TrabajadorDTO> trabajadores = new ArrayList<>();
    private List<ServicioDTO> servicios = new ArrayList<>();

}
