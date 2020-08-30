package com.dimka.springcloud.config;

import com.dimka.springcloud.mapper.Mapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@org.springframework.context.annotation.Configuration
public class Config implements WebMvcConfigurer {

    @Bean
    public Mapper modelMapper() {
        Mapper mapper = new Mapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true).setSkipNullEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        return mapper;
    }
}
