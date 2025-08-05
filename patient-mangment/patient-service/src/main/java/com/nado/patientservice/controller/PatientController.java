package com.nado.patientservice.controller;

import com.nado.patientservice.dto.ApiResponse;
import com.nado.patientservice.dto.PatientRequestDTO;
import com.nado.patientservice.dto.PatientResponseDTO;
import com.nado.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PatientResponseDTO>>> getPatients(){
        List<PatientResponseDTO> responseDTO = patientService.getPatients();

        ApiResponse<List<PatientResponseDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), true, responseDTO);

        return ResponseEntity.ok().body(response);

    }

    @PostMapping
    public ResponseEntity<ApiResponse<PatientResponseDTO>> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO){

        PatientResponseDTO responseDTO = patientService.createPatient(patientRequestDTO);

        ApiResponse<PatientResponseDTO> response = new ApiResponse<>(HttpStatus.CREATED.value(),true,responseDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
