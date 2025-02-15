import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  imports: [CommonModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  user = typeof localStorage !== 'undefined' ? JSON.parse(localStorage.getItem('user') || 'null') : null;
  success = 'home';
  constructor(private route: ActivatedRoute) {
  }
  ngOnInit() {
    this.route.params.subscribe((params) => this.success = params['value']);
  }
}
