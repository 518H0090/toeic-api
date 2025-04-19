package com.toeic.auth.application.dto;

import com.toeic.auth.domain.enums.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddUserDto {

    @NotBlank(message = "username must not be empty")
    @Length(max = 30, message = "length of username must be less than 30")
    private String username;

    @NotBlank(message = "text must not be empty")
    private String password;

    @NotBlank(message = "email must not be empty")
    @Length(max = 50, message = "length of email must be less than 50")
    private String email;

    @NotBlank(message = "firstname must not be empty")
    @Length(max = 30, message = "length of firstname must be less than 30")
    private String firstname;

    @NotBlank(message = "lastname must not be empty")
    @Length(max = 30, message = "length of lastname must be less than 30")
    private String lastname;

    @Enumerated(EnumType.STRING)
    private Role role;
}
