import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { faUnlock, faLock, faUnlockKeyhole, faLockOpen } from '@fortawesome/free-solid-svg-icons';
import { HardcodedAuthenticationService } from 'src/app/services/hardcoded-authentication.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  faLock = faLock;
  faLockOpen = faLockOpen;

  username: string;

  constructor(private router: Router,
    public hardcodedAuth: HardcodedAuthenticationService) { }

  ngOnInit(): void {

  }

  continue() {
    setTimeout(() => {
      this.router.navigate(['login']);
    }, 900);

    setTimeout(() => {
      this.faLockOpen = faLock;
    }, 5);
  }


}
