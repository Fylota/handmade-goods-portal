import { Component } from '@angular/core';
import { EventService, Event } from 'src/app/service/event.service';

@Component({
  selector: 'app-upload-event',
  templateUrl: './upload-event.component.html',
  styleUrls: ['./upload-event.component.scss']
})
export class UploadEventComponent {
  event = new Event("", "", "", new Date(), new Date(), new Date())
  constructor(private eventService: EventService) { }

  createEvent() {
    this.event.creationDate = new Date();
    this.eventService.createEvent(this.event)
    .subscribe(() => {
      console.log(this.event);
      alert("Event created successfully.");
    });
  }
}
