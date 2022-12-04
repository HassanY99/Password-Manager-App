import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PasswordDataService } from 'src/app/services/data/password-data.service';
import { HardcodedAuthenticationService } from 'src/app/services/hardcoded-authentication.service';
import { Password, UserDao } from '../applications/applications.component';
import { faLock, faLockOpen } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-new-app',
  templateUrl: './new-app.component.html',
  styleUrls: ['./new-app.component.css']
})
export class NewAppComponent implements OnInit {

  password: Password;
  id: number;
  submitted: boolean;

  user: UserDao;
  username:string;

  faLock = faLock;
  faLockOpen = faLockOpen;

  showPassword = false;

  constructor(private passwordData: PasswordDataService,
    private router: Router,
    private route: ActivatedRoute,
    private hardcodedAuth: HardcodedAuthenticationService) { }

  ngOnInit(): void {
    this.password = new Password(this.id, '', '');

    this.username = this.route.snapshot.params['username'];
  }


  saveApp() {

    if(this.hardcodedAuth.isUserLoggedIn()) {
      this.username = this.route.snapshot.params['username'];
      this.passwordData.createNewApp(this.password, this.username).subscribe(
        data => {

          setTimeout(() => {
            this.router.navigate(['apps', this.username])
          }, 900);

          setTimeout(() => {
            this.faLockOpen = faLock;
          }, 5);
        }
      )
    }
  }

  checkboxDisplayPassword() {
    this.showPassword = !this.showPassword;
  }
}
