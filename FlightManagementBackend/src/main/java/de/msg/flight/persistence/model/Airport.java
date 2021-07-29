package de.msg.flight.persistence.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "airports")
public class Airport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true)
	private String airportCode;

	private String address;

	private String city;

	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "departureAirport", fetch = FetchType.LAZY)
	private Set<Flight> departureFlights;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "arrivalAirport", fetch = FetchType.LAZY)
	private Set<Flight> arrivalFlights;

	@OneToMany(cascade = { CascadeType.MERGE,
			CascadeType.REMOVE }, mappedBy = "departureAirport", fetch = FetchType.LAZY)
	private Set<FlightTemplate> departureFlightTemplates;

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.REMOVE }, mappedBy = "arrivalAirport", fetch = FetchType.LAZY)
	private Set<FlightTemplate> arrivalFlightTemplates;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "departureAirport", fetch = FetchType.LAZY)
	private Set<Itinerary> departureItineraries;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "arrivalAirport", fetch = FetchType.LAZY)
	private Set<Itinerary> arrivalItineraries;

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

	public Set<Itinerary> getDepartureItineraries() {
		return this.departureItineraries;
	}

	public void setDepartureItineraries(final Set<Itinerary> departureItineraries) {
		this.departureItineraries = departureItineraries;
	}

	public Set<Itinerary> getArrivalItineraries() {
		return this.arrivalItineraries;
	}

	public void setArrivalItineraries(final Set<Itinerary> arrivalItineraries) {
		this.arrivalItineraries = arrivalItineraries;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public Set<Flight> getDepartureFlights() {
		return this.departureFlights;
	}

	public void setDepartureFlights(final Set<Flight> departureFlights) {
		this.departureFlights = departureFlights;
	}

	public Set<Flight> getArrivalFlights() {
		return this.arrivalFlights;
	}

	public void setArrivalFlights(final Set<Flight> arrivalFlights) {
		this.arrivalFlights = arrivalFlights;
	}

	public Set<FlightTemplate> getDepartureFlightTemplates() {
		return this.departureFlightTemplates;
	}

	public void setDepartureFlightTemplates(final Set<FlightTemplate> departureFlightTemplates) {
		this.departureFlightTemplates = departureFlightTemplates;
	}

	public Set<FlightTemplate> getArrivalFlightTemplates() {
		return this.arrivalFlightTemplates;
	}

	public void setArrivalFlightTemplates(final Set<FlightTemplate> arrivalFlightTemplates) {
		this.arrivalFlightTemplates = arrivalFlightTemplates;
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
		final Airport other = (Airport) obj;
		return Objects.equals(this.id, other.id);
	}

}
