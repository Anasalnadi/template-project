package com.nado.patientservice.mapper;

import com.nado.patientservice.dto.PatientRequestDTO;
import com.nado.patientservice.dto.PatientResponseDTO;
import com.nado.patientservice.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        uses = DateMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PatientMapperInterface {

    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    PatientResponseDTO toDTO(Patient patient);

    @Mapping(target = "dateOfBirth", source = "barthDate")
    @Mapping(target = "registerDate", expression = "java(java.time.LocalDate.now())")
    Patient toModel(PatientRequestDTO dto); // was toEntity

    @Mapping(target = "dateOfBirth", source = "barthDate")
    void updatePatientFromDTO(PatientRequestDTO dto, @MappingTarget Patient patient); // match exact case
}


