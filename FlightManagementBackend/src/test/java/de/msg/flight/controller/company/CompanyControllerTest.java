package de.msg.flight.controller.company;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.msg.flight.dto.CompanyDto;
import de.msg.flight.service.ServiceException;
import de.msg.flight.service.company.ICompanyService;
import de.msg.flight.service.plane.IPlaneService;
import de.msg.flight.validation.CompanyValidator;
import de.msg.flight.validation.ValidationException;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = CompanyController.class)
public class CompanyControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private ICompanyService companyService;

	@MockBean
	private IPlaneService planeService;

	@MockBean
	CompanyValidator companyValidator;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void getAll_companiesExist_returnsListOfCompanies() throws Exception {
		final List<CompanyDto> allCompanies = new ArrayList<CompanyDto>();
		allCompanies.add(createCompanyDto());

		Mockito.when(this.companyService.getAllCompanies()).thenReturn(allCompanies);
		this.mvc.perform(get("/companies")).andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$[0].address", equalTo("address")))
		.andExpect(jsonPath("$[0].email", equalTo("mail@mail.com")))
		.andExpect(jsonPath("$[0].name", equalTo("name"))).andExpect(jsonPath("$[0].id", equalTo(1)))
		.andExpect(jsonPath("$[0].phone", equalTo("+50936538992"))).andExpect(status().isOk());

	}

	@Test
	public void getAll_noCompanyExists_returnsEmptyList() throws Exception {

		final MvcResult result = this.mvc.perform(get("/companies")).andExpect(status().isOk()).andReturn();

		final String expectedResult = "[]";

		assertEquals(result.getResponse().getContentAsString(), expectedResult);

	}

	@Test
	public void insertCompany_companyDtoIsValid_returnsInsertedCompany() throws Exception {

		Mockito.doNothing().when(this.companyValidator).validateForInsert(Mockito.eq(createCompanyDto()));

		Mockito.when(this.companyService.addCompany(Mockito.eq(createCompanyDto()))).thenReturn(createCompanyDto());

		this.mvc.perform(post("/companies").contentType("application/json")
				.content(this.objectMapper.writeValueAsString(createCompanyDto())))
		.andExpect(jsonPath("$.address", equalTo("address"))).andExpect(jsonPath("$.name", equalTo("name")))
		.andExpect(jsonPath("$.phone", equalTo("+50936538992")));

	}

	@Test
	public void insertCompany_companyDtoIsInvalid_returnsValidationException() throws Exception {

		Mockito.doThrow(ValidationException.class).when(this.companyValidator)
		.validateForInsert(Mockito.eq(createCompanyDto()));

		final MvcResult result = this.mvc
				.perform(post("/companies").contentType("application/json")
						.content(this.objectMapper.writeValueAsString(createCompanyDto())))
				.andExpect(status().is4xxClientError()).andReturn();

		final Exception validationException = result.getResolvedException();

		assertTrue(validationException instanceof ValidationException);

	}

	@Test
	public void insertCompany_companyDtoIsValid_returnsServiceException() throws Exception {

		Mockito.doNothing().when(this.companyValidator).validateForInsert(Mockito.eq(createCompanyDto()));

		Mockito.when(this.companyService.addCompany(Mockito.eq(createCompanyDto()))).thenThrow(ServiceException.class);

		final MvcResult result = this.mvc
				.perform(post("/companies").contentType("application/json")
						.content(this.objectMapper.writeValueAsString(createCompanyDto())))
				.andExpect(status().is5xxServerError()).andReturn();

		final Exception serviceException = result.getResolvedException();

		assertTrue(serviceException instanceof ServiceException);

	}

	@Test
	public void updateCompany_companyDtoIsInvalid_returnsValidationException() throws Exception {

		Mockito.doThrow(ValidationException.class).when(this.companyValidator)
		.validateForUpdate(Mockito.eq(createCompanyDto()));

		final MvcResult result = this.mvc
				.perform(put("/companies").contentType("application/json")
						.content(this.objectMapper.writeValueAsString(createCompanyDto())))
				.andExpect(status().is4xxClientError()).andReturn();

		final Exception validationException = result.getResolvedException();

		assertTrue(validationException instanceof ValidationException);

	}

	@Test
	public void updateCompany_companyDtoIsValid_returnsUpdatedCompany() throws Exception {

		Mockito.when(this.companyService.updateCompany(Mockito.eq(createCompanyDto()))).thenReturn(createCompanyDto());

		this.mvc.perform(put("/companies").contentType("application/json")
				.content(this.objectMapper.writeValueAsString(createCompanyDto())))
		.andExpect(jsonPath("$.address", equalTo("address"))).andExpect(jsonPath("$.name", equalTo("name")))
		.andExpect(jsonPath("$.phone", equalTo("+50936538992"))).andReturn();

	}

	@Test
	public void updateCompany_companyDtoIsValid_returnsServiceException() throws Exception {

		Mockito.doNothing().when(this.companyValidator).validateForUpdate(Mockito.eq(createCompanyDto()));

		Mockito.when(this.companyService.updateCompany(Mockito.eq(createCompanyDto())))
		.thenThrow(ServiceException.class);

		final MvcResult result = this.mvc
				.perform(put("/companies").contentType("application/json")
						.content(this.objectMapper.writeValueAsString(createCompanyDto())))
				.andExpect(status().is5xxServerError()).andReturn();

		final Exception serviceException = result.getResolvedException();

		assertTrue(serviceException instanceof ServiceException);

	}

	@Test
	public void deleteCompany_companyDtoIsInvalid_returnsValidationException() throws Exception {

		Mockito.doThrow(ValidationException.class).when(this.companyValidator)
		.validateForDelete(Mockito.eq(createCompanyDto()));

		final MvcResult result = this.mvc
				.perform(delete("/companies").contentType("application/json")
						.content(this.objectMapper.writeValueAsString(createCompanyDto())))
				.andExpect(status().is4xxClientError()).andReturn();

		final Exception validationException = result.getResolvedException();

		assertTrue(validationException instanceof ValidationException);

	}

	@Test
	public void deleteCompany_companyDtoIsValid_returnsServiceException() throws Exception {

		Mockito.doNothing().when(this.companyValidator).validateForDelete(Mockito.eq(createCompanyDto()));

		Mockito.doThrow(ServiceException.class).when(this.companyService).deleteCompany(Mockito.eq(createCompanyDto()));

		final MvcResult result = this.mvc
				.perform(delete("/companies").contentType("application/json")
						.content(this.objectMapper.writeValueAsString(createCompanyDto())))
				.andExpect(status().is5xxServerError()).andReturn();

		final Exception serviceException = result.getResolvedException();

		assertTrue(serviceException instanceof ServiceException);
	}

	@Test
	public void deleteCompany_companyDtoIsValid_isSuccessful() throws Exception {

		Mockito.doNothing().when(this.companyValidator).validateForDelete(Mockito.eq(createCompanyDto()));
		Mockito.doNothing().when(this.companyService).deleteCompany(Mockito.eq(createCompanyDto()));

		this.mvc.perform(delete("/companies").contentType("application/json")
				.content(this.objectMapper.writeValueAsString(createCompanyDto()))).andExpect(status().isOk());

	}

	private CompanyDto createCompanyDto() {

		final CompanyDto companyDto = new CompanyDto();
		companyDto.setAddress("address");
		companyDto.setEmail("mail@mail.com");
		companyDto.setName("name");
		companyDto.setPhone("+50936538992");
		companyDto.setId(1);

		return companyDto;
	}

}
