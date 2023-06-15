import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Articulo } from './articulo';
import { Observable } from 'rxjs';
import { ArticuloDetail } from './articulo-detail';

@Injectable({
  providedIn: 'root'
})
export class ArticuloService {

  private apiUrl: string = environment.baseUrl + 'articulosDeRopa';
  constructor(private http: HttpClient) { }

  getArticulosDeRopa(): Observable<ArticuloDetail[]> {
    return this.http.get<ArticuloDetail[]>(this.apiUrl);
  }

  getArticuloxID(id: string): Observable<ArticuloDetail> {
    return this.http.get<ArticuloDetail>(this.apiUrl + "/" + id);
  }

  createArticulo(articulo: ArticuloDetail): Observable<ArticuloDetail> {
    return this.http.post<ArticuloDetail>(this.apiUrl, articulo);
  }

  deleteArticulo(articuloID: string) {
    return this.http.delete(this.apiUrl + "/" + articuloID);
  }

  updateArticulo(articulo: Articulo, artID: string): Observable<ArticuloDetail> {
    return this.http.put<ArticuloDetail>(this.apiUrl + "/" + artID, articulo)
  }
}
