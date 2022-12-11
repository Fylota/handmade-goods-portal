import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Event } from '../models/event.model';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(private httpClient: HttpClient) { }

  createEvent(event: Event) {
    return this.httpClient.post<Event>("http://localhost:8080/events", event);
  }

  getEvents() {
    return this.httpClient.get<Event[]>('http://localhost:8080/events');
  }

  getEvent(eventId: string) {
    return this.httpClient.get<Event>('http://localhost:8080/events' + "/" + eventId);
  }

  updateEvent(event: Event) {
    return this.httpClient.put<Event>('http://localhost:8080/events' + "/" + event.id, event);
  }

  deleteEvent(event: Event) {
    return this.httpClient.delete<Event>("http://localhost:8080/events" + "/" + event.id);
  }
}
export { Event };
