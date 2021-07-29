package de.msg.flight.security.jwt;

import java.io.Serializable;

public class Claim implements Serializable {

	private static final long serialVersionUID = 1011719331250582021L;

	private String role;

	private Integer companyId;

	public Claim(final String role, final Integer companyId) {
		this.role = role;
		this.companyId = companyId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(final Integer companyId) {
		this.companyId = companyId;
	}

}
