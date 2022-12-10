import { TestBed } from '@angular/core/testing';

import { HttpCategoryService } from './http-category.service';

describe('HttpCategoryService', () => {
  let service: HttpCategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HttpCategoryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
