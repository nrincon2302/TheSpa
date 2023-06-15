import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ServicioExtraDetail } from '../servicioExtra-detail';
import { ServicioExtraService } from '../servicioExtra.service';

@Component({
  selector: 'app-servicioExtra-detail',
  templateUrl: './servicioExtra-detail.component.html',
  styleUrls: ['./servicioExtra-detail.component.css']
})
export class ServicioExtraDetailComponent implements OnInit {

  servicioExtraId!: string;
  @Input() servicioExtraDetail!: ServicioExtraDetail;
  disponible: String = "Si";

  constructor(private route: ActivatedRoute, private servicioExtraService: ServicioExtraService) { }

  getServicioExtra() {
    this.servicioExtraService.getServicioExtra(this.servicioExtraId).subscribe(servicioExtra => {
      this.servicioExtraDetail = servicioExtra;
    })
  }

  ngOnInit() {
    window.scrollTo(0, 0);
    if (this.servicioExtraDetail === undefined) {
      this.servicioExtraId = this.route.snapshot.paramMap.get('id')!
      if (this.servicioExtraId) {
        this.getServicioExtra();
      }
    }
  }

  deleteServicioExtra() {
    const confirmacion = confirm('¿Estás seguro de que deseas eliminar?');
    if (confirmacion === true) {
      this.servicioExtraService.deleteServicioExtra(this.servicioExtraId).subscribe(() => {
        this.ngOnInit();
      });
    }
    else {
      alert('No se eliminó el servicio extra');
    }
  }

}
