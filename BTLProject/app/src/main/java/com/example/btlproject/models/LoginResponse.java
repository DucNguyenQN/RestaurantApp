package com.example.btlproject.models;

import java.io.Serializable;
import java.util.List;

public class LoginResponse implements Serializable {
    private int statusCode;
    private boolean isSuccess;
    private List<String> errorMessages;
    private Result result;
    public int getStatusCode() {
        return statusCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public Result getResult() {
        return result;
    }

    public class Result implements Serializable{
        private User user;
        private String token;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
    public class User implements Serializable{
        private String id;
        private String userName;
        private String name;

        // Getters and Setters
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
    }
}
