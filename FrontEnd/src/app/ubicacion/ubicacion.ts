export class Ubicacion {
    id: number;
    latitud: number;
    longitud: number;
    ciudad: string;
    direccion: string;

    constructor(id: number, latitud: number, longitud: number, ciudad: string, direccion: string) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ciudad = ciudad;
        this.direccion = direccion;
    }
}