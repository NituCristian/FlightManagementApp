package de.msg.flight.dto;

import java.util.Objects;

public class UserDto {

	private Integer id;

	private Boolean activated;

	private String firstName;

	private String lastName;

	private String role;

	private String email;

	private String password;

	private String username;

	private CompanyDto company;

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Boolean getActivated() {
		return this.activated;
	}

	public void setActivated(final Boolean activated) {
		this.activated = activated;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public CompanyDto getCompany() {
		return this.company;
	}

	public void setCompany(final CompanyDto company) {
		this.company = company;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
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
		final UserDto other = (UserDto) obj;
		return Objects.equals(this.id, other.id);
	}

}
