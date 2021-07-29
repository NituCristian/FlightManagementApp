package de.msg.flight.mapper.airport;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import de.msg.flight.dto.AirportDto;
import de.msg.flight.persistence.model.Airport;

@Mapper
public interface AirportMapper {

	AirportMapper MAPPER = Mappers.getMapper(AirportMapper.class);

	Airport mapAirportDtoToAirport(AirportDto airportDto);

	@InheritInverseConfiguration
	AirportDto mapAirportToAirportDto(Airport airport);
}
