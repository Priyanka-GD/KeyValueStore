import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {catchError, map, Observable, throwError} from 'rxjs';
import {User} from './user.model';


@Injectable({
  providedIn: 'root',
})
export class KeyValueStoreService {
  private baseUrl = 'http://localhost:8081/keyvaluestore'; // Backend API URL

  constructor(private http: HttpClient) {}

  // Add User API call
  addUser(user: User): Observable<string> {
    return this.http.post<{ message: string }>(`${this.baseUrl}/addUser`, user)
      .pipe(
        map(response => response.message), // Extract the message from the response
        catchError((error) => {
          console.error('HTTP error:', error); // Log the error
          return throwError(error); // Rethrow the error for handling
        })
      );
  }
  // Add Node API call
  addNode(nodeData: any): Observable<string> {
    return this.http.post<string>(`${this.baseUrl}/addNode`, nodeData);
  }

  // Remove Node API call
  removeNode(nodeId: string): Observable<string> {
    return this.http.delete<string>(`${this.baseUrl}/removeNode/${nodeId}`);
  }

  // Get User API call
  getUser(userId: string): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/getUser/${userId}`);
  }
}
