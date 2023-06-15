import { Component, OnInit } from '@angular/core';
import { ArticuloService } from '../articulo.service';
import { ArticuloDetail } from '../articulo-detail';

@Component({
  selector: 'app-articulo-list',
  templateUrl: './articulo-list.component.html',
  styleUrls: ['./articulo-list.component.css']
})
export class ArticuloListComponent implements OnInit {
  articulosDeRopa: ArticuloDetail[] = [];
  selectedArticulo: ArticuloDetail = undefined as any;
  selected = false;

  //Filtering info
  searchNombre: string = '';
  searchSedeNombre: string = '';
  searchPrecioMax: number = Number.MAX_SAFE_INTEGER;
  isOpen: boolean[] = [];

  constructor(private articuloService: ArticuloService) { }

  getArticulosDeRopa(): void {
    if (this.searchPrecioMax == null || this.searchPrecioMax == undefined || this.searchPrecioMax.toString().length == 0) {
      this.searchPrecioMax = Number.MAX_SAFE_INTEGER;
    }

    this.articuloService.getArticulosDeRopa().subscribe((articulos) => {
      this.articulosDeRopa = (<ArticuloDetail[]>articulos).filter(
        articulo => articulo.nombre.toLowerCase().includes(this.searchNombre.toLowerCase()) && (<ArticuloDetail>articulo).sede.nombre.toLowerCase().includes(this.searchSedeNombre.toLowerCase()) && articulo.precio <= this.searchPrecioMax);
    });
  }

  selectArticulo(articulo: ArticuloDetail) {
    this.selected = true;
    this.selectedArticulo = articulo;
  }

  ngOnInit() {
    this.getArticulosDeRopa();
  }

  toggleAccordion(index: number) {
    this.isOpen[index] = !this.isOpen[index];
  }

}
