import { Component, OnInit } from '@angular/core';
import { HardcodedAuthenticationService } from 'src/app/services/hardcoded-authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  message = 'Logout Successful';
  success = 'You have successfully logged out.';
  faSpinner:any;

  redirecting = '';

  constructor(private hardcodedAuthentication: HardcodedAuthenticationService,
    private router: Router) { }

  ngOnInit(): void {
    this.hardcodedAuthentication.loggedOut();

    setTimeout(() => {
      this.message = '';
      this.success = '';
      this.redirecting = 'Redirecting you now to the Log in page...'
    }, 2000);

    setTimeout(() => {
      this.router.navigate(['']);
    }, 4500);

  }

}
