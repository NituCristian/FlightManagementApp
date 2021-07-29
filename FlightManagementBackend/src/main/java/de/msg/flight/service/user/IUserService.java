package de.msg.flight.service.user;

import de.msg.flight.dto.UserDto;

public interface IUserService {

	UserDto getUserByUsername(String username);

	UserDto addUser(UserDto user);
}
