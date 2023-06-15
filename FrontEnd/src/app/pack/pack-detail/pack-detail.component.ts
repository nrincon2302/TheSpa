import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PackDetail } from '../pack-detail';
import { PackService } from '../pack.service';

@Component({
  selector: 'app-pack-detail',
  templateUrl: './pack-detail.component.html',
  styleUrls: ['./pack-detail.component.css']
})
export class PackDetailComponent implements OnInit {

  pakId!: string;
  @Input() packDetail!: PackDetail;

  constructor(private rout: ActivatedRoute, private packService: PackService) { }

  getPack() {
    this.packService.getPack(this.pakId).subscribe(pack => { this.packDetail = pack; })
  }

  ngOnInit() {
    window.scrollTo(0, 0);
    if (this.packDetail === undefined) {
      this.pakId = this.rout.snapshot.paramMap.get('id')!
      if (this.pakId) {
        this.getPack();
      }
    }

  }

  deletePack() {
    const confirmacion = confirm('¿Estás seguro de que deseas eliminar?');
    if (confirmacion === true) {
      this.packService.deletPack(this.pakId).subscribe(() => {
        this.ngOnInit();
      });
    }
    else {
      alert('No se eliminó el paquete de servicios');
    }
  }

}
