package com.bezbednost.servicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bezbednost.models.Accommodation;
import com.bezbednost.models.AccommodationDto;
import com.bezbednost.models.Consumer;
import com.bezbednost.repositories.AccommodationRepository;
import com.bezbednost.repositories.ConsumerRepository;
import com.bezbednost.services.AccommodationService;

@Service
public class AccommodationServiceImpl implements AccommodationService {
	
	@Autowired
	private AccommodationRepository accommodationRepository;
	
	@Autowired
	private ConsumerRepository consumerRepository;

	@Override
	public Accommodation createAccommodation(AccommodationDto accommodationDto, Long consumer_id) {
		Consumer consumer = consumerRepository.findById(consumer_id).get();
		if(consumer == null) {
			return null;
		}
		Accommodation accommodation = new Accommodation();
		accommodation.setRoomName(accommodationDto.getRoomName());
		accommodation.setRoomType(accommodationDto.getRoomType());
		accommodation.setNumberOfAdults(accommodationDto.getNumberOfAdults());
		accommodation.setNumberOfChild(accommodationDto.getNumberOfChild());
		accommodation.setConsumer(consumer);
		return accommodationRepository.save(accommodation);
	}

}
