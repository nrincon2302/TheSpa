import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ServicioExtra } from '../servicioExtra';
import { ServicioExtraDetail } from '../servicioExtra-detail';
import { ServicioExtraService } from '../servicioExtra.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Sede } from 'src/app/sede/sede';
import { SedeService } from 'src/app/sede/sede.service';

@Component({
  selector: 'app-servicioExtra-create',
  templateUrl: './servicioExtra-create.component.html',
  styleUrls: ['./servicioExtra-create.component.css']
})

export class ServicioExtraCreateComponent implements OnInit {
  servicioExtraForm!: FormGroup;
  sede!: FormGroup;
  sedes!: Sede[]

  constructor(
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
    private servicioExtraService: ServicioExtraService,
    private sedeService: SedeService,
    private router: Router
  ) { }

  getSedes(): void {
    this.sedeService.getSedes().subscribe(sedes => {
      this.sedes = sedes;
    }, err => {
      this.toastr.error(err, 'Error');
    });
  }

  createServicioExtra(servicioExtra: ServicioExtraDetail) {
    console.log(servicioExtra);
    if (!this.servicioExtraForm.valid) return;
    // adds to the servicio extra a "disponible" property, which is true by default
    servicioExtra.disponible = true;
    // convierte el atributo sede en un FormGroup, cuyo id será el id de la sede
    this.sede = this.formBuilder.group({
      id: [servicioExtra.sede, [Validators.required]]
    });
    servicioExtra.sede = this.sede.value;


    this.servicioExtraService.createServicioExtra(servicioExtra).subscribe((servicioExtra) => {
      console.info('The servicio extra was created: ', servicioExtra);
      this.toastr.success('Confirmation', 'Servicio extra created');
      this.router.navigate(['/serviciosExtra/list']);
      this.servicioExtraForm.reset();
    }, err => {
      this.toastr.error(err, 'Error');
    }
    );
  }

  cancelCreation() {
    this.toastr.warning("The servicio extra wasn't created", 'Servicio Extra creation');
    this.servicioExtraForm.reset();
  }

  ngOnInit() {
    window.scrollTo(0, 0);

    this.getSedes();

    this.servicioExtraForm = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      descripcion: ['', [Validators.required, Validators.maxLength(100)]],
      sede: ['', [Validators.required]],
      precio: ['', [Validators.required, Validators.pattern('^[0-9]+(\.[0-9]{1,2})?$')]],
      // imagen checks if the value is a valid URL, meaning it must start with http:// or https://
      imagen: ['', [Validators.required, Validators.pattern('^(http|https)://.*$')]],
    });
  }
}
