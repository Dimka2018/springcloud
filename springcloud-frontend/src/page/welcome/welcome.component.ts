import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {User} from "../../model/user";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.scss']
})
export class WelcomeComponent {

  public user: User = new User
  registrationMode: boolean = false
  errorMessage: string = ''

  constructor(private userService: UserService, private router: Router) {
  }

  register() {
    this.userService.register(this.user)
      .subscribe(response => {
        this.user = new User()
        if (response.success) {
          this.router.navigate([`/storage`])
        }
      })
  }

  login() {
    this.userService.login(this.user)
      .subscribe(() => {
          this.user = new User()
          this.router.navigate(["/storage"])
        },
        () => {
        this.errorMessage = "Invalid user or password"
        })
  }

  toggleRegistrationMode() {
    this.registrationMode = !this.registrationMode;
  }

}
