package de.msg.flight.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.msg.flight.dto.CompanyDto;

@Component
public class CompanyValidator {

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyValidator.class);

	private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-z][a-zA-Z1-9_]*@[a-zA-Z1-9_]*(\\.[a-z]{2,4})+");

	private static final Pattern PHONE_PATTERN = Pattern.compile("(\\+49[0-9]{11})|(\\+40[1-9][0-9]{8})");

	public void validateForInsert(final CompanyDto companyDto) throws ValidationException {

		validateCompanyNotNull(companyDto);
		validateIdForInsert(companyDto.getId());
		validateAddress(companyDto.getAddress());
		validateEmail(companyDto.getEmail());
		validateName(companyDto.getName());
		validatePhone(companyDto.getPhone());
	}

	public void validateForUpdate(final CompanyDto companyDto) throws ValidationException {

		validateCompanyNotNull(companyDto);
		validateId(companyDto.getId());
		validateAddress(companyDto.getAddress());
		validateEmail(companyDto.getEmail());
		validateName(companyDto.getName());
		validatePhone(companyDto.getPhone());
	}

	public void validateForDelete(final CompanyDto companyDto) throws ValidationException {

		validateCompanyNotNull(companyDto);
		validateId(companyDto.getId());
	}

	private void validateId(final Integer id) throws ValidationException {

		if (null == id) {
			LOGGER.error("Missing company id");
			throw new ValidationException("Company id is missing");
		}
	}

	private void validateCompanyNotNull(final CompanyDto companyDto) throws ValidationException {

		if (null == companyDto) {
			LOGGER.error("Company DTO cannot be null");
			throw new ValidationException("Company cannot be null.");
		}
	}

	private void validateIdForInsert(final Integer id) throws ValidationException {
		if (id != null) {
			LOGGER.error("Company Id cannot be present for insert");
			throw new ValidationException("Company Id cannot be present.Insert is not possible");
		}
	}

	private void validateAddress(final String address) throws ValidationException {

		if (StringUtils.isBlank(address)) {
			LOGGER.error("Missing company address");
			throw new ValidationException("Address is a mandatory field.");
		}
	}

	private void validateEmail(final String email) throws ValidationException {

		if (StringUtils.isBlank(email)) {
			LOGGER.error("Missing company email");
			throw new ValidationException("Email is a mandatory field.");
		}
		validateEmailFormat(email);
	}

	private void validateName(final String name) throws ValidationException {

		if (StringUtils.isBlank(name)) {
			LOGGER.error("Missing company name");
			throw new ValidationException("Name is a mandatory field.");
		}
	}

	private void validatePhone(final String phone) throws ValidationException {

		if (StringUtils.isBlank(phone)) {
			LOGGER.error("Missing company phone");
			throw new ValidationException("Phone is a mandatory field.");
		}

		validateGermanAndRomanianPhoneNumberFormats(phone);
	}

	private void validateGermanAndRomanianPhoneNumberFormats(final String phone) throws ValidationException {

		final String phoneNumberWithoutSpaces = phone.replace(" ", "");
		final Matcher matcher = PHONE_PATTERN.matcher(phoneNumberWithoutSpaces);

		if (!matcher.matches()) {
			LOGGER.error("Phone number format invalid for DE or RO");
			throw new ValidationException(
					"Phone number has incorrect format. Make sure you introduce a german or romanian valid number.");
		}
	}

	private void validateEmailFormat(final String email) throws ValidationException {

		final Matcher matcher = EMAIL_PATTERN.matcher(email);

		if (!matcher.matches()) {
			LOGGER.error("Email format is invalid");
			throw new ValidationException("Email has incorrect format. Make sure you introduce a valid email address.");
		}
	}

}
