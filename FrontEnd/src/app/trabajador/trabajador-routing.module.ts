import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TrabajadorComponent } from './trabajador.component';
import { TrabajadorDetailComponent } from './trabajador-detail/trabajador-detail.component';


const routes: Routes = [{
    path: 'trabajadores',
    children: [
        {
            path: 'list',
            component: TrabajadorComponent
        },
        {
            path: ':id',
            component: TrabajadorDetailComponent
        }

    ]
}
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TrabajadorRoutingModule { }
