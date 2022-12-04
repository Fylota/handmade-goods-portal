import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadNewsComponent } from './upload-news.component';

describe('UploadNewsComponent', () => {
  let component: UploadNewsComponent;
  let fixture: ComponentFixture<UploadNewsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadNewsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UploadNewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
