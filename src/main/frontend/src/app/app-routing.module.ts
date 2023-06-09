import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddUserComponent } from './components/user/add-user/add-user.component';
import { AdminPageComponent } from './components/admin/admin-page/admin-page.component';
import { CartComponent } from './components/shop/cart/cart.component';
import { CategoryComponent } from './components/shop/category/category.component';
import { CheckoutComponent } from './components/shop/checkout/checkout.component';
import { EventsComponent } from './components/events/events.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/user/login/login.component';
import { LogoutComponent } from './components/user/logout/logout.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { PostsComponent } from './components/posts/posts.component';
import { ProductDetailsComponent } from './components/shop/product-details/product-details.component';
import { ProductsComponent } from './components/shop/products/products.component';
import { ProfileComponent } from './components/user/profile/profile.component';
import { WishlistComponent } from './components/shop/wishlist/wishlist.component';
import { AuthGuardService } from './service/auth-guard.service';
import { PasswordResetComponent } from './components/user/password-reset/password-reset.component';
import { ForgotPasswordComponent } from './components/user/forgot-password/forgot-password.component';
import { OrderDetailsComponent } from './components/shop/order-details/order-details.component';
import { AllCategoriesComponent } from './components/shop/all-categories/all-categories.component';
import { PostDetailsComponent } from './components/posts/post-details/post-details.component';
import { UploadProductComponent } from './components/admin/upload-product/upload-product.component';
import { UploadEventComponent } from './components/admin/upload-event/upload-event.component';
import { UploadNewsComponent } from './components/admin/upload-news/upload-news.component';
import { UploadCategoryComponent } from './components/admin/upload-category/upload-category.component';
import { ManageOrdersComponent } from './components/admin/manage-orders/manage-orders.component';
import { ManageCategoriesComponent } from './components/admin/manage-categories/manage-categories.component';
import { ManageEventsComponent } from './components/admin/manage-events/manage-events.component';
import { ManagePostsComponent } from './components/admin/manage-posts/manage-posts.component';
import { ManageProductsComponent } from './components/admin/manage-products/manage-products.component';
import { ManageUsersComponent } from './components/admin/manage-users/manage-users.component';
import { NewsletterComponent } from './components/admin/newsletter/newsletter.component';
import { ContactComponent } from './components/contact/contact.component';


const routes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: 'products', component: ProductsComponent},
  { path: 'products/category', component: CategoryComponent},
  { path: 'products/view', component: ProductDetailsComponent},
  { path: 'categories', component: AllCategoriesComponent },
  { path: 'posts', component: PostsComponent },
  { path: 'posts/view', component: PostDetailsComponent },
  { path: 'events', component: EventsComponent},
  { path: 'register', component: AddUserComponent},
  { path: 'login', component: LoginComponent},
  { path: 'logout', component: LogoutComponent, canActivate:[AuthGuardService], data: {roles: 'READ_PRIVILEGE'} },
  { path: 'profile', component: ProfileComponent, canActivate:[AuthGuardService], data: {roles: 'READ_PRIVILEGE'} },
  { path: 'cart', component: CartComponent, canActivate:[AuthGuardService], data: {roles: 'READ_PRIVILEGE'} },
  { path: 'wishlist', component: WishlistComponent, canActivate:[AuthGuardService], data: {roles: 'READ_PRIVILEGE'} },
  
  { path: 'admin', component: AdminPageComponent, canActivate:[AuthGuardService], data: {roles: 'WRITE_PRIVILEGE'} },
  { path: 'admin/add-product', component: UploadProductComponent, canActivate:[AuthGuardService], data: {roles: 'WRITE_PRIVILEGE'} },
  { path: 'admin/add-event', component: UploadEventComponent, canActivate:[AuthGuardService], data: {roles: 'WRITE_PRIVILEGE'} },
  { path: 'admin/add-post', component: UploadNewsComponent, canActivate:[AuthGuardService], data: {roles: 'WRITE_PRIVILEGE'} },
  { path: 'admin/add-category', component: UploadCategoryComponent, canActivate:[AuthGuardService], data: {roles: 'WRITE_PRIVILEGE'} },
  { path: 'admin/orders', component: ManageOrdersComponent, canActivate:[AuthGuardService], data: {roles: 'WRITE_PRIVILEGE'} },
  { path: 'admin/users', component: ManageUsersComponent, canActivate:[AuthGuardService], data: {roles: 'WRITE_PRIVILEGE'} },
  { path: 'admin/products', component: ManageProductsComponent, canActivate:[AuthGuardService], data: {roles: 'WRITE_PRIVILEGE'} },
  { path: 'admin/posts', component: ManagePostsComponent, canActivate:[AuthGuardService], data: {roles: 'WRITE_PRIVILEGE'} },
  { path: 'admin/events', component: ManageEventsComponent, canActivate:[AuthGuardService], data: {roles: 'WRITE_PRIVILEGE'} },
  { path: 'admin/categories', component: ManageCategoriesComponent, canActivate:[AuthGuardService], data: {roles: 'WRITE_PRIVILEGE'} },
  { path: 'admin/newsletter', component: NewsletterComponent, canActivate:[AuthGuardService], data: {roles: 'WRITE_PRIVILEGE'} },

  { path: 'checkout', component: CheckoutComponent, canActivate:[AuthGuardService], data: {roles: 'READ_PRIVILEGE'} },
  { path: 'changePassword', component: PasswordResetComponent },
  { path: 'forgotPassword', component: ForgotPasswordComponent },
  { path: 'orders/view', component: OrderDetailsComponent, data: {roles: 'READ_PRIVILEGE'} },

  { path: 'contact', component: ContactComponent },
  
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
