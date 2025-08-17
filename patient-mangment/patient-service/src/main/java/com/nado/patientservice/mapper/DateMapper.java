package com.nado.patientservice.mapper;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LocalDate asLocalDate(String date) {
        if (date == null || date.isBlank()) {
            return null;
        }
        return LocalDate.parse(date, FORMATTER);
    }

    public String asString(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(FORMATTER);
    }
}
