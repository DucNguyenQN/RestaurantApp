package com.example.btlproject;

import com.example.btlproject.models.LoginResponse;

import java.io.Serializable;
import java.util.List;

public class UserResponse {
    private int statusCode;
    private boolean isSuccess;
    private List<String> errorMessages;
    private LoginResponse.Result result;

    public class Result {
        private String id;
        private String userName;
        private String name;
        private String role;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public LoginResponse.Result getResult() {
        return result;
    }

    public void setResult(LoginResponse.Result result) {
        this.result = result;
    }
}
