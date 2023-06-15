import { Component, Renderer2, OnInit } from '@angular/core';
import { SedeDetail } from '../sede-detail';
import { SedeService } from '../sede.service';


@Component({
  selector: 'app-sede-list',
  templateUrl: './sede-list.component.html',
  styleUrls: ['./sede-list.component.css']
})
export class SedeListComponent implements OnInit {

  isOpen: boolean[] = [];

  sedes: SedeDetail[] = [];
  selectedSede: SedeDetail = undefined as any;
  selected = false;
  searchNombre: string = ''; // Add the searchText property for filtering
  searchCiudad: string = ''; // Add the searchText property for filtering
  searchDireccion: string = ''; // Add the searchText property for filtering

  constructor(private sedeService: SedeService) { }



  getSedes(): void {
    this.sedeService.getSedes().subscribe(sedes => {
      // Apply the filter based on the searchText property
      this.sedes = sedes.filter(sede => sede.nombre.toLowerCase().includes(this.searchNombre.toLowerCase()) && sede.ubicacion.ciudad.toLowerCase().includes(this.searchCiudad.toLowerCase()) && sede.ubicacion.direccion.toLowerCase().includes(this.searchDireccion.toLowerCase()));

    });
  }





  selectSede(sede: SedeDetail) {
    this.selected = true;
    this.selectedSede = sede;
  }

  ngOnInit() {

    this.isOpen = new Array(2).fill(false);
    this.getSedes();

  }

  toggleAccordion(index: number) {
    this.isOpen[index] = !this.isOpen[index]; // Cambiamos el valor del índice seleccionado
  }

}
