import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ImageSliderComponent } from './imageSlider.component';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [ImageSliderComponent],
  exports: [ImageSliderComponent]
})
export class ImageSliderModule {}
