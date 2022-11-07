import { HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { HardcodedAuthenticationService } from '../hardcoded-authentication.service';

@Injectable({
  providedIn: 'root'
})
export class HttpIntercepterService implements HttpInterceptor {

  constructor(private authentication: HardcodedAuthenticationService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler) {

    let authenticatedHeaderToken = this.authentication.getAuthenticatedToken();
    let authenticatedUsername = this.authentication.getAuthenticatedUser();

    if(authenticatedHeaderToken && authenticatedUsername) {

      request = request.clone({
        setHeaders : {
          Authorization : authenticatedHeaderToken
        }
      })
    }

    return next.handle(request);

  }

}
