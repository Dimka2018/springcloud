import {Component, EventEmitter, Injectable, Input, Output} from "@angular/core";
import {ControlValueAccessor} from "@angular/forms";
import {FileService} from "../../service/file.service";
import {File} from "../../model/File";

@Component({
  selector: 'file',
  templateUrl: './file.component.html',
  styleUrls: ['./file.component.scss']
})
export class FileComponent {

  modalMode: boolean = false
  private fileService: FileService

  @Input("name")
  name: string = ''

  @Input("id")
  id: string = ''

  @Output("delete")
  delete: EventEmitter<any> = new EventEmitter();

  constructor(fileService: FileService) {
    this.fileService = fileService;
  }

  toggleModalMode() {
    this.modalMode = !this.modalMode
  }

  download() {
    this.fileService.downloadFile(this.id);
  }

  deleteFile() {
    this.fileService.delete(this.id)
      .subscribe(response => {
        if (response.success) {
          this.delete.emit(this.id)
        }
      })
  }

  renameFile(newName: string) {
    let file = new File(this.id, newName);
    this.fileService.rename(file)
      .subscribe(response => {
        if (response.success) {
          this.name = newName;
          this.toggleModalMode()
        }
      })
  }

}
