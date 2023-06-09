import { Component } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ContactForm, EmailControllerService } from 'src/app/core/api/v1';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss'],
})
export class ContactComponent {
  contactForm = this.fb.group({
    email: ['', Validators.compose([Validators.required, Validators.email])],
    body: ['', Validators.required]
  });

  constructor(
    private fb: FormBuilder,
    private emailService: EmailControllerService,
    private _snackBar: MatSnackBar,
  ) {}

  onSubmit() {
    const data: ContactForm = {
      email: this.contactForm.value.email!,
      body: this.contactForm.value.body!
    }
    this.emailService.sendContactUsMessage(data).subscribe({
      next: () => this._snackBar.open("Email Sent! If you do not get an automatic reply please try again.", "Dismiss"),
      error: () => this._snackBar.open("Something went wrong. Please try again later.", "Dismiss")
    });
  }
}
