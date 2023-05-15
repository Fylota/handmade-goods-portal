import { HttpClientModule, HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';

import { AppComponent } from './app.component';
import { UserComponent } from './components/admin/listUsers/user.component';
import { AddUserComponent } from './components/user/add-user/add-user.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { LoginComponent } from './components/user/login/login.component';
import { LogoutComponent } from './components/user/logout/logout.component';
import { BasicAuthHtppInterceptorService } from './service/basic-auth-htpp-interceptor.service';
import { ProfileComponent } from './components/user/profile/profile.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { HomeComponent } from './components/home/home.component';
import { CartComponent } from './components/shop/cart/cart.component';
import { WishlistComponent } from './components/shop/wishlist/wishlist.component';
import { MaterialModule } from './material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ProductsComponent } from './components/shop/products/products.component';
import { ProductDetailsComponent } from './components/shop/product-details/product-details.component';
import { UploadProductComponent } from './components/admin/upload-product/upload-product.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { CategoryComponent } from './components/shop/category/category.component';
import { ProductsListComponent } from './components/shop/products-list/products-list.component';
import { UploadNewsComponent } from './components/admin/upload-news/upload-news.component';
import { PostsComponent } from './components/posts/posts.component';
import { EventsComponent } from './components/events/events.component';
import { UploadEventComponent } from './components/admin/upload-event/upload-event.component';
import { HomeHeroComponent } from './components/home-hero/home-hero.component';
import { AdminPageComponent } from './components/admin/admin-page/admin-page.component';
import { MessageService } from './service/message.service';
import { HttpErrorHandler } from './service/http-error-handler.service';
import { CheckoutComponent } from './components/shop/checkout/checkout.component';
import { CartItemsTableComponent } from './components/shop/cart-items-table/cart-items-table.component';
import { AdminSectionListComponent } from './components/admin/admin-section-list/admin-section-list.component';
import { PasswordResetComponent } from './components/user/password-reset/password-reset.component';
import { ForgotPasswordComponent } from './components/user/forgot-password/forgot-password.component';
import { EditProfileComponent } from './components/user/edit-profile/edit-profile.component';
import { ContactInformationComponent } from './components/user/contact-information/contact-information.component';
import { AddressBookComponent } from './components/user/address-book/address-book.component';
import { EditAddressBookComponent } from './components/user/edit-address-book/edit-address-book.component';
import { OrdersListComponent } from './components/shop/orders-list/orders-list.component';
import { OrderDetailsComponent } from './components/shop/order-details/order-details.component';
import { AllCategoriesComponent } from './components/shop/all-categories/all-categories.component';
import { PostDetailsComponent } from './components/posts/post-details/post-details.component';

// AoT requires an exported function for factories
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    AddUserComponent,
    HeaderComponent,
    FooterComponent,
    LoginComponent,
    LogoutComponent,
    ProfileComponent,
    HomeComponent,
    CartComponent,
    WishlistComponent,
    ProductsComponent,
    ProductDetailsComponent,
    UploadProductComponent,
    PageNotFoundComponent,
    CategoryComponent,
    ProductsListComponent,
    UploadNewsComponent,
    PostsComponent,
    EventsComponent,
    UploadEventComponent,
    HomeHeroComponent,
    AdminPageComponent,
    CheckoutComponent,
    CartItemsTableComponent,
    AdminSectionListComponent,
    PasswordResetComponent,
    ForgotPasswordComponent,
    EditProfileComponent,
    ContactInformationComponent,
    AddressBookComponent,
    EditAddressBookComponent,
    OrdersListComponent,
    OrderDetailsComponent,
    AllCategoriesComponent,
    PostDetailsComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    FontAwesomeModule,
    MaterialModule,
    BrowserAnimationsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      },
      defaultLanguage: 'en'
    })
  ],
  providers: [
    HttpErrorHandler,
    MessageService,
    {
      provide:HTTP_INTERCEPTORS, useClass:BasicAuthHtppInterceptorService, multi:true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
