package de.msg.flight.persistence.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "planes")
public class Plane {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	@Column(unique = true)
	private String code;

	private String model;

	@Column(name = "passengers")
	private Integer numberOfPassengers;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "plane", fetch = FetchType.LAZY)
	private Set<Flight> flights;

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.REMOVE }, mappedBy = "plane", fetch = FetchType.LAZY)
	private Set<FlightTemplate> flightTemplates = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

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

	public Set<Flight> getFlights() {
		return this.flights;
	}

	public void setFlights(final Set<Flight> flights) {
		this.flights = flights;
	}

	public Set<FlightTemplate> getFlightTemplates() {
		return this.flightTemplates;
	}

	public void setFlightTemplates(final Set<FlightTemplate> flightTemplates) {
		this.flightTemplates = flightTemplates;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(final Company company) {
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
		final Plane other = (Plane) obj;
		return Objects.equals(this.id, other.id);
	}

}