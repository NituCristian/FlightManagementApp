package de.msg.flight.service.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.msg.flight.dto.UserDto;
import de.msg.flight.mapper.user.UserMapper;
import de.msg.flight.persistence.model.User;
import de.msg.flight.persistence.repository.UserRepository;

@Service
public class UserService implements IUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto getUserByUsername(final String username) {

		LOGGER.info("Retrieving user with userName={}", username);

		final User user = getUserByName(username);

		if (user == null) {
			LOGGER.warn("There is no user for userName={}", username);
		} else {
			LOGGER.info("User with userName={} retrieved", username);
		}

		return mapUserToUserDto(user);
	}

	@Override
	public UserDto addUser(final UserDto user) {

		LOGGER.info("Adding new  user with userName={}", user.getUsername());

		final User userToInsert = buildUserForInsert(user);

		LOGGER.info("User with userName={} was successfully created", user.getUsername());

		return mapUserToUserDto(saveUser(userToInsert));
	}

	private User getUserByName(final String username) {

		LOGGER.info("Retrieving user with userName={} from db", username);

		return this.userRepository.findUserByUsername(username);
	}

	private User saveUser(final User userToInsert) {

		LOGGER.info("Saving user with userName={} in db", userToInsert.getUsername());

		return this.userRepository.save(userToInsert);
	}

	private String encodePassword(final String password) {

		return this.passwordEncoder.encode(password);
	}

	private User buildUserForInsert(final UserDto user) {

		final User userToInsert = mapUserDtoToUser(user);

		userToInsert.setPassword(encodePassword(userToInsert.getPassword()));

		return userToInsert;
	}

	private UserDto mapUserToUserDto(final User user) {

		return UserMapper.MAPPER.mapUserToUserDto(user);
	}

	private User mapUserDtoToUser(final UserDto user) {

		return UserMapper.MAPPER.mapUserDtoToUser(user);
	}

}
