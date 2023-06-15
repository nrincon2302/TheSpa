import { Sede } from "../sede/sede";
import { Ubicacion } from "./ubicacion";


export class UbicacionDetail extends Ubicacion {

  sede: Sede;


  constructor(id: number, latitud: number, longitud: number, ciudad: string, direccion: string, sede: Sede) {
      super(id, latitud, longitud, ciudad, direccion );
      this.sede = sede;
  }

}
