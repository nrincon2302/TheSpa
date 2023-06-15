import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ServicioExtra } from './servicioExtra';
import { ServicioExtraDetail } from './servicioExtra-detail';

@Injectable({
  providedIn: 'root'
})

export class ServicioExtraService {

  private apiUrl: string = environment.baseUrl + 'serviciosExtra';

  constructor(private http: HttpClient) { }

  getServiciosExtra(): Observable<ServicioExtraDetail[]> {
    return this.http.get<ServicioExtraDetail[]>(this.apiUrl);
  }

  getServicioExtra(id: string): Observable<ServicioExtraDetail> {
    return this.http.get<ServicioExtraDetail>(this.apiUrl + "/" + id);
  }

  createServicioExtra(servicioExtra: ServicioExtraDetail): Observable<ServicioExtraDetail> {
    return this.http.post<ServicioExtraDetail>(this.apiUrl, servicioExtra);
  }

  deleteServicioExtra(servicioExtraId: string) {
    return this.http.delete(this.apiUrl + "/" + servicioExtraId);
  }

  updateServicioExtra(servicioExtra: ServicioExtraDetail, servicioExtraID: string): Observable<ServicioExtraDetail> {
    return this.http.put<ServicioExtraDetail>(this.apiUrl + "/" + servicioExtraID, servicioExtra)
  }
}
