import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Password, UserDao, UserDto, UserPassword, ValidationCode,UserProfile } from 'src/app/components/applications/applications.component';


@Injectable({
  providedIn: 'root'
})
export class PasswordDataService {

    ROOT_URL:string="http://passwordmanager-dev.us-east-2.elasticbeanstalk.com";

    constructor(private httpClient: HttpClient) { }

    executeGetAllApps(username: string) {
      return this.httpClient.get<Password[]>(`${this.ROOT_URL}/secure/app/findById/${username}/safe`);
     }

    deleteAppById(id: number) {
      return this.httpClient.delete<Password>(`${this.ROOT_URL}/secure/app/removeById/${id}`);
    }

    deleteUserByUsername(username: any) {
      return this.httpClient.delete<UserDao>(`${this.ROOT_URL}/secure/app/removeUser/${username}`);
    }

    findById(id: number) {
      return this.httpClient.get<Password>(`${this.ROOT_URL}/secure/app/findAll/${id}/safe`);
    }

    updateById(passwordId: number, username: string, password: Password) {
      return this.httpClient.put(`${this.ROOT_URL}/secure/app/findById/${passwordId}/user/${username}`, password);
    }

    createNewApp(password: Password, username: string) {
      return this.httpClient.post(`${this.ROOT_URL}/secure/app/save/user/${username}`, password);
    }

    registerNewUser(userDto: UserDto) {
      return this.httpClient.post(`${this.ROOT_URL}/register`, userDto);
    }

    getBasicAuth() {
      return this.httpClient.get(`${this.ROOT_URL}/basicAuth`);
     }

     findUserByEmail(email: string) {
      return this.httpClient.get(`${this.ROOT_URL}/secure/app/findByEmail/${email}/safe`);
     }

     resetUserPassword(userPassword: UserPassword, validationCode: number) {
      return this.httpClient.put(`${this.ROOT_URL}/secure/app/user/${validationCode}/change`, userPassword);
    }

    getOneTimePassword(validationCode: ValidationCode, email: string) {
      return this.httpClient.post(`${this.ROOT_URL}/secure/app/getUser/${email}/safe/code`, validationCode);
    }

    getUserProfile(username: string) {
      return this.httpClient.get<UserDao>(`${this.ROOT_URL}/secure/app/findUser/${username}/secure`);
     }

     updateUserProfile(userProfile: UserProfile, username: string) {
      return this.httpClient.put(`${this.ROOT_URL}/secure/app/getUsersProfile/${username}`, userProfile);
     }

}
