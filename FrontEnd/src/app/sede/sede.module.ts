import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SedeListComponent } from './sede-list/sede-list.component';
import { SedeDetailComponent } from './sede-detail/sede-detail.component';
import { RouterModule } from '@angular/router';
import { SedeRoutingModule } from './sede-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SedeCreateComponent } from './sede-create/sede-create.component';
import { SedeUpdateComponent } from './sede-update/sede-update.component';


@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    SedeRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [SedeListComponent, SedeDetailComponent, SedeCreateComponent, SedeUpdateComponent],
  exports: [SedeListComponent, SedeDetailComponent, SedeCreateComponent, SedeUpdateComponent]
})
export class SedeModule { }
