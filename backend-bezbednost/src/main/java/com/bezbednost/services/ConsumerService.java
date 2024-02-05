package com.bezbednost.services;

import com.bezbednost.models.Consumer;
import com.bezbednost.models.ConsumerDto;

public interface ConsumerService {
	
	Consumer registration(ConsumerDto userDto);
	
	String confirmationEmailToken(String token);
	
	Long getByEmail(String email);

}
