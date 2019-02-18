package com.epam.springcloud.dao;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.epam.springcloud.entity.user.User;
import com.epam.springcloud.entity.user_file.FileToDelete;
import com.epam.springcloud.entity.user_file.FileToUpload;
import com.epam.springcloud.entity.user_file.FileToUser;

public interface FileDao {
    /**
     * 
     * @param file
     *            which you want to test
     * @return true if file exists in data source
     */
    boolean isFileNameExists(FileToUser file) throws Exception;
    
    boolean isFileExists(FileToUpload file) throws Exception;

    /**
     * 
     * @param file
     *            which you want to save
     * 
     *            save file to data source
     */
    FileToUser saveFile(FileToUpload file) throws Exception;

    /**
     * 
     * @param id
     *            of user
     * @return list of files for this user
     */
    List<FileToUser> getFileList(User user) throws Exception;

    /**
     * 
     * @param id
     *            of deleted file
     * 
     *            delete target file
     */
    void deleteFile(FileToDelete file) throws Exception;

    /**
     * 
     * @param id
     *            of file which name you want to change
     * @param newName
     *            of file
     */
    void renameFile(FileToUser file) throws Exception;

    void attachBinaryFileToResponse(FileToDelete file,
	    HttpServletResponse response) throws Exception;
}
