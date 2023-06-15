import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArticuloListComponent } from './articulo-list/articulo-list.component';
import { ArticuloDetailComponent } from './articulo-detail/articulo-detail.component';
import { RouterModule } from '@angular/router';
import { ArticuloRoutingModule } from './articulo-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ArticuloCreateComponent } from './articulo-create/articulo-create.component';
import { ArticuloUpdateComponent } from './articulo-update/articulo-update.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    ArticuloRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [ArticuloListComponent, ArticuloDetailComponent, ArticuloCreateComponent, ArticuloUpdateComponent],
  declarations: [ArticuloListComponent, ArticuloDetailComponent, ArticuloCreateComponent, ArticuloUpdateComponent]
})
export class ArticuloModule { }
