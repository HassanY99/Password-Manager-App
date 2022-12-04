import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-success-pop-up',
  templateUrl: './success-pop-up.component.html',
  styleUrls: ['./success-pop-up.component.css']
})
export class SuccessPopUpComponent implements OnInit {

  modalReference: NgbModalRef;

  constructor() { }

  ngOnInit(): void {
  }

}
