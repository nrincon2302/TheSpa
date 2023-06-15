package co.edu.uniandes.dse.thespa.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
public class PackDeServiciosEntity extends BaseEntity {
    private Integer descuento;
    private String nombre;
    private String imagen;

    @PodamExclude
    @ManyToOne
    private SedeEntity sede;

    @PodamExclude
    @ManyToMany
    private List<ServicioEntity> servicios = new ArrayList<>();

}
