import { Component, Renderer2, OnInit } from '@angular/core';
import { ServicioExtra } from '../servicioExtra';
import { ServicioExtraDetail } from '../servicioExtra-detail';
import { ServicioExtraService } from '../servicioExtra.service';
import { empty } from 'rxjs';

@Component({
  selector: 'app-servicioExtra-list',
  templateUrl: './servicioExtra-list.component.html',
  styleUrls: ['./servicioExtra-list.component.css']
})

export class ServicioExtraListComponent implements OnInit {

  searchNombre: String = '';
  searchSedeNombre: String = '';
  searchDescripcion: String = '';
  searchPrecioMax: number = Number.MAX_SAFE_INTEGER;
  isOpen: boolean[] = [];
  serviciosExtra: ServicioExtra[] = [];
  selectedServicioExtra: ServicioExtraDetail = undefined as any;
  selected = false;

  constructor(private servicioExtraService: ServicioExtraService) { }

  getServiciosExtra(): void {
    if (this.searchPrecioMax == null || this.searchPrecioMax == undefined || this.searchPrecioMax.toString().length == 0) {
      this.searchPrecioMax = Number.MAX_SAFE_INTEGER;
    }

    // Apply the filter based on the searchNombre property
    this.servicioExtraService.getServiciosExtra().subscribe(serviciosExtra =>
      { this.serviciosExtra = serviciosExtra.filter(servicioExtra => servicioExtra.nombre.toLowerCase().includes(this.searchNombre.toLowerCase()) &&
        servicioExtra.sede.nombre.toLowerCase().includes(this.searchSedeNombre.toLowerCase()) &&
        servicioExtra.descripcion.toLowerCase().includes(this.searchDescripcion.toLowerCase()) &&
        servicioExtra.precio <= this.searchPrecioMax);
      });
  }

  selectServicioExtra(servicioExtra: ServicioExtraDetail) {
    this.selected = true;
    this.selectedServicioExtra = servicioExtra;
  }

  ngOnInit() {
    this.isOpen = new Array(2).fill(false);
    this.getServiciosExtra();
  }

  toggleAccordion(index: number) {
    this.isOpen[index] = !this.isOpen[index]; // Cambiamos el valor del índice seleccionado
  }

}

