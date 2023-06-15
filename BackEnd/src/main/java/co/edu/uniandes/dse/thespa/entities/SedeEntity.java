package co.edu.uniandes.dse.thespa.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa la entidad de sede
 * 
 * @author ISIS2603 - Juan Coronel
 */

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
public class SedeEntity extends BaseEntity {
    private String nombre;
    private String imagen;

    @PodamExclude
    @ManyToMany(mappedBy = "sedes")
    private List<TrabajadorEntity> trabajadores = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "sede")
    private List<ArticuloDeRopaEntity> articulosDeRopa = new ArrayList<>();

    @PodamExclude
    @OneToOne
    private UbicacionEntity ubicacion;

    @PodamExclude
    @OneToMany(mappedBy = "sede")
    private List<PackDeServiciosEntity> packsDeServicios = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "sede")
    private List<ServicioEntity> servicios = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "sede")
    private List<ServicioExtraEntity> serviciosExtra = new ArrayList<>();

}
