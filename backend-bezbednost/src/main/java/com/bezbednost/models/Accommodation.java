package com.bezbednost.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "accommodation")
public class Accommodation {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column(name = "room_name", nullable=false)
	@NotBlank
	@Size(max = 55, min = 3, message = "Size of room name is not correct")
    private String roomName;
	
	@Column(name = "room_type", nullable=false)
	@NotBlank
	@Size(max = 55, min = 3, message = "Size of room type is not correct")
    private String roomType;
	
	@Column(name = "number_of_adults", nullable = false)
	//@Size(min = 1, message = "Size of number of adults is not correct")
	@Min(value = 1, message = "Size of number of adults is not correct")
    private int numberOfAdults;
	
	@ManyToOne
	@JoinColumn(name = "consumer_id", nullable = false)
	private Consumer consumer;
	
	@Column(name = "number_of_child", nullable = false)
	//@Size(min = 0, message = "Size of number of child is not correct")
	@Min(value = 0, message = "Size of number of child is not correct")
    private int numberOfChild;

}
