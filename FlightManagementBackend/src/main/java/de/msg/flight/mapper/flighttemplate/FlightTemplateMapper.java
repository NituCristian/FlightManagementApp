package de.msg.flight.mapper.flighttemplate;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import de.msg.flight.dto.FlightTemplateDto;
import de.msg.flight.mapper.airport.AirportMapper;
import de.msg.flight.mapper.plane.PlaneMapper;
import de.msg.flight.persistence.model.FlightTemplate;

@Mapper(uses = { AirportMapper.class, PlaneMapper.class })
public interface FlightTemplateMapper {

	FlightTemplateMapper MAPPER = Mappers.getMapper(FlightTemplateMapper.class);

	FlightTemplate mapFlightTemplateDtoToFlightTemplate(FlightTemplateDto flightTemplateDto);

	FlightTemplateDto mapFlightTemplateToFlightTemplateDto(FlightTemplate flightTemplate);
}
