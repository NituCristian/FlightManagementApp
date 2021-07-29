package de.msg.flight.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.msg.flight.dto.AirportDto;

@Component
public class AirportValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(AirportValidator.class);

	public void validateForInsert(final AirportDto airportDto) throws ValidationException {

		validateAirportNotNull(airportDto);
		validateIdForInsert(airportDto.getId());
		validateAddress(airportDto.getAddress());
		validateCode(airportDto.getAirportCode());
		validateName(airportDto.getName());
		validateCity(airportDto.getCity());
	}

	public void validateForUpdate(final AirportDto airportDto) throws ValidationException {

		validateAirportNotNull(airportDto);
		validateId(airportDto.getId());
		validateAddress(airportDto.getAddress());
		validateCode(airportDto.getAirportCode());
		validateName(airportDto.getName());
		validateCity(airportDto.getCity());
	}

	private void validateAirportNotNull(final AirportDto airportDto) throws ValidationException {

		if (null == airportDto) {
			LOGGER.error("Airport DTO cannot be null");
			throw new ValidationException("Airport cannot be null.");
		}
	}

	private void validateId(final Integer id) throws ValidationException {

		if (null == id) {
			LOGGER.error("Missing airport id");
			throw new ValidationException("Airport id is missing");
		}
	}

	private void validateIdForInsert(final Integer id) throws ValidationException {

		if (id != null) {
			LOGGER.error("Airport Id cannot be present for insert");
			throw new ValidationException("Airport Id cannot be present.Insert is not possible");
		}
	}

	private void validateAddress(final String address) throws ValidationException {

		if (StringUtils.isBlank(address)) {
			LOGGER.error("Missing airport address");
			throw new ValidationException("Address is a mandatory field.");
		}
	}

	private void validateCode(final String code) throws ValidationException {

		if (StringUtils.isBlank(code)) {
			LOGGER.error("Missing airport code");
			throw new ValidationException("Code is a mandatory field.");
		}
	}

	private void validateName(final String name) throws ValidationException {

		if (StringUtils.isBlank(name)) {
			LOGGER.error("Missing airport name");
			throw new ValidationException("Name is a mandatory field.");
		}
	}

	private void validateCity(final String city) throws ValidationException {

		if (StringUtils.isBlank(city)) {
			LOGGER.error("Missing airport city");
			throw new ValidationException("City is a mandatory field.");
		}
	}

}
