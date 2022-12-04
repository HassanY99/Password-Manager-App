import { Component, OnInit } from '@angular/core';
import { UserDao } from '../applications/applications.component';
import { PasswordDataService } from 'src/app/services/data/password-data.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HardcodedAuthenticationService } from 'src/app/services/hardcoded-authentication.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  users:UserDao;
  id: number;
  username:string;

  showImg = true;

  currentDate = new Date();

  constructor(private passwordAuth: PasswordDataService,
    private route: ActivatedRoute,
    private router: Router,
    private hardcodedAuth: HardcodedAuthenticationService) { }

  ngOnInit(): void {

    this.users = new UserDao(this.id, '','','','','');

    this.username = this.route.snapshot.params['username'];

    this.passwordAuth.getUserProfile(this.username).subscribe(
      data => {
        this.users = data;
      }
    )
}


updateProfile() {
  this.router.navigate(['profile/update', this.hardcodedAuth.getAuthenticatedUser()])
}

}
