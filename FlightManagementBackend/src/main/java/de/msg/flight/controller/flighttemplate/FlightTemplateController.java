package de.msg.flight.controller.flighttemplate;

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

import de.msg.flight.dto.FlightTemplateDto;
import de.msg.flight.service.ServiceException;
import de.msg.flight.service.flighttemplate.IFlightTemplateService;
import de.msg.flight.validation.FlightTemplateValidator;
import de.msg.flight.validation.ValidationException;

@RestController
@RequestMapping("/flight_templates")
@CrossOrigin
public class FlightTemplateController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FlightTemplateController.class);

	private static final String FLIGHT_MANAGER_ROLE = "ROLE_FLIGHT_MANAGER";

	private static final String ADMIN_ROLE = "ROLE_ADMIN";

	@Autowired
	private IFlightTemplateService flightTemplateService;

	@Autowired
	private FlightTemplateValidator validator;

	@RolesAllowed(value = { ADMIN_ROLE, FLIGHT_MANAGER_ROLE })
	@GetMapping(value = "/{flightTemplateId}")
	public ResponseEntity<FlightTemplateDto> getFlightTemplateById(@PathVariable final Integer flightTemplateId)
			throws ValidationException {

		LOGGER.debug("Retrieving flight template with id={}...", flightTemplateId);

		final FlightTemplateDto retrievedFlightTemplate = this.flightTemplateService
				.getFlightTemplateById(flightTemplateId);

		return ResponseEntity.ok(retrievedFlightTemplate);
	}

	@RolesAllowed(value = { ADMIN_ROLE, FLIGHT_MANAGER_ROLE })
	@GetMapping
	public ResponseEntity<List<FlightTemplateDto>> getAllFlightTemplates() {

		LOGGER.debug("Retrieving all flight templates...");

		return ResponseEntity.ok(this.flightTemplateService.getAllFlightTemplates());
	}

	@RolesAllowed(value = { ADMIN_ROLE, FLIGHT_MANAGER_ROLE })
	@PostMapping
	public ResponseEntity<FlightTemplateDto> insertFlightTemplate(
			@RequestBody final FlightTemplateDto newFlightTemplateDto) throws ValidationException {

		this.validator.validateForInsert(newFlightTemplateDto);

		LOGGER.debug("Adding new flight template with name={}", newFlightTemplateDto.getName());

		return ResponseEntity.ok(this.flightTemplateService.insertFlightTemplate(newFlightTemplateDto));
	}

	@RolesAllowed(value = { ADMIN_ROLE, FLIGHT_MANAGER_ROLE })
	@PutMapping
	public ResponseEntity<FlightTemplateDto> updateFlightTemplate(
			@RequestBody final FlightTemplateDto flightTemplateDto) throws ValidationException {

		this.validator.validateForUpdate(flightTemplateDto);

		LOGGER.debug("Updating flight template with id={}", flightTemplateDto.getId());

		return ResponseEntity.ok(this.flightTemplateService.updateFlightTemplate(flightTemplateDto));
	}

	@RolesAllowed(value = { ADMIN_ROLE, FLIGHT_MANAGER_ROLE })
	@DeleteMapping(value = "/{flightTemplateId}")
	public void removeFlightTemplate(@PathVariable final Integer flightTemplateId)
			throws ServiceException, ValidationException {

		this.validator.validateForDelete(flightTemplateId);

		LOGGER.debug("Deleting flight template with id={}", flightTemplateId);

		this.flightTemplateService.removeFlightTemplate(flightTemplateId);
	}
}
