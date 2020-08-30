package com.dimka.springcloud.dao;


import com.dimka.springcloud.entity.File;
import com.dimka.springcloud.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
     * @return list of files for this user
     */
    List<File> getFileList(User user) throws Exception;

    void deleteFile(File file) throws Exception;

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
