package com.example.dagger_hilt.data.datasource.web.models;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {
    @SerializedName("status_code")
    int statusCode;
    @SerializedName("status_message")
    String statusMessage;
    @SerializedName("success")
    String success;
}
