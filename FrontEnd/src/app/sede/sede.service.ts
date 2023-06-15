import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { SedeDetail } from './sede-detail';
import { Sede } from './sede';

@Injectable({
  providedIn: 'root'
})
export class SedeService {
  private apiUrl: string = environment.baseUrl + 'sedes';

  constructor(private http: HttpClient) { }

  getSedes(): Observable<SedeDetail[]> {
    return this.http.get<SedeDetail[]>(this.apiUrl);

  }

  getSede(id: string): Observable<SedeDetail> {
    return this.http.get<SedeDetail>(this.apiUrl + "/" + id);
  }

  createSede(sede: Sede): Observable<SedeDetail> {
    return this.http.post<SedeDetail>(this.apiUrl, sede);
  }

  deleteSede(sedeID: string) {
    return this.http.delete(this.apiUrl + "/" + sedeID);
  }

  updateSede(servicio: Sede, serID: string): Observable<SedeDetail> {
    return this.http.put<SedeDetail>(this.apiUrl + "/" + serID, servicio)
  }

}
