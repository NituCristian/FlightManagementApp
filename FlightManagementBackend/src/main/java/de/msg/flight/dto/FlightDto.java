package de.msg.flight.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class FlightDto {

	private Integer id;

	private LocalDate arrivalTime;

	private LocalDate departureTime;

	private CompanyDto company;

	private PlaneDto plane;

	private Set<UserDto> users;

	private AirportDto departureAirport;

	private AirportDto arrivalAirport;

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public LocalDate getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(final LocalDate arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public LocalDate getDepartureTime() {
		return this.departureTime;
	}

	public void setDepartureTime(final LocalDate departureTime) {
		this.departureTime = departureTime;
	}

	public CompanyDto getCompany() {
		return this.company;
	}

	public void setCompany(final CompanyDto company) {
		this.company = company;
	}

	public PlaneDto getPlane() {
		return this.plane;
	}

	public void setPlane(final PlaneDto plane) {
		this.plane = plane;
	}

	public Set<UserDto> getUsers() {

		if (null == this.users) {
			this.users = new HashSet<>();
		}

		return this.users;
	}

	public void setUsers(final Set<UserDto> users) {
		this.users = users;
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
		final FlightDto other = (FlightDto) obj;
		return Objects.equals(this.id, other.id);
	}

}
