package de.msg.flight.dto;

import java.util.Objects;

public class AirportDto {

	private Integer id;

	private String airportCode;

	private String address;

	private String city;

	private String name;

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getAirportCode() {
		return this.airportCode;
	}

	public void setAirportCode(final String airportCode) {
		this.airportCode = airportCode;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
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
		final AirportDto other = (AirportDto) obj;
		return Objects.equals(this.id, other.id);
	}

}
