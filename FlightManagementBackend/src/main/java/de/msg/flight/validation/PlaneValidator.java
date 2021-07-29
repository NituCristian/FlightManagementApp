package de.msg.flight.validation;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.msg.flight.dto.PlaneDto;

@Component
public class PlaneValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlaneValidator.class);

	public void validateForInsert(final PlaneDto planeDto) throws ValidationException {

		validatePlaneNotNull(planeDto);
		validateIdForInsert(planeDto.getId());
		validateCode(planeDto.getCode());
		validateModel(planeDto.getModel());
		validateName(planeDto.getName());
		validateNumberOfPassengers(planeDto.getNumberOfPassengers());

	}

	public void validateForUpdate(final PlaneDto planeDto) throws ValidationException {

		validatePlaneNotNull(planeDto);
		validateId(planeDto.getId());
		validateCode(planeDto.getCode());
		validateModel(planeDto.getModel());
		validateName(planeDto.getName());
		validateNumberOfPassengers(planeDto.getNumberOfPassengers());

	}

	public void validateForDelete(final PlaneDto planeDto) throws ValidationException {

		validatePlaneNotNull(planeDto);
		validateId(planeDto.getId());
	}

	public void validatePlanesRetrievalByCompanyId(final Integer companyId) throws ValidationException {

		if (null == companyId) {
			LOGGER.error("Company ID cannot be null. Plane retrival by company is not possible");
			throw new ValidationException("Company ID cannot be null");
		}
	}

	private void validatePlaneNotNull(final PlaneDto plane) throws ValidationException {
		if (null == plane) {
			LOGGER.error("Plane is null");
			throw new ValidationException("Plane must not be null");
		}
	}

	private void validateIdForInsert(final Integer id) throws ValidationException {
		if (id != null) {
			LOGGER.error("Id not null for insert operation for Plane");
			throw new ValidationException("Id must me null for insert operation");
		}
	}

	private void validateId(final Integer id) throws ValidationException {
		if (null == id) {
			LOGGER.error("Plane id is null for update or delete operation");
			throw new ValidationException("Id cannot be null for update or delete operation");
		}
	}

	private void validateCode(final String code) throws ValidationException {
		if (StringUtils.isBlank(code)) {
			LOGGER.error("Missing code for Plane");
			throw new ValidationException("Code is a mandatory field");
		}
	}

	private void validateModel(final String model) throws ValidationException {
		if (StringUtils.isBlank(model)) {
			LOGGER.error("Missing model for Plane");
			throw new ValidationException("Model is a mandatory field");
		}
	}

	private void validateNumberOfPassengers(final Integer numberOfPassengers) throws ValidationException {

		if (null == numberOfPassengers) {
			LOGGER.error("Missing number of passengers for Plane");
			throw new ValidationException("Number of passengers is a mandatory field");
		}

		if (isNumberSmallerThanZero(numberOfPassengers)) {
			LOGGER.error("Number of passengers ({}) is a negative number", numberOfPassengers);
			throw new ValidationException("Number of passengers must be greater than 0");
		}

		if (isNumberGreaterThanFiveHundred(numberOfPassengers)) {
			LOGGER.error("Number of passengers ({}) is greater than 500", numberOfPassengers);
			throw new ValidationException("Number of passengers must be less than or equal to 500");
		}

	}

	private void validateName(final String name) throws ValidationException {
		if (StringUtils.isBlank(name)) {
			LOGGER.error("Missing name for Plane");
			throw new ValidationException("Name is a mandatory field");
		}
	}

	private boolean isNumberGreaterThanFiveHundred(final Integer number) {

		return number.compareTo(Integer.valueOf(500)) > 0;
	}

	private boolean isNumberSmallerThanZero(final Integer number) {

		return number.compareTo(Integer.valueOf(0)) < 0;
	}

}
