package com.dimka.springcloud.service;

import com.dimka.springcloud.entity.File;
import com.dimka.springcloud.entity.FileContent;
import com.dimka.springcloud.repository.FileContentRepository;
import com.dimka.springcloud.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final FileContentRepository fileContentRepository;

    public File uploadFile(File file, FileContent content) {
        FileContent savedContent = fileContentRepository.save(content);
        file.setContentId(savedContent.getId());
        return fileRepository.save(file);
    }

    public List<File> getUserFiles(Long userId) {
        return fileRepository.findAllByUserId(userId);
    }

    public Optional<FileContent> getFileContent(String contentId) {
        return fileContentRepository.findById(contentId);
    }

    public Optional<File> getFile(Long fileId, Long userId) {
        return fileRepository.findByUserIdAndId(userId, fileId);
    }

    @Transactional
    public void deleteFile(Long fileId, Long userId) {
        Optional<File> file = fileRepository.findByUserIdAndId(userId, fileId);
        if (file.isPresent()) {
            fileRepository.deleteByUserIdAndId(userId, fileId);
            fileContentRepository.deleteById(file.get().getContentId());
        }
    }

    public void save(File file) {
        fileRepository.save(file);
    }
}
