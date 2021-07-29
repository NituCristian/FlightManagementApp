package de.msg.flight.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ItineraryDto {

	private Integer id;

	private String name;

	private AirportDto departureAirport;

	private AirportDto arrivalAirport;

	private Set<FlightDto> flights;

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

	public AirportDto getDepartureAirport() {
		return this.departureAirport;
	}

	public void setDepartureAirport(final AirportDto departureAirport) {
		this.departureAirport = departureAirport;
	}

	public AirportDto getArrivalAirport() {
		return this.arrivalAirport;
	}

	public void setArrivalAirport(final AirportDto arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public Set<FlightDto> getFlights() {

		if (null == this.flights) {
			this.flights = new HashSet<>();
		}

		return this.flights;
	}

	public void setFlights(final Set<FlightDto> flights) {
		this.flights = flights;
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
		final ItineraryDto other = (ItineraryDto) obj;
		return Objects.equals(this.id, other.id);
	}

}
