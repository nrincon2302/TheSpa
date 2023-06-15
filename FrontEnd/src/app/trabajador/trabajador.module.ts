import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TrabajadorComponent } from './trabajador.component';
import { ImageSliderModule } from '../imageSlider/imageSlider.module';
import { RouterModule } from '@angular/router';
import { TrabajadorRoutingModule } from './trabajador-routing.module';
import { TrabajadorDetailComponent } from './trabajador-detail/trabajador-detail.component';

@NgModule({
  imports: [
    CommonModule,
    ImageSliderModule,
    RouterModule,
    TrabajadorRoutingModule
  ],
  declarations: [TrabajadorComponent, TrabajadorDetailComponent],
  exports: [TrabajadorComponent, TrabajadorDetailComponent],
})
export class TrabajadorModule { }
