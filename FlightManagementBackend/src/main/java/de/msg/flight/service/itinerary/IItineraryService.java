package de.msg.flight.service.itinerary;

import java.util.List;

import de.msg.flight.dto.ItineraryDto;

public interface IItineraryService {

	public ItineraryDto getItineraryById(Integer itineraryId);

	public List<ItineraryDto> getAllItineraries();

	public ItineraryDto insertItinerary(ItineraryDto itineraryDto);

	public void removeItinerary(Integer itineraryId);

	public ItineraryDto updateItinerary(ItineraryDto itineraryDto);

}
