package de.msg.flight.service.company;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import de.msg.flight.dto.CompanyDto;
import de.msg.flight.mapper.company.CompanyMapper;
import de.msg.flight.persistence.model.Company;
import de.msg.flight.persistence.repository.CompanyRepository;
import de.msg.flight.service.ServiceException;

@Service
public class CompanyService implements ICompanyService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);

	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public CompanyDto getCompanyById(final Integer companyId) throws ServiceException {

		final Optional<Company> company = this.companyRepository.findById(companyId);

		if (!company.isPresent()) {
			LOGGER.error("Company with id {} does not exist", companyId);

			throw new ServiceException("Could not get the company with id " + companyId);
		}

		LOGGER.info("Successfully retrieved Company with id= {}", companyId);

		return mapCompanyToCompanyDto(company.get());
	}

	@Override
	public List<CompanyDto> getAllCompanies() {

		return this.companyRepository.findAll().stream().map(company -> mapCompanyToCompanyDto(company))
				.collect(Collectors.toList());
	}

	@Override
	public CompanyDto updateCompany(final CompanyDto companyDto) throws ServiceException {

		final Company updatedCompany = update(companyDto);

		LOGGER.info("Successfully updated Company with id= {}", companyDto.getId());

		return mapCompanyToCompanyDto(updatedCompany);

	}

	@Override
	public void deleteCompany(final CompanyDto companyDto) throws ServiceException {

		try {
			this.companyRepository.deleteById(mapCompanyDtoToCompany(companyDto).getId());
		} catch (final EmptyResultDataAccessException ex) {
			LOGGER.error("Failed to delete Company with id= {}. None was found", companyDto.getId());

			throw new ServiceException("No Company with id= " + companyDto.getId() + " exists");
		}

		LOGGER.info("Successfully deleted Company with id= {}", companyDto.getId());
	}

	@Override
	public CompanyDto addCompany(final CompanyDto companyDto) throws ServiceException {

		final Company newCompany = add(companyDto);

		LOGGER.info("Successfully inserted Company with id= {}", newCompany.getId());

		return mapCompanyToCompanyDto(newCompany);
	}

	private Company add(final CompanyDto companyDto) throws ServiceException {
		try {
			return this.companyRepository.save(mapCompanyDtoToCompany(companyDto));

		} catch (final DataIntegrityViolationException e) {
			LOGGER.error("Failed to insert Company with name={} because of unique db integrity constraint violation",
					companyDto.getName());

			throw new ServiceException("Failed to insert the company. Another one with the same email: "
					+ companyDto.getEmail() + ", or name: " + companyDto.getName() + ", or telephone: "
					+ companyDto.getPhone() + " already exists");
		}

	}

	private Company update(final CompanyDto companyDto) throws ServiceException {

		try {
			return this.companyRepository.save(mapCompanyDtoToCompany(companyDto));
		} catch (final DataIntegrityViolationException e) {
			LOGGER.error("Failed to update the company with id={} due to unique constraints", companyDto.getId());

			throw new ServiceException("Failed to update the Company because Another one with the same email: "
					+ companyDto.getEmail() + ", or name: " + companyDto.getName() + ", or telephone: "
					+ companyDto.getPhone() + " already exists");
		}
	}

	private CompanyDto mapCompanyToCompanyDto(final Company company) {

		return CompanyMapper.MAPPER.mapCompanyToCompanyDto(company);
	}

	private Company mapCompanyDtoToCompany(final CompanyDto companyDto) {
		return CompanyMapper.MAPPER.mapCompanyDtoToCompany(companyDto);
	}

}
