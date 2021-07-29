package de.msg.flight.mapper.itinerary;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import de.msg.flight.dto.ItineraryDto;
import de.msg.flight.mapper.airport.AirportMapper;
import de.msg.flight.mapper.flight.FlightMapper;
import de.msg.flight.persistence.model.Itinerary;

@Mapper(uses = { FlightMapper.class, AirportMapper.class })
public interface ItineraryMapper {

	ItineraryMapper MAPPER = Mappers.getMapper(ItineraryMapper.class);

	Itinerary mapItineraryDtoToItinerary(ItineraryDto itineraryDto);

	@InheritInverseConfiguration
	ItineraryDto mapItineraryToItineraryDto(Itinerary itinerary);
}
