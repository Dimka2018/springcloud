import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FileService} from "../../service/file.service";
import {File} from "../../model/File";
import {UserService} from "../../service/user.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'storage',
  templateUrl: './storage.component.html',
  styleUrls: ['./storage.component.scss']
})
export class StorageComponent implements OnInit {

  fileDrag: boolean = false
  files: File[] = []

  constructor(private fileService: FileService, private userService: UserService, private router: Router) {
  }

  ngOnInit(): void {
    this.fileService.getFiles()
      .subscribe(files => this.files = files);
  }

  toggleFileDrag(event: any) {
    this.preventDefaults(event)
    this.fileDrag = !this.fileDrag;
  }

  onFileChange(event: any) {
    this.preventDefaults(event);
    this.saveFile(event.target.files[0])
  }

  onFileDrop(event: any) {
    this.preventDefaults(event);
    this.saveFile(event.dataTransfer.files[0]);
  }

  removeFile(id: string) {
    this.files.forEach((file, index) => {
      if (file.id === id) this.files.splice(index, 1);
    })
  }

  trackFile(index: number, file: File) {
    return file.id;
  }

  logout() {
    this.userService.logout()
      .subscribe(() => this.router.navigate(["/welcome"]))
  }

  preventDefaults(event: any) {
    event.preventDefault();
    event.stopPropagation();
  }

  private saveFile(file: any) {
    return this.fileService.uploadFile(file)
      .subscribe(file => {
        this.files.push(file)
        this.fileDrag = false;
      })
  }

}
