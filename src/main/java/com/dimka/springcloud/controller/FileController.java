package com.dimka.springcloud.controller;

import com.dimka.springcloud.dao.FileDao;
import com.dimka.springcloud.dto.*;
import com.dimka.springcloud.entity.File;
import com.dimka.springcloud.entity.User;
import com.dimka.springcloud.mapper.Mapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
public class FileController {

    private final FileDao fileDao;
    private final User user;
    private final Mapper mapper;

    @PostMapping(path = { "user/file" })
    public FileToUserDTO uploadFile(@Validated FileUploadDTO fileDTO,
                                    BindingResult bindingResult) throws Exception {
        log.debug("user trys to upload file");
        log.debug("DTO from user: " + fileDTO);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                    bindingResult.getFieldError().getDefaultMessage());
        }
        File file = mapper.map(fileDTO, File.class);
        file.setUserId(user.getId());
        log.debug("mapped file: " + file);
        File savedFile = null;
        if (fileDao.isFileExists(file)) {
            throw new FileAlreadyExistsException(
                    "file already exists: " + file);
        }
        savedFile = fileDao.saveFile(file);
        log.debug("file has been saved: " + file);
        return mapper.map(savedFile, FileToUserDTO.class);
    }

    @PutMapping(path = { "user/file" })
    public FileToUserDTO renameFile(@Validated FileRenameDTO fileDTO,
                                    BindingResult bindingResult) throws Exception {
        log.debug("user trys to rename file");
        log.debug("DTO from user " + fileDTO);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                    bindingResult.getFieldError().getDefaultMessage());
        }
        File file = mapper.map(fileDTO, File.class);
        file.setUserId(user.getId());
        log.debug("mapped file: " + file);
        if (fileDao.isFileNameExists(file)) {
            throw new FileAlreadyExistsException(
                    "file already exists: " + file);
        }
        fileDao.renameFile(file);
        log.debug("file has been renamed: " + file);
        return new FileToUserDTO(file.getId(), file.getName());
    }

    @DeleteMapping(path = { "user/file" })
    public void deleteFile(@Validated FileDeleteDTO fileDTO,
                           BindingResult bindingResult) throws Exception {
        log.debug("user trys to delete file");
        log.debug("DTO from user " + fileDTO);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                    bindingResult.getFieldError().getDefaultMessage());
        }
        File file = mapper.map(fileDTO, File.class);
        file.setUserId(user.getId());
        fileDao.deleteFile(file);
        log.debug("file has been deleted: " + file);
    }

    @GetMapping(path = { "user/file" })
    public void downloadFile(@Validated FileDownloadDTO fileDTO,
                             BindingResult bindingResult, HttpServletResponse response)
            throws Exception {
        log.debug("user trys to download file");
        log.debug("DTO from user: " + fileDTO);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                    bindingResult.getFieldError().getDefaultMessage());
        }
        File file = mapper.map(fileDTO, File.class);
        file.setUserId(user.getId());
        log.debug("mapped file: " + file);
        fileDao.attachBinaryFileToResponse(file, response);
        log.debug("file has been attached to response: " + file);
    }

    @GetMapping(path = "/user/files")
    public List<FileToUserDTO> getFileList() throws Exception {
        log.debug("user try to get file list");
        List<File> fileList = new ArrayList<>();
        fileList.addAll(fileDao.getFileList(user));
        log.debug("extracted number of files: " + fileList.size());
        return mapper.mapAll(fileList, FileToUserDTO.class);
    }

}
