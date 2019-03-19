package com.epam.springcloud.dao.oracle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.epam.springcloud.dao.FileDao;
import com.epam.springcloud.entity.user.User;
import com.epam.springcloud.entity.user_file.FileToDelete;
import com.epam.springcloud.entity.user_file.FileToDownload;
import com.epam.springcloud.entity.user_file.FileToUpload;
import com.epam.springcloud.entity.user_file.FileToUser;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@Transactional(rollbackFor = { Exception.class })
public class OracleFileDao implements FileDao {

    private static final String SAVE_PATH = "C:\\epam\\files";

    private static final String USER_ID_ATTRIBUTE_NAME = "userId";
    private static final String ID_ATTRIBUTE_NAME = "id";
    private static final String NAME_ATTRIBUTE_NAME = "name";
    private static final String PATH_ATTRIBUTE_NAME = "path";

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean isFileNameExists(FileToUser file) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(FileToUser.class);
        criteria.add(Restrictions.eq(NAME_ATTRIBUTE_NAME, file.getName()))
                .add(Restrictions.eq(USER_ID_ATTRIBUTE_NAME, file.getUserId()))
                .add(Restrictions.not(
                        Restrictions.eq(ID_ATTRIBUTE_NAME, file.getId())));
        return !criteria.list().isEmpty();
    }

    @Override
    public FileToUser saveFile(FileToUpload file) throws Exception {
        String dirPath = SAVE_PATH + File.separatorChar + file.getUserId()
                + File.separatorChar + file.getName();
        file.setPath(dirPath);
        file.setId((Integer) sessionFactory.getCurrentSession().save(file));
        writeFileToDisk(file);
        return new FileToUser(file);
    }

    private void writeFileToDisk(FileToUpload file) throws Exception {
        File dir = new File(file.getPath());
        if (!dir.mkdirs()) {
            throw new Exception("Couldn't create dir: " + dir);
        }
        try (OutputStream outputStream = new FileOutputStream(
                file.getPath() + File.separatorChar + file.getName())) {
            outputStream.write(file.getContent().getBytes());
            log.debug("file has been saved: " + file);
        }
    }

    @Transactional(readOnly = true)
    public FileToDownload getUserFileById(Integer fileId, Integer userId)
            throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(FileToDownload.class)
                .add(Restrictions.eq(ID_ATTRIBUTE_NAME, fileId))
                .add(Restrictions.eq(USER_ID_ATTRIBUTE_NAME, userId));
        return (FileToDownload) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<FileToUser> getFileList(User user) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(FileToUser.class)
                .add(Restrictions.eq(USER_ID_ATTRIBUTE_NAME, user.getId()));
        return criteria.list();
    }

    @Override
    public void deleteFile(FileToDelete file) throws Exception {
        FileToDownload deletedFile = getUserFileById(file.getId(),
                file.getUserId());
        sessionFactory.getCurrentSession().delete(file);
        deleteFileAtDisk(deletedFile);

    }

    private void deleteFileAtDisk(FileToDownload file) throws Exception {
        File path = new File(file.getPath());
        log.debug("try to clear dir: " + path);
        File[] listFiles = path.listFiles();
        for (File deletingFile : listFiles) {
            Files.delete(deletingFile.toPath());
        }
        Files.delete(path.toPath());
    }

    @Override
    public void renameFile(FileToUser file) throws Exception {
        FileToDownload oldFile = getUserFileById(file.getId(),
                file.getUserId());
        log.debug("old file: " + oldFile);
        sessionFactory.getCurrentSession().update(file);
        renameFileAtDisk(file, oldFile);
    }

    private void renameFileAtDisk(FileToUser newFile, FileToDownload oldFile)
            throws Exception {
        File oldFilePath = new File(
                oldFile.getPath() + File.separatorChar + oldFile.getName());
        File newFilePath = new File(
                oldFile.getPath() + File.separatorChar + newFile.getName());
        Files.move(oldFilePath.toPath(), newFilePath.toPath());

    }

    @Override
    public void attachBinaryFileToResponse(FileToDelete file,
            HttpServletResponse response) throws Exception {
        FileToDownload savedFile = getUserFileById(file.getId(),
                file.getUserId());
        log.debug("file extracted from db: " + savedFile);
        Path path = new File(
                savedFile.getPath() + File.separatorChar + savedFile.getName())
                        .toPath();
        log.debug("try to open file at: " + path);
        byte[] content = Files.readAllBytes(path);
        response.setHeader("Content-disposition",
                "attachment; filename=" + savedFile.getName());
        response.setContentLength(content.length);
        response.getOutputStream().write(content);
    }

    @Override
    public boolean isFileExists(FileToUpload file) throws Exception {
        return isFilePathExists(file);
    }

    public boolean isFilePathExists(FileToUpload file) {
        String filePath = SAVE_PATH + File.separatorChar + file.getUserId()
                + File.separatorChar + file.getName();
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(FileToDownload.class);
        criteria.add(Restrictions.eq(PATH_ATTRIBUTE_NAME, filePath)).add(
                Restrictions.eq(USER_ID_ATTRIBUTE_NAME, file.getUserId()));
        return !criteria.list().isEmpty();
    }

}
