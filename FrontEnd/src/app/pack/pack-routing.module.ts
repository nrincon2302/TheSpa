import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PackListComponent } from './pack-list/pack-list.component';
import { PackDetailComponent } from './pack-detail/pack-detail.component';
import { PackCreateComponent } from './pack-create/pack-create.component';
import { PackUpdateComponent } from './pack-update/pack-update.component';


const routes: Routes = [{
    path: 'packs',
    children: [
        {
            path: 'list',
            component: PackListComponent
        },
        {
            path: 'create',
            component: PackCreateComponent
        },
        {
            path: 'update/:id',
            component: PackUpdateComponent
        },
        {
            path: ':id',
            component: PackDetailComponent
        },
    ]
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PackRoutingModule { }
