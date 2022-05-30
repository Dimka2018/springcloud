package com.dimka.springcloud.mapper;

import com.dimka.springcloud.dto.FileResponse;
import com.dimka.springcloud.entity.File;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileToFileResponseMapper {

    public List<FileResponse> convert(List<File> files) {
        return files.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public FileResponse convert(File file) {
        return new FileResponse()
                .setId(file.getId())
                .setName(file.getName());
    }
}
