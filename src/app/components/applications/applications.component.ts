import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PasswordDataService } from 'src/app/services/data/password-data.service';
import { HardcodedAuthenticationService } from 'src/app/services/hardcoded-authentication.service';
import { faUnlock, faLock, faUnlockKeyhole, faMusic, faShield, faCheckCircle } from '@fortawesome/free-solid-svg-icons';

export class Password {
  constructor(
    public id: number,
    public app: string,
    public password: string
  ) {}
}

export class UserDao {
  constructor(
    public id: number,
    public firstName: string,
    public lastName: string,
    public email: string,
    public username: string,
    public password: string
  ) {}
}

export class UserDto {
  constructor(
    public id: number,
    public firstName: string,
    public lastName: string,
    public email: string,
    public username: string,
    public password: string
  ) {}
}

export class UserPassword {
  constructor(
    public id: number,
    public password: string,
    public verifyPassword: string
  ) {}
}

export class UserData {
	constructor(
	public email: string
  ) {}
}

export class ValidationCode {
  constructor(
    public validationCode: number
    ) {}
}

@Component({
  selector: 'app-applications',
  templateUrl: './applications.component.html',
  styleUrls: ['./applications.component.css']
})
export class ApplicationsComponent implements OnInit {

  passwords: Password[] = [];
  message = '';
  users: UserDao[] = [];
  username: string;
  id: number;

  showSign = false;

  faLock = faLock;
  faUnlock = faUnlock;
  faUnlockKeyhole = faUnlockKeyhole;
  faMusic = faMusic;
  faCheckCircle = faCheckCircle;

  constructor(private passwordData: PasswordDataService,
    private router: Router,
    private route: ActivatedRoute,
    public hardcodedAuth: HardcodedAuthenticationService) { }

  ngOnInit(): void {
    this.refreshApp();

    // this.findAppByIDToDisplaySign();

    this.username = this.route.snapshot.params['username'];
  }

  refreshApp() {
    this.username = this.route.snapshot.params['username'];
    this.passwordData.executeGetAllApps(this.username).subscribe(
      response => {
        this.passwords = response;
      }

    )
  }

  // findAppByIDToDisplaySign() {
  //   this.passwordData.findById(2).subscribe(
  //     data => {
  //       console.log(data.app);
  //     }
  //   )
  // }

  deleteById(id: number) {
    this.passwordData.deleteAppById(id).subscribe(
      response => {
        this.message = "Successfully Deleted!"

        setTimeout(() => {
          this.message = '';
        }, 1000);

        this.refreshApp();
      }
    )
  }

  updateById(id: number) {
    console.log(`${id} updated`)
    this.router.navigate(['updateApp', id, this.username])
  }

  // uniq =  (new Date()).getTime();

  addApp() {
    this.username = this.route.snapshot.params['username'];
    this.router.navigate(['newApp', this.username]);
  }


}
