package de.msg.flight.controller.flighttemplate;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.msg.flight.dto.AirportDto;
import de.msg.flight.dto.FlightTemplateDto;
import de.msg.flight.dto.PlaneDto;
import de.msg.flight.persistence.repository.FlightTemplateRepository;
import de.msg.flight.service.ServiceException;
import de.msg.flight.service.flighttemplate.IFlightTemplateService;
import de.msg.flight.validation.FlightTemplateValidator;
import de.msg.flight.validation.ValidationException;

@AutoConfigureJsonTesters
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@WebMvcTest(FlightTemplateController.class)
public class FlightTemplateControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IFlightTemplateService flightTemplateService;

	@MockBean
	private FlightTemplateRepository flightTemplateRepository;

	@MockBean
	private FlightTemplateValidator validator;

	@Autowired
	private ObjectMapper objectMapper;

	private FlightTemplateDto flightTemplateDto;

	private List<FlightTemplateDto> flightTemplateDtos;

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Before
	public void init() {
		this.flightTemplateDto = initializeFlightTemplateDto();

		this.flightTemplateDtos = new ArrayList<>();
		this.flightTemplateDtos.add(this.flightTemplateDto);
	}

	@Test
	public void getFlightTemplateById_flightTemplateWithIdDoesNotExist_throwServiceException()
			throws Exception, ServiceException {

		Mockito.when(this.flightTemplateService.getFlightTemplateById(100)).thenThrow(ServiceException.class);

		this.mockMvc.perform(get("/flight_templates/{id}", 100)).andDo(print())
		.andExpect(status().isInternalServerError());
	}

	@Test
	public void getFlightTemplateById_flightTemplateWithIdDoesExist_returnsFlightTemplateDto() throws Exception {

		Mockito.when(this.flightTemplateService.getFlightTemplateById(1))
		.thenReturn(this.flightTemplateDto);

		this.mockMvc.perform(get("/flight_templates/{id}", 1)).andDo(print()).andExpect(jsonPath("$.id", equalTo(1)))
		.andExpect(jsonPath("$.name", equalTo(this.flightTemplateDto.getName())))
		.andExpect(jsonPath("$.arrivalTime", equalTo(this.flightTemplateDto.getArrivalTime().toString())))
		.andExpect(jsonPath("$.departureTime", equalTo(this.flightTemplateDto.getDepartureTime().toString())))
		.andExpect(jsonPath("$.arrivalAirport.id", equalTo(this.flightTemplateDto.getArrivalAirport().getId())))
		.andExpect(jsonPath("$.departureAirport.id",
				equalTo(this.flightTemplateDto.getDepartureAirport().getId())))
		.andExpect(jsonPath("$.plane.id", equalTo(this.flightTemplateDto.getPlane().getId())));
	}

	@Test
	public void getAllFlightTemplates_flightTemplatesFound_returnFlightTemplatesList() throws Exception {

		Mockito.when(this.flightTemplateService.getAllFlightTemplates()).thenReturn(this.flightTemplateDtos);

		this.mockMvc.perform(get("/flight_templates")).andExpect(status().isOk())
		.andExpect(jsonPath("$[0].name", equalTo(this.flightTemplateDto.getName())))
		.andExpect(jsonPath("$[0].arrivalTime", equalTo(this.flightTemplateDto.getArrivalTime().toString())))
		.andExpect(
				jsonPath("$[0].departureTime", equalTo(this.flightTemplateDto.getDepartureTime().toString())))
		.andExpect(
				jsonPath("$[0].arrivalAirport.id", equalTo(this.flightTemplateDto.getArrivalAirport().getId())))
		.andExpect(jsonPath("$[0].departureAirport.id",
				equalTo(this.flightTemplateDto.getDepartureAirport().getId())))
		.andExpect(jsonPath("$[0].plane.id", equalTo(this.flightTemplateDto.getPlane().getId())));
		;

		verify(this.flightTemplateService, times(1)).getAllFlightTemplates();
	}

	@Test
	public void insertFlightTemplate_flightTemplateDtoHasId_throwValidationException() throws Exception {

		Mockito.doThrow(ValidationException.class).when(this.validator).validateForInsert(this.flightTemplateDto);

		this.mockMvc
		.perform(post("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(this.flightTemplateDto)))
		.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void insertFlightTemplate_flightTemplateDtoIsNull_throwValidationException() throws Exception {

		Mockito.doThrow(ValidationException.class).when(this.validator).validateForInsert(null);

		this.mockMvc
		.perform(post("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(null)))
		.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void insertFlightTemplate_flightTemplateDtoHasMissingName_throwValidationException() throws Exception {

		this.flightTemplateDto.setId(null);
		this.flightTemplateDto.setName(null);

		Mockito.doThrow(ValidationException.class).when(this.validator).validateForInsert(this.flightTemplateDto);

		this.mockMvc
		.perform(post("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(this.flightTemplateDto)))
		.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void insertFlightTemplate_flightTemplateDtoHasMissingArrivalTime_throwValidationException()
			throws Exception {

		this.flightTemplateDto.setId(null);
		this.flightTemplateDto.setArrivalTime(null);

		Mockito.doThrow(ValidationException.class).when(this.validator).validateForInsert(this.flightTemplateDto);

		this.mockMvc
		.perform(post("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(this.flightTemplateDto)))
		.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void insertFlightTemplate_flightTemplateDtoHasMissingDepartureTime_throwValidationException()
			throws Exception {

		this.flightTemplateDto.setId(null);
		this.flightTemplateDto.setDepartureTime(null);

		Mockito.doThrow(ValidationException.class).when(this.validator).validateForInsert(this.flightTemplateDto);

		this.mockMvc
		.perform(post("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(this.flightTemplateDto)))
		.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void insertFlightTemplate_flightTemplateDtoHasMissingArrivalAirport_throwValidationException()
			throws Exception {

		this.flightTemplateDto.setId(null);
		this.flightTemplateDto.setArrivalAirport(null);

		Mockito.doThrow(ValidationException.class).when(this.validator).validateForInsert(this.flightTemplateDto);

		this.mockMvc
		.perform(post("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(this.flightTemplateDto)))
		.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void insertFlightTemplate_flightTemplateDtoHasMissingDepartureAirport_throwValidationException()
			throws Exception {

		this.flightTemplateDto.setId(null);
		this.flightTemplateDto.setDepartureAirport(null);

		Mockito.doThrow(ValidationException.class).when(this.validator).validateForInsert(this.flightTemplateDto);

		this.mockMvc
		.perform(post("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(this.flightTemplateDto)))
		.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void insertFlightTemplate_flightTemplateDtoHasMissingPlane_throwValidationException() throws Exception {

		this.flightTemplateDto.setId(null);
		this.flightTemplateDto.setPlane(null);

		Mockito.doThrow(ValidationException.class).when(this.validator).validateForInsert(this.flightTemplateDto);

		this.mockMvc
		.perform(post("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(this.flightTemplateDto)))
		.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void insertFlightTemplate_flightTemplateIsValid_returnsNewFlightTemplate() throws Exception {

		this.flightTemplateDto.setId(null);

		Mockito.when(this.flightTemplateService.insertFlightTemplate(this.flightTemplateDto))
		.thenReturn(this.initializeFlightTemplateDto());

		this.mockMvc
		.perform(post("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(this.flightTemplateDto)))
		.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", equalTo(1)))
		.andExpect(jsonPath("$.name", equalTo(this.flightTemplateDto.getName())))
		.andExpect(jsonPath("$.arrivalTime", equalTo(this.flightTemplateDto.getArrivalTime().toString())))
		.andExpect(jsonPath("$.departureTime", equalTo(this.flightTemplateDto.getDepartureTime().toString())))
		.andExpect(jsonPath("$.arrivalAirport.id", equalTo(this.flightTemplateDto.getArrivalAirport().getId())))
		.andExpect(jsonPath("$.departureAirport.id",
				equalTo(this.flightTemplateDto.getDepartureAirport().getId())))
		.andExpect(jsonPath("$.plane.id", equalTo(this.flightTemplateDto.getPlane().getId())));
		;
	}

	@Test
	public void updateFlightTemplate_flightTemplateDtoIsNull_throwValidationException() throws Exception {

		Mockito.doThrow(ValidationException.class).when(this.validator).validateForUpdate(null);

		this.mockMvc
		.perform(put("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(null)))
		.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void updateFlightTemplate_flightTemplateDtoHasMissingId_throwValidationException() throws Exception {

		this.flightTemplateDto.setId(null);

		Mockito.doThrow(ValidationException.class).when(this.validator).validateForUpdate(flightTemplateDto);

		this.mockMvc
		.perform(put("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(this.flightTemplateDto)))
		.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void updateFlightTemplate_flightTemplateDtoHasMissingName_throwValidationException() throws Exception {

		this.flightTemplateDto.setName(null);

		Mockito.doThrow(ValidationException.class).when(this.validator).validateForUpdate(flightTemplateDto);

		this.mockMvc
		.perform(put("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(this.flightTemplateDto)))
		.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void updateFlightTemplate_flightTemplateDtoHasMissingArrivalTime_throwValidationException()
			throws Exception {

		this.flightTemplateDto.setArrivalTime(null);

		Mockito.doThrow(ValidationException.class).when(this.validator).validateForUpdate(flightTemplateDto);

		this.mockMvc
		.perform(put("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(this.flightTemplateDto)))
		.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void updateFlightTemplate_flightTemplateDtoHasMissingDepartureTime_throwValidationException()
			throws Exception {

		this.flightTemplateDto.setDepartureTime(null);

		Mockito.doThrow(ValidationException.class).when(this.validator).validateForUpdate(flightTemplateDto);

		this.mockMvc
		.perform(put("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(this.flightTemplateDto)))
		.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void updateFlightTemplate_flightTemplateDtoHasMissingArrivalAirport_throwValidationException()
			throws Exception {

		this.flightTemplateDto.setArrivalAirport(null);

		Mockito.doThrow(ValidationException.class).when(this.validator).validateForUpdate(flightTemplateDto);

		this.mockMvc
		.perform(put("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(this.flightTemplateDto)))
		.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void updateFlightTemplate_flightTemplateDtoHasMissingDepartureAirport_throwValidationException()
			throws Exception {

		this.flightTemplateDto.setDepartureAirport(null);

		Mockito.doThrow(ValidationException.class).when(this.validator).validateForUpdate(flightTemplateDto);

		this.mockMvc
		.perform(put("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(this.flightTemplateDto)))
		.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void updateFlightTemplate_flightTemplateDtoHasMissingPlane_throwValidationException() throws Exception {

		this.flightTemplateDto.setPlane(null);

		Mockito.doThrow(ValidationException.class).when(this.validator).validateForUpdate(flightTemplateDto);

		this.mockMvc
		.perform(put("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(this.flightTemplateDto)))
		.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void updateFlightTemplate_flightTemplateIsValid_returnsNewFlightTemplate() throws Exception {

		this.flightTemplateDto.setName("Template2");
		Mockito.when(this.flightTemplateService.updateFlightTemplate(this.flightTemplateDto))
		.thenReturn(this.flightTemplateDto);

		this.mockMvc
		.perform(put("/flight_templates/").contentType(APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(this.flightTemplateDto)))
		.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", equalTo(1)))
		.andExpect(jsonPath("$.name", equalTo(this.flightTemplateDto.getName())))
		.andExpect(jsonPath("$.arrivalTime", equalTo(this.flightTemplateDto.getArrivalTime().toString())))
		.andExpect(jsonPath("$.departureTime", equalTo(this.flightTemplateDto.getDepartureTime().toString())))
		.andExpect(jsonPath("$.arrivalAirport.id", equalTo(this.flightTemplateDto.getArrivalAirport().getId())))
		.andExpect(jsonPath("$.departureAirport.id",
				equalTo(this.flightTemplateDto.getDepartureAirport().getId())))
		.andExpect(jsonPath("$.plane.id", equalTo(this.flightTemplateDto.getPlane().getId())));
	}

	@Test
	public void removeFlightTemplate_idValid_deletesFlightTemplate() throws Exception {

		this.mockMvc.perform(delete("/flight_templates/{id}", 1)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void removeFlightTemplate_idIdInvalid_throwsServiceException() throws Exception {

		Mockito.doThrow(ServiceException.class).when(this.flightTemplateService).removeFlightTemplate(Mockito.eq(100));

		this.mockMvc.perform(delete("/flight_templates/{id}", 100)).andDo(print())
		.andExpect(status().isInternalServerError());
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
		flightTemplateDto.setArrivalTime(LocalTime.parse("12:30:01"));
		flightTemplateDto.setDepartureTime(LocalTime.parse("12:30:01"));
		flightTemplateDto.setArrivalAirport(arrivalAirportDto);
		flightTemplateDto.setDepartureAirport(departureAirportDto);
		flightTemplateDto.setPlane(planeDto);

		Set<FlightTemplateDto> flightTemplateDtos = new HashSet<>();
		flightTemplateDtos.add(flightTemplateDto);

		return flightTemplateDto;
	}
}
