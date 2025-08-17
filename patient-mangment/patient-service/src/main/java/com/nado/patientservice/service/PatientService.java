package com.nado.patientservice.service;

import com.nado.patientservice.constant.ErrorMessage;
import com.nado.patientservice.dto.PatientRequestDTO;
import com.nado.patientservice.dto.PatientResponseDTO;
import com.nado.patientservice.exception.custom.EmailAlreadyExistsException;
import com.nado.patientservice.exception.custom.PatientNotFoundException;
import com.nado.patientservice.grpc.BillingServiceGrpcClient;
import com.nado.patientservice.kafka.KafkaProducer;
import com.nado.patientservice.mapper.PatientMapperInterface;
import com.nado.patientservice.model.Patient;
import com.nado.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapperInterface patientMapper;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public PatientService(PatientRepository patientRepository, PatientMapperInterface patientMapper, BillingServiceGrpcClient billingServiceGrpcClient, KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.patientMapper = patientMapper;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public PatientResponseDTO getPatientById(int id){

        Patient patient= patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException(String.format(ErrorMessage.PATIENT_NOT_FOUND_BY_ID,id)));

        return patientMapper.toDTO(patient);
    }

    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();

        return patients.stream()
                .map(patientMapper::toDTO)
                .toList();

    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){

        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException(String.format(ErrorMessage.EMAIL_ALREADY_EXISTS,patientRequestDTO.getEmail()));
        }

        Patient newPatient = patientRepository.save(patientMapper.toModel(patientRequestDTO));

        billingServiceGrpcClient.createBillingAccount(String.valueOf(newPatient.getId()),newPatient.getFullName(),newPatient.getEmail());

        kafkaProducer.sendEvent(newPatient);

        return patientMapper.toDTO(newPatient);
    }

    public PatientResponseDTO updatePatient(int id, PatientRequestDTO patientRequestDTO){

        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(),id)){
            throw new EmailAlreadyExistsException(String.format(ErrorMessage.EMAIL_ALREADY_EXISTS,patientRequestDTO.getEmail()));
        }

        Patient exsistingPatient= patientRepository.findById(id).orElseThrow(
                ()->new PatientNotFoundException(String.format(ErrorMessage.PATIENT_NOT_FOUND_BY_ID,id)));

        // **Update dynamically** only non-null fields
        patientMapper.updatePatientFromDTO(patientRequestDTO, exsistingPatient);


        Patient update = patientRepository.save(exsistingPatient);
        return patientMapper.toDTO(update);
    }

    public void deletePatient(int id) {

        Patient patient = patientRepository.findById(id).orElseThrow(
                () -> new PatientNotFoundException(String.format(ErrorMessage.PATIENT_NOT_FOUND_BY_ID,id)));

        patientRepository.delete(patient);
    }

}
