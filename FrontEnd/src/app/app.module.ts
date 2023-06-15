import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { SedeRoutingModule } from './sede/sede-routing.module';
import { ServicioRoutingModule } from './servicio/servicio-routing.module';
import { AppComponent } from './app.component';
import { TrabajadorModule } from './trabajador/trabajador.module';
import { PackModule } from './pack/pack.module';
import { ServicioModule } from './servicio/servicio.module';
import { HttpClientModule } from '@angular/common/http';
import { SedeModule } from './sede/sede.module';
import { ArticuloModule } from './articulo/articulo.module';
import { ImageSliderModule } from './imageSlider/imageSlider.module';
import { HomeComponent } from './home/home.component';
import { ServicioExtraModule } from './servicioExtra/servicioExtra.module';
import { RouterModule } from '@angular/router';
import { ToastrModule } from 'ngx-toastr';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FooterPanelComponent } from './footerPanel/footerPanel.component';
import { UbicacionModule } from './ubicacion/ubicacion.module';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    FooterPanelComponent
  ],
  imports: [
    ToastrModule.forRoot({
      timeOut: 10000,
      positionClass: 'toast-bottom-right',
      preventDuplicates: true,
    }
    ),
    BrowserAnimationsModule,
    ServicioRoutingModule,
    BrowserModule,
    RouterModule,
    AppRoutingModule,
    ServicioExtraModule,
    SedeRoutingModule,
    ServicioModule,
    ArticuloModule,
    HttpClientModule,
    PackModule,
    HttpClientModule,
    SedeModule,
    ImageSliderModule,
    TrabajadorModule,
    PackModule,
    HttpClientModule,
    SedeModule,
    ServicioExtraModule,
    UbicacionModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
