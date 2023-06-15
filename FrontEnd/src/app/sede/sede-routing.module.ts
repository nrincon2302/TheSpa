import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SedeListComponent } from './sede-list/sede-list.component';
import { SedeDetailComponent } from './sede-detail/sede-detail.component';
import { SedeCreateComponent } from './sede-create/sede-create.component';
import { SedeUpdateComponent } from './sede-update/sede-update.component';


const routes: Routes = [{
    path: 'sedes',
    children: [
        {
            path: 'list',
            component: SedeListComponent
        },
        {
            path: 'create',
            component: SedeCreateComponent
        },
        {
          path: 'update/:id',
          component: SedeUpdateComponent
        },
        {
            path: ':id',
            component: SedeDetailComponent
        },
    ]
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class SedeRoutingModule { }
