package de.msg.flight.validator.company;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import de.msg.flight.dto.CompanyDto;
import de.msg.flight.validation.CompanyValidator;
import de.msg.flight.validation.ValidationException;

@RunWith(MockitoJUnitRunner.class)
public class CompanyValidatorTest {

	@InjectMocks
	private CompanyValidator companyValidator;

	@Test
	public void validateForInsert_companyDtoIsValid_returnsTrue() throws ValidationException {
		companyValidator.validateForInsert(this.createCompanyDtoWithoutId());
	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_companyIdNotNull_throwsValidationException() throws ValidationException {

		this.companyValidator.validateForInsert(this.createCompleteCompanyDto());

	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_companyAddressIsMissing_throwsValidationException() throws ValidationException {

		this.companyValidator.validateForInsert(this.createCompanyDtoWithoutAddress());

	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_companyNameIsMissing_throwsValidationException() throws ValidationException {

		this.companyValidator.validateForInsert(this.createCompanyDtoWithoutName());

	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_companyEmailIsInvalid_throwsValidationException() throws ValidationException {

		this.companyValidator.validateForInsert(this.createCompanyDtoWithInvalidEmail());

	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_companyPhoneNumberIsInvalid_throwsValidationException() throws ValidationException {

		this.companyValidator.validateForInsert(this.createCompanyDtoWithInvalidPhoneNumber());

	}

	@Test(expected = ValidationException.class)
	public void validateForInsert_companyIsNull_throwsValidationException() throws ValidationException {

		this.companyValidator.validateForInsert(null);

	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_companyIdIsNull_throwsValidationException() throws ValidationException {

		this.companyValidator.validateForUpdate(this.createCompanyDtoWithoutId());

	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_companyNameIsMissing_throwsValidationException() throws ValidationException {

		this.companyValidator.validateForUpdate(this.createCompanyDtoWithoutName());

	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_companyAddressIsMissing_throwsValidationException() throws ValidationException {

		this.companyValidator.validateForUpdate(this.createCompanyDtoWithoutAddress());

	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_companyPhoneNumberIsInvalid_throwsValidationException() throws ValidationException {

		this.companyValidator.validateForUpdate(this.createCompanyDtoWithInvalidPhoneNumber());

	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_companyEmailIsInvalid_throwsValidationException() throws ValidationException {

		this.companyValidator.validateForUpdate(this.createCompanyDtoWithInvalidEmail());

	}

	@Test(expected = ValidationException.class)
	public void validateForUpdate_companyIsNull_throwsValidationException() throws ValidationException {

		this.companyValidator.validateForUpdate(null);

	}

	@Test
	public void validateForUpdate_companyDtoIsValid_isSuccessful() throws ValidationException {

		this.companyValidator.validateForUpdate(this.createCompleteCompanyDto());

	}

	@Test
	public void validateForDelete_companyDtoIsValid_isSuccessful() throws ValidationException {

		this.companyValidator.validateForDelete(this.createCompleteCompanyDto());
	}

	@Test(expected = ValidationException.class)
	public void validateForDelete_companyIdIsNull_throwsValidationException() throws ValidationException {

		this.companyValidator.validateForDelete(this.createCompanyDtoWithoutId());
	}

	@Test(expected = ValidationException.class)
	public void validateForDelete_companyIsNull_throwsValidationException() throws ValidationException {

		this.companyValidator.validateForDelete(null);
	}

	private CompanyDto createCompanyDtoWithoutId() {

		final CompanyDto companyDto = new CompanyDto();
		companyDto.setAddress("address");
		companyDto.setEmail("mail@mail.com");
		companyDto.setName("name");
		companyDto.setPhone("+40778099342");

		return companyDto;
	}

	private CompanyDto createCompleteCompanyDto() {

		final CompanyDto companyDto = new CompanyDto();
		companyDto.setAddress("address");
		companyDto.setEmail("mail@mail.com");
		companyDto.setName("name");
		companyDto.setPhone("+40778099342");
		companyDto.setId(1);
		return companyDto;
	}

	private CompanyDto createCompanyDtoWithoutAddress() {

		final CompanyDto companyDto = new CompanyDto();
		companyDto.setEmail("mail@mail.com");
		companyDto.setName("name");
		companyDto.setPhone("+40778099342");
		companyDto.setId(1);
		return companyDto;
	}

	private CompanyDto createCompanyDtoWithInvalidEmail() {

		final CompanyDto companyDto = new CompanyDto();
		companyDto.setAddress("address");
		companyDto.setEmail("mailmail.com");
		companyDto.setName("name");
		companyDto.setPhone("+40778099342");
		companyDto.setId(1);
		return companyDto;
	}

	private CompanyDto createCompanyDtoWithoutName() {

		final CompanyDto companyDto = new CompanyDto();
		companyDto.setAddress("address");
		companyDto.setEmail("mail@mail.com");
		companyDto.setPhone("+40778099342");
		companyDto.setId(1);
		return companyDto;
	}

	private CompanyDto createCompanyDtoWithInvalidPhoneNumber() {

		final CompanyDto companyDto = new CompanyDto();
		companyDto.setAddress("address");
		companyDto.setEmail("mail@mail.com");
		companyDto.setName("name");
		companyDto.setPhone("+0778094444449342");
		companyDto.setId(1);
		return companyDto;
	}

}
