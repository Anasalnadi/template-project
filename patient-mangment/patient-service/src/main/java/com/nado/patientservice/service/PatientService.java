package com.nado.patientservice.service;

import com.nado.patientservice.dto.PatientRequestDTO;
import com.nado.patientservice.dto.PatientResponseDTO;
import com.nado.patientservice.mapper.PatientMapper;
import com.nado.patientservice.model.Patient;
import com.nado.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();

        return patients.stream()
                .map(PatientMapper::toDTO)
                .toList();

    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){

        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);
    }
}
