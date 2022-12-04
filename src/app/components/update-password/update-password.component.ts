import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PasswordDataService } from 'src/app/services/data/password-data.service';
import { Password } from '../applications/applications.component';
import { faLock, faLockOpen } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-update-password',
  templateUrl: './update-password.component.html',
  styleUrls: ['./update-password.component.css']
})
export class UpdatePasswordComponent implements OnInit {

  id: number;
  password: Password;
  username: string;

  faLock = faLock;
  faLockOpen = faLockOpen;

  showPassword = false;

  constructor(private passwordData: PasswordDataService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.username = this.route.snapshot.params['username'];
    this.password = new Password(this.id, '', '');

    this.passwordData.findById(this.id).subscribe(
      data => {
        this.password = data;
      }
    )
  }

  savePassword() {
    this.id = this.route.snapshot.params['id'];
    this.username = this.route.snapshot.params['username'];

    this.passwordData.updateById(this.id, this.username ,this.password).subscribe(
      response => {

        setTimeout(() => {
          this.router.navigate(['apps', this.username])
        }, 900);

        setTimeout(() => {
          this.faLockOpen = faLock;
        }, 5);
      }
    )
  }

  checkboxDisplayPassword() {
    this.showPassword = !this.showPassword;
  }


}
