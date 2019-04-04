package com.epam.springcloud.resource;

import org.springframework.stereotype.Component;

@Component
public class MessageKey {

    public static final String INVALID_USER_MESSAGE = "INVALID_USER_MESSAGE";
    public static final String SERVER_PROBLEM_MESSAGE = "SERVER_PROBLEM_MESSAGE";
    public static final String USER_EXISTS_MESSAGE = "USER_EXISTS_MESSAGE";
    public static final String FILE_EXISTS_MESSAGE = "FILE_EXISTS_MESSAGE";
    public static final String INVALID_DATA_MESSAGE = "INVALID_DATA_MESSAGE";

    public static final String EMPTY_LOGIN_MESSAGE = "EMPTY_LOGIN";
    public static final String EMPTY_PASSWORD_MESSAGE = "EMPTY_PASSWORD"; // NOSONAR
    public static final String INVALID_LOGIN_SIZE_MESSAGE = "INVALID_LOGIN_SIZE";
    public static final String INVALID_PASSWORD_SIZE_MESSAGE = "INVALID_PASSWORD_SIZE"; // NOSONAR

    public static final String EMPTY_FILE_ID_MESSAGE = "EMPTY_FILE_ID";
    public static final String EMPTY_FILE_NAME_MESSAGE = "EMPTY_FILE_NAME";
    public static final String INVALID_FILE_NAME_SIZE_MESSAGE = "INVALID_FILE_NAME_SIZE";
    public static final String EMPTY_FILE_CONTENT_MESSAGE = "EMPTY_FILE_CONTENT";
    public static final String INVALID_FILE_NAME_MESSAGE = "INVALID_FILE_NAME";

    private MessageKey() {

    }

}
