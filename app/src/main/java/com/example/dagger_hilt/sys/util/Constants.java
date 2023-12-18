package com.example.dagger_hilt.sys.util;

import com.example.dagger_hilt.BuildConfig;

public class Constants {
    public static final String API_KEY = BuildConfig.API_KEY;
    public static final String BASE_URL_IMAGES = BuildConfig.BASE_URL_IMAGES;
    public enum StatusResponse { SUCCESS, ERROR, LOADING, BLANK }
    public static final String USER_MEMORY = "user";
    public static final String REGISTERED_USER = "registeredUser";
    public static final String PASSWORD = "password";
}
