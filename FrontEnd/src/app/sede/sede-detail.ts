import { Pack } from "../pack/pack";
import { Servicio } from "../servicio/servicio";
import { ServicioExtra } from "../servicioExtra/servicioExtra";
import { Trabajador } from "../trabajador/trabajador";
import { Ubicacion } from "../ubicacion/ubicacion";
import { Sede } from "./sede";
import { Articulo } from "../articulo/articulo";

export class SedeDetail extends Sede {

  trabajadores: Array<Trabajador>;
  serviciosExtra: Array<ServicioExtra>;
  articulos: Array<Articulo>;
  servicios: Array<Servicio>;
  packsDeServicios: Array<Pack>;
  ubicacion: Ubicacion;

  constructor(id: number, nombre: string, imagen: string, trabajadores: Array<Trabajador>, serviciosExtra: Array<ServicioExtra>, articulosDeRopa: Array<Articulo>, servicios: Array<Servicio>, packsDeServicios: Array<Pack>, ubicacion: Ubicacion) {
    super(id, nombre, imagen);
    this.trabajadores = trabajadores;
    this.serviciosExtra = serviciosExtra;
    this.articulos = articulosDeRopa;
    this.servicios = servicios;
    this.packsDeServicios = packsDeServicios;
    this.ubicacion = ubicacion;


  }



}
