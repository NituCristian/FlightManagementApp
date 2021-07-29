package de.msg.flight.persistence.model;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "flights")
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private LocalDate arrivalTime;

	private LocalDate departureTime;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@ManyToOne
	@JoinColumn(name = "plane_id")
	private Plane plane;

	@OneToMany(cascade = { CascadeType.MERGE,
			CascadeType.PERSIST }, mappedBy = "flight", fetch = FetchType.LAZY, orphanRemoval = false)
	private Set<FlightHistory> flightHistories;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "flights_users", joinColumns = @JoinColumn(name = "flight_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, mappedBy = "flights", fetch = FetchType.LAZY)
	private Set<Itinerary> itineraries;

	@ManyToOne
	@JoinColumn(name = "departure_airport_id")
	private Airport departureAirport;

	@ManyToOne
	@JoinColumn(name = "arrival_airport_id")
	private Airport arrivalAirport;

	public Integer getId() {
		return this.id;
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

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}

	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(final Set<User> users) {
		this.users = users;
	}

	public Set<Itinerary> getItineraries() {
		return this.itineraries;
	}

	public void setItineraries(final Set<Itinerary> itineraries) {
		this.itineraries = itineraries;
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

	public void setId(final Integer id) {
		this.id = id;
	}

	public Plane getPlane() {
		return this.plane;
	}

	public void setPlane(final Plane plane) {
		this.plane = plane;
	}

	public Set<FlightHistory> getFlightHistories() {
		return this.flightHistories;
	}

	public void setFlightHistories(final Set<FlightHistory> flightHistories) {
		this.flightHistories = flightHistories;
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
		final Flight other = (Flight) obj;
		return Objects.equals(this.id, other.id);
	}

}
