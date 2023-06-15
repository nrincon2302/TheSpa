import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ServicioDetail } from '../servicio-detail';
import { ServicioService } from '../servicio.service';

@Component({
  selector: 'app-servicio-detail',
  templateUrl: './servicio-detail.component.html',
  styleUrls: ['./servicio-detail.component.css']
})
export class ServicioDetailComponent implements OnInit {


  servicioId!: string;
  @Input() servicioDetail!: ServicioDetail;

  constructor(
    private route: ActivatedRoute,
    private servicioService: ServicioService
  ) { }

  getServicio() {
    this.servicioService.getService(this.servicioId).subscribe(servicio => {
      this.servicioDetail = servicio;
    })
  }

  ngOnInit() {
    window.scrollTo(0, 0);
    if (this.servicioDetail === undefined) {
      this.servicioId = this.route.snapshot.paramMap.get('id')!
      if (this.servicioId) {
        this.getServicio();
      }
    }

  }

  deleteServicio() {
    // Muestra un mensaje diciendo 'antes de borrar un servicio, asegurese de haber borrado todos los paquetes que contengan este servicio'

    let mgs: string = 'Antes de borrar un servicio, asegurese de haber borrado todos los paquetes que contengan este servicio';
    alert(mgs);


    const confirmacion = confirm('¿Estás seguro de que deseas eliminar?');
    if (confirmacion === true) {
      this.servicioService.deleteService(this.servicioId).subscribe(() => {
        this.ngOnInit();
      });
    }
    else {
      alert('No se eliminó el servicio');
    }
  }
}
