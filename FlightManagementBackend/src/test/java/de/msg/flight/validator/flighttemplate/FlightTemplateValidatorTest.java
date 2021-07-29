package de.msg.flight.validator.flighttemplate;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import de.msg.flight.dto.AirportDto;
import de.msg.flight.dto.FlightTemplateDto;
import de.msg.flight.dto.PlaneDto;
import de.msg.flight.validation.FlightTemplateValidator;
import de.msg.flight.validation.ValidationException;

@RunWith(MockitoJUnitRunner.class)
public class FlightTemplateValidatorTest {

	@InjectMocks
	private FlightTemplateValidator validator;

	private FlightTemplateDto flightTemplateDto;

	@Before
	public void init() {
		this.flightTemplateDto = initializeFlightTemplateDto();
	}

	@Test
	public void validateForInsert_dtoIsValid_returnsTrue() throws ValidationException {

		this.flightTemplateDto.setId(null);

		this.validator.validateForInsert(this.flightTemplateDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_dtoIsNull_throwsValidationException() throws ValidationException {

		this.validator.validateForInsert(null);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_dtoHasId_throwsValidationException() throws ValidationException {

		this.validator.validateForInsert(this.flightTemplateDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_dtoHasMissingName_throwsValidationException() throws ValidationException {

		this.flightTemplateDto.setName(null);

		this.validator.validateForInsert(this.flightTemplateDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_dtoHasMissingArrivalTime_throwsValidationException() throws ValidationException {

		this.flightTemplateDto.setArrivalTime(null);

		this.validator.validateForInsert(this.flightTemplateDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_dtoHasMissingDepartureTime_throwsValidationException() throws ValidationException {

		this.flightTemplateDto.setDepartureTime(null);

		this.validator.validateForInsert(this.flightTemplateDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_dtoHasMissingArrivalAirport_throwsValidationException() throws ValidationException {

		this.flightTemplateDto.setArrivalAirport(null);

		this.validator.validateForInsert(this.flightTemplateDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_dtoHasMissingDepartureAirport_throwsValidationException() throws ValidationException {

		this.flightTemplateDto.setDepartureAirport(null);

		this.validator.validateForInsert(this.flightTemplateDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_dtoHasMissingPlane_throwsValidationException() throws ValidationException {

		this.flightTemplateDto.setPlane(null);

		this.validator.validateForInsert(this.flightTemplateDto);
	}

	@Test
	public void validateForUpdate_dtoIsValid_returnsTrue() throws ValidationException {

		this.validator.validateForUpdate(this.flightTemplateDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_dtoIsNull_throwsValidationException() throws ValidationException {

		this.validator.validateForUpdate(null);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_dtoHasMissingId_throwsValidationException() throws ValidationException {

		this.flightTemplateDto.setId(null);

		this.validator.validateForUpdate(this.flightTemplateDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_dtoHasMissingName_throwsValidationException() throws ValidationException {

		this.flightTemplateDto.setName(null);

		this.validator.validateForUpdate(this.flightTemplateDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_dtoHasMissingArrivalTime_throwsValidationException() throws ValidationException {

		this.flightTemplateDto.setArrivalTime(null);

		this.validator.validateForUpdate(this.flightTemplateDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_dtoHasMissingDepartureTime_throwsValidationException() throws ValidationException {

		this.flightTemplateDto.setDepartureTime(null);

		this.validator.validateForUpdate(this.flightTemplateDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_dtoHasMissingArrivalAirport_throwsValidationException() throws ValidationException {

		this.flightTemplateDto.setArrivalAirport(null);

		this.validator.validateForUpdate(this.flightTemplateDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_dtoHasMissingDepartureAirport_throwsValidationException() throws ValidationException {

		this.flightTemplateDto.setDepartureAirport(null);

		this.validator.validateForUpdate(this.flightTemplateDto);
	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_dtoHasMissingPlane_throwsValidationException() throws ValidationException {

		this.flightTemplateDto.setPlane(null);

		this.validator.validateForUpdate(this.flightTemplateDto);
	}

	@Test
	public void validateForDelete_dtoIsValid_throwsValidationException() throws ValidationException {

		this.validator.validateForDelete(this.flightTemplateDto.getId());
	}

	@Test(expected = ValidationException.class)
	public void validateForDelete_dtoIsNull_throwsValidationException() throws ValidationException {

		this.validator.validateForDelete(null);
	}

	private FlightTemplateDto initializeFlightTemplateDto() {

		FlightTemplateDto flightTemplateDto = new FlightTemplateDto();

		AirportDto arrivalAirportDto = new AirportDto();
		arrivalAirportDto.setId(1);
		arrivalAirportDto.setAirportCode("OTP");
		arrivalAirportDto.setAddress("Calea Bucureștilor nr. 224, Otopeni, județul Ilfov");
		arrivalAirportDto.setCity("Bucuresti");

		AirportDto departureAirportDto = new AirportDto();
		departureAirportDto.setId(1);
		departureAirportDto.setAirportCode("CLJ");
		departureAirportDto.setAddress("Adresa");
		departureAirportDto.setCity("Cluj-Napoca");

		PlaneDto planeDto = new PlaneDto();
		planeDto.setId(1);
		planeDto.setCode("GSFUJGBLIK");
		planeDto.setName("Airbus300Blue");
		planeDto.setModel("Airbus300");
		planeDto.setNumberOfPassengers(200);

		flightTemplateDto.setId(1);
		flightTemplateDto.setName("Template 1");
		flightTemplateDto.setArrivalTime(LocalTime.parse("12:00:00"));
		flightTemplateDto.setDepartureTime(LocalTime.parse("12:00:00"));
		flightTemplateDto.setArrivalAirport(arrivalAirportDto);
		flightTemplateDto.setDepartureAirport(departureAirportDto);
		flightTemplateDto.setPlane(planeDto);

		Set<FlightTemplateDto> flightTemplateDtos = new HashSet<>();
		flightTemplateDtos.add(flightTemplateDto);

		return flightTemplateDto;
	}
}
