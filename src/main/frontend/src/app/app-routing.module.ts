import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddUserComponent } from './components/add-user/add-user.component';
import { CartComponent } from './components/cart/cart.component';
import { CategoryComponent } from './components/category/category.component';
import { EventsComponent } from './components/events/events.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { LogoutComponent } from './components/logout/logout.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { PostsComponent } from './components/posts/posts.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { ProductsComponent } from './components/products/products.component';
import { ProfileComponent } from './components/profile/profile.component';
import { UploadEventComponent } from './components/upload-event/upload-event.component';
import { UploadNewsComponent } from './components/upload-news/upload-news.component';
import { UploadProductComponent } from './components/upload-product/upload-product.component';
import { UserComponent } from './components/user/user.component';
import { WishlistComponent } from './components/wishlist/wishlist.component';
import { AuthGuardService } from './service/auth-guard.service';


const routes: Routes = [
  { path: 'home', component: HomeComponent},
  { path: 'products', component: ProductsComponent},
  { path: 'products/category', component: CategoryComponent},
  { path: 'products/view', component: ProductDetailsComponent},
  { path: 'posts', component: PostsComponent},
  { path: 'events', component: EventsComponent},
  { path: 'user', component: UserComponent, canActivate:[AuthGuardService] },
  { path: 'register', component: AddUserComponent},
  { path: 'login', component: LoginComponent},
  { path: 'logout', component: LogoutComponent, canActivate:[AuthGuardService] },
  { path: 'profile', component: ProfileComponent, canActivate:[AuthGuardService] },
  { path: 'cart', component: CartComponent, canActivate:[AuthGuardService] },
  { path: 'wishlist', component: WishlistComponent, canActivate:[AuthGuardService] },
  { path: 'admin/uploadProduct', component: UploadProductComponent, canActivate:[AuthGuardService]},
  { path: 'admin/uploadNews', component: UploadNewsComponent, canActivate:[AuthGuardService]},
  { path: 'admin/uploadEvent', component: UploadEventComponent, canActivate:[AuthGuardService]},
  
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
