package com.epam.springcloud.config;

import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.epam.springcloud.intercepter.AutentificationIntercepter;
import com.epam.springcloud.intercepter.LoggingInterceptor;
import com.epam.springcloud.mapper.Mapper;

/**
 * 
 * @author Dmitry Plotnikov Servlet-context.xml analog javaconfig
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.epam.springcloud")
public class WebConfig implements WebMvcConfigurer {

    private static final String MESSAGE_SOURCE_NAME = "/WEB-INF/classes/userPhrases";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String[] UNAUTHORIZED_PAGES = {"/", "/welcome.html", "/user", "/user/session"};
    private static final String FORWARD_COMMAND = "forward:/welcome.html";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor());
        registry.addInterceptor(autentificationIntercepter())
                .addPathPatterns("/*").excludePathPatterns(UNAUTHORIZED_PAGES);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName(FORWARD_COMMAND);

    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/", ".html");
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    @Bean
    public MultipartFilter multipartFilter() {
        MultipartFilter multipartFilter = new MultipartFilter();
        multipartFilter.setMultipartResolverBeanName("multipartResolver");
        return multipartFilter;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames(MESSAGE_SOURCE_NAME);
        messageSource.setDefaultEncoding(DEFAULT_ENCODING);
        return messageSource;
    }

    @Bean
    public Mapper modelMapper() {
        Mapper mapper = new Mapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true).setSkipNullEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE);
        return mapper;
    }
    
    @Bean
    public LoggingInterceptor loggingInterceptor() {
        return new LoggingInterceptor();
    }
    
    @Bean
    public AutentificationIntercepter autentificationIntercepter() {
        return new AutentificationIntercepter();
    }

}
