import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PasswordDataService } from 'src/app/services/data/password-data.service';
import { HardcodedAuthenticationService } from 'src/app/services/hardcoded-authentication.service';
import { UserDao, UserPassword } from '../applications/applications.component';
import { NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import { MatDialog } from '@angular/material/dialog';
import { PopUpComponent } from '../pop-up/pop-up.component';

@Component({
  selector: 'app-password-restore',
  templateUrl: './password-restore.component.html',
  styleUrls: ['./password-restore.component.css']
})
export class PasswordRestoreComponent implements OnInit {

  modalReference: NgbModalRef;

  closeResult = '';
  user: UserDao;

  userPassword: UserPassword;
  validationCode: number;

  username: string;
  password: string;
  email: string;
  id: number;

  invalidPasswords = false;

  content = "Your password has been successfully updated";

  constructor(private passwordAuth: PasswordDataService,
    private route: ActivatedRoute,
    private router: Router,
    private hardcodedAuth: HardcodedAuthenticationService,
    private dialogRef: MatDialog) { }

  ngOnInit(): void {

    this.userPassword = new UserPassword(this.id, '', '');

    this.validationCode = this.route.snapshot.params['validationCode'];
  }



  resetPassword() {

    this.passwordAuth.resetUserPassword(this.userPassword, this.validationCode).subscribe(
      data => {
        this.validationCode = this.route.snapshot.params['validationCode'];
        if(this.userPassword.password == this.userPassword.verifyPassword) {
        setTimeout(() => {
          this.dialogRef.open(PopUpComponent);
        }, 100);

        setTimeout(() => {
          this.dialogRef.closeAll();
          this.router.navigate(['login']);
        }, 2500);
      }
    },
    error => {
      if(error.error.message == "Password do not Match") {
        this.invalidPasswords = true;
      }
    }
    )
  }
}
