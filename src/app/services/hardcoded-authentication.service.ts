import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs';

export class BasicAuth {
  constructor(
    public message: string
  ) {}
}

@Injectable({
  providedIn: 'root'
})
export class HardcodedAuthenticationService {

  ROOT_URL:string="http://passwordmanager-dev.us-east-2.elasticbeanstalk.com";

  token:any;

  constructor(private http: HttpClient) { }


  executBackendAuthentication(username: string, password: string) {

    return this.http.post<any>(`${this.ROOT_URL}/authenticate`, {
      username,
      password
    }).pipe(
      map(
        data => {
          sessionStorage.setItem('authenticatedUser', username);
          sessionStorage.setItem('token', `Bearer ${data.token}`);
          return data;
        }
      )
    );
  }

  getAuthenticatedUser() {
    return sessionStorage.getItem('authenticatedUser');
  }

  getAuthenticatedToken() {
    if(this.getAuthenticatedUser()) {
      return sessionStorage.getItem('token');
    }
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('authenticatedUser');
    let token = sessionStorage.getItem('token');
    if(user && token) {
      return !(user === null);
    }

  }

  loggedOut() {
    sessionStorage.removeItem('authenticatedUser');
    sessionStorage.removeItem('token');
  }

}
