package com.nado.patientservice.service;

import com.nado.patientservice.dto.PatientRequestDTO;
import com.nado.patientservice.dto.PatientResponseDTO;
import com.nado.patientservice.exception.custom.EmailAlreadyExistsException;
import com.nado.patientservice.exception.custom.PatientNotFoundException;
import com.nado.patientservice.mapper.PatientMapper;
import com.nado.patientservice.model.Patient;
import com.nado.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient with this email already exists " + patientRequestDTO.getEmail());
        }

        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(int id, PatientRequestDTO patientRequestDTO){
        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id)){
            throw new EmailAlreadyExistsException("A patient with this email already exists " + patientRequestDTO.getEmail());
        }

        Patient exsistingPatient= patientRepository.findById(id).orElseThrow(
                ()->new PatientNotFoundException("Patient not found with ID: " + id));

        //Patient patientUpdate= PatientMapper.toModel(patientRequestDTO);
        exsistingPatient.setFullName(patientRequestDTO.getFullName());
        exsistingPatient.setEmail(patientRequestDTO.getEmail());
        exsistingPatient.setAddress(patientRequestDTO.getAddress());
        exsistingPatient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getBarthDate()));

        Patient update = patientRepository.save(exsistingPatient);
        return PatientMapper.toDTO(update);


    }

    public void deletePatient(int id) {

        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException("The patient not found with id "+id));

        patientRepository.delete(patient);
    }

}
