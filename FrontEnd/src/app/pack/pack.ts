export class Pack {
    id: number;
    nombre: string;
    descuento: number;
    imagen: string;

    constructor(id: number, nombre: string, descuento: number, imagen: string) {
        this.id = id;
        this.nombre = nombre;
        this.descuento = descuento;
        this.imagen = imagen;
    }
}