export class Trabajador {
    id: number;
    nombre: string;
    foto: string;
    calificacion: number;
    enHallOfFame: boolean;

    constructor(id: number, nombre: string, foto: string, calificacion: number, enHallOfFame: boolean) {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
        this.calificacion = calificacion;
        this.enHallOfFame = enHallOfFame;
    }
}