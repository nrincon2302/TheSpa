import { Producto } from "../producto/producto";

export class Servicio extends Producto {
    duracion: number;
    restricciones: string;
    disponible: boolean;
    horaInicio: number;

    constructor(id: number, nombre: string, descripcion: string, precio: number, imagen: string, duracion: number, restricciones: string, disponible: boolean, horaInicio: number) {
        super(id, nombre, descripcion, precio, imagen);
        this.duracion = duracion;
        this.restricciones = restricciones;
        this.disponible = disponible;
        this.horaInicio = horaInicio;
    }
}