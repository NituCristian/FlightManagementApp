package de.msg.flight.validator.airport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import de.msg.flight.dto.AirportDto;
import de.msg.flight.validation.AirportValidator;
import de.msg.flight.validation.ValidationException;

@RunWith(MockitoJUnitRunner.class)
public class AirportValidatorTest {

	@InjectMocks
	private AirportValidator airportValidator;

	@Test(expected = ValidationException.class)
	public void validateForInsert_airportDtoIsNull_throwsValidationException() throws ValidationException {

		this.airportValidator.validateForInsert(null);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_airportDtoIdIsNotNull_throwsValidationException() throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithoutId();

		airportDto.setId(1);

		this.airportValidator.validateForInsert(airportDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_airportDtoWithEmptyAddressField_throwsValidationException()
			throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithoutId();

		airportDto.setAddress("");

		this.airportValidator.validateForInsert(airportDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_airportDtoWithNullAddressField_throwsValidationException()
			throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithoutId();

		airportDto.setAddress(null);

		this.airportValidator.validateForInsert(airportDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_airportDtoWithEmptyCodeField_throwsValidationException() throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithoutId();

		airportDto.setAirportCode("");

		this.airportValidator.validateForInsert(airportDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_airportDtoWithNullCodeField_throwsValidationException() throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithoutId();

		airportDto.setAirportCode(null);

		this.airportValidator.validateForInsert(airportDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_airportDtoWithEmptyNameField_throwsValidationException() throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithoutId();

		airportDto.setName("");

		this.airportValidator.validateForInsert(airportDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_airportDtoWithNullNameField_throwsValidationException() throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithoutId();

		airportDto.setName(null);

		this.airportValidator.validateForInsert(airportDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_airportDtoWithEmptyCityField_throwsValidationException() throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithoutId();

		airportDto.setCity("");

		this.airportValidator.validateForInsert(airportDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_airportDtoWithNullCityField_throwsValidationException() throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithoutId();

		airportDto.setCity(null);

		this.airportValidator.validateForInsert(airportDto);
	}

	@Test
	public void validateForInsert_airportDtoIsValid_insertSuccessfully() throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithoutId();

		this.airportValidator.validateForInsert(airportDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_airportDtoIsNull_throwsValidationException() throws ValidationException {

		this.airportValidator.validateForUpdate(null);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_airportDtoIdIsNull_throwsValidationException() throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithoutId();

		this.airportValidator.validateForUpdate(airportDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_airportDtoWithEmptyAddressField_throwsValidationException()
			throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithId();

		airportDto.setAddress("");

		this.airportValidator.validateForUpdate(airportDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_airportDtoWithNullAddressField_throwsValidationException()
			throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithId();

		airportDto.setAddress(null);

		this.airportValidator.validateForUpdate(airportDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_airportDtoWithEmptyCodeField_throwsValidationException() throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithId();

		airportDto.setAirportCode("");

		this.airportValidator.validateForUpdate(airportDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_airportDtoWithNullCodeField_throwsValidationException() throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithId();

		airportDto.setAirportCode(null);

		this.airportValidator.validateForUpdate(airportDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_airportDtoWithEmptyNameField_throwsValidationException() throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithoutId();

		airportDto.setName("");

		this.airportValidator.validateForUpdate(airportDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_airportDtoWithNullNameField_throwsValidationException() throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithId();

		airportDto.setName(null);

		this.airportValidator.validateForUpdate(airportDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_airportDtoWithEmptyCityField_throwsValidationException() throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithId();

		airportDto.setCity("");

		this.airportValidator.validateForUpdate(airportDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_airportDtoWithNullCityField_throwsValidationException() throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithId();

		airportDto.setCity(null);

		this.airportValidator.validateForUpdate(airportDto);
	}

	@Test
	public void validateForUpdate_airportDtoIsValid_updateSuccessfully() throws ValidationException {

		final AirportDto airportDto = createAirportDtoWithId();

		this.airportValidator.validateForUpdate(airportDto);
	}

	private AirportDto createAirportDtoWithId() {

		AirportDto airportDto = new AirportDto();

		airportDto.setId(1);
		airportDto.setAddress("address");
		airportDto.setAirportCode("JGI");
		airportDto.setCity("Paris");
		airportDto.setName("Name");

		return airportDto;
	}

	private AirportDto createAirportDtoWithoutId() {

		AirportDto airportDto = new AirportDto();

		airportDto.setAddress("address");
		airportDto.setAirportCode("JGI");
		airportDto.setCity("Paris");
		airportDto.setName("Name");

		return airportDto;
	}
}
