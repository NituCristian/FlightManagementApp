package de.msg.flight.validator.plane;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import de.msg.flight.dto.CompanyDto;
import de.msg.flight.dto.PlaneDto;
import de.msg.flight.validation.PlaneValidator;
import de.msg.flight.validation.ValidationException;

@RunWith(MockitoJUnitRunner.class)
public class PlaneValidatorTest {

	@InjectMocks
	private PlaneValidator planeValidator;

	@Test
	public void validateForInsert_planeDtoIsValid_isSuccessful() throws ValidationException {

		this.planeValidator.validateForInsert(this.createCompletePlaneDtoWithoutId());
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_planeDtoIsNull_throwsValidationException() throws ValidationException {

		this.planeValidator.validateForInsert(null);
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_planeDtoCodeIsEmpty_throwsValidationException() throws ValidationException {

		this.planeValidator.validateForInsert(this.createPlaneDtoWithoutCode());

	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_planeDtoNameIsEmpty_throwsValidationException() throws ValidationException {

		this.planeValidator.validateForInsert(this.createPlaneDtoWithoutName());

	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_planeDtoModelIsEmpty_throwsValidationException() throws ValidationException {

		this.planeValidator.validateForInsert(this.createPlaneDtoWithoutModel());

	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_planeDtoNumberOfPassengersIsNegative_throwsValidationException()
			throws ValidationException {

		this.planeValidator.validateForInsert(this.createPlaneDtoWithNegativeNumberOfPassengers());

	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_planeDtoIdNotNull_throwsValidationException() throws ValidationException {

		this.planeValidator.validateForInsert(this.createCompletePlaneDto());

	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_planeDtoIdNull_throwsValidationException() throws ValidationException {

		this.planeValidator.validateForUpdate(this.createCompletePlaneDtoWithoutId());

	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_planeDtoNull_throwsValidationException() throws ValidationException {

		this.planeValidator.validateForUpdate(null);

	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_planeDtoCodeIsEmpty_throwsValidationException() throws ValidationException {

		this.planeValidator.validateForUpdate(this.createPlaneDtoWithoutCode());

	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_planeDtoNameIsEmpty_throwsValidationException() throws ValidationException {

		this.planeValidator.validateForUpdate(this.createPlaneDtoWithoutName());

	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_planeDtoModelIsEmpty_throwsValidationException() throws ValidationException {

		this.planeValidator.validateForUpdate(this.createPlaneDtoWithoutModel());

	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_planeDtoNumberOfPassengersIsNegative_throwsValidationException()
			throws ValidationException {

		this.planeValidator.validateForUpdate(this.createPlaneDtoWithNegativeNumberOfPassengers());

	}

	@Test
	public void validateForUpdate_planeDtoIsValid_isSuccessful() throws ValidationException {

		this.planeValidator.validateForUpdate(this.createCompletePlaneDto());

	}

	@Test(expected = ValidationException.class)
	public void validateForDelete_planeDtoIdNull_throwsValidationException() throws ValidationException {

		this.planeValidator.validateForDelete(this.createCompletePlaneDtoWithoutId());

	}

	@Test(expected = ValidationException.class)
	public void validateForDelete_planeDtoNull_throwsValidationException() throws ValidationException {

		this.planeValidator.validateForDelete(null);

	}

	@Test
	public void validateForDelete_planeDtoIsValid_returnsTrue() throws ValidationException {

		this.planeValidator.validateForDelete(this.createCompletePlaneDto());

	}

	private PlaneDto createCompletePlaneDtoWithoutId() {

		PlaneDto planeDto = new PlaneDto();
		planeDto.setCode("ABCD");
		planeDto.setCompany(this.createCompleteCompanyDto());
		planeDto.setModel("Airbus 500");
		planeDto.setNumberOfPassengers(200);
		planeDto.setName("BlueAirPlane1");

		return planeDto;

	}

	private CompanyDto createCompleteCompanyDto() {

		CompanyDto companyDto = new CompanyDto();
		companyDto.setAddress("address");
		companyDto.setEmail("mail@mail.com");
		companyDto.setName("name");
		companyDto.setPhone("+50936538992");
		companyDto.setId(1);

		return companyDto;

	}

	private PlaneDto createCompletePlaneDto() {

		PlaneDto planeDto = new PlaneDto();
		planeDto.setCode("ABCD");
		planeDto.setCompany(this.createCompleteCompanyDto());
		planeDto.setModel("Airbus 500");
		planeDto.setNumberOfPassengers(200);
		planeDto.setName("BlueAirPlane1");
		planeDto.setId(1);
		return planeDto;

	}

	private PlaneDto createPlaneDtoWithoutCode() {

		PlaneDto planeDto = new PlaneDto();
		planeDto.setCompany(this.createCompleteCompanyDto());
		planeDto.setModel("Airbus 500");
		planeDto.setNumberOfPassengers(200);
		planeDto.setName("BlueAirPlane1");
		planeDto.setId(1);
		return planeDto;

	}

	private PlaneDto createPlaneDtoWithoutModel() {

		PlaneDto planeDto = new PlaneDto();
		planeDto.setCode("ABCD");
		planeDto.setCompany(this.createCompleteCompanyDto());
		planeDto.setNumberOfPassengers(200);
		planeDto.setName("BlueAirPlane1");
		planeDto.setId(1);
		return planeDto;

	}

	private PlaneDto createPlaneDtoWithoutName() {

		PlaneDto planeDto = new PlaneDto();
		planeDto.setCode("ABCD");
		planeDto.setCompany(this.createCompleteCompanyDto());
		planeDto.setNumberOfPassengers(200);
		planeDto.setModel("Airbus 500");
		planeDto.setId(1);
		return planeDto;

	}

	private PlaneDto createPlaneDtoWithNegativeNumberOfPassengers() {

		PlaneDto planeDto = new PlaneDto();
		planeDto.setCode("ABCD");
		planeDto.setCompany(this.createCompleteCompanyDto());
		planeDto.setModel("Airbus 500");
		planeDto.setNumberOfPassengers(-200);
		planeDto.setName("BlueAirPlane1");
		planeDto.setId(1);
		return planeDto;

	}

}
