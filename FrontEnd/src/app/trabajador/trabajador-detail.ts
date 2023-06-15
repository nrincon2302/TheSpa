import { Sede } from '../sede/sede';
import { Trabajador } from '../trabajador/trabajador';
import { Servicio } from '../servicio/servicio';

export class TrabajadorDetail extends Trabajador {

    sedes: Array<Sede>;
    servicios: Array<Servicio>;

    constructor(id: number, nombre: string, imagen: string, calificacion: number, enHallOfFame: boolean, sedes: Array<Sede>, servicios: Array<Servicio>) {
        super(id, nombre, imagen, calificacion, enHallOfFame);
        this.sedes = sedes;
        this.servicios = servicios;
    }

}

