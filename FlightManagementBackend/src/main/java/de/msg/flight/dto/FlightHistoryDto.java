package de.msg.flight.dto;

import java.time.LocalDate;
import java.util.Objects;

public class FlightHistoryDto {

	private Integer id;

	private LocalDate previousDepartureTime;

	private LocalDate previousArrivalTime;

	private LocalDate newDepartureTime;

	private LocalDate newArrivalTime;

	private String operation;

	private LocalDate modifiedOn;

	private UserDto user;

	private FlightDto flight;

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public LocalDate getPreviousDepartureTime() {
		return this.previousDepartureTime;
	}

	public void setPreviousDepartureTime(final LocalDate previousDepartureTime) {
		this.previousDepartureTime = previousDepartureTime;
	}

	public LocalDate getPreviousArrivalTime() {
		return this.previousArrivalTime;
	}

	public void setPreviousArrivalTime(final LocalDate previousArrivalTime) {
		this.previousArrivalTime = previousArrivalTime;
	}

	public LocalDate getNewDepartureTime() {
		return this.newDepartureTime;
	}

	public void setNewDepartureTime(final LocalDate newDepartureTime) {
		this.newDepartureTime = newDepartureTime;
	}

	public LocalDate getNewArrivalTime() {
		return this.newArrivalTime;
	}

	public void setNewArrivalTime(final LocalDate newArrivalTime) {
		this.newArrivalTime = newArrivalTime;
	}

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(final String operation) {
		this.operation = operation;
	}

	public LocalDate getModifiedOn() {
		return this.modifiedOn;
	}

	public void setModifiedOn(final LocalDate modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public UserDto getUser() {
		return this.user;
	}

	public void setUser(final UserDto user) {
		this.user = user;
	}

	public FlightDto getFlight() {
		return this.flight;
	}

	public void setFlight(final FlightDto flight) {
		this.flight = flight;
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
		final FlightHistoryDto other = (FlightHistoryDto) obj;
		return Objects.equals(this.id, other.id);
	}

}
