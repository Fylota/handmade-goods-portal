import { Component } from '@angular/core';
import { EventControllerService } from 'src/app/core/api/v1';


@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent {
  events$ = this.eventService.getEvents();
  constructor(private eventService: EventControllerService) { }

}
