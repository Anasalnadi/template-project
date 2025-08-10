package com.nado.patientservice.controller;

import com.nado.patientservice.dto.ApiResponse;
import com.nado.patientservice.dto.PatientRequestDTO;
import com.nado.patientservice.dto.PatientResponseDTO;
import com.nado.patientservice.model.Patient;
import com.nado.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patient",description = "Rest API for manging Patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "Get Patients",description = "Get API for get all patients")
    public ResponseEntity<ApiResponse<List<PatientResponseDTO>>> getPatients(){
        List<PatientResponseDTO> responseDTO = patientService.getPatients();

        ApiResponse<List<PatientResponseDTO>> response = new ApiResponse<>(HttpStatus.OK.value(), true, responseDTO);

        return ResponseEntity.ok().body(response);

    }

    @PostMapping
    @Operation(summary = "Create Patient")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO){

        PatientResponseDTO responseDTO = patientService.createPatient(patientRequestDTO);

        ApiResponse<PatientResponseDTO> response = new ApiResponse<>(HttpStatus.CREATED.value(),true,responseDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Patient")
    public ResponseEntity<ApiResponse<PatientResponseDTO>> updatePatient(@PathVariable int id,
                                                                         @Validated({Default.class}) @RequestBody PatientRequestDTO patientRequestDTO){

        PatientResponseDTO responseDTO = patientService.updatePatient(id,patientRequestDTO);

        ApiResponse<PatientResponseDTO> response = new ApiResponse<>(HttpStatus.OK.value(),true,responseDTO);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Patient")
    public ResponseEntity deletePatient(@PathVariable int id){

        patientService.deletePatient(id);

        return ResponseEntity.noContent().build();
    }

}
