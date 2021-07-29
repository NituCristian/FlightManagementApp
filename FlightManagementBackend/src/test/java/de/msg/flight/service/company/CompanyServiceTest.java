package de.msg.flight.service.company;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import de.msg.flight.dto.CompanyDto;
import de.msg.flight.persistence.model.Company;
import de.msg.flight.persistence.repository.CompanyRepository;
import de.msg.flight.service.ServiceException;
import de.msg.flight.validation.ValidationException;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {

	@InjectMocks
	private CompanyService companyService;

	@Mock
	private CompanyRepository companyRepository;

	@Test
	public void updateCompany_companyDtoIsValid_returnsUpdatedCompany() throws ValidationException, ServiceException {

		final CompanyDto companyDtoToUpdate = this.createCompleteCompanyDto();

		Mockito.when(this.companyRepository.save(Mockito.eq(createCompleteCompany().get())))
		.thenReturn(this.createCompleteCompany().get());

		assertEquals(createCompleteCompanyDto(), companyService.updateCompany(companyDtoToUpdate));

	}

	@Test(expected = ServiceException.class)
	public void updateCompany_companyViolatesUniqueConstraint_throwsServiceException() throws ServiceException {

		final CompanyDto companyDtoToUpdate = this.createCompleteCompanyDto();

		Mockito.when(this.companyRepository.save(Mockito.eq(createCompleteCompany().get())))
		.thenThrow(DataIntegrityViolationException.class);

		companyService.updateCompany(companyDtoToUpdate);

	}

	@Test
	public void insertCompany_companyDtoIsValid_returnsInsertedCompany() throws ValidationException, ServiceException {

		final CompanyDto companyDtoToInsert = this.createCompanyDtoWithoutId();

		Mockito.when(this.companyRepository.save(Mockito.eq(createCompanyForInsert())))
		.thenReturn(this.createCompleteCompany().get());

		assertEquals(createCompleteCompanyDto(), companyService.addCompany(companyDtoToInsert));

	}

	@Test(expected = ServiceException.class)
	public void insertCompany_companyViolatesUniqueConstraint_throwsServiceException()
			throws ValidationException, ServiceException {

		final CompanyDto companyDtoToInsert = this.createCompanyDtoWithoutId();

		Mockito.when(this.companyRepository.save(Mockito.eq(createCompanyForInsert())))
		.thenThrow(DataIntegrityViolationException.class);

		companyService.addCompany(companyDtoToInsert);

	}

	@Test
	public void deleteCompany_companyDtoIsValid_isSuccessful() throws ValidationException, ServiceException {

		companyService.deleteCompany(this.createCompleteCompanyDto());
		Mockito.verify(this.companyRepository, times(1)).deleteById(1);

	}

	@Test(expected = ServiceException.class)
	public void deleteCompany_companyDoesNotExist_throwsServiceException()
			throws ValidationException, ServiceException {

		CompanyDto companyDtoToDelete = this.createCompleteCompanyDto();

		Mockito.doThrow(EmptyResultDataAccessException.class).when(this.companyRepository).deleteById(1);

		companyService.deleteCompany(companyDtoToDelete);

	}

	private Optional<Company> createCompleteCompany() {

		Company company = new Company();
		company.setAddress("address");
		company.setEmail("mail@mail.com");
		company.setName("name");
		company.setPhone("+50936538992");
		company.setId(1);

		return Optional.ofNullable(company);
	}

	private CompanyDto createCompleteCompanyDto() {

		final CompanyDto companyDto = new CompanyDto();
		companyDto.setAddress("address");
		companyDto.setEmail("mail@mail.com");
		companyDto.setName("name");
		companyDto.setPhone("+50936538992");
		companyDto.setId(1);

		return companyDto;
	}

	private CompanyDto createCompanyDtoWithoutId() {

		final CompanyDto companyDto = new CompanyDto();
		companyDto.setAddress("address");
		companyDto.setEmail("mail@mail.com");
		companyDto.setName("name");
		companyDto.setPhone("+50936538992");

		return companyDto;
	}

	private Company createCompanyForInsert() {

		Company company = new Company();
		company.setAddress("address");
		company.setEmail("mail@mail.com");
		company.setName("name");
		company.setPhone("+50936538992");

		return company;
	}

}
