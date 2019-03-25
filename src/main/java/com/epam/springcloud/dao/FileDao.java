package com.epam.springcloud.dao;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.epam.springcloud.entity.user.User;
import com.epam.springcloud.entity.user_file.File;

public interface FileDao {
    /**
     * 
     * @param file
     *            which you want to test
     * @return true if file exists in data source
     */
    boolean isFileNameExists(File file) throws Exception;

    /**
     * 
     * @param file
     * @return true if file already exists
     * @throws Exception
     */
    boolean isFileExists(File file) throws Exception;

    /**
     * 
     * @param file
     *            which you want to save
     * 
     *            save file to data source
     */
    File saveFile(File file) throws Exception;

    /**
     * 
     * @param id
     *            of user
     * @return list of files for this user
     */
    List<File> getFileList(User user) throws Exception;

    /**
     * 
     * @param id
     *            of deleted file
     * 
     *            delete target file
     */
    void deleteFile(File file) throws Exception;

    /**
     * 
     * @param id
     *            of file which name you want to change
     * @param newName
     *            of file
     */
    void renameFile(File file) throws Exception;

    /**
     * 
     * @param file
     *            to attach
     * @param response
     *            to attach file
     * @throws Exception
     *             if can't attach
     */
    void attachBinaryFileToResponse(File file, HttpServletResponse response)
            throws Exception;
}
