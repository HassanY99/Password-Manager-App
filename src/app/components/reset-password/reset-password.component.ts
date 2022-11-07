import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PasswordDataService } from 'src/app/services/data/password-data.service';
import { HardcodedAuthenticationService } from 'src/app/services/hardcoded-authentication.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  email: string;
  username: string;
  password: string;

  invalidEmail = false;
  emailErrorMessage = "No user found with that email."

  constructor(private router: Router,
    private route: ActivatedRoute,
    private passwordAuth: PasswordDataService,
    private hardcodedAuth: HardcodedAuthenticationService) { }

  ngOnInit(): void {
  }

  redirectToReset() {

    this.passwordAuth.findUserByEmail(this.email).subscribe({

      next: (data) => {
        this.router.navigate(['opt', this.email]);
      },
      error: (error) => {
        if(error.error.message == 'User with that email does not exist') {
          this.invalidEmail = true;
        }
      }
    }
    )
  }

}
