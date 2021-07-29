package de.msg.flight.controller.airport;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.msg.flight.dto.AirportDto;
import de.msg.flight.service.ServiceException;
import de.msg.flight.service.airport.IAirportService;
import de.msg.flight.validation.AirportValidator;
import de.msg.flight.validation.ValidationException;

@RestController
@RequestMapping("/airports")
@CrossOrigin
public class AirportController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AirportController.class);

	private static final String CREW_ROLE = "ROLE_CREW";

	private static final String FLIGHT_MANAGER_ROLE = "ROLE_FLIGHT_MANAGER";

	private static final String COMPANY_MANAGER_ROLE = "ROLE_COMPANY_MANAGER";

	private static final String ADMIN_ROLE = "ROLE_ADMIN";

	@Autowired
	private IAirportService airportService;

	@Autowired
	private AirportValidator airportValidator;

	@RolesAllowed(value = { ADMIN_ROLE, COMPANY_MANAGER_ROLE, FLIGHT_MANAGER_ROLE, CREW_ROLE })
	@GetMapping(value = "/{airportId}")
	public ResponseEntity<AirportDto> getAirportById(@PathVariable final Integer airportId) throws ServiceException {

		LOGGER.debug("Get airport with id {}", airportId);

		return ResponseEntity.ok().body(this.airportService.getAirportById(airportId));
	}

	@RolesAllowed(value = { ADMIN_ROLE, COMPANY_MANAGER_ROLE, FLIGHT_MANAGER_ROLE, CREW_ROLE })
	@GetMapping
	public ResponseEntity<List<AirportDto>> getAllAirports() {

		LOGGER.debug("Get all airports");

		return ResponseEntity.ok().body(this.airportService.getAllAirports());
	}

	@RolesAllowed(value = { ADMIN_ROLE, COMPANY_MANAGER_ROLE })
	@PostMapping
	public ResponseEntity<AirportDto> insertAirport(@RequestBody final AirportDto airportDto)
			throws ValidationException, ServiceException {

		this.airportValidator.validateForInsert(airportDto);

		LOGGER.debug("Insert airport with name {}", airportDto.getName());

		return ResponseEntity.ok().body(this.airportService.insertAirport(airportDto));
	}

	@RolesAllowed(value = { ADMIN_ROLE, COMPANY_MANAGER_ROLE })
	@DeleteMapping(value = "/{airportId}")
	public void removeAirport(@PathVariable final Integer airportId) throws ServiceException {

		LOGGER.debug("Delete airport with id {}", airportId);

		this.airportService.removeAirport(airportId);
	}

	@RolesAllowed(value = { ADMIN_ROLE, COMPANY_MANAGER_ROLE })
	@PutMapping
	public ResponseEntity<AirportDto> updateAirport(@RequestBody final AirportDto airportDto)
			throws ValidationException, ServiceException {

		LOGGER.debug("Update airport with id {}", airportDto.getId());

		this.airportValidator.validateForUpdate(airportDto);

		return ResponseEntity.ok().body(this.airportService.updateAirport(airportDto));
	}
}
