package com.nado.patientservice.mapper;

import com.nado.patientservice.dto.PatientRequestDTO;
import com.nado.patientservice.dto.PatientResponseDTO;
import com.nado.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {

    public static PatientResponseDTO toDTO(Patient patient){

        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(patient.getId());
        patientResponseDTO.setFullName(patient.getFullName());
        patientResponseDTO.setEmail(patient.getEmail());
        patientResponseDTO.setAddress(patient.getAddress());
        patientResponseDTO.setDateOfBirth(String.valueOf(patient.getDateOfBirth()));

        return patientResponseDTO;

    }

    public static Patient toModel(PatientRequestDTO patientRequestDTO){

        Patient patient = new Patient();
        patient.setFullName(patientRequestDTO.getFullName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getBarthDate()));
        patient.setRegisterDate(LocalDate.now());

        return patient;
    }
}
