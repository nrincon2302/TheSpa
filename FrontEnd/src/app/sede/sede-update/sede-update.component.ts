import { Component, Input, OnInit } from '@angular/core';
import { SedeDetail } from '../sede-detail';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Sede } from '../sede';
import { Router, ActivatedRoute } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ServicioService } from 'src/app/servicio/servicio.service';
import { SedeService } from '../sede.service';

@Component({
  selector: 'app-sede-update',
  templateUrl: './sede-update.component.html',
  styleUrls: ['./sede-update.component.css']
})
export class SedeUpdateComponent implements OnInit {

  sedeId!: string;
  @Input() sedeDetail!: SedeDetail;
  sedeForm!: FormGroup;
  sede!: FormGroup;
  sedes!: Sede[]

  constructor(
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
    private sedeService: SedeService,
    private router: Router,
    private route: ActivatedRoute,
  ) { }

  getSede() {
    this.sedeService.getSede(this.sedeId).subscribe(sede => {
      this.sedeDetail = sede;
    })
  }

  ngOnInit() {
    window.scrollTo(0, 0);

    if (this.sedeDetail === undefined) {
      this.sedeId = this.route.snapshot.paramMap.get('id')!
      if (this.sedeId) {
        this.getSede();
      }
    }

    this.getSedes();


    this.sedeForm = this.formBuilder.group({
      nombre: ['', [Validators.required, Validators.minLength(2)]],
      // imagen checks if the value is a valid URL, meaning it must start with http:// or https://
      imagen: ['', [Validators.required, Validators.pattern('^(http|https)://.*$')]],

    });



  }

  updateSede(sede: SedeDetail) {
    if (!this.sedeForm.valid) return;

    this.sedeService.updateSede(sede, this.sedeId).subscribe((sede) => {
      console.info('The sede was updated: ', sede);
      this.toastr.success('Confirmation', 'Sede updated');
      this.router.navigate(['/sedes/list']);
      this.sedeForm.reset();
    }, err => {
      this.toastr.error(err, 'Error');
    }
    );
  }

  cancelCreation() {
    this.toastr.warning("The sede wasn't updated", 'Sede update');
    this.sedeForm.reset();
  }

  getSedes(): void {
    this.sedeService.getSedes().subscribe(sedes => {
      this.sedes = sedes;
    }, err => {
      this.toastr.error(err, 'Error');
    });
    console.log(this.sedes);
  }

}
