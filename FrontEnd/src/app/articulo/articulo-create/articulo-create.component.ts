import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Articulo } from '../articulo';
import { ArticuloDetail } from '../articulo-detail';
import { ArticuloService } from '../articulo.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { Sede } from 'src/app/sede/sede';
import { SedeService } from 'src/app/sede/sede.service';

@Component({
  selector: 'app-articulo-create',
  templateUrl: './articulo-create.component.html',
  styleUrls: ['./articulo-create.component.css']
})
export class ArticuloCreateComponent implements OnInit {

  articuloForm!: FormGroup;
  sede!: FormGroup;
  sedes!: Sede[]

  constructor(
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
    private servicioService: ArticuloService,
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

  createArticulo(articulo: ArticuloDetail) {
    console.log(articulo);
    if (!this.articuloForm.valid) return;
    this.sede = this.formBuilder.group({
      id: [articulo.sede, [Validators.required]]
    });
    articulo.sede = this.sede.value;

    this.servicioService.createArticulo(articulo).subscribe((articulo) => {
      console.info('The articulo was created: ', articulo);
      this.toastr.success('Confirmation', 'Articulo created');
      this.router.navigate(['/articulos/list']);
      this.articuloForm.reset();
    }, err => {
      this.toastr.error(err, 'Error');
    });
  }

  cancelCreation() {
    this.toastr.warning("The articulo wasn't created", 'Articulo creation');
    this.articuloForm.reset();
  }

  ngOnInit() {
    window.scrollTo(0, 0);
    this.getSedes();

    this.articuloForm = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      descripcion: ['', [Validators.required, Validators.minLength(2)]],
      precio: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      sede: ['', [Validators.required]],
      imagen: ['', [Validators.required, Validators.pattern('^(http|https)://.*$')]],
      talla: ['', [Validators.required, Validators.minLength(1)]],
      color: ['', [Validators.required]],
      numDisponible: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
    });
  }
}
