import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddUserComponent } from './components/add-user/add-user.component';
import { CartComponent } from './components/cart/cart.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { ProductsComponent } from './components/products/products.component';
import { ProfileComponent } from './components/profile/profile.component';
import { UploadProductComponent } from './components/upload-product/upload-product.component';
import { UserComponent } from './components/user/user.component';
import { WishlistComponent } from './components/wishlist/wishlist.component';
import { AuthGuardService } from './service/auth-guard.service';


const routes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: 'products', component: ProductsComponent},
  { path: 'user', component: UserComponent, canActivate:[AuthGuardService] },
  { path: 'register', component: AddUserComponent},
  { path: 'login', component: LoginComponent},
  { path: 'logout', component: LogoutComponent, canActivate:[AuthGuardService] },
  { path: 'profile', component: ProfileComponent, canActivate:[AuthGuardService] },
  { path: 'cart', component: CartComponent, canActivate:[AuthGuardService] },
  { path: 'wishlist', component: WishlistComponent, canActivate:[AuthGuardService] },
  { path: 'admin/uploadProduct', component: UploadProductComponent, canActivate:[AuthGuardService]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
