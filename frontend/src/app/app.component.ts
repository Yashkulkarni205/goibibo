import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { UserService } from './modules/user/services/user.service';

@Component({
  selector: 'app-root',
  imports: [CommonModule, RouterModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  user: any = {
    firstName: '',
    lastName: '',
    dateOfBirth: '',
    gender: '',
    city: '',
    phone: ''
  };
  constructor(private userService: UserService, private router: Router) {
  }
  ngOnInit() {
    this.userService.user$.subscribe((user) => this.user = user);
  }
  navOptions = [
    {
      "name": "Flights",
      "class": "fa-plane",
      "path": "/flights"
    },
    {
      "name": "Hotels",
      "class": "fa-hotel",
      "path": "/hotels"
    },
    {
      "name": "Trains",
      "class": "fa-train",
      "path": "/trains"
    },
    {
      "name": "Cabs",
      "class": "fa-taxi",
      "path": "/cabs"
    },
    {
      "name": "Bus",
      "class": "fa-bus",
      "path": "/flights"
    },
    {
      "name": "My Bookings",
      "class": "fa-suitcase",
      "path": "/bookings"
    }
  ]
  logout() {
    this.userService.logout().subscribe(() => {
      this.userService.setUser(null);
      this.router.navigate(['/success', { value: 'logout' }])
  });
  }
}
