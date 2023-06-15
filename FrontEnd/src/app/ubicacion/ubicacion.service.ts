import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Ubicacion } from './ubicacion';
import { UbicacionDetail } from './ubicacion-detail';

@Injectable({
  providedIn: 'root'
})
export class UbicacionService {

  private apiUrl: string = environment.baseUrl + 'ubicaciones';

constructor(private http: HttpClient) { }

getUbicaciones(): Observable<Ubicacion[]> {
  return this.http.get<Ubicacion[]>(this.apiUrl);
}

getUbicacionDetail(id: any): Observable<UbicacionDetail> {
  return this.http.get<UbicacionDetail>(this.apiUrl + `/${id}`);
}

createUbicacion(ubicacion: Ubicacion): Observable<Ubicacion> {
  return this.http.post<Ubicacion>(this.apiUrl, ubicacion);
}

}
