import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  user: any;
  constructor(private userService: UserService, private router: Router) { 
  }
  ngOnInit() {
    this.userService.user$.subscribe((user) => {
      this.user = user;
      this.user.dateOfBirth = new Date(this.user.dateOfBirth).toISOString().slice(0, 10);
    });
  }
  update(profileForm: NgForm) {
    this.userService.update(this.user).subscribe(() => {
      this.userService.setUser(this.user);
      this.router.navigate(['/success', { value: 'profile' }]);
    });
  }
}
