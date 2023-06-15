package co.edu.uniandes.dse.thespa.entities;

import javax.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un trabajador en la persistencia
 *
 * @author Nicolás Rincón Sánchez
 */

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
public class TrabajadorEntity extends BaseEntity {
    private String nombre;
    private String foto;
    private Integer calificacion;
    private Boolean enHallOfFame;

    @PodamExclude
    @ManyToMany
    private List<SedeEntity> sedes = new ArrayList<>();

    @PodamExclude
    @ManyToMany
    private List<ServicioEntity> servicios = new ArrayList<>();

}
