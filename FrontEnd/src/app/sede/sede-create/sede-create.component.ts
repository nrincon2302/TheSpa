import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ServicioDetail } from 'src/app/servicio/servicio-detail';
import { ServicioService } from 'src/app/servicio/servicio.service';
import { Sede } from '../sede';
import { SedeService } from '../sede.service';
import { Ubicacion } from 'src/app/ubicacion/ubicacion';
import { TrabajadorService } from 'src/app/trabajador/trabajador.service';
import { Trabajador } from 'src/app/trabajador/trabajador';
import { UbicacionService } from 'src/app/ubicacion/ubicacion.service';
import { Servicio } from 'src/app/servicio/servicio';
import { UbicacionDetail } from 'src/app/ubicacion/ubicacion-detail';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-sede-create',
  templateUrl: './sede-create.component.html',
  styleUrls: ['./sede-create.component.css']
})
export class SedeCreateComponent implements OnInit {
  servicioForm!: FormGroup;
  sede!: FormGroup;
  ubicacion!: FormGroup;
  ubId!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
    private sedeService: SedeService,
    private router: Router,
    private ubicacionService: UbicacionService
  ) { }


  createUbicacion(ubicacionIn: Ubicacion, any: any) {

    let ubicacionCreadaID: number = 0;

    this.ubicacionService.createUbicacion(ubicacionIn).subscribe((ubicacionOut) => {
      ubicacionCreadaID = ubicacionOut.id;
      console.log('ubicacionOutId', ubicacionOut.id);
      console.info('The ubicacion was created: ', ubicacionOut);
      this.toastr.success('Confirmation', 'ubicacion created');


      this.ubId = this.formBuilder.group({
        id: [ubicacionCreadaID, [Validators.required]]
      })

      //Create sede with ubicacion id as input
      this.sede = this.formBuilder.group({
        nombre: [any.nombre, [Validators.required]],
        imagen: [any.imagen, [Validators.required]],
        ubicacion: [this.ubId.value, [Validators.required]]
      });

      console.log('llegamos hasta aqui', this.sede.value)

      this.sedeService.createSede(this.sede.value).subscribe((sede) => {
        console.info('The sede was created: ', sede);
        this.toastr.success('Confirmation', 'Sede created');
        this.router.navigate(['/sedes/list']);
        this.servicioForm.reset();
      }, err => {
        this.toastr.error(err, 'Error');
      }
      );




    }, err => {
      this.toastr.error(err, 'Error');
    }
    );


  }

  createSede(any: any) {

    if (!this.servicioForm.valid) return;

    this.ubicacion = this.formBuilder.group({
      latitud: [parseFloat(any.latitud), [Validators.required]],
      longitud: [parseFloat(any.longitud), [Validators.required]],
      ciudad: [any.ciudad, [Validators.required]],
      direccion: [any.direccion, [Validators.required]]
    });



    // Create Ubicacion
    this.createUbicacion(this.ubicacion.value, any);


  }

  cancelCreation() {
    this.toastr.warning("The sede wasn't created", 'Servicio creation');
    this.servicioForm.reset();
  }

  ngOnInit() {
    window.scrollTo(0, 0);

    this.servicioForm = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],

      latitud: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],

      longitud: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],

      ciudad: ['', [Validators.required, Validators.minLength(2)]],

      direccion: ['', [Validators.required, Validators.minLength(2)]],

      imagen: ['', [Validators.required, Validators.pattern('^(http|https)://.*$')]],
    });

  }
}
