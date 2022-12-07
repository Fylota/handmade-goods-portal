import { Component } from '@angular/core';
import { HttpEventService, Event } from 'src/app/service/http-event.service';

@Component({
  selector: 'app-upload-event',
  templateUrl: './upload-event.component.html',
  styleUrls: ['./upload-event.component.scss']
})
export class UploadEventComponent {
  event = new Event("", "", "", new Date(), new Date(), new Date())
  constructor(private eventService: HttpEventService) { }

  createEvent() {
    this.event.creationDate = new Date();
    this.eventService.createEvent(this.event)
    .subscribe(data => {
      console.log(this.event);
      alert("Event created successfully.");
    });
  }
}
