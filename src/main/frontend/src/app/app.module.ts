import { HttpClientModule, HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';

import { AppComponent } from './app.component';
import { UserComponent } from './components/user/user.component';
import { AddUserComponent } from './components/add-user/add-user.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { BasicAuthHtppInterceptorService } from './service/basic-auth-htpp-interceptor.service';
import { ProfileComponent } from './components/profile/profile.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { HomeComponent } from './components/home/home.component';
import { CartComponent } from './components/cart/cart.component';
import { WishlistComponent } from './components/wishlist/wishlist.component';
import { MaterialModule } from './material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { SidenavdrawerComponent } from './components/sidenavdrawer/sidenavdrawer.component';
import { ProductsComponent } from './components/products/products.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { UploadProductComponent } from './components/upload-product/upload-product.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { CategoryComponent } from './components/category/category.component';
import { ProductsListComponent } from './components/products-list/products-list.component';
import { UploadNewsComponent } from './components/upload-news/upload-news.component';
import { PostsComponent } from './components/posts/posts.component';
import { EventsComponent } from './components/events/events.component';
import { UploadEventComponent } from './components/upload-event/upload-event.component';
import { HomeHeroComponent } from './components/home-hero/home-hero.component';
import { AdminPageComponent } from './components/admin-page/admin-page.component';
import { MessageService } from './service/message.service';
import { HttpErrorHandler } from './service/http-error-handler.service';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { CartItemsTableComponent } from './components/cart-items-table/cart-items-table.component';
import { AdminSectionListComponent } from './components/admin-section-list/admin-section-list.component';

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
    SidenavdrawerComponent,
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
    AdminSectionListComponent
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
