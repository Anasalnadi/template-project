package com.nado.patientservice.service;

import com.nado.patientservice.constant.ErrorMessage;
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

    public PatientResponseDTO getPatientById(int id){

        Patient patient= patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException(String.format(ErrorMessage.PATIENT_NOT_FOUND_BY_ID,id)));

        return PatientMapper.toDTO(patient);
    }

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();

        return patients.stream()
                .map(PatientMapper::toDTO)
                .toList();

    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){

        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException(String.format(ErrorMessage.EMAIL_ALREADY_EXISTS,patientRequestDTO.getEmail()));
        }

        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(int id, PatientRequestDTO patientRequestDTO){
        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id)){
            throw new EmailAlreadyExistsException(String.format(ErrorMessage.EMAIL_ALREADY_EXISTS,patientRequestDTO.getEmail()));
        }

        Patient exsistingPatient= patientRepository.findById(id).orElseThrow(
                ()->new PatientNotFoundException(String.format(ErrorMessage.PATIENT_NOT_FOUND_BY_ID,id)));

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
                () -> new PatientNotFoundException(String.format(ErrorMessage.PATIENT_NOT_FOUND_BY_ID,id)));

        patientRepository.delete(patient);
    }

}
