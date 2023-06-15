import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TrabajadorDetail } from '../trabajador-detail';
import { TrabajadorService } from '../trabajador.service';

@Component({
  selector: 'app-trabajador-detail',
  templateUrl: './trabajador-detail.component.html',
  styleUrls: ['./trabajador-detail.component.css']
})
export class TrabajadorDetailComponent implements OnInit {

  trabajadorId!: string;
  @Input() trabajadorDetail!: TrabajadorDetail;
  rating: string = "";
  enHallOfFame: string = "";

  constructor(private route: ActivatedRoute, private trabajadorService: TrabajadorService) { }

  getTrabajador() {
    let id: number = +this.trabajadorId;
    this.trabajadorService.getTrabajador(id).subscribe(trabajador => {
      this.trabajadorDetail = <TrabajadorDetail>trabajador;
      // for each rating, add a star to the rating string
      for (let i = 0; i < this.trabajadorDetail.calificacion; i++) {
        // a star is the string '★' (not the character '✩')
        this.rating += '★';
      }
      // if enHallOfFame is true, the string enHallOfFame is 'Sí'
      if (this.trabajadorDetail.enHallOfFame) {
        this.enHallOfFame = 'Sí';
      }
      // if enHallOfFame is false, the string enHallOfFame is 'No'
      else {
        this.enHallOfFame = 'No';
      }
    })
  }

  ngOnInit() {
    window.scrollTo(0, 0);


    if (this.trabajadorDetail === undefined) {
      this.trabajadorId = this.route.snapshot.paramMap.get('id')!
      if (this.trabajadorId) {
        this.getTrabajador();
      }
    }
  }

  deleteTrabajador() {
    const confirmacion = confirm('¿Estás seguro de que deseas eliminar?');
    if (confirmacion === true) {
      this.trabajadorService.deleteTrabajador(this.trabajadorId).subscribe(() => {
        this.ngOnInit();
      });
    }
    else {
      alert('No se eliminó el trabajador');
    }
  }
}
