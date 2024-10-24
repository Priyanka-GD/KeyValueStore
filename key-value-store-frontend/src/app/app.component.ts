import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html', // Use external HTML file
  imports: [RouterModule],
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'key-value-store';
}
