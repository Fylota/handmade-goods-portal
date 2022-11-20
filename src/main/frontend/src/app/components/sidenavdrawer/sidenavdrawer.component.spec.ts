import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidenavdrawerComponent } from './sidenavdrawer.component';

describe('SidenavdrawerComponent', () => {
  let component: SidenavdrawerComponent;
  let fixture: ComponentFixture<SidenavdrawerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SidenavdrawerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SidenavdrawerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
