package com.dimka.springcloud.controller;

import com.dimka.springcloud.dto.FileRenameRequest;
import com.dimka.springcloud.dto.FileResponse;
import com.dimka.springcloud.dto.FileUploadResponse;
import com.dimka.springcloud.dto.Response;
import com.dimka.springcloud.service.api.FileApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FileController {

    private final FileApiService fileApiService;

    @PostMapping("/api/file")
    public FileUploadResponse uploadFile(MultipartFile file) {
        return fileApiService.uploadFile(file);
    }

    @PutMapping("/api/file")
    public Response renameFile(@RequestBody FileRenameRequest renameRequest) {
        return fileApiService.renameFile(renameRequest);
    }

    @DeleteMapping("/api/file/{fileId}")
    public Response deleteFile(@PathVariable Long fileId) {
        return fileApiService.deleteFile(fileId);
    }

    @GetMapping("/api/file/{fileId}/content")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        return fileApiService.downloadFile(fileId);
    }

    @GetMapping(path = "/api/files")
    public List<FileResponse> getFileList() {
        return fileApiService.getUserFiles();
    }
}
