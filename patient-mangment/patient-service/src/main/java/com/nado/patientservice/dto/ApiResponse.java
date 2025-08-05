package com.nado.patientservice.dto;

public class ApiResponse<T> {

    private int status;
    private boolean success;
    private T data;

    public ApiResponse(int status, boolean success, T data) {
        this.status = status;
        this.success = success;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
