import { Component, OnInit } from '@angular/core';
import { ServicioService } from '../servicio.service';
import { ServicioDetail } from '../servicio-detail';
import { empty } from 'rxjs';

@Component({
  selector: 'app-servicio-list',
  templateUrl: './servicio-list.component.html',
  styleUrls: ['./servicio-list.component.css']
})
export class ServicioListComponent implements OnInit {

  searchNombre: String = '';
  searchSedeNombre: String = '';
  searchDescripcion: String = '';
  searchPrecioMax: number = Number.MAX_SAFE_INTEGER;
  isOpen: boolean[] = [];
  servicios: ServicioDetail[] = [];
  selectedService: ServicioDetail = undefined as any;
  selected = false;

  constructor(private servicioService: ServicioService) { }

  getServices(): void {

    if (this.searchPrecioMax == null || this.searchPrecioMax == undefined || this.searchPrecioMax.toString().length == 0) {
      this.searchPrecioMax = Number.MAX_SAFE_INTEGER;
    }


    // Apply the filter based on the searchNombre property

    this.servicioService.getServices().subscribe(servicios => { this.servicios = servicios.filter(servicio => servicio.nombre.toLowerCase().includes(this.searchNombre.toLowerCase()) && servicio.sede.nombre.toLowerCase().includes(this.searchSedeNombre.toLowerCase()) && servicio.descripcion.toLowerCase().includes(this.searchDescripcion.toLowerCase()) && servicio.precio <= this.searchPrecioMax); });


  }



  selectService(servicio: ServicioDetail) {
    this.selected = true;
    this.selectedService = servicio;
  }

  ngOnInit() {
    this.getServices();
  }

  toggleAccordion(index: number) {
    this.isOpen[index] = !this.isOpen[index]; // Cambiamos el valor del índice seleccionado
  }


}
