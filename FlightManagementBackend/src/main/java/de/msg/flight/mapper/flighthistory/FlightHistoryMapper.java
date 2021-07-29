package de.msg.flight.mapper.flighthistory;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import de.msg.flight.dto.FlightHistoryDto;
import de.msg.flight.mapper.flight.FlightMapper;
import de.msg.flight.mapper.user.UserMapper;
import de.msg.flight.persistence.model.FlightHistory;

@Mapper(uses = { UserMapper.class, FlightMapper.class })
public interface FlightHistoryMapper {

	FlightHistoryMapper MAPPER = Mappers.getMapper(FlightHistoryMapper.class);

	FlightHistory mapFlightHistoryDtoToFlightHistory(FlightHistoryDto flightHistoryDto);

	@InheritInverseConfiguration
	FlightHistoryDto mapFlightHistoryToFlightHistoryDto(FlightHistory flightHistory);
}
