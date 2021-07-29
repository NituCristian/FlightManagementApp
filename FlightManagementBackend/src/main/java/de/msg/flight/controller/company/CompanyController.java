package de.msg.flight.controller.company;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.msg.flight.dto.CompanyDto;
import de.msg.flight.service.ServiceException;
import de.msg.flight.service.company.ICompanyService;
import de.msg.flight.validation.CompanyValidator;
import de.msg.flight.validation.ValidationException;

@RolesAllowed(value = { "ROLE_ADMIN", "ROLE_COMPANY_MANAGER" })
@RequestMapping("/companies")
@RestController
@CrossOrigin
public class CompanyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);

	@Autowired
	private ICompanyService companyService;

	@Autowired
	private CompanyValidator companyValidator;

	@GetMapping
	public ResponseEntity<List<CompanyDto>> getAllCompanies() {

		LOGGER.debug("Retrieving all companies...");

		return ResponseEntity.ok().body(this.companyService.getAllCompanies());
	}

	@GetMapping("/{id}")
	public ResponseEntity<CompanyDto> getCompanyById(@PathVariable final Integer id) throws ServiceException {

		LOGGER.debug("Retrieving company with id={}", id);

		return ResponseEntity.ok().body(this.companyService.getCompanyById(id));
	}

	@PostMapping
	public ResponseEntity<CompanyDto> addCompany(@RequestBody final CompanyDto companyDto)
			throws ValidationException, ServiceException {

		this.companyValidator.validateForInsert(companyDto);

		LOGGER.debug("Adding new company with name={}", companyDto.getName());

		return ResponseEntity.ok().body(this.companyService.addCompany(companyDto));
	}

	@PutMapping
	public ResponseEntity<CompanyDto> updateCompany(@RequestBody final CompanyDto companyDto)
			throws ValidationException, ServiceException {

		this.companyValidator.validateForUpdate(companyDto);

		LOGGER.debug("Updating company with id={}", companyDto.getId());

		return ResponseEntity.ok().body(this.companyService.updateCompany(companyDto));
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteCompany(@RequestBody final CompanyDto companyDto)
			throws ServiceException, ValidationException {

		this.companyValidator.validateForDelete(companyDto);

		LOGGER.debug("Deleting company with id {}", companyDto.getId());

		this.companyService.deleteCompany(companyDto);

		return ResponseEntity.ok().build();
	}

}
