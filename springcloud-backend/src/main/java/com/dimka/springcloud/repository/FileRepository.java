package com.dimka.springcloud.repository;

import com.dimka.springcloud.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findAllByUserId(Long userId);

    Optional<File> findByUserIdAndId(Long userId, Long id);

    void deleteByUserIdAndId(Long userId, Long id);
}
