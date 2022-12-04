import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PasswordDataService } from 'src/app/services/data/password-data.service';
import { HardcodedAuthenticationService } from 'src/app/services/hardcoded-authentication.service';
import { UserProfile } from '../applications/applications.component';

@Component({
  selector: 'app-delete-user-pop-up',
  templateUrl: './delete-user-pop-up.component.html',
  styleUrls: ['./delete-user-pop-up.component.css']
})
export class DeleteUserPopUpComponent implements OnInit {

  // username = this.hardcodedAuth.getAuthenticatedUser()?.toString();

  userProfile: UserProfile;
  modalReference: NgbModalRef;

  constructor(private router: Router,
    private hardcodedAuth: HardcodedAuthenticationService,
    private passwordData: PasswordDataService,
    private route: ActivatedRoute,
    private dialogRef: MatDialog) { }

  ngOnInit(): void {
  }



  Confirm() {
    this.passwordData.deleteUserByUsername(this.hardcodedAuth.getAuthenticatedUser()).subscribe(
      data => {
        this.hardcodedAuth.loggedOut();
        this.dialogRef.closeAll();
        this.router.navigate(['login']);
      }
    )
  }

}
