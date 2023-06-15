import { Component, Input, OnInit } from '@angular/core';
import { SedeDetail } from '../sede-detail';
import { SedeService } from '../sede.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-sede-detail',
  templateUrl: './sede-detail.component.html',
  styleUrls: ['./sede-detail.component.css']
})
export class SedeDetailComponent implements OnInit {

  sedeId!: string;
  @Input() sedeDetail!: SedeDetail;


  constructor(
    private route: ActivatedRoute,
    private sedeService: SedeService,
  ) { }

  getSede() {

    this.sedeService.getSede(this.sedeId).subscribe(sede => {
      this.sedeDetail = sede;
    });
  }

  ngOnInit() {
    window.scrollTo(0, 0);


    if (this.sedeDetail === undefined) {
      this.sedeId = this.route.snapshot.paramMap.get('id')!
      if (this.sedeId) {
        this.getSede();
      }
    }

  }

  deleteSede() {
    // Muestra un mensaje diciendo 'antes de borrar un servicio, asegurese de haber borrado todos los paquetes que contengan este servicio'

    let mgs: string = 'Antes de borrar una sede, asegurese de haber borrado todos los componentes que contengan esta sede. Considere que la aplicacion esta centralizada con base a este componente "Sede".';
    alert(mgs);


    const confirmacion = confirm('¿Estás seguro de que deseas eliminar?');
    if (confirmacion === true) {
      this.sedeService.deleteSede(this.sedeId).subscribe(() => {
        this.ngOnInit();
      });
    }
    else {
      alert('No se eliminó el servicio');
    }
  }


}
