import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';
import {
  EmailControllerService,
  NewsletterControllerService,
  ResponseFile
} from 'src/app/core/api/v1';

@Component({
  selector: 'app-newsletter',
  templateUrl: './newsletter.component.html',
  styleUrls: ['./newsletter.component.scss'],
})
export class NewsletterComponent implements OnInit {
  fileToUpload: File | null = null;
  allFiles: ResponseFile[] = [];
  selectedFile: ResponseFile | undefined;

  constructor(
    private emailService: EmailControllerService,
    private newsletterService: NewsletterControllerService,
    private _snackBar: MatSnackBar,
    public translate: TranslateService
  ) {}

  ngOnInit(): void {
    this.newsletterService.getListFiles().subscribe(res => {
      this.allFiles = res;
      console.log(res);
    });
  }

  onFileSelected(event: FileList) {
    this.fileToUpload = event.item(0);
  }

  onChange(event: any) {
    this.fileToUpload = event.target.files[0];
  }

  onSaveFile() {
    if (this.fileToUpload !== null) {
      this.newsletterService.uploadFile(this.fileToUpload).subscribe();
    }
  }

  sendNewsletter(fileId: number) {
    this.emailService.sendNewsletter(fileId).subscribe(
      () => this._snackBar.open("Newsletter successfully sent", "Dismiss"),
      (error) => {
        console.log(error);
        this._snackBar.open("Something went wrong :/", "Dismiss");
      }
      );
  }

}
