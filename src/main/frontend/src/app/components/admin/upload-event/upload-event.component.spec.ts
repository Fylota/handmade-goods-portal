import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UploadEventComponent } from './upload-event.component';

describe('UploadEventComponent', () => {
  let component: UploadEventComponent;
  let fixture: ComponentFixture<UploadEventComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UploadEventComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UploadEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
