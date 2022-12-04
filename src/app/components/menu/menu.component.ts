import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PasswordDataService } from 'src/app/services/data/password-data.service';
import { HardcodedAuthenticationService } from 'src/app/services/hardcoded-authentication.service';
import { Password, UserDto } from '../applications/applications.component';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  username: string;
  password:Password;

  userDto: UserDto;
  id: number;

  mdbCollapse:any;

  isShown = false;

  constructor(public hardcodedAuthentiction: HardcodedAuthenticationService,
    private route: ActivatedRoute,
    private router: Router,
    private passwordAuth: PasswordDataService) { }

  ngOnInit(): void {
    this.hardcodedAuthentiction.isUserLoggedIn();

  }


  getToAppsWithUsername() {
    this.router.navigate(['apps', this.hardcodedAuthentiction.getAuthenticatedUser()])
  }

  routeToLogout() {
    this.router.navigate(['logout']);
  }

  routeToHome() {
    this.router.navigate(['']);

  }

  routeToProfile() {
    this.router.navigate(['profile',this.hardcodedAuthentiction.getAuthenticatedUser()]);
  }


}
