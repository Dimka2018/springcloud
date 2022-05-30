package com.dimka.springcloud.repository;

import com.dimka.springcloud.entity.FileContent;
import org.springframework.data.repository.CrudRepository;

public interface FileContentRepository extends CrudRepository<FileContent, String> {
}
