import { ServicioExtra } from "./servicioExtra";
import { Sede } from "../sede/sede";

export class ServicioExtraDetail extends ServicioExtra {
    sede: Sede;

    constructor(id: number, nombre: string, descripcion: string, precio: number, imagen: string, disponible: boolean, sede: Sede) {
        super(id, nombre, descripcion, precio, imagen, disponible);
        this.sede = sede;
    }
}
