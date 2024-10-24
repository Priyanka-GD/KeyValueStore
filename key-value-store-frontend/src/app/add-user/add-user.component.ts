import { Component } from '@angular/core';
import { KeyValueStoreService } from '../key-value-store.service';
import { User } from '../user.model';
import { FormsModule } from '@angular/forms';
import { catchError, of, tap } from 'rxjs';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NgIf
  ],
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent {
  user: User = {
    userId: '',
    name: '',
    email: '',
    address: ''
  };
  responseMessage: string = '';

  constructor(private keyValueStoreService: KeyValueStoreService) {}

  onSubmit() {
    this.keyValueStoreService.addUser(this.user).pipe(
      tap((response: string) => { // Use 'any' temporarily to inspect the response structure
        console.log('Response from server:', response); // Log the entire response
        this.responseMessage = response; // Adjust this if response is an object
        this.resetForm(); // Reset the form after successful submission
      }),
      catchError((error: any) => {
        console.error('Error adding user:', error); // Log the error for debugging
        this.responseMessage = 'Error adding user';
        return of(); // Return empty observable on error
      })
    ).subscribe();
  }

  resetForm() {
    this.user = { userId: '', name: '', email: '', address: '' }; // Reset form fields
  }
}
