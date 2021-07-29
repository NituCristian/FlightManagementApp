package de.msg.flight.mapper.flight;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import de.msg.flight.dto.FlightDto;
import de.msg.flight.mapper.airport.AirportMapper;
import de.msg.flight.mapper.company.CompanyMapper;
import de.msg.flight.mapper.plane.PlaneMapper;
import de.msg.flight.mapper.user.UserMapper;
import de.msg.flight.persistence.model.Flight;

@Mapper(uses = { CompanyMapper.class, PlaneMapper.class, UserMapper.class, AirportMapper.class })
public interface FlightMapper {

	FlightMapper MAPPER = Mappers.getMapper(FlightMapper.class);

	Flight mapFlightDtoToFlight(FlightDto flightDto);

	@InheritInverseConfiguration
	FlightDto mapFlightToFlightDto(Flight flight);
}
