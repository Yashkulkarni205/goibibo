import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private userSubject = new BehaviorSubject<any>(typeof localStorage !== 'undefined' ? JSON.parse(localStorage.getItem('user') || 'null') : null);
  user$ = this.userSubject.asObservable();

  private baseUrL = "http://localhost:9001/";
  constructor(private http: HttpClient) {
  }
  setUser(user: any) {
    this.userSubject.next(user);
    user ? localStorage.setItem('user', JSON.stringify(user)) : localStorage.removeItem('user');
  }

  register(userData: any): Observable<any> {
    return this.http.post(`${this.baseUrL}register`, userData, { responseType : "text", withCredentials: true });
  }

  login(credentials: any): Observable<any> {
    return this.http.post(`${this.baseUrL}login`, credentials, { responseType : "text", withCredentials: true });
  }

  logout(): Observable<any> {
    return this.http.get(`${this.baseUrL}logout`, { responseType : "text", withCredentials: true });
  }

  getCurrentUser(): Observable<any> {
    return this.http.get(`${this.baseUrL}user`, { withCredentials: true });
  }
}


