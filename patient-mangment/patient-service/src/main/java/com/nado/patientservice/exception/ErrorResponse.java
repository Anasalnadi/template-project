package com.nado.patientservice.exception;

import java.util.Map;

public class ErrorResponse {
    private int status;
    private boolean success;
    private String message;
    private Map<String, String> errors;

    public ErrorResponse(int status, boolean success, String message, Map<String, String> errors) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
