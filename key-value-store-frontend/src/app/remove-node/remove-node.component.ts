import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';

import { KeyValueStoreService } from '../key-value-store.service';

@Component({
  selector: 'app-remove-node',
  templateUrl: './remove-node.component.html',
  standalone: true,
  imports: [
    FormsModule
  ],
  styleUrls: ['./remove-node.component.css']
})
export class RemoveNodeComponent {
  nodeId: string = '';
  responseMessage: string = '';

  constructor(private keyValueStoreService: KeyValueStoreService) {}

  onSubmit() {
    this.keyValueStoreService.removeNode(this.nodeId).subscribe(
      (response) => {
        this.responseMessage = response;
        this.nodeId = ''; // Reset form
      },
      (error) => {
        this.responseMessage = 'Error removing node';
      }
    );
  }
}
