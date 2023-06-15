import { Component, OnInit } from '@angular/core';
import { PackService } from '../pack.service';
import { PackDetail } from '../pack-detail';

@Component({
  selector: 'app-pack-list',
  templateUrl: './pack-list.component.html',
  styleUrls: ['./pack-list.component.css']
})
export class PackListComponent implements OnInit {

  searchNombre: String = '';
  searchSedeNombre: String = '';
  searchMinServicios: number = 0;
  searchMinDescuento: number = 0;
  packs: PackDetail[] = [];
  isOpen: boolean[] = [];
  selectedPack: PackDetail = undefined as any;
  selected = false;

  constructor(private packService: PackService) { }

  getPacks(): void {

    if (this.searchMinServicios == null || this.searchMinServicios == undefined || this.searchMinServicios.toString().length == 0) {
      this.searchMinServicios = 0;
    }

    if (this.searchMinDescuento == null || this.searchMinDescuento == undefined || this.searchMinDescuento.toString().length == 0) {
      this.searchMinServicios = 0;
    }
    // Apply the filter based on the searchNombre property

    this.packService.getPacks().subscribe(packs => {
      this.packs = packs.filter(pack => pack.nombre.toLowerCase().includes(this.searchNombre.toLowerCase()) && pack.sede.nombre.toLowerCase().includes(this.searchSedeNombre.toLowerCase()) && pack.servicios.length >= this.searchMinServicios && pack.descuento >= this.searchMinDescuento);
    }
    );
  }

  selectPack(pack: PackDetail): void {
    this.selected = true;
    this.selectedPack = pack;
  }

  ngOnInit() {
    this.getPacks();
  }

  toggleAccordion(index: number) {
    this.isOpen[index] = !this.isOpen[index]; // Cambiamos el valor del índice seleccionado
  }

}
