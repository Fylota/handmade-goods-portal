import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

export class Event {
  constructor(
    public id: string,
    public title: string,
    public description: string,
    public creationDate: Date,
    public startDateTime: Date,
    public endDateTime: Date
  ) {}
}

@Injectable({
  providedIn: 'root'
})
export class HttpEventService {

  constructor(private httpClient: HttpClient) { }

  createEvent(event: Event) {
    return this.httpClient.post<Event>("http://localhost:8080/event", event);
  }

  getEvents() {
    return this.httpClient.get<Event[]>('http://localhost:8080/event');
  }
}
