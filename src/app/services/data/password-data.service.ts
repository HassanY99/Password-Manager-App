import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Password, UserDao, UserDto, UserPassword, ValidationCode } from 'src/app/components/applications/applications.component';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json;' })
};

let headers = new HttpHeaders({
  'Content-Type': 'application/json'
});
let options = { headers: headers };

@Injectable({
  providedIn: 'root'
})
export class PasswordDataService {


    constructor(private httpClient: HttpClient) { }

    executeGetAllApps(username: string) {
      return this.httpClient.get<Password[]>(`http://localhost:8080/secure/app/findById/${username}/safe`);
     }

    deleteAppById(id: number) {
      return this.httpClient.delete<Password>(`http://localhost:8080/secure/app/removeById/${id}`);
    }

    findById(id: number) {
      return this.httpClient.get<Password>(`http://localhost:8080/secure/app/findAll/${id}/safe`);
    }

    updateById(passwordId: number, username: string, password: Password) {
      return this.httpClient.put(`http://localhost:8080/secure/app/findById/${passwordId}/user/${username}`, password);
    }

    createNewApp(password: Password, username: string) {
      return this.httpClient.post(`http://localhost:8080/secure/app/save/user/${username}`, password);
    }

    registerNewUser(userDto: UserDto) {
      return this.httpClient.post(`http://localhost:8080/register`, userDto);
    }

    getBasicAuth() {
      return this.httpClient.get(`http://localhost:8080/basicAuth`);
     }

     findUserByEmail(email: string) {
      return this.httpClient.get(`http://localhost:8080/secure/app/findByEmail/${email}/safe`);
     }

     resetUserPassword(userPassword: UserPassword, validationCode: number) {
      return this.httpClient.put(`http://localhost:8080/secure/app/user/${validationCode}/change`, userPassword);
    }

    getOneTimePassword(validationCode: ValidationCode, email: string) {
      return this.httpClient.post(`http://localhost:8080/secure/app/getUser/${email}/safe/code`, validationCode);
    }

}
