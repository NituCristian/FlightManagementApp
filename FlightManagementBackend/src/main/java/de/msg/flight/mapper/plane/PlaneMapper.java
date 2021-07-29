package de.msg.flight.mapper.plane;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import de.msg.flight.dto.PlaneDto;
import de.msg.flight.mapper.company.CompanyMapper;
import de.msg.flight.persistence.model.Plane;

@Mapper(uses = { CompanyMapper.class })
public interface PlaneMapper {

	PlaneMapper MAPPER = Mappers.getMapper(PlaneMapper.class);

	Plane mapPlaneDtoToPlane(PlaneDto planeDto);

	@InheritInverseConfiguration
	PlaneDto mapPlaneToPlaneDto(Plane plane);
}
