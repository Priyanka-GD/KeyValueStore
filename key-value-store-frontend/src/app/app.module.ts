import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule, Routes} from '@angular/router'; // Import RouterModule and Routes
import {provideHttpClient} from '@angular/common/http';

import {AppComponent} from './app.component'; // Import AppComponent
import {AddUserComponent} from './add-user/add-user.component'; // Import AddUserComponent
import {AddNodeComponent} from './add-node/add-node.component'; // Example: import other components
import {RemoveNodeComponent} from './remove-node/remove-node.component'; // Example: import other components

// Define routes
const routes: Routes = [
    {path: 'add-user', component: AddUserComponent},
    {path: 'add-node', component: AddNodeComponent},
    {path: 'remove-node', component: RemoveNodeComponent},
    {path: '', redirectTo: '/add-user', pathMatch: 'full'} // Redirect to default path
];

@NgModule({
    declarations: [
        AppComponent,
    ],
    imports: [
        BrowserModule,
        RouterModule.forRoot(routes), // Add RouterModule with defined routes
    ],
    providers: [
        provideHttpClient(),
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
