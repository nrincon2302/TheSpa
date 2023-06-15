import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ArticuloDetail } from '../articulo-detail';
import { ArticuloService } from '../articulo.service';

@Component({
  selector: 'app-articulo-detail',
  templateUrl: './articulo-detail.component.html',
  styleUrls: ['./articulo-detail.component.css']
})
export class ArticuloDetailComponent implements OnInit {

  articuloId!: string;
  @Input() articuloDetail!: ArticuloDetail;

  constructor(private route: ActivatedRoute, private articuloService: ArticuloService) { }

  getArticulo() {
    this.articuloService.getArticuloxID(this.articuloId).subscribe(articulo => {
      this.articuloDetail = <ArticuloDetail>articulo;
    })
  }

  ngOnInit() {
    window.scrollTo(0, 0);


    if (this.articuloDetail === undefined) {
      this.articuloId = this.route.snapshot.paramMap.get('id')!
      if (this.articuloId) {
        this.getArticulo();
      }
    }
  }

  deleteArticulo() {
    const confirmacion = confirm('¿Estás seguro de que deseas eliminar?');
    if (confirmacion === true) {
      this.articuloService.deleteArticulo(this.articuloId).subscribe(() => {
        this.ngOnInit();
      });
    }
    else {
      alert('No se eliminó el articulo');
    }
  }
}
