package com.dimka.springcloud.service.api;

import com.dimka.springcloud.dto.FileRenameRequest;
import com.dimka.springcloud.dto.FileResponse;
import com.dimka.springcloud.dto.FileUploadResponse;
import com.dimka.springcloud.dto.Response;
import com.dimka.springcloud.entity.File;
import com.dimka.springcloud.entity.FileContent;
import com.dimka.springcloud.entity.User;
import com.dimka.springcloud.entity.UserDetailsImpl;
import com.dimka.springcloud.mapper.FileToFileResponseMapper;
import com.dimka.springcloud.mapper.FileToFileUploadResponseMapper;
import com.dimka.springcloud.mapper.MultipartFileToFileContentMapper;
import com.dimka.springcloud.mapper.MultipartFileToFileMapper;
import com.dimka.springcloud.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FileApiService {

    private final FileService fileService;

    private final MultipartFileToFileMapper multipartFileToFileMapper;
    private final MultipartFileToFileContentMapper multipartFileToFileContentMapper;

    private final FileToFileUploadResponseMapper fileToFileUploadResponseMapper;
    private final FileToFileResponseMapper fileToFileResponseMapper;

    public FileUploadResponse uploadFile(MultipartFile multipartFile) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userDetails.getUser();
        File file = multipartFileToFileMapper.convert(multipartFile, user);
        FileContent fileContent = multipartFileToFileContentMapper.convert(multipartFile);
        file = fileService.uploadFile(file, fileContent);
        return fileToFileUploadResponseMapper.convert(file);
    }

    public List<FileResponse> getUserFiles() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userDetails.getUser();
        List<File> files = fileService.getUserFiles(user.getId());
        return fileToFileResponseMapper.convert(files);
    }

    public ResponseEntity<Resource> downloadFile(Long fileId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userDetails.getUser();
        File file = fileService.getFile(fileId, user.getId()).orElseThrow(() -> new RuntimeException("Not Found"));
        FileContent content = fileService.getFileContent(file.getContentId()).orElseThrow(() -> new RuntimeException("Unauthorized"));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .body(new ByteArrayResource(content.getContent()));
    }

    public Response deleteFile(Long fileId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userDetails.getUser();
        fileService.deleteFile(fileId, user.getId());
        return new Response()
                .setSuccess(true);
    }

    public Response renameFile(FileRenameRequest renameRequest) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userDetails.getUser();
        File file = fileService.getFile(renameRequest.getId(), user.getId()).orElseThrow(() -> new RuntimeException("Not found"));
        file.setName(renameRequest.getName());
        fileService.save(file);
        return new Response()
                .setSuccess(true);
    }
}
