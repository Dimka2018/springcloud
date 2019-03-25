$(document).ready(() => {
    new MainView().init();
});

class MainView {

    init() {
        this.uploadingBox = new DragAndDropBox("file-upload-div", "file-upload-state")
        var context = this;
        refreshFileList();


        $(".change-save-button").click(() => {
            $(".changing-form-send-button").click();
        });


        $(".changing-form").submit(function (event) {
            event.preventDefault();
            $.ajax({
                url: "user/file?id=" + $(".changing-file-id").val() + "&name=" + $(".changing-name").val(),
                type: "PUT",
                success: (file) => {
                    $(".changing-form").toggle();
                    let id = $(".changing-file-id").val();
                    $(".changing-file-id").val("");
                    $(".file-id").each(function () {
                        if ($(this).val() == id) {
                            let nameField = $(this).parent().parent().find(".file-name");
                            nameField.text(file.name);
                        }
                    });
                },
                error: (jqxhr) => {
                    openMessageWindow(extractErrorMessage(jqxhr));
                }
            });
        });

        $(".message-close-button").click(() => {
            $(".message-modal-window").toggle();
        });

        $(".change-cancel-button").click(() => {
            $(".changing-form").toggle();
        });

        this.uploadingBox.onChange = () => {
            $(".uploading-file-name").val(this.uploadingBox.savedFile.name);
            $(".file-upload-form").submit();
        };

        $(".uploading-file").change((event) => {
            let file = event.target.files[0];
            if (file != undefined) {
                $(".uploading-file-name").val(event.target.files[0].name);
                $(".file-upload-form").submit();
            }
        });

        $(".logout-button").click(function (event) {
            event.preventDefault();
            $.ajax({
                url: "user/session",
                type: "DELETE",
                success: () => {
                    window.location = "welcome.html";
                }
            });
        });

        $(".file-upload-form").submit(function (event) {
            event.preventDefault();
            let formData = new FormData(this);
            if (context.uploadingBox.savedFile != undefined) {
                formData.set("content", context.uploadingBox.savedFile);
            }
            $.ajax({
                url: "user/file",
                type: "POST",
                data: formData,
                enctype: "multipart/form-data",
                contentType: false,
                processData: false,
                xhr: function () {
                    let xhr = $.ajaxSettings.xhr(); // get XMLHttpRequest
                    xhr.upload.addEventListener('progress', function (event) {
                        if (event.lengthComputable) {
                            var percentComplete = Math.ceil(event.loaded / event.total * 100);
                            context.uploadingBox.setText('Loaing ' + percentComplete + '%');
                        }
                    }, false);
                    return xhr;
                },
                success: (file) => {
                    context.uploadingBox.setText("Uploaded");
                    addUserFile(file.id, file.name)
                },
                error: (jqxhr) => {
                    openMessageWindow(extractErrorMessage(jqxhr));
                }
            });

        });

        function refreshFileList() {
            $.ajax({
                url: "user/files",
                type: "GET",
                success: (fileArray) => {
                    clearFileList();
                    for (let file of fileArray) {
                        addUserFile(file.id, file.name);
                    }
                },
                error: (jqxhr) => {
                    openMessageWindow(extractErrorMessage(jqxhr));
                }
            });
        }

        function addUserFile(id, name) {
            let file = $(".file-pattern").clone();
            file.find(".file-id").val(id);
            let fileNameField = file.find(".file-name");
            fileNameField.text(name);
            file.find(".download-button").click(() => {
                let fileId = file.find(".file-id").val();
                window.location = "user/file?id=" + fileId;
            });

            file.find(".edit-button").click(() => {
                $(".changing-name").val(fileNameField.text());
                $(".changing-file-id").val(id);
                $(".changing-form").toggle();
            });

            file.find(".delete-button").click(() => {
                let fileId = file.find(".file-id").val();
                $.ajax({
                    url: "user/file?id=" + fileId,
                    type: "DELETE",
                    success: () => {
                        file.remove();
                        uploadingBox.refresh();
                    },
                    error: (jqxhr) => {
                        openMessageWindow(extractErrorMessage(jqxhr));
                    }
                });

            });
            file.removeClass("file-pattern");
            file.removeClass("invisible");
            file.appendTo(".file-container");
        }

        function clearFileList() {
            $(".file-container").html("");
        }

        function openMessageWindow(text) {
            $(".message-text").html(text);
            $(".message-modal-window").toggle();
        }

        function extractErrorMessage(jqxhr) {
            let startPattern = "Message</b> ";
            let startPosition = jqxhr.responseText.indexOf(startPattern) + startPattern.length;
            let endPosition = jqxhr.responseText.indexOf("</p><p><b>Description");
            console.log(startPosition);
            console.log(endPosition);
            return jqxhr.responseText.substring(startPosition, endPosition);
        }


    }

    clearUploadingForm() {
        $(".file-name").val("");
        this.uploadingBox.refresh();
    }
}