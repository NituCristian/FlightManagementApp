package de.msg.flight.persistence.model;

import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Boolean activated;

	private String firstName;

	private String lastName;

	@Enumerated(value = EnumType.STRING)
	private UserRole role;

	@Column(unique = true)
	private String email;

	private String password;

	@Column(unique = true)
	private String username;

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, mappedBy = "user", fetch = FetchType.LAZY)
	private Set<FlightHistory> flightHistories;

	@ManyToOne
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, mappedBy = "users", fetch = FetchType.LAZY)
	private Set<Flight> flights;

	public Integer getId() {
		return this.id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Boolean getActivated() {
		return this.activated;
	}

	public void setActivated(final Boolean activated) {
		this.activated = activated;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public UserRole getRole() {
		return this.role;
	}

	public void setRole(final UserRole role) {
		this.role = role;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public Set<FlightHistory> getFlightHistories() {
		return this.flightHistories;
	}

	public void setFlightHistories(final Set<FlightHistory> flightHistories) {
		this.flightHistories = flightHistories;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}

	public Set<Flight> getFlights() {
		return this.flights;
	}

	public void setFlights(final Set<Flight> flights) {
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
		final User other = (User) obj;
		return Objects.equals(this.id, other.id);
	}

}
