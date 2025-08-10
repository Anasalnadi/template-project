package com.nado.patientservice.constant;

public final class ErrorMessage {

    private ErrorMessage() {
    }

    public static final String PATIENT_NOT_FOUND_BY_ID = "Patient with id %d not found";
    public static final String EMAIL_ALREADY_EXISTS = "A patient with this email already exists: %s";
}
