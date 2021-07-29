package de.msg.flight.controller.plane;

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

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
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

import de.msg.flight.controller.plane.PlaneController;
import de.msg.flight.dto.CompanyDto;
import de.msg.flight.dto.PlaneDto;
import de.msg.flight.service.ServiceException;
import de.msg.flight.service.plane.IPlaneService;
import de.msg.flight.validation.PlaneValidator;
import de.msg.flight.validation.ValidationException;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = PlaneController.class)
public class PlaneControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private IPlaneService planeService;
	
	@MockBean
	private PlaneValidator planeValidator;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void getById_planeExists_returnsPlane() throws Exception {

		Mockito.when(this.planeService.getPlaneById(1)).thenReturn(createPlaneDto());

		this.mvc.perform(get("/planes/{id}", 1))
				.andExpect(jsonPath("$.code", equalTo("ABCD")))
				.andExpect(jsonPath("$.model", equalTo("Airbus 500")))
				.andExpect(jsonPath("$.company.phone", equalTo("+50936538992")))
				.andExpect(status().isOk());

	}

	@Test
	public void getById_planeDoesNotExist_returnsNull() throws Exception {

		Mockito.when(this.planeService.getPlaneById(1)).thenReturn(createPlaneDto());

		final MvcResult mvcResult = this.mvc.perform(get("/planes/{id}", 2))
				.andExpect(status().isOk()).andReturn();
		String result= mvcResult.getResponse().getContentAsString();
		
		assertTrue(StringUtils.isBlank(result));
	}

	@Test
	public void getAll_planesExist_returnsListOfPlanes() throws Exception {

		Mockito.when(this.planeService.getAllPlanes()).thenReturn(Arrays.asList(createPlaneDto()));
				this.mvc.perform(get("/planes"))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].code", equalTo("ABCD")))
				.andExpect(jsonPath("$[0].model", equalTo("Airbus 500")))
				.andExpect(jsonPath("$[0].numberOfPassengers", equalTo(200)))
				.andExpect(jsonPath("$[0].id", equalTo(1)))
				.andExpect(jsonPath("$[0].company.address", equalTo("address")))
				.andExpect(jsonPath("$[0].company.email", equalTo("mail@mail.com")))
				.andExpect(jsonPath("$[0].company.phone", equalTo("+50936538992")))
				.andExpect(jsonPath("$[0].company.name", equalTo("name")));

	}
	
	
	@Test
	public void getAll_noPlanesExist_returnsEmptyList() throws Exception {

		Mockito.when(this.planeService.getAllPlanes()).thenReturn(Arrays.asList());
				this.mvc.perform(get("/planes"))
				.andExpect(jsonPath("$", hasSize(0)));

	}
	
	

	@Test
	public void insert_planeDtoIsValid_returnsInsertedPlane() throws  Exception {

		Mockito.doNothing().when(this.planeValidator).validateForInsert(Mockito.eq(createPlaneDto()));

		Mockito.when(this.planeService.insertPlane(Mockito.eq(createPlaneDto()))).thenReturn(createPlaneDto());

		this.mvc.perform(post("/planes").contentType("application/json")
				.content(this.objectMapper.writeValueAsString(createPlaneDto())))
				.andExpect(jsonPath("$.code", equalTo("ABCD")))
				.andExpect(jsonPath("$.model", equalTo("Airbus 500")))
				.andExpect(jsonPath("$.company.phone", equalTo("+50936538992")))
				.andExpect(status().isOk());

	}

	@Test
	public void insert_planeDtoIsInvalid_returnsValidationException() throws Exception {

		Mockito.doThrow(ValidationException.class).when(this.planeValidator).validateForInsert(Mockito.eq(createPlaneDto()));
		

		final MvcResult result = this.mvc
				.perform(post("/planes").contentType("application/json")
				.content(this.objectMapper.writeValueAsString(createPlaneDto())))
				.andExpect(status().is4xxClientError()).andReturn();

		final Exception validationException = result.getResolvedException();

		assertTrue(validationException instanceof ValidationException);

	}

	@Test
	public void insert_planeDtoIsValid_returnsServiceException() throws Exception {

		Mockito.doNothing().when(this.planeValidator).validateForInsert(Mockito.eq(createPlaneDto()));

		Mockito.when(this.planeService.insertPlane(Mockito.eq(createPlaneDto()))).thenThrow(ServiceException.class);

		final MvcResult result = this.mvc
				.perform(post("/planes").contentType("application/json")
				.content(this.objectMapper.writeValueAsString(createPlaneDto())))
				.andExpect(status().is5xxServerError()).andReturn();

		final Exception serviceException = result.getResolvedException();

		assertTrue(serviceException instanceof ServiceException);

	}

	@Test
	public void update_planeDtoIsInvalid_returnsValidationException() throws Exception {

		Mockito.doThrow(ValidationException.class).when(this.planeValidator).validateForUpdate(Mockito.eq(createPlaneDto()));

		final MvcResult result = this.mvc
				.perform(put("/planes").contentType("application/json")
				.content(this.objectMapper.writeValueAsString(createPlaneDto())))
				.andExpect(status().is4xxClientError()).andReturn();

		final Exception validationException = result.getResolvedException();

		assertTrue(validationException instanceof ValidationException);

	}

	@Test
	public void update_planeDtoIsValid_returnsUpdatedPlane() throws Exception {
		
		Mockito.doNothing().when(this.planeValidator).validateForUpdate(Mockito.eq(createPlaneDto()));

		Mockito.when(this.planeService.updatePlane(Mockito.eq(createPlaneDto()))).thenReturn(createPlaneDto());

		this.mvc.perform(put("/planes").contentType("application/json")
				.content(this.objectMapper.writeValueAsString(createPlaneDto())))
				.andExpect(jsonPath("$.code", equalTo("ABCD")))
				.andExpect(jsonPath("$.model", equalTo("Airbus 500")))
				.andExpect(jsonPath("$.company.phone", equalTo("+50936538992")))
				.andExpect(status().isOk()).andReturn();

	}

	@Test
	public void update_planeDtoIsValid_returnsServiceException() throws Exception {

		Mockito.doNothing().when(this.planeValidator).validateForUpdate(Mockito.eq(createPlaneDto()));

		Mockito.when(this.planeService.updatePlane(Mockito.eq(createPlaneDto()))).thenThrow(ServiceException.class);

		final MvcResult result = this.mvc
				.perform(put("/planes").contentType("application/json")
				.content(this.objectMapper.writeValueAsString(createPlaneDto())))
				.andExpect(status().is5xxServerError()).andReturn();

		final Exception serviceException = result.getResolvedException();

		assertTrue(serviceException instanceof ServiceException);

	}

	@Test
	public void delete_planeDtoIsInvalid_returnsValidationException() throws Exception {

		Mockito.doThrow(ValidationException.class).when(this.planeValidator).validateForDelete(Mockito.eq(createPlaneDto()));

		final MvcResult result = this.mvc
				.perform(delete("/planes").contentType("application/json")
				.content(this.objectMapper.writeValueAsString(createPlaneDto())))
				.andExpect(status().is4xxClientError()).andReturn();

		final Exception validationException = result.getResolvedException();

		assertTrue(validationException instanceof ValidationException);

	}

	@Test
	public void delete_planeDtoIsValid_returnsServiceException() throws Exception {

		Mockito.doNothing().when(this.planeValidator).validateForDelete(Mockito.eq(createPlaneDto()));

		Mockito.when(this.planeService.deletePlane(Mockito.eq(createPlaneDto()))).thenThrow(ServiceException.class);

		final MvcResult result = this.mvc
				.perform(delete("/planes").contentType("application/json")
				.content(this.objectMapper.writeValueAsString(createPlaneDto())))
				.andExpect(status().is5xxServerError()).andReturn();

		final Exception serviceException = result.getResolvedException();

		assertTrue(serviceException instanceof ServiceException);
	}

	@Test
	public void delete_planeDtoIsValid_returnsDeletedPlane() throws  Exception {
		
		Mockito.doNothing().when(this.planeValidator).validateForDelete(Mockito.eq(createPlaneDto()));

		Mockito.when(this.planeService.deletePlane(Mockito.eq(createPlaneDto()))).thenReturn(createPlaneDto());

		this.mvc.perform(delete("/planes").contentType("application/json")
				.content(this.objectMapper.writeValueAsString(createPlaneDto())))
				.andExpect(status().isOk()).andReturn();
	}
	
	@Test
	public void getPlanesByCompanyId_companyExistsAndHasPlanes_returnsPlanes() throws Exception {

		Mockito.when(this.planeService.getPlanesByCompanyId(1)).thenReturn(Arrays.asList(createPlaneDto()));
		this.mvc.perform(post("/planes/company")
				.content(this.objectMapper.writeValueAsString(createCompanyDto())).contentType("application/json"))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].code", equalTo("ABCD")))
				.andExpect(jsonPath("$[0].model", equalTo("Airbus 500")))
				.andExpect(jsonPath("$[0].numberOfPassengers", equalTo(200)))
				.andExpect(jsonPath("$[0].id", equalTo(1)))
				.andExpect(jsonPath("$[0].company.address", equalTo("address")))
				.andExpect(jsonPath("$[0].company.email", equalTo("mail@mail.com")))
				.andExpect(jsonPath("$[0].company.phone", equalTo("+50936538992")))
				.andExpect(jsonPath("$[0].company.name", equalTo("name")));

	}

	@Test
	public void getPlanesByCompanyId_companyExistsAndHasNoPlanes_returnsEmptyList() throws Exception {

		Mockito.when(this.planeService.getPlanesByCompanyId(1)).thenReturn(Arrays.asList());

		final MvcResult result = this.mvc.perform(post("/planes/company")
				.content(this.objectMapper.writeValueAsString(createCompanyDto())).contentType("application/json"))
				.andReturn();
		final String expectedResult = "[]";
		assertEquals(result.getResponse().getContentAsString(), expectedResult);

	}

	private PlaneDto createPlaneDto() {

		final PlaneDto planeDto = new PlaneDto();
		planeDto.setCode("ABCD");
		planeDto.setCompany(createCompanyDto());
		planeDto.setModel("Airbus 500");
		planeDto.setNumberOfPassengers(200);
		planeDto.setName("PLANE NAME");
		planeDto.setId(1);
		return planeDto;
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
