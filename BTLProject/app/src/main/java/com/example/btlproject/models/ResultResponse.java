package com.example.btlproject.models;

import java.util.List;

public class ResultResponse {
    private int statusCode;
    private boolean isSuccess;
    private List<String> errorMessages;
    private LoginResponse.Result result;
    public int getStatusCode() {
        return statusCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }
    public LoginResponse.Result getResult() {
        return result;
    }

    public class Result {

    }
}
