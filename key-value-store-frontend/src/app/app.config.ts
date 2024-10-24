import { ApplicationConfig } from '@angular/core';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes'; // Assuming you have a routes file

export const appConfig: ApplicationConfig = {
  providers: [
    provideHttpClient(), // Replaces the deprecated HttpClientModule
    provideRouter(routes),
  ],
};
