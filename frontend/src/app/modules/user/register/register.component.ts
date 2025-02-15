import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { RouterModule, Router, ActivatedRoute } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-register',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  constructor(private userService: UserService, private router: Router) {
  }
  register(ngForm: NgForm) {
    const userDto = ngForm.value;
    this.userService.register(userDto).subscribe(
      (response) => this.router.navigate(['/success',{ value : 'register' } ]),
      (error) => console.log(error.error)
    );
  }
}
