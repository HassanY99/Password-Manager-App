import { Component, OnInit } from '@angular/core';
import { UserDao, UserProfile } from '../applications/applications.component';
import { PasswordDataService } from 'src/app/services/data/password-data.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HardcodedAuthenticationService } from 'src/app/services/hardcoded-authentication.service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { MatDialog } from '@angular/material/dialog';
import { DeleteUserPopUpComponent } from '../delete-user-pop-up/delete-user-pop-up.component';


@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css']
})
export class UpdateProfileComponent implements OnInit {

  modalReference: NgbModalRef;

  users:UserDao;
  id: number;
  username:string;

  invalidLogin = false;
  invalidUsername = false;
  errorMessageUsername = "A user with this username already exists."
  invalidEmail = false;
  errorMessageEmail = "An account with this email already exists."

  userProfile: UserProfile;

  currentDate = new Date();

  show_button: Boolean = false;
  show_eye: Boolean = false;

  constructor(private passwordAuth: PasswordDataService,
    private route: ActivatedRoute,
    private router: Router,
    private hardcodedAuth: HardcodedAuthenticationService,
    private dialogRef: MatDialog) { }

  ngOnInit(): void {

    this.users = new UserDao(this.id, '','','','','');

    this.userProfile = new UserProfile('','','','','');

    this.username = this.route.snapshot.params['username'];

    this.passwordAuth.getUserProfile(this.username).subscribe(
      data => {
        this.userProfile = data;
      }
    )
  }


  updateProfile() {
    this.passwordAuth.updateUserProfile(this.userProfile, this.username).subscribe(
      data => {

        const profuser = this.userProfile;
        this.hardcodedAuth.loggedOut();

        this.hardcodedAuth.executBackendAuthentication(profuser.username, profuser.password).subscribe({
          next: (data) => {
            this.router.navigate(['profile', this.hardcodedAuth.getAuthenticatedUser()]);
           }
         })
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

  removeAccount() {
    this.dialogRef.open(DeleteUserPopUpComponent);
  }

}
