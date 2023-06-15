export class Articulo {
  id: number;
  nombre: string;
  descripcion: string;
  precio: number;
  imagen: string;
  talla: number;
  color: string;
  numDisponible: number;

  constructor(id: number, nombre: string, descripcion: string, precio: number, imagen: string, talla: number, color: string, numDisponible: number) {
      this.id = id;
      this.nombre = nombre;
      this.descripcion = descripcion;
      this.precio = precio;
      this.imagen = imagen;
      this.talla = talla;
      this.color = color;
      this.numDisponible = numDisponible;
  }
}
