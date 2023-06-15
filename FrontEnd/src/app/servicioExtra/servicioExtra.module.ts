import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ServicioExtraRoutingModule } from './servicioExtra-routing.module';
import { ServicioExtraListComponent } from './servicioExtra-list/servicioExtra-list.component';
import { ServicioExtraDetailComponent } from './servicioExtra-detail/servicioExtra-detail.component';
import { ServicioExtraCreateComponent } from './servicioExtra-create/servicioExtra-create.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ServicioExtraUpdateComponent } from './servicioExtra-update/servicioExtra-update.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    ServicioExtraRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [ServicioExtraListComponent, ServicioExtraDetailComponent, ServicioExtraCreateComponent, ServicioExtraUpdateComponent],
  exports: [ServicioExtraListComponent, ServicioExtraDetailComponent, ServicioExtraCreateComponent, ServicioExtraUpdateComponent]
})

export class ServicioExtraModule { }
