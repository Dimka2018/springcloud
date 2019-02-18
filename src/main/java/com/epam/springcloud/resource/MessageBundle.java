package com.epam.springcloud.resource;

import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

@Component
public class MessageBundle {

    public static final String INVALID_USER_MESSAGE = "INVALID_USER_MESSAGE";
    public static final String SERVER_PROBLEM_MESSAGE = "SERVER_PROBLEM_MESSAGE";
    public static final String USER_EXISTS_MESSAGE = "USER_EXISTS_MESSAGE";
    public static final String FILE_EXISTS_MESSAGE = "FILE_EXISTS_MESSAGE";
    public static final String INVALID_DATA_MESSAGE = "INVALID_DATA_MESSAGE";

    public static final String EMPTY_LOGIN_MESSAGE = "EMPTY_LOGIN";
    public static final String EMPTY_PASSWORD_MESSAGE = "EMPTY_PASSWORD";
    public static final String INVALID_LOGIN_SIZE_MESSAGE = "INVALID_LOGIN_SIZE";
    public static final String INVALID_PASSWORD_SIZE_MESSAGE = "INVALID_PASSWORD_SIZE";

    public static final String EMPTY_FILE_ID_MESSAGE = "EMPTY_FILE_ID";
    public static final String EMPTY_FILE_NAME_MESSAGE = "EMPTY_FILE_NAME";
    public static final String INVALID_FILE_NAME_SIZE_MESSAGE = "INVALID_FILE_NAME_SIZE";
    public static final String EMPTY_FILE_CONTENT_MESSAGE = "EMPTY_FILE_CONTENT";
    public static final String INVALID_FILE_NAME_MESSAGE = "INVALID_FILE_NAME";

    private static final String RESOURCE_NAME = "userPhrases";

    public String getMessage(String messageType, Locale locale) {
	return ResourceBundle.getBundle(RESOURCE_NAME, locale)
		.getString(messageType);
    }

    public String getFileExistsMessage(Locale locale) {
	return getMessage(FILE_EXISTS_MESSAGE, locale);
    }

    public String getUserExistsMessage(Locale locale) {
	return getMessage(USER_EXISTS_MESSAGE, locale);
    }

    public String getServerProblemMessage(Locale locale) {
	return getMessage(SERVER_PROBLEM_MESSAGE, locale);
    }

    public String getInvalidUserMessage(Locale locale) {
	return getMessage(INVALID_USER_MESSAGE, locale);
    }

    public String getInvalidDataMessage(Locale locale) {
	return getMessage(INVALID_DATA_MESSAGE, locale);
    }

}
