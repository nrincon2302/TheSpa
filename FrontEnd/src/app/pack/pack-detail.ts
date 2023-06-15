import { Sede } from '../sede/sede';
import { Servicio } from '../servicio/servicio';
import { Pack } from './pack';

export class PackDetail extends Pack {

    sede: Sede;
    servicios: Array<Servicio>;

    constructor(id: number, nombre: string, descuento: number, imagen: string, sede: Sede, servicios: Array<Servicio>) {
        super(id, nombre, descuento, imagen);
        this.sede = sede;
        this.servicios = servicios;
    }

}

