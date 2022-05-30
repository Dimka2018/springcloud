import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../model/user";
import {Response} from "../model/Response";

@Injectable({providedIn: 'root'})
export class UserService {

  constructor(private http: HttpClient) {}

  public register(user: User): Observable<Response> {
    return this.http.post<Response>("/api/user", user);
  }

  public login(user: User): Observable<void> {
    let formData = new FormData();
    // @ts-ignore
    formData.append("username", user.login)
    // @ts-ignore
    formData.append("password", user.password)
    return this.http.post<void>("/api/login", formData);
  }

  public logout(): Observable<void> {
    return this.http.get<void>("/api/logout");
  }

}
