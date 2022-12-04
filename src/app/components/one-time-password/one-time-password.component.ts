import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PasswordDataService } from 'src/app/services/data/password-data.service';
import { ValidationCode } from '../applications/applications.component';

@Component({
  selector: 'app-one-time-password',
  templateUrl: './one-time-password.component.html',
  styleUrls: ['./one-time-password.component.css']
})
export class OneTimePasswordComponent implements OnInit {

  email: string;
  validationCode:ValidationCode;
  id: number;

  invalidCode: boolean;
  codeErrorMsg = "Invalid Code";

  constructor(private route: ActivatedRoute,
    private passwordAuth: PasswordDataService,
    private router: Router) { }

  ngOnInit(): void {
    this.email = this.route.snapshot.params['email'];

    this.validationCode = new ValidationCode(this.id);
  }


  retrieveOneTimePassword() {
    this.passwordAuth.getOneTimePassword(this.validationCode, this.email).subscribe({

      next: (data) => {
        this.router.navigate(['passwordRestore', this.validationCode.validationCode]);
      },
      error: (error) => {
        if(error.error.message == 'Invalid Code') {
          this.invalidCode = true;
        }
      }
    }
    )
  }
}
