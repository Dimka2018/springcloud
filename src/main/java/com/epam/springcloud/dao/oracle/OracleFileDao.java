package com.epam.springcloud.dao.oracle;

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
import com.epam.springcloud.entity.user_file.File;

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
    public boolean isFileNameExists(File file) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(File.class);
        criteria.add(Restrictions.eq(NAME_ATTRIBUTE_NAME, file.getName())).add(
                Restrictions.eq(USER_ID_ATTRIBUTE_NAME, file.getUserId()));
        return !criteria.list().isEmpty();
    }

    @Override
    public File saveFile(File file) throws Exception {
        String dirPath = SAVE_PATH + java.io.File.separatorChar
                + file.getUserId() + java.io.File.separatorChar
                + file.getName();
        file.setPath(dirPath);
        file.setId((Integer) sessionFactory.getCurrentSession().save(file));
        writeFileToDisk(file);
        return file;
    }

    private void writeFileToDisk(File file) throws Exception {
        java.io.File dir = new java.io.File(file.getPath());
        if (!dir.mkdirs()) {
            throw new Exception("Couldn't create dir: " + dir);
        }
        try (OutputStream outputStream = new FileOutputStream(file.getPath()
                + java.io.File.separatorChar + file.getName())) {
            outputStream.write(file.getContent().getBytes());
            log.debug("file has been saved: " + file);
        }
    }

    @Transactional(readOnly = true)
    public File getUserFileById(Integer fileId, Integer userId)
            throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(File.class)
                .add(Restrictions.eq(ID_ATTRIBUTE_NAME, fileId))
                .add(Restrictions.eq(USER_ID_ATTRIBUTE_NAME, userId));
        return (File) criteria.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<File> getFileList(User user) throws Exception {
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(File.class)
                .add(Restrictions.eq(USER_ID_ATTRIBUTE_NAME, user.getId()));
        return criteria.list();
    }

    @Override
    public void deleteFile(File file) throws Exception {
        File deletedFile = sessionFactory.getCurrentSession().load(File.class,
                file.getId());
        sessionFactory.getCurrentSession().delete(deletedFile);
        deleteFileAtDisk(deletedFile);

    }

    private void deleteFileAtDisk(File file) throws Exception {
        java.io.File path = new java.io.File(file.getPath());
        log.debug("try to clear dir: " + path);
        java.io.File[] listFiles = path.listFiles();
        for (java.io.File deletingFile : listFiles) {
            Files.delete(deletingFile.toPath());
        }
        Files.delete(path.toPath());
    }

    @Override
    public void renameFile(File file) throws Exception {
        File oldFile = getUserFileById(file.getId(), file.getUserId());
        log.debug("old file: " + oldFile);
        file.setPath(oldFile.getPath());
        sessionFactory.getCurrentSession().clear();
        sessionFactory.getCurrentSession().update(file);
        renameFileAtDisk(file, oldFile);
    }

    private void renameFileAtDisk(File newFile, File oldFile)
            throws Exception {
        java.io.File oldFilePath = new java.io.File(oldFile.getPath()
                + java.io.File.separatorChar + oldFile.getName());
        java.io.File newFilePath = new java.io.File(oldFile.getPath()
                + java.io.File.separatorChar + newFile.getName());
        Files.move(oldFilePath.toPath(), newFilePath.toPath());

    }

    @Override
    public void attachBinaryFileToResponse(File file,
            HttpServletResponse response) throws Exception {
        File savedFile = getUserFileById(file.getId(), file.getUserId());
        log.debug("file extracted from db: " + savedFile);
        Path path = new java.io.File(savedFile.getPath()
                + java.io.File.separatorChar + savedFile.getName()).toPath();
        log.debug("try to open file at: " + path);
        byte[] content = Files.readAllBytes(path);
        response.setHeader("Content-disposition",
                "attachment; filename=" + savedFile.getName());
        response.setContentLength(content.length);
        response.getOutputStream().write(content);
    }

    @Override
    public boolean isFileExists(File file) throws Exception {
        return isFilePathExists(file) || isFileNameExists(file);
    }

    public boolean isFilePathExists(File file) {
        String filePath = SAVE_PATH + java.io.File.separatorChar
                + file.getUserId() + java.io.File.separatorChar
                + file.getName();
        Criteria criteria = sessionFactory.getCurrentSession()
                .createCriteria(File.class);
        criteria.add(Restrictions.eq(PATH_ATTRIBUTE_NAME, filePath)).add(
                Restrictions.eq(USER_ID_ATTRIBUTE_NAME, file.getUserId()));
        return !criteria.list().isEmpty();
    }

}
