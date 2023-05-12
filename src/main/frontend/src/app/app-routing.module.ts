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
import { UserComponent } from './components/admin/listUsers/user.component';
import { WishlistComponent } from './components/shop/wishlist/wishlist.component';
import { AuthGuardService } from './service/auth-guard.service';
import { PasswordResetComponent } from './components/user/password-reset/password-reset.component';
import { ForgotPasswordComponent } from './components/user/forgot-password/forgot-password.component';
import { OrderDetailsComponent } from './components/shop/order-details/order-details.component';


const routes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: 'products', component: ProductsComponent},
  { path: 'products/category', component: CategoryComponent},
  { path: 'products/view', component: ProductDetailsComponent},
  { path: 'posts', component: PostsComponent},
  { path: 'events', component: EventsComponent},
  { path: 'user', component: UserComponent, canActivate:[AuthGuardService], data: {roles: 'ROLE_USER'} },
  { path: 'register', component: AddUserComponent},
  { path: 'login', component: LoginComponent},
  { path: 'logout', component: LogoutComponent, canActivate:[AuthGuardService], data: {roles: 'ROLE_USER'} },
  { path: 'profile', component: ProfileComponent, canActivate:[AuthGuardService], data: {roles: 'ROLE_USER'} },
  { path: 'cart', component: CartComponent, canActivate:[AuthGuardService], data: {roles: 'ROLE_USER'} },
  { path: 'wishlist', component: WishlistComponent, canActivate:[AuthGuardService], data: {roles: 'ROLE_USER'} },
  { path: 'admin', component: AdminPageComponent, canActivate:[AuthGuardService], data: {roles: 'ROLE_ADMIN'} },
  { path: 'checkout', component: CheckoutComponent, canActivate:[AuthGuardService], data: {roles: 'ROLE_USER'} },
  { path: 'changePassword', component: PasswordResetComponent },
  { path: 'forgotPassword', component: ForgotPasswordComponent },
  { path: 'orders/view', component: OrderDetailsComponent, data: {roles: 'ROLE_USER'} },
  
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
