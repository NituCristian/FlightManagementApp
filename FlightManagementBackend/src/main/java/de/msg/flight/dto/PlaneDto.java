package de.msg.flight.dto;

import java.util.Objects;

public class PlaneDto {

	private Integer id;

	private String name;

	private String code;

	private String model;

	private Integer numberOfPassengers;

	private CompanyDto company;

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(final String model) {
		this.model = model;
	}

	public Integer getNumberOfPassengers() {
		return this.numberOfPassengers;
	}

	public void setNumberOfPassengers(final Integer numberOfPassengers) {
		this.numberOfPassengers = numberOfPassengers;
	}

	public CompanyDto getCompany() {
		return this.company;
	}

	public void setCompany(final CompanyDto company) {
		this.company = company;
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
		final PlaneDto other = (PlaneDto) obj;
		return Objects.equals(this.id, other.id);
	}

}
