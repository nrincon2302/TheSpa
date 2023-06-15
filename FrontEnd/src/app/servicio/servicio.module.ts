import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ServicioListComponent } from './servicio-list/servicio-list.component';
import { ServicioDetailComponent } from './servicio-detail/servicio-detail.component';
import { ServicioCreateComponent } from './servicio-create/servicio-create.component';
import { ServicioUpdateComponent } from './servicio-update/servicio-update.component';
import { RouterModule } from '@angular/router';
import { ServicioRoutingModule } from './servicio-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
  imports: [
    CommonModule
    , RouterModule
    , ServicioRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [ServicioListComponent, ServicioDetailComponent, ServicioCreateComponent, ServicioUpdateComponent],
  exports: [ServicioListComponent, ServicioDetailComponent, ServicioCreateComponent, ServicioUpdateComponent]
})
export class ServicioModule { }
