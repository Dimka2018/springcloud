class DragAndDropBox{
	
	constructor(dropBoxClass, textFrameClass){
		this.box = $("." + dropBoxClass);
		this.textFrame = $("." + textFrameClass);
		this.init();
	}
	
	init(){
		this.prventDefaultBehavor();
		this.bindBoxFiering();
		this.bindBoxExtinguishing();
		this.bindFileSaving();
	}
	
	prventDefaultBehavor(){
		['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
			this.box.bind(eventName, (event) => {
				event.preventDefault();
				event.stopPropagation();
			});
		});
	}
	
	bindBoxFiering(){
		['dragenter', 'dragover'].forEach(eventName => {
			this.box.bind(eventName, () => {
				this.box.addClass("fire");
			});
		});
	}
	
	bindBoxExtinguishing(){
		['dragleave', 'drop'].forEach(eventName => {
            this.box.bind(eventName, () => {
				this.box.removeClass("fire");
			});
         });
	}
	
	bindFileSaving(){
		this.box.bind("drop", (event) => {
			this.savedFile = event.originalEvent.dataTransfer.files[0];
			this.onChange();
		});
	}
	
	onChange(){
	}
	
	refresh(){
		this.savedFiles = undefined;
		this.textFrame.text("File upload");
	}
	
	setText(text){
		this.textFrame.text(text);
	}
}