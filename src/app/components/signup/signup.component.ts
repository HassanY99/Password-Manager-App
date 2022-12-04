import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { faUser } from '@fortawesome/free-solid-svg-icons';
import { PasswordDataService } from 'src/app/services/data/password-data.service';
import { HardcodedAuthenticationService } from 'src/app/services/hardcoded-authentication.service';
import { UserDao, UserDto } from '../applications/applications.component';
import { NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import { MatDialog } from '@angular/material/dialog';
import { SuccessPopUpComponent } from '../success-pop-up/success-pop-up.component';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})

export class SignupComponent implements OnInit {

  faUser=faUser;
  userDto: UserDto;

  modalReference: NgbModalRef;

  user: UserDao;
  id: number;

  show_button: Boolean = false;
  show_eye: Boolean = false;

  invalidUsername = false;
  errorMessageUsername = "A user with this username already exists."
  invalidEmail = false;
  errorMessageEmail = "An account with this email already exists."

  constructor(private passwordAuth: PasswordDataService,
    private router: Router,
    private hardcodedAuth: HardcodedAuthenticationService,
    private dialogRef: MatDialog) { }

  ngOnInit(): void {
    this.userDto = new UserDto(this.id, '', '', '', '', '');
  }

  registerNewUser() {
    this.passwordAuth.registerNewUser(this.userDto).subscribe(
      data => {
        setTimeout(() => {
          this.dialogRef.open(SuccessPopUpComponent);
        }, 100);

        setTimeout(() => {
          this.dialogRef.closeAll();
          this.router.navigate(['login']);
        }, 2500);
      },
      error => {
          this.invalidEmail = false;
          this.invalidUsername = false;

        if(error.error.message == "Username and Email Already exists") {
          this.invalidEmail = true;
          this.invalidUsername = true;
        } else if(error.error.message == "Username Already exists") {
          this.invalidUsername = true;
        } else if(error.error.message == "Email Already exists") {
          this.invalidEmail = true;
        }
      }
    )
  }

  showPassword() {
    this.show_button = !this.show_button;
    this.show_eye = !this.show_eye;
  }

}
