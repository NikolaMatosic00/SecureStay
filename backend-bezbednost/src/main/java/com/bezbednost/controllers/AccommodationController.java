package com.bezbednost.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bezbednost.models.Accommodation;
import com.bezbednost.models.AccommodationDto;
import com.bezbednost.services.AccommodationService;
import com.bezbednost.services.ConsumerService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class AccommodationController {
	
	@Autowired
	private AccommodationService accommodationService;
	
	@Autowired
	private ConsumerService consumerService;
	
	@PreAuthorize("hasAnyRole('REGISTRED')")
	@PostMapping("/accommodation")
	public ResponseEntity<Accommodation> createAccommodation(@Validated @RequestBody AccommodationDto accommodationDto, BindingResult errors, HttpServletRequest request) {
		if (errors.hasErrors()) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		String authorizationHeaderValue = request.getHeader("Authorization");
    	Long consumer_id = null;
    	if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer")) {
    	      String token = authorizationHeaderValue.substring(7, authorizationHeaderValue.length());
    	      try {
	    		DecodedJWT jwt = JWT.decode(token);
	        	consumer_id = consumerService.getByEmail(jwt.getSubject());
    	      } catch(Exception e) {
    	    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	      }
    	}
    	if(consumer_id == null) {
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	}
		Accommodation accommodation = accommodationService.createAccommodation(accommodationDto, consumer_id);
		if(accommodation == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else
      	    return new ResponseEntity<>(HttpStatus.OK);
		
	}

}
