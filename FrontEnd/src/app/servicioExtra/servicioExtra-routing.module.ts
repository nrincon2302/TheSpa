import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ServicioExtraListComponent } from './servicioExtra-list/servicioExtra-list.component';
import { ServicioExtraDetailComponent } from './servicioExtra-detail/servicioExtra-detail.component';
import { ServicioExtraCreateComponent } from './servicioExtra-create/servicioExtra-create.component';
import { ServicioExtraUpdateComponent } from './servicioExtra-update/servicioExtra-update.component';

const routes: Routes = [{
    path: 'serviciosExtra',
    children: [
        {
            path: 'list',
            component: ServicioExtraListComponent
        },
        {
            path: 'create',
            component: ServicioExtraCreateComponent
        },
        {
            path: ':id',
            component: ServicioExtraDetailComponent
        },
        {
            path: 'update/:id',
            component: ServicioExtraUpdateComponent
        }
    ]
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ServicioExtraRoutingModule { }
