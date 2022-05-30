package com.dimka.springcloud.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.RedisHash;

@Accessors(chain = true)
@Data
@RedisHash("FILE_CONTENT")
public class FileContent {

    private String id;
    private byte[] content;
}
