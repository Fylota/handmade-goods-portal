import { Component } from '@angular/core';
import { EventControllerService, EventDto } from 'src/app/core/api/v1';

@Component({
  selector: 'app-upload-event',
  templateUrl: './upload-event.component.html',
  styleUrls: ['./upload-event.component.scss']
})
export class UploadEventComponent {
  event: EventDto = {};
  constructor(private eventService: EventControllerService) { }

  createEvent() {
    this.eventService.addEvent(this.event)
    .subscribe(() => {
      console.log(this.event);
      alert("Event created successfully.");
    });
  }
}
