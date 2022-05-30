import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {StorageComponent} from "../page/storage/storage.component";
import {WelcomeComponent} from "../page/welcome/welcome.component";

const routes: Routes = [
  { path: 'welcome', component: WelcomeComponent },
  { path: 'storage', component: StorageComponent },
  { path: '',   redirectTo: '/welcome', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
