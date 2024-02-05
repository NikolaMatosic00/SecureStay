package com.bezbednost.services;

import com.bezbednost.models.Accommodation;
import com.bezbednost.models.AccommodationDto;

public interface AccommodationService {

	Accommodation createAccommodation(AccommodationDto accommodationDto, Long consumer_id);
}
