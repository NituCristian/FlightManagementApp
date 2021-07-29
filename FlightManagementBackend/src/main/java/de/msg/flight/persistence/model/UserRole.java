package de.msg.flight.persistence.model;

public enum UserRole {

	ROLE_ADMIN("ROLE_ADMIN"),

	ROLE_CREW("ROLE_CREW"),

	ROLE_FLIGHT_MANAGER("ROLE_FLIGHT_MANAGER"),

	ROLE_COMPANY_MANAGER("ROLE_COMPANY_MANAGER");

	private String role;

	private UserRole(final String role) {
		this.role = role;
	}

	public String getRoleName() {
		return this.role;
	}

}
