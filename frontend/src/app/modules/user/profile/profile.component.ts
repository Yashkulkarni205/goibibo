import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-profile',
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  user: any;
  constructor(private userService: UserService) {
  }
  ngOnInit() {
    this.userService.user$.subscribe((user) => {
      this.user = user;
      this.user.dateOfBirth = new Date(this.user.dateOfBirth).toISOString().slice(0, 10);
    });
  }
  update(profileForm: NgForm) {
    this.userService.setUser(this.user);
    this.userService.update(this.user).subscribe(() => {
      alert('Profile updated successfully');
    });
  }
}
