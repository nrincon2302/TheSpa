import { Sede } from '../sede/sede';
import { Trabajador } from '../trabajador/trabajador';
import { Pack } from '../pack/pack';
import { Servicio } from './servicio';

export class ServicioDetail extends Servicio {

    sede: Sede;
    trabajador: Array<Trabajador>;
    pack: Array<Pack>;

    constructor(id: number, nombre: string, descripcion: string, precio: number, imagen: string, duracion: number, restricciones: string, disponible: boolean, horaInicio: number, sede: Sede, trabajador: Array<Trabajador>, Pack: Array<Pack>) {
        super(id, nombre, descripcion, precio, imagen, duracion, restricciones, disponible, horaInicio);
        this.sede = sede;
        this.trabajador = trabajador;
        this.pack = Pack;
    }

}

