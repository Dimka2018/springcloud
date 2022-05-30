import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {File} from "../model/File";
import {Response} from "../model/Response";

@Injectable({providedIn: 'root'})
export class FileService {

  constructor(private http: HttpClient) {}

  public uploadFile(file: any): Observable<File> {
    let formData = new FormData();
    formData.append("file", file)
    return this.http.post<File>("/api/file", formData);
  }

  public getFiles(): Observable<File[]> {
    return this.http.get<File[]>("/api/files")
  }

  public downloadFile(id: string) {
    // @ts-ignore
    window.location = `/api/file/${id}/content`;
  }

  public delete(id: string): Observable<Response> {
    return this.http.delete<Response>(`/api/file/${id}`);
  }

  public rename(file: File): Observable<Response> {
    return this.http.put<Response>(`/api/file`, file);
  }

}
