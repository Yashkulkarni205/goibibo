import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-login',
  imports: [CommonModule,FormsModule,RouterModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  constructor(private userService: UserService, private router: Router) {
    }
    login(ngForm: NgForm) {
      const credentials = ngForm.value;
      this.userService.login(credentials).subscribe(
        (response) => this.userService.getCurrentUser().subscribe((response) => {
            this.userService.setUser(response);
            this.router.navigate(['/success',{ value : 'login' }]);
        }),
        (error) => console.log(error.error)
      ); 
    }
}
