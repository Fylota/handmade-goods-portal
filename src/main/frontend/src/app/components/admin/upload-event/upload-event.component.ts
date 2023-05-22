import { Component } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';
import { EventControllerService, EventDto } from 'src/app/core/api/v1';

@Component({
  selector: 'app-upload-event',
  templateUrl: './upload-event.component.html',
  styleUrls: ['./upload-event.component.scss']
})
export class UploadEventComponent {
  event: EventDto = {};
  constructor(
    private eventService: EventControllerService,
    private _snackBar: MatSnackBar,
    public translate: TranslateService
  ) { }

  createEvent() {
    this.eventService.addEvent(this.event)
    .subscribe(() => {
      this._snackBar.open("Event saved", "Dismiss");
      this.event = {};
    });
  }
}
