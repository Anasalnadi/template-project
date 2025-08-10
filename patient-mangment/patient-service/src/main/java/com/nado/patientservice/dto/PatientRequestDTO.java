package com.nado.patientservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PatientRequestDTO {

  @NotBlank(message = "the full name is required ")
  @Size(max = 100, message = "full name cannot be exceed 100 characters")
  private String fullName;

  @NotBlank(message = "the email is required ")
  @Email(message = "Email should be valid")
  private String email;

  @NotBlank(message = "the address is required ")
  private String address;

  @NotBlank(message = "the barth date  is required")
  private String barthDate;

//    @NotNull(message = "the registered date  is required ")
//    private String registeredDate;


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBarthDate() {
        return barthDate;
    }

    public void setBarthDate(String barthDate) {
        this.barthDate = barthDate;
    }

}
