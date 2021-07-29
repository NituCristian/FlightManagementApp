package de.msg.flight.security.user;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import de.msg.flight.persistence.model.User;
import de.msg.flight.persistence.repository.UserRepository;

@Service
public class FlightUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FlightUserDetailsService.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) {

		final User user = this.userRepository.findUserByUsername(username);

		if (user != null) {
			final List<SimpleGrantedAuthority> authorities = Arrays
					.asList(new SimpleGrantedAuthority(user.getRole().getRoleName()));

			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					authorities);
		}

		LOGGER.info("user null from flight user details service");

		return null;
	}

}
