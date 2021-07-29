package de.msg.flight.persistence.model;

import java.time.LocalTime;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "flight_templates")
public class FlightTemplate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private LocalTime arrivalTime;

	private LocalTime departureTime;

	@ManyToOne
	@JoinColumn(name = "departure_airport_id", nullable = true)
	private Airport departureAirport;

	@ManyToOne
	@JoinColumn(name = "arrival_airport_id", nullable = true)
	private Airport arrivalAirport;

	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "plane_id", nullable = true)
	private Plane plane;

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

	public Airport getDepartureAirport() {
		return this.departureAirport;
	}

	public void setDepartureAirport(final Airport departureAirport) {
		this.departureAirport = departureAirport;
	}

	public Airport getArrivalAirport() {
		return this.arrivalAirport;
	}

	public void setArrivalAirport(final Airport arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public Plane getPlane() {
		return this.plane;
	}

	public void setPlane(final Plane plane) {
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
		final FlightTemplate other = (FlightTemplate) obj;
		return Objects.equals(this.id, other.id);
	}

}
