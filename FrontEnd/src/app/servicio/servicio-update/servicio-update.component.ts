import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Servicio } from '../servicio';
import { ServicioDetail } from '../servicio-detail';
import { ServicioService } from '../servicio.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Sede } from 'src/app/sede/sede';
import { SedeService } from 'src/app/sede/sede.service';

@Component({
  selector: 'app-servicio-update',
  templateUrl: './servicio-update.component.html',
  styleUrls: ['./servicio-update.component.css'],
})
export class ServicioUpdateComponent implements OnInit {
  servicioId!: string;
  @Input() servicioDetail!: ServicioDetail;
  servicioForm!: FormGroup;
  sede!: FormGroup;
  sedes!: Sede[]

  constructor(
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
    private servicioService: ServicioService,
    private sedeService: SedeService,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  getSedes(): void {
    this.sedeService.getSedes().subscribe(sedes => {
      this.sedes = sedes;
    }, err => {
      this.toastr.error(err, 'Error');
    });
  }

  updateServicio(servicio: ServicioDetail) {
    console.log(servicio);
    if (!this.servicioForm.valid) return;
    // adds to the servicio a "disponible" property, which is true by default
    servicio.disponible = true;
    // convierte el atributo sede en un FormGroup, cuyo id será el id de la sede
    this.sede = this.formBuilder.group({
      id: [Number(servicio.sede), [Validators.required]]
    });
    servicio.sede = this.sede.value;

    // parsea la duracion a un number
    servicio.duracion = Number(servicio.duracion);

    // parsea el precio a un number
    servicio.precio = Number(servicio.precio);

    // parsea la hora de inicio a un number
    servicio.horaInicio = Number(servicio.horaInicio);



    this.servicioService.updateService(servicio, this.servicioId).subscribe((servicio) => {
      console.info('The servicio was updated: ', servicio);
      this.toastr.success('Confirmation', 'Servicio updated');
      this.router.navigate(['/servicios/list']);
      this.servicioForm.reset();
    }, err => {
      this.toastr.error(err, 'Error');
    }
    );
  }

  cancelCreation() {
    this.toastr.warning("The servicio wasn't updated", 'Servicio update');
    this.servicioForm.reset();
  }

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

    this.getSedes();

    this.servicioForm = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      descripcion: ['', [Validators.required, Validators.maxLength(100)]],
      sede: ['', [Validators.required]],
      restricciones: ['', [Validators.required, Validators.maxLength(100)]],
      // horaDeInicio checks if the value is a valid time
      horaInicio: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      // duracion checks if the value is numeric
      duracion: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      // precio checks if the value is numeric, can be decimal
      precio: ['', [Validators.required, Validators.pattern('^[0-9]+(\.[0-9]{1,2})?$')]],
      // imagen checks if the value is a valid URL, meaning it must start with http:// or https://    
      imagen: ['', [Validators.required, Validators.pattern('^(http|https)://.*$')]],
    });
  }
}
