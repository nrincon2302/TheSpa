export class Producto {
    id: number;
    nombre: string;
    descripcion: string;
    precio: number;
    imagen: string;

    constructor(id: number, nombre: string, descripcion: string, precio: number, imagen: string) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
    }
}