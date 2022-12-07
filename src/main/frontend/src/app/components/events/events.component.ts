import { Component, OnInit } from '@angular/core';
import { HttpEventService, Event } from 'src/app/service/http-event.service';


@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit {
  events: Event[] = []
  constructor(private eventService: HttpEventService) { }

  ngOnInit(): void {
    this.eventService.getEvents().subscribe(
      response => this.events = response
    );
  }

}
