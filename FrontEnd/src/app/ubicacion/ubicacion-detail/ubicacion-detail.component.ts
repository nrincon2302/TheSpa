import { Component, Input, OnInit } from '@angular/core';
import { UbicacionDetail } from '../ubicacion-detail';
import { Ubicacion } from '../ubicacion';
import { UbicacionService } from '../ubicacion.service';
import { UbicacionComponent } from '../ubicacion.component';

@Component({
  selector: 'app-ubicacion-detail',
  templateUrl: './ubicacion-detail.component.html',
  styleUrls: ['./ubicacion-detail.component.css']
})
export class UbicacionDetailComponent implements OnInit {

  @Input() ubicacion: Ubicacion | undefined;

  ubicacionDetail: UbicacionDetail | undefined;

  constructor(private ubicacionService: UbicacionService, private ubicacionComponent: UbicacionComponent) { }

  ngOnInit() {
    this.getUbicacionDetail();
  }

  getUbicacionDetail() {
    this.ubicacionService.getUbicacionDetail(this.ubicacion?.id || 0).subscribe(
      ubicacionDetail => {
        this.ubicacionDetail = ubicacionDetail;
      }
    );
  }

  refreshUbicacionDetail() {
    // Implement the logic to refresh or recall the `app-ubicacion-detail` component here
    // For example, you can reset the `selectedUbicacion` variable to null
    this.ubicacionComponent.refreshUbicacionDetail();

  }

}
