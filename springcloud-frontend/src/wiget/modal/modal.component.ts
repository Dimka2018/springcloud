import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss']
})
export class ModalComponent {

  modalMessage: string = 'Enter file name'

  @Output("ok")
  ok: EventEmitter<any> = new EventEmitter();

  @Output("cancel")
  cancel: EventEmitter<any> = new EventEmitter();

  inputContent: string = ''

  onOk() {
    this.ok.emit(this.inputContent)
  }

  onCancel() {
    this.cancel.emit()
  }

}
