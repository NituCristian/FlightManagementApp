package de.msg.flight.dto;

import java.util.Objects;

public class UserCredentialsDto {

	private String username;

	private String password;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.password, this.username);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final UserCredentialsDto other = (UserCredentialsDto) obj;
		return Objects.equals(this.password, other.password) && Objects.equals(this.username, other.username);
	}

}
