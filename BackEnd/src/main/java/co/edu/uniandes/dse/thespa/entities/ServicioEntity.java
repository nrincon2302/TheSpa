package co.edu.uniandes.dse.thespa.entities;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import javax.persistence.CascadeType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
public class ServicioEntity extends ProductoEntity {
    private Integer duracion;
    private String restricciones;
    private Boolean disponible;
    private Integer horaInicio;

    @PodamExclude
    @ManyToMany(mappedBy = "servicios", cascade = CascadeType.PERSIST)
    private List<PackDeServiciosEntity> packsDeServicios = new ArrayList<>();

    @PodamExclude
    @ManyToOne
    private SedeEntity sede;

    @PodamExclude
    @ManyToMany(mappedBy = "servicios", cascade = CascadeType.PERSIST)
    private List<TrabajadorEntity> trabajadores = new ArrayList<>();
}
