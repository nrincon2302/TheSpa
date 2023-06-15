package co.edu.uniandes.dse.thespa.entities;

import javax.persistence.Entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

import javax.persistence.ManyToOne;

/**
 * Clase que representa un servicio extra en la persistencia
 *
 * @author Nicolás Rincón Sánchez
 */

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true)
public class ServicioExtraEntity extends ProductoEntity {
    private Boolean disponible;

    @PodamExclude
    @ManyToOne
    private SedeEntity sede;

}
