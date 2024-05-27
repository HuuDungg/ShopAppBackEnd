package com.example.shopAppSpringBoot.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotBlank
    @JsonProperty("fullname")
    private String fullname;

    @NotBlank
    @JsonProperty("phone_number")
    private String phoneNumber;

    private String address;

    @NotBlank
    private String password;

    @JsonProperty("retype_password")
    @NotBlank
    private String retypePassword;

    @JsonProperty("date_of_birth")
    private Date datOfBirth;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;

    @JsonProperty("role_id")
    @NotBlank
    private Long roleId;
}
