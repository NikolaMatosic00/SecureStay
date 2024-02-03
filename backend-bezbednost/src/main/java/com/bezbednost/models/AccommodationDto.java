package com.bezbednost.models;

import jakarta.validation.constraints.Min;
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
public class AccommodationDto {
	
	@NotBlank
	@Size(max = 55, min = 3, message = "Size of room name is not correct")
    private String roomName;
	
	@NotBlank
	@Size(max = 55, min = 3, message = "Size of room type is not correct")
    private String roomType;
	
	@Min(value = 1, message = "Minimum number is 1")
    private int numberOfAdults;
	
	@Min(value = 0, message = "Minimum number is 0")
    private int numberOfChild;

}
