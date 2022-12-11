import { Component, OnInit } from '@angular/core';
import { EventService, Event } from 'src/app/service/event.service';


@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit {
  events: Event[] = []
  constructor(private eventService: EventService) { }

  ngOnInit(): void {
    this.eventService.getEvents().subscribe(
      (response: any) => this.events = response._embedded.eventList
    );
  }

}
