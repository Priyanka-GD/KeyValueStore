import { Component } from '@angular/core';
import { KeyValueStoreService } from '../key-value-store.service';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-add-node',
  templateUrl: './add-node.component.html',
  standalone: true,
  imports: [
    FormsModule
  ],
  styleUrls: ['./add-node.component.css']
})
export class AddNodeComponent {
  nodeId: string = '';
  startRange: number = 0;
  endRange: number = 0;
  responseMessage: string = '';

  constructor(private keyValueStoreService: KeyValueStoreService) {}

  onSubmit() {
    const nodeData = { nodeId: this.nodeId, startRange: this.startRange, endRange: this.endRange };
    this.keyValueStoreService.addNode(nodeData).subscribe(
      (response) => {
        this.responseMessage = response;
        this.nodeId = '';
        this.startRange = 0;
        this.endRange = 0; // Reset form
      },
      (error) => {
        this.responseMessage = 'Error adding node';
      }
    );
  }
}
