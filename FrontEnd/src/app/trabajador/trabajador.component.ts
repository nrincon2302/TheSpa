import { Component, OnInit } from '@angular/core';
import { SlideInterface } from '../imageSlider/types/slide.interface';
import { TrabajadorService } from './trabajador.service';
import { Trabajador } from './trabajador';
import { TrabajadorDetail } from './trabajador-detail';
import { newArray } from '@angular/compiler/src/util';

@Component({
  selector: 'app-trabajador',
  templateUrl: './trabajador.component.html',
  styleUrls: ['./trabajador.component.css']
})
export class TrabajadorComponent implements OnInit {

  trabajadores: any[] = [];

  slides: SlideInterface[] = [];

  selectedTrabajador!: TrabajadorDetail;
  selected = false;
  arrayT = new Array();
  arrayF = new Array();


  /*
    {url: 'https://www.officetally.com/wp-content/uploads/2009/03/rainn-wilson-dwight-schrute-the-office.jpg', title: 'Dwight Schrute'},
    {url: 'https://www.officetally.com/wp-content/uploads/2009/03/brian-baumgartner-kevin-malone-the-office.jpg', title: 'Kevin Malone'},
    {url: 'https://www.officetally.com/wp-content/uploads/2009/03/angela-kinsey-angela-martin-the-office.jpg', title: 'Angela Martin'},
    {url: 'https://www.officetally.com/wp-content/uploads/2009/03/jenna-fischer-pam-beesly-the-office.jpg', title: 'Pam Beesly'},
    {url: 'https://www.officetally.com/wp-content/uploads/2009/03/john-krasinski-jim-halpert-the-office.jpg', title: 'Jim Halpert'}

  */

  constructor(private trabajadorService: TrabajadorService) { }

  getTrabajadores(): void {
    // use the getTrajadores() method from the TrabajadorService to retrieve the trabajadores
    // but only those that are in the Hall of Fame
    this.trabajadorService.getTrabajadores().subscribe(trabajadores => {
      let trabajadoresHallOfFame: Trabajador[] = [];
      for (let i = 0; i < trabajadores.length; i++) {
        if (trabajadores[i].enHallOfFame) {
          trabajadoresHallOfFame.push(trabajadores[i]);
        }
      }
      this.trabajadores = trabajadoresHallOfFame;
    });
    


  }

  setSlides(): void {
    this.slides = [];
    this.trabajadorService.getTrabajadores().subscribe(trabajadores => {
      for (let i = 0; i < trabajadores.length; i++) {
        if (trabajadores[i].enHallOfFame) {
          this.slides.push({ url: trabajadores[i].foto, title: trabajadores[i].nombre });
        }
      }
    }
    );
  }

  setSelect(trabajador: TrabajadorDetail): void {
    this.selectedTrabajador = trabajador;
    this.selected = true;
    this.getStarArray();
  }

  getStarArray(): void {
    this.arrayT = new Array();
    this.arrayF = new Array();
    for (let i = 0; i < Math.round(this.selectedTrabajador.calificacion / 2); i++) {
      this.arrayT.push(1);
    }
    for (let i = 0; i < 5 - Math.round(this.selectedTrabajador.calificacion / 2); i++) {
      this.arrayF.push(1);
    }
  }

  ngOnInit() {
    this.getTrabajadores();
    this.setSlides();
  }

}
