package com.epam.springcloud.controller;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.epam.springcloud.dao.FileDao;
import com.epam.springcloud.entity.user.User;
import com.epam.springcloud.entity.user_file.FileToDelete;
import com.epam.springcloud.entity.user_file.FileToUpload;
import com.epam.springcloud.entity.user_file.FileToUser;
import com.epam.springcloud.resource.MessageBundle;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
public class FileController {

    @Autowired
    private FileDao fileDao;

    @Autowired
    private MessageBundle messageBundle;

    @PostMapping(path = { "user/file" })
    public FileToUser uploadFile(@Validated FileToUpload file,
            BindingResult bindingResult, @SessionAttribute User user,
            HttpServletResponse response, Locale locale) throws Exception {
        file.setUserId(user.getId());
        log.debug("user try to add file: " + user + " " + file);
        FileToUser savedFile = null;
        if (!bindingResult.hasErrors()) {
            if (!fileDao.isFileExists(file)) {
                savedFile = fileDao.saveFile(file);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        messageBundle.getFileExistsMessage(locale));
            }
        } else {
            sendBindingError(response, bindingResult, locale);
        }
        return savedFile;
    }

    @PutMapping(path = { "user/file" })
    public FileToUser renameFile(@Validated FileToUser file,
            BindingResult bindingResult, @SessionAttribute User user,
            HttpServletResponse response, Locale locale) throws Exception {
        file.setUserId(user.getId());
        log.debug("user try to rename file: " + file);
        if (!bindingResult.hasErrors()) {
            if (!fileDao.isFileNameExists(file)) {
                fileDao.renameFile(file);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        messageBundle.getFileExistsMessage(locale));
            }
        } else {
            sendBindingError(response, bindingResult, locale);
        }
        return file;
    }

    @DeleteMapping(path = { "user/file" })
    public void deleteFile(@Validated FileToDelete file,
            BindingResult bindingResult, @SessionAttribute User user,
            HttpServletResponse response, Locale locale) throws Exception {
        file.setUserId(user.getId());
        log.debug("user try to rename file: " + file);
        if (!bindingResult.hasErrors()) {
            fileDao.deleteFile(file);
        } else {
            sendBindingError(response, bindingResult, locale);
        }

    }

    @GetMapping(path = { "user/file" })
    public void downloadFile(@Validated FileToDelete file,
            BindingResult bindingResult, @SessionAttribute User user,
            HttpServletResponse response, Locale locale) throws Exception {
        file.setUserId(user.getId());
        log.debug("User try to download file: " + file);
        if (!bindingResult.hasErrors()) {
            fileDao.attachBinaryFileToResponse(file, response);
        } else {
            sendBindingError(response, bindingResult, locale);
        }
    }

    @GetMapping(path = "/user/files")
    public List<FileToUser> getFileList(@SessionAttribute User user)
            throws Exception {
        log.debug("user try to get file list: " + user);
        List<FileToUser> fileList = fileDao.getFileList(user);
        log.debug("extracted number of files: "
                + (fileList != null ? fileList.size() : "null"));
        return fileList;
    }

    private void sendBindingError(HttpServletResponse response,
            BindingResult bindingResult, Locale locale) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                messageBundle.getMessage(
                        bindingResult.getFieldError().getDefaultMessage(),
                        locale));
    }

    @ExceptionHandler({ Exception.class })
    public void handleException(Exception exception,
            HttpServletResponse response, Locale locale) {
        log.error("Exception occurs", exception);
        try {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    messageBundle.getServerProblemMessage(locale));
        } catch (IOException e) {
            log.fatal("Error sending exception", e);
        }
    }
}
