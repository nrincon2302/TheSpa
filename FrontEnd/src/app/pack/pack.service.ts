import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PackDetail } from './pack-detail';
import { environment } from 'src/environments/environment';
import { Pack } from './pack';
@Injectable({
  providedIn: 'root'
})
export class PackService {

  private apiUrl: string = environment.baseUrl + 'packsDeServicios';

  constructor(private http: HttpClient) { }

  getPacks(): Observable<PackDetail[]> {
    return this.http.get<PackDetail[]>(this.apiUrl);
  }

  getPack(id: string): Observable<PackDetail> {
    return this.http.get<PackDetail>(this.apiUrl + "/" + id);
  }

  createPack(paquete: Pack): Observable<PackDetail> {
    return this.http.post<PackDetail>(this.apiUrl, paquete);
  }

  deletPack(packID: string) {
    return this.http.delete(this.apiUrl + "/" + packID);
  }

  updatePack(paquete: Pack, pakId: string): Observable<PackDetail> {
    return this.http.put<PackDetail>(this.apiUrl + "/" + pakId, paquete)
  }

}