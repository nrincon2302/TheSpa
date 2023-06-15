import { Component, Input, OnInit } from '@angular/core';
import { ArticuloDetail } from '../articulo-detail';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ArticuloService } from '../articulo.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Sede } from 'src/app/sede/sede';
import { SedeService } from 'src/app/sede/sede.service';

@Component({
  selector: 'app-articulo-update',
  templateUrl: './articulo-update.component.html',
  styleUrls: ['./articulo-update.component.css']
})
export class ArticuloUpdateComponent implements OnInit {

  articuloID!: string;
  @Input() articuloDetail!: ArticuloDetail;
  articuloForm!: FormGroup;
  sede!: FormGroup;
  sedes!: Sede[]

  constructor(
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
    private articuloService: ArticuloService,
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

  // id, nombre, descripcion, precio, imagen, talla, color, numDisponible);

  updateArticulo(articulo: ArticuloDetail) {
    console.log(articulo);
    if (!this.articuloForm.valid) return;

    this.sede = this.formBuilder.group({
      id: [Number(articulo.sede), [Validators.required]]
    });

    articulo.nombre = articulo.nombre

    articulo.descripcion = articulo.descripcion;

    articulo.talla = articulo.talla;

    articulo.color = articulo.color;

    articulo.sede = this.sede.value;

    articulo.precio = Number(articulo.precio);

    articulo.numDisponible = Number(articulo.numDisponible);

    this.articuloService.updateArticulo(articulo, this.articuloID).subscribe(() => {
      console.info('This articulo was updated: ', articulo);
      this.toastr.success('Confirmation', 'Servicio updated');
      this.router.navigate(['/articulos/list']);
    }, err => {
      this.toastr.error(err, 'Error');
    });

  }

  cancelCreation() {
    this.toastr.warning("The articulo wasn't updated", "Articulo update");
    this.articuloForm.reset();
  }

  getArticulo() {
    this.articuloService.getArticuloxID(this.articuloID).subscribe(articulo => {
      this.articuloDetail = articulo;
    });
  }

  ngOnInit() {
    window.scrollTo(0, 0);
    if (this.articuloDetail == undefined) {
      this.articuloID = this.route.snapshot.paramMap.get('id')!;
      if (this.articuloID) {
        this.getArticulo();
      }
    }

    this.getSedes();

    this.articuloForm = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      descripcion: ['', [Validators.required, Validators.minLength(2)]],
      precio: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      sede: ['', [Validators.required]],
      imagen: ['', [Validators.required, Validators.pattern('^(http|https)://.*$')]],
      talla: ['', [Validators.required, Validators.minLength(1)]],
      color: ['', [Validators.required]],
      numDisponible: ['', [Validators.required, Validators.pattern('^[0-9]*$')]]
    });
  }
}
