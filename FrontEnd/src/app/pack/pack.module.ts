import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PackDetailComponent } from './pack-detail/pack-detail.component';
import { PackListComponent } from './pack-list/pack-list.component';
import { PackCreateComponent } from './pack-create/pack-create.component';
import { PackUpdateComponent } from './pack-update/pack-update.component';
import { RouterModule } from '@angular/router';
import { PackRoutingModule } from './pack-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    PackRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [PackListComponent, PackDetailComponent, PackCreateComponent, PackUpdateComponent],
  exports: [PackListComponent, PackDetailComponent, PackCreateComponent, PackUpdateComponent]
})
export class PackModule { }
