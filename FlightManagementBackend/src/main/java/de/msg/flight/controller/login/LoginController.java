package de.msg.flight.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.msg.flight.dto.UserCredentialsDto;
import de.msg.flight.dto.UserDto;
import de.msg.flight.security.jwt.JwtUtils;
import de.msg.flight.service.ServiceException;
import de.msg.flight.service.user.IUserService;

@RequestMapping("/authenticate")
@RestController
@CrossOrigin
public class LoginController {

	@Autowired
	private IUserService userService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping
	public ResponseEntity<UserDto> getUserByUsernameAndPassword(@RequestBody final UserCredentialsDto userCredentials)
			throws ServiceException {

		final UserDto user = this.userService.getUserByUsername(userCredentials.getUsername());
		if (user != null) {

			final boolean isMatch = this.passwordEncoder.matches(userCredentials.getPassword(), user.getPassword());
			if (isMatch) {
				final String jwtToken = this.jwtUtils.generateToken(user);
				final HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.add("Authorization", jwtToken);
				return ResponseEntity.ok().headers(httpHeaders).body(user);
			}
		}

		return null;
	}

}
