import { Producto } from '../producto/producto';

export class ServicioExtra extends Producto {
  disponible: boolean;


  constructor(id: number, nombre: string, descripcion: string, precio: number, imagen: string, disponible: boolean) {
    super(id, nombre, descripcion, precio, imagen);
    this.disponible = disponible;

  }

}
