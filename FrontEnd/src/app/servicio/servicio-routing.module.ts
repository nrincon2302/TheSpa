import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ServicioListComponent } from './servicio-list/servicio-list.component';
import { ServicioDetailComponent } from './servicio-detail/servicio-detail.component';
import { ServicioCreateComponent } from './servicio-create/servicio-create.component';
import { ServicioUpdateComponent } from './servicio-update/servicio-update.component';


const routes: Routes = [{
    path: 'servicios',
    children: [
        {
            path: 'list',
            component: ServicioListComponent
        },
        {
            path: 'create',
            component: ServicioCreateComponent
        },
        {
            path: 'update/:id',
            component: ServicioUpdateComponent
        },
        {
            path: ':id',
            component: ServicioDetailComponent
        }


    ]
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ServicioRoutingModule { }
