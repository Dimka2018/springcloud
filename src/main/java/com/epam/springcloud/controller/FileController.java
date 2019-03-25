package com.epam.springcloud.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.epam.springcloud.dao.FileDao;
import com.epam.springcloud.entity.user.User;
import com.epam.springcloud.entity.user_file.File;
import com.epam.springcloud.entity.user_file.FileDeleteDTO;
import com.epam.springcloud.entity.user_file.FileDownloadDTO;
import com.epam.springcloud.entity.user_file.FileRenameDTO;
import com.epam.springcloud.entity.user_file.FileToUserDTO;
import com.epam.springcloud.entity.user_file.FileUploadDTO;
import com.epam.springcloud.mapper.Mapper;
import com.epam.springcloud.resource.MessageBundle;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class FileController {

    @Autowired
    private FileDao fileDao;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private Mapper mapper;

    @PostMapping(path = { "user/file" })
    public FileToUserDTO uploadFile(@Validated FileUploadDTO fileDTO,
            BindingResult bindingResult, @SessionAttribute User user,
            HttpServletResponse response, Locale locale) throws Exception {
        log.debug("DTO from user: " + fileDTO);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                    bindingResult.getFieldError().getDefaultMessage());
        }
        File file = mapper.map(fileDTO, File.class);
        file.setUserId(user.getId());
        log.debug("mapped file: " + file);
        File savedFile = null;
        if (!fileDao.isFileExists(file)) {
            savedFile = fileDao.saveFile(file);
        } else {
            String userMessage = messageSource.getMessage(
                    MessageBundle.FILE_EXISTS_MESSAGE, null, locale);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    Encode.forHtml(userMessage));
        }
        return savedFile != null ? mapper.map(savedFile, FileToUserDTO.class)
                : null;
    }

    @PutMapping(path = { "user/file" })
    public FileToUserDTO renameFile(@Validated FileRenameDTO fileDTO,
            BindingResult bindingResult, @SessionAttribute User user,
            HttpServletResponse response, Locale locale) throws Exception {
        log.debug("DTO from user " + fileDTO);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                    bindingResult.getFieldError().getDefaultMessage());
        }
        File file = mapper.map(fileDTO, File.class);
        file.setUserId(user.getId());
        log.debug("mapped file: " + file);
        if (!fileDao.isFileNameExists(file)) {
            fileDao.renameFile(file);
        } else {
            String userMessage = messageSource.getMessage(
                    MessageBundle.FILE_EXISTS_MESSAGE, null, locale);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    Encode.forHtml(userMessage));
        }
        return new FileToUserDTO(file.getId(), file.getName());
    }

    @DeleteMapping(path = { "user/file" })
    public void deleteFile(@Validated FileDeleteDTO fileDTO,
            BindingResult bindingResult, @SessionAttribute User user,
            HttpServletResponse response, Locale locale) throws Exception {
        log.debug("DTO from user " + fileDTO);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                    bindingResult.getFieldError().getDefaultMessage());
        }
        File file = mapper.map(fileDTO, File.class);
        file.setUserId(user.getId());
        fileDao.deleteFile(file);
    }

    @GetMapping(path = { "user/file" })
    public void downloadFile(@Validated FileDownloadDTO fileDTO,
            BindingResult bindingResult, @SessionAttribute User user,
            HttpServletResponse response, Locale locale) throws Exception {
        log.debug("DTO from user: " + fileDTO);
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                    bindingResult.getFieldError().getDefaultMessage());
        }
        File file = mapper.map(fileDTO, File.class);
        file.setUserId(user.getId());
        log.debug("mapped file: " + file);
        fileDao.attachBinaryFileToResponse(file, response);
    }

    @GetMapping(path = "/user/files")
    public List<FileToUserDTO> getFileList(@SessionAttribute User user)
            throws Exception {
        log.debug("user try to get file list");
        List<File> fileList = fileDao.getFileList(user);
        log.debug("extracted number of files: "
                + (fileList != null ? fileList.size() : "null"));
        return mapper.mapAll(fileList, FileToUserDTO.class);
    }
}
