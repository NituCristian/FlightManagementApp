package de.msg.flight.service.company;

import java.util.List;

import de.msg.flight.dto.CompanyDto;
import de.msg.flight.service.ServiceException;

public interface ICompanyService {

	/**
	 * gets company by id
	 *
	 * @return CompanyDto
	 * @throws ServiceException
	 */

	CompanyDto getCompanyById(final Integer companyId) throws ServiceException;

	/**
	 * gets all companies from the db
	 *
	 * @return List of CompanyDtos
	 */

	List<CompanyDto> getAllCompanies();

	/**
	 * updates a Company
	 *
	 * @param companyDto
	 * @return updated Company as a CompanyDto
	 *
	 * @throws ServiceException
	 */

	CompanyDto updateCompany(CompanyDto companyDto) throws ServiceException;

	/**
	 * deletes a company
	 *
	 * @param companyDto
	 * @return deleted company as a CompanyDto
	 *
	 * @throws ServiceException
	 */

	void deleteCompany(CompanyDto companyDto) throws ServiceException;

	/**
	 * inserts a new Company in the db
	 *
	 * @param companyDto
	 * @return newly inserted Company as a CompanyDto *
	 * @throws ServiceException
	 *
	 */
	CompanyDto addCompany(CompanyDto companyDto) throws ServiceException;

}
