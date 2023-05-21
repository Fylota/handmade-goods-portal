import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatTableDataSource } from '@angular/material/table';
import { Event, EventControllerService, EventDto } from 'src/app/core/api/v1';

interface SortValue {
  value: string[];
  viewValue: string;
}

@Component({
  selector: 'app-manage-events',
  templateUrl: './manage-events.component.html',
  styleUrls: ['./manage-events.component.scss']
})
export class ManageEventsComponent implements OnInit {
  length = 10;
  pageSize = 5;
  pageSizeOptions = [5, 10, 25];
  pageIndex = 0;
  sort = ["createdDate", "desc"];
  pageEvent: PageEvent | undefined;
  sortValues: SortValue[] = [
    {value: ["title", "asc"], viewValue: 'Title ascending'},
    {value: ["title", "desc"], viewValue: 'Title descending'},
    {value: ["createdDate", "asc"], viewValue: 'From oldest to newest'},
    {value: ["createdDate", "desc"], viewValue: 'From newest to oldest'},
  ]
  dataSource: MatTableDataSource<Event> = new MatTableDataSource<Event>();
  columnsToDisplay: string[] = ['id', 'title', 'createdDate'];
  selectedEvent: Event | null = null;

  constructor(
    private eventService: EventControllerService,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.refreshData();
  }

  handlePageEvent(e: PageEvent) {
    this.pageEvent = e;
    this.length = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.refreshData();
  }

  refreshData(): void {
    this.eventService.getEventPages(this.pageIndex, this.pageSize, this.sort).subscribe((res: any) => {
      this.length = res["totalItems"];
      this.dataSource.data = res["data"];
    })
  }

  handleRowClicked(row: Event) {
    this.selectedEvent = row;
  }

  updateEvent() {
    if (this.selectedEvent !== null) {
      let dto: EventDto = {
        title: this.selectedEvent.title,
        description: this.selectedEvent.description,
        startDateTime: this.selectedEvent.startDateTime,
        endDateTime: this.selectedEvent.endDateTime
      };
      this.eventService.updateEvent(this.selectedEvent.id!, dto ).subscribe({
        next: () => {
          this._snackBar.open("Event saved", "Dismiss");
          this.selectedEvent = null;
        },
        error: () => this._snackBar.open("Something went wrong :/", "Dismiss")
      });
    }
  }

  cancelEdit() {
    this.selectedEvent = null;
  }

  deleteEvent() {
    if (this.selectedEvent !== null) {
      this.eventService.deleteEvent(this.selectedEvent.id!).subscribe({
        next: () => {
          this._snackBar.open("Event deleted", "Dismiss");
          this.selectedEvent = null;
          this.refreshData();
        },
        error: () => this._snackBar.open("Something went wrong :/", "Dismiss")
      });
    }
  }

}
