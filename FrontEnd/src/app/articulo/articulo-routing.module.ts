import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ArticuloListComponent } from './articulo-list/articulo-list.component';
import { ArticuloDetailComponent } from './articulo-detail/articulo-detail.component';
import { ArticuloCreateComponent } from './articulo-create/articulo-create.component';
import { ArticuloUpdateComponent } from './articulo-update/articulo-update.component';


const routes: Routes = [{
    path: 'articulos',
    children: [
        {
            path: 'list',
            component: ArticuloListComponent
        },
        {
            path: 'update/:id',
            component: ArticuloUpdateComponent
        },
        {
            path: 'create',
            component: ArticuloCreateComponent
        },
        {
            path: ':id',
            component: ArticuloDetailComponent
        }
    ]
}];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ArticuloRoutingModule { }
