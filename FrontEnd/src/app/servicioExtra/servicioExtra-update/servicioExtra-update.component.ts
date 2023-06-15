import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ServicioExtra } from '../servicioExtra';
import { ServicioExtraDetail } from '../servicioExtra-detail';
import { ServicioExtraService } from '../servicioExtra.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Sede } from 'src/app/sede/sede';
import { SedeService } from 'src/app/sede/sede.service';

@Component({
  selector: 'app-servicioExtra-update',
  templateUrl: './servicioExtra-update.component.html',
  styleUrls: ['./servicioExtra-update.component.css']
})

export class ServicioExtraUpdateComponent implements OnInit {
  servicioExtraForm!: FormGroup;
  servicioId!: string;

  @Input() servicioDetail!: ServicioExtraDetail;
  sede!: FormGroup;
  sedes!: Sede[]

  constructor(
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
    private servicioExtraService: ServicioExtraService,
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

  updateServicioExtra(servicioExtra: ServicioExtraDetail) {
    console.log(servicioExtra);
    if (!this.servicioExtraForm.valid) return;
    // adds to the servicio extra a "disponible" property, which is true by default
    servicioExtra.disponible = true;
    // convierte el atributo sede en un FormGroup, cuyo id será el id de la sede
    this.sede = this.formBuilder.group({
      id: [servicioExtra.sede, [Validators.required]]
    });
    servicioExtra.sede = this.sede.value;


    this.servicioExtraService.updateServicioExtra(servicioExtra, this.servicioId).subscribe((servicioExtra) => {
      console.info('The servicio extra was updated: ', servicioExtra);
      this.toastr.success('Confirmation', 'Servicio extra updated');
      this.router.navigate(['/serviciosExtra/list']);
      this.servicioExtraForm.reset();
    }, err => {
      this.toastr.error(err, 'Error');
    }
    );
  }

  cancelCreation() {
    this.toastr.warning("The servicio extra wasn't updated", 'Servicio Extra creation');
    this.servicioExtraForm.reset();
  }

  getServicio() {
    this.servicioExtraService.getServicioExtra(this.servicioId).subscribe(servicio => {
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
