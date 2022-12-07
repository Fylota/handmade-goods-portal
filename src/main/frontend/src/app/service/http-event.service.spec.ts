import { TestBed } from '@angular/core/testing';

import { HttpEventService } from './http-event.service';

describe('HttpEventService', () => {
  let service: HttpEventService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HttpEventService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
