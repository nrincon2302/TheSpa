import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { PackDetail } from '../pack-detail';
import { PackService } from '../pack.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Sede } from 'src/app/sede/sede';
import { SedeService } from 'src/app/sede/sede.service';
import { Servicio } from 'src/app/servicio/servicio';
import { ServicioService } from 'src/app/servicio/servicio.service';

@Component({
  selector: 'app-pack-create',
  templateUrl: './pack-create.component.html',
  styleUrls: ['./pack-create.component.css'],
})
export class PackCreateComponent implements OnInit {
  packForm!: FormGroup;
  sede!: FormGroup;
  sedes!: Sede[]
  servicios!: Servicio[];

  constructor(
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
    private packService: PackService,
    private sedeService: SedeService,
    private servicioService: ServicioService,
    private router: Router
  ) { }

  getSedes(): void {
    this.sedeService.getSedes().subscribe(sedes => {
      this.sedes = sedes;
    }, err => {
      this.toastr.error(err, 'Error');
    });
  }

  getServicios(): void {
    this.servicioService.getServices().subscribe(servicios => {
      this.servicios = servicios;
    }, err => {
      this.toastr.error(err, 'Error');
    });
  }

  createPack(pack: PackDetail) {
    if (!this.packForm.valid) return;
    // convierte el atributo sede en un FormGroup, cuyo id será el id de la sede
    this.sede = this.formBuilder.group({
      id: [pack.sede, [Validators.required]]
    });
    pack.sede = this.sede.value;
    // convierte el atributo servicios de una lista de ids en una lista de FormGroups
    // con id el id del servicio
    let servicios: FormArray = this.packForm.get('servicios') as FormArray;
    let serviciosForm: FormGroup[] = [];
    servicios.value.forEach((servicio: any) => {
      serviciosForm.push(this.formBuilder.group({
        id: [servicio, [Validators.required]]
      }));
    });
    pack.servicios = serviciosForm.map(servicio => servicio.value);



    console.log(serviciosForm);


    this.packService.createPack(pack).subscribe(() => {
      console.info('The pack was created: ', pack);
      this.toastr.success('Confirmation', 'Pack created');
      this.router.navigate(['/packs/list']);
      this.packForm.reset();
    }, err => {
      this.toastr.error(err, 'Error');
    }
    );
  }

  cancelCreation() {
    this.toastr.warning("The pack wasn't created", 'Pack creation');
    this.packForm.reset();
  }

  ngOnInit() {
    window.scrollTo(0, 0);

    this.getSedes();

    this.getServicios();

    this.packForm = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      descuento: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      sede: ['', [Validators.required]],

      imagen: ['', [Validators.required, Validators.pattern('^(http|https)://.*$')]],

      // Un paquete puede tener muchos servicios
      servicios: this.formBuilder.array([]) // Initialize servicios as a FormArray
    });


  }


  onCheckboxChange(e: any) {
    const checkArray: FormArray = this.packForm.get('servicios') as FormArray;

    if (e.target.checked) {
      checkArray.push(new FormControl(e.target.value));
      console.log(e.target.value);
    }
    else {
      let i: number = 0;
      checkArray.controls.forEach((item: AbstractControl) => {
        if (item.value == e.target.value) {
          checkArray.removeAt(i);
          return;
        }
        i++;
      });
    }

  }
}