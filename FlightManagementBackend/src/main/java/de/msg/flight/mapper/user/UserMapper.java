package de.msg.flight.mapper.user;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import de.msg.flight.dto.UserDto;
import de.msg.flight.mapper.company.CompanyMapper;
import de.msg.flight.persistence.model.User;

@Mapper(uses = { CompanyMapper.class })
public interface UserMapper {

	UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

	@Mappings({ @Mapping(source = "userDto.role", target = "role") })
	User mapUserDtoToUser(UserDto userDto);

	@InheritInverseConfiguration
	UserDto mapUserToUserDto(User user);
}
