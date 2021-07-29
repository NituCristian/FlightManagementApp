package de.msg.flight.validation;

import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.msg.flight.dto.AirportDto;
import de.msg.flight.dto.FlightTemplateDto;
import de.msg.flight.dto.PlaneDto;

@Component
public class FlightTemplateValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(FlightTemplateValidator.class);

	public void validateForInsert(final FlightTemplateDto flightTemplateDto) throws ValidationException {

		validateFlightTemplateNotNull(flightTemplateDto);
		validateIdForInsert(flightTemplateDto.getId());
		validateName(flightTemplateDto.getName());
		validateArrivalTime(flightTemplateDto);
		validateDepartureTime(flightTemplateDto);
		validateArrivalAirport(flightTemplateDto);
		validateDepartureAirport(flightTemplateDto);
		validatePlane(flightTemplateDto.getPlane());
	}

	public void validateForUpdate(final FlightTemplateDto flightTemplateDto) throws ValidationException {

		validateFlightTemplateNotNull(flightTemplateDto);
		validateId(flightTemplateDto.getId());
		validateName(flightTemplateDto.getName());
		validateArrivalTime(flightTemplateDto);
		validateDepartureTime(flightTemplateDto);
		validateArrivalAirport(flightTemplateDto);
		validateDepartureAirport(flightTemplateDto);
		validatePlane(flightTemplateDto.getPlane());

	}

	public void validateForDelete(final Integer id) throws ValidationException {

		validateId(id);
	}

	private void validateDepartureAirport(final FlightTemplateDto flightTemplateDto) throws ValidationException {

		validateAirport(flightTemplateDto.getDepartureAirport(), "departure");
	}

	private void validateArrivalAirport(final FlightTemplateDto flightTemplateDto) throws ValidationException {

		validateAirport(flightTemplateDto.getArrivalAirport(), "arrival");
	}

	private void validateDepartureTime(final FlightTemplateDto flightTemplateDto) throws ValidationException {

		validateTime(flightTemplateDto.getDepartureTime(), "departure");
	}

	private void validateArrivalTime(final FlightTemplateDto flightTemplateDto) throws ValidationException {

		validateTime(flightTemplateDto.getArrivalTime(), "arrival");
	}

	private void validateFlightTemplateNotNull(final FlightTemplateDto flightTemplateDto) throws ValidationException {
		if (null == flightTemplateDto) {
			LOGGER.error("FlightTemplateDto cannot be null");
			throw new ValidationException("FlightTemplate cannot be null");
		}
	}

	private void validateIdForInsert(final Integer id) throws ValidationException {
		if (id != null) {
			LOGGER.error("FlightTemplate Id cannot be present for insert");
			throw new ValidationException("FlightTemplateId cannot be present. Insert not possible");
		}
	}

	private void validateId(final Integer id) throws ValidationException {

		if (null == id) {
			LOGGER.error("Missing FlightTemplate Id");
			throw new ValidationException("FlightTemplate id is missing");
		}
	}

	private void validateName(final String name) throws ValidationException {

		if (StringUtils.isBlank(name)) {
			LOGGER.error("Missing FlightTemplate name");
			throw new ValidationException("Name is a mandatory field.");
		}
	}

	private void validateTime(final LocalTime time, final String type) throws ValidationException {

		if (null == time) {
			LOGGER.error("Missing FlightTemplate {} time", type);
			throw new ValidationException(type + " time is mandatory");
		}
	}

	private void validateAirport(final AirportDto airportDto, final String type) throws ValidationException {
		if (null == airportDto) {
			LOGGER.error("Missing FlightTemplate {} airport", type);
			throw new ValidationException(type + " airport is mandatory");
		}
	}

	private void validatePlane(final PlaneDto planeDto) throws ValidationException {
		if (null == planeDto) {
			LOGGER.error("Missing FlightTemplate plane");
			throw new ValidationException("Plane is mandatory");
		}
	}

}
