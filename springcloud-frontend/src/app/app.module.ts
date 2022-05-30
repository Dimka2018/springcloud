import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {WelcomeComponent} from "../page/welcome/welcome.component";
import {StorageComponent} from "../page/storage/storage.component";
import {ModalComponent} from "../wiget/modal/modal.component";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {FileComponent} from "../wiget/file/file.component";
import {UnauthorizedInterceptor} from "../interceptor/unauthorized.interceptor";

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    StorageComponent,
    FileComponent,
    ModalComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: UnauthorizedInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
