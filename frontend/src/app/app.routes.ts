import { Routes } from '@angular/router';
import { HomeComponent } from './common/components/home/home.component';
import { LoginComponent } from './modules/user/login/login.component';
import { RegisterComponent } from './modules/user/register/register.component';
import { ProfileComponent } from './modules/user/profile/profile.component';

export const routes: Routes = [
    {path: "", component: HomeComponent},
    {path: "success", component: HomeComponent},
    {path: "login", component: LoginComponent},
    {path: "register", component: RegisterComponent},
    {path: "profile", component: ProfileComponent}
];
