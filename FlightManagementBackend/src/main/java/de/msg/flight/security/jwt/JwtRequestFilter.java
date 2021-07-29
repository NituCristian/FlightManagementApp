package de.msg.flight.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import de.msg.flight.security.user.FlightUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private FlightUserDetailsService userDetailsService;

	@Autowired
	private JwtUtils jwtUtils;

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;

		if (isTokenHeaderValid(requestTokenHeader)) {
			jwtToken = requestTokenHeader.substring(7);
			username = getUsernameFromToken(jwtToken);
		} else {
			this.logger.warn("JWT Token does not begin with Bearer String");
		}

		handleAuthentication(request, username, jwtToken);

		response.addHeader("Access-Control-Expose-Headers", "Authorization");

		filterChain.doFilter(request, response);
	}

	private void handleAuthentication(final HttpServletRequest request, final String username, final String jwtToken) {

		if (isAuthenticationNeeded(username)) {
			handleAuthenticationToken(request, username, jwtToken);
		}
	}

	private void handleAuthenticationToken(final HttpServletRequest request, final String username,
			final String jwtToken) {

		final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

		if (isTokenValidationSuccessful(jwtToken, userDetails)) {
			final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());

			usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
	}

	private String getUsernameFromToken(final String jwtToken) {
		String username = null;

		try {
			username = this.jwtUtils.getUsernameFromToken(jwtToken);
		} catch (final IllegalArgumentException e) {
			this.logger.error("Unable to get JWT Token");
		} catch (final ExpiredJwtException e) {
			this.logger.error("JWT Token has expired");
		}

		return username;
	}

	private boolean isAuthenticationNeeded(final String username) {
		return username != null && SecurityContextHolder.getContext().getAuthentication() == null;
	}

	private boolean isTokenValidationSuccessful(final String jwtToken, final UserDetails userDetails) {
		return Boolean.TRUE.equals(this.jwtUtils.validateToken(jwtToken, userDetails))
				&& Boolean.TRUE.equals(this.jwtUtils.validateUserRole(jwtToken, userDetails));
	}

	private boolean isTokenHeaderValid(final String requestTokenHeader) {
		return requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ");
	}

}
