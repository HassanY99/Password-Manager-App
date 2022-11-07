import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {WelcomeComponent} from './components/welcome/welcome.component';
import {LoginComponent} from './components/login/login.component';
import {ApplicationsComponent} from './components/applications/applications.component';
import {LogoutComponent} from './components/logout/logout.component';
import { RouteGuardService } from './services/route-guard.service';
import { UpdatePasswordComponent } from './components/update-password/update-password.component';
import { NewAppComponent } from './components/new-app/new-app.component';
import { SignupComponent } from './components/signup/signup.component';
import { ResetPasswordComponent } from './components/reset-password/reset-password.component';
import { PasswordRestoreComponent } from './components/password-restore/password-restore.component';
import { OneTimePasswordComponent } from './components/one-time-password/one-time-password.component';

const routes: Routes = [
  {path: '', component: WelcomeComponent, },
  {path: 'login', component: LoginComponent},
  {path: 'apps/:username', component: ApplicationsComponent, canActivate: [RouteGuardService]},
  {path: 'logout', component: LogoutComponent, canActivate: [RouteGuardService]},
  {path: 'updateApp/:id/:username', component: UpdatePasswordComponent, canActivate: [RouteGuardService]},
  {path: 'newApp/:username', component: NewAppComponent, canActivate: [RouteGuardService]},
  {path: 'signup', component: SignupComponent},
  {path: 'resetPassword', component: ResetPasswordComponent},
  {path: 'passwordRestore/:validationCode', component: PasswordRestoreComponent},
  {path: 'opt/:email', component: OneTimePasswordComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
