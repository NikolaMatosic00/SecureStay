package com.bezbednost.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "consumer")
public class Consumer {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column(name = "firstName", nullable=false)
	@NotBlank
	@Size(max = 55, min = 3, message = "Size of first name is not correct")
    private String firstName;
	
	@Column(name = "lastName", nullable=false)
	@NotBlank
	@Size(max = 55, min = 3, message = "Size of last name is not correct")
    private String lastName;
	
	@Column(name = "email", unique = true, nullable=false)
	@NotBlank
	@Size(max = 35, min = 3, message = "Size of email is not correct")
    @Email
    private String email;
	
	@Column(name = "password", nullable=false)
	@NotBlank
	@Size(min = 3, message = "Size of password is not correct")
    private String password;
	
	@Column(name = "status",nullable = false)
    private boolean status;
	
	@Column(name = "role", nullable = false)
	@Size(max = 15, min = 3, message = "Size of role is not correct")
	private String role;

}
