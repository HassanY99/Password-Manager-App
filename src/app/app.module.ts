import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { MenuComponent } from './components/menu/menu.component';
import { LoginComponent } from './components/login/login.component';
import { ApplicationsComponent } from './components/applications/applications.component';
import { LogoutComponent } from './components/logout/logout.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { UpdatePasswordComponent } from './components/update-password/update-password.component';
import { NewAppComponent } from './components/new-app/new-app.component';
import { HttpIntercepterService } from './services/http/http-intercepter.service';
import { SignupComponent } from './components/signup/signup.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MdbCollapseModule } from 'mdb-angular-ui-kit/collapse';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { PasswordRestoreComponent } from './components/password-restore/password-restore.component';
import { NgbModal, NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MatDialogModule } from '@angular/material/dialog';
import { PopUpComponent } from './components/pop-up/pop-up.component';
import { OneTimePasswordComponent } from './components/one-time-password/one-time-password.component';
@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    MenuComponent,
    LoginComponent,
    ApplicationsComponent,
    LogoutComponent,
    UpdatePasswordComponent,
    NewAppComponent,
    SignupComponent,
    ResetPasswordComponent,
    PasswordRestoreComponent,
    PopUpComponent,
    OneTimePasswordComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    FontAwesomeModule,
    BrowserModule,
    MDBBootstrapModule.forRoot(),
    BrowserAnimationsModule,
    MdbCollapseModule,
    NgbModule,
    MatDialogModule,
    ReactiveFormsModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS, useClass: HttpIntercepterService, multi: true
  },
  NgbModal
  ],
  bootstrap: [AppComponent],
  schemas: [NO_ERRORS_SCHEMA]
})
export class AppModule { }
