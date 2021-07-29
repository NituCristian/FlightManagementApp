package de.msg.flight.dto;

import java.time.LocalTime;
import java.util.Objects;

public class FlightTemplateDto {

	private Integer id;

	private String name;

	private LocalTime arrivalTime;

	private LocalTime departureTime;

	private AirportDto departureAirport;

	private AirportDto arrivalAirport;

	private PlaneDto plane;

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

	public LocalTime getArrivalTime() {
		return this.arrivalTime;
	}

	public void setArrivalTime(final LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public LocalTime getDepartureTime() {
		return this.departureTime;
	}

	public void setDepartureTime(final LocalTime departureTime) {
		this.departureTime = departureTime;
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

	public PlaneDto getPlane() {
		return this.plane;
	}

	public void setPlane(final PlaneDto plane) {
		this.plane = plane;
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
		final FlightTemplateDto other = (FlightTemplateDto) obj;
		return Objects.equals(this.id, other.id);
	}

}
