import { Routes } from '@angular/router';
import { AddUserComponent } from './add-user/add-user.component';
import { AddNodeComponent } from './add-node/add-node.component';
import { RemoveNodeComponent } from './remove-node/remove-node.component';

export const routes: Routes = [
  { path: 'add-user', component: AddUserComponent },
  { path: 'add-node', component: AddNodeComponent },
  { path: 'remove-node', component: RemoveNodeComponent },
  { path: '', redirectTo: 'add-user', pathMatch: 'full' }, // default route
];
