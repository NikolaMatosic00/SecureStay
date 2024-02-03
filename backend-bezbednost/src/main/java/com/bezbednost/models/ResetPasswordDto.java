package com.bezbednost.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDto {
	
	@NotBlank(message = "Email is required")
	@Size(max = 35, min = 3, message = "Size of email is not correct")
    @Email
    private String email;
   
	@NotBlank(message = "Password is required")
	@Size(max = 25, min = 3, message = "Size of password is not correct")
    private String password;

}
