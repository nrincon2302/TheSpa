import { Sede } from '../sede/sede';
import { Articulo } from './articulo';

export class ArticuloDetail extends Articulo {

  sede: Sede;

  constructor(id: number, nombre: string, descripcion: string, precio: number, imagen: string, talla: number, color: string, numDisponible: number, sede: Sede) {
      super(id, nombre, descripcion, precio, imagen, talla, color, numDisponible);
      this.sede = sede;
  }
}
