import { Component, ContentChild, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HardcodedAuthenticationService } from 'src/app/services/hardcoded-authentication.service';
import { IonInput } from '@ionic/angular';
// import { faL } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  username = '';
  password = '';

  firstName = '';
  lastName = '';
  errorMessage = 'Your Username or Password is incorrect.'
  invalidLogin = false;

  invalidPassword = false;
  passwordErrorMessage = 'Password is invalid.'

  @ContentChild(IonInput) input: IonInput;
  // showPassword = false;

  show_button: Boolean = false;
  show_eye: Boolean = false;

  constructor(private router: Router,
    private hardcodedAuthenticate: HardcodedAuthenticationService) { }

  ngOnInit(): void {
  }

  handleLoginBackend() {
    this.invalidLogin = false;
    this.invalidPassword = false;

    this.hardcodedAuthenticate.executBackendAuthentication(this.username, this.password).subscribe({
     next: (data) => {
        this.invalidLogin = false;
        this.router.navigate(['apps', this.username])
      },
      error: (error) => {
        if(error.error.message == "Password is incorrect") {
          this.invalidPassword = true;
        } else if(error.error.message == "No User exists credentials") {
          this.invalidLogin = true;
        }
      }
    }
    )
  }

  signUp() {
    this.router.navigate(['signup']);
  }

  routeToReset() {
    this.router.navigate(['resetPassword']);
  }

  //Function
showPassword() {
  this.show_button = !this.show_button;
  this.show_eye = !this.show_eye;
}
}
