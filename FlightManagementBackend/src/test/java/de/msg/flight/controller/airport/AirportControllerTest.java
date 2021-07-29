package de.msg.flight.controller.airport;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.msg.flight.dto.AirportDto;
import de.msg.flight.security.config.WebSecurityConfig;
import de.msg.flight.service.ServiceException;
import de.msg.flight.service.airport.IAirportService;
import de.msg.flight.validation.AirportValidator;
import de.msg.flight.validation.ValidationException;

//@AutoConfigureJsonTesters
@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
@Import(WebSecurityConfig.class)
@WebMvcTest(AirportController.class)
public class AirportControllerTest {

	private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IAirportService airportService;

	@MockBean
	private AirportValidator airportValidator;

//	@MockBean
//	private UserRepository userRepository;
//	
//	@MockBean
//	private CompanyRepository companyRepository;
//	
//	@MockBean
//	private FlightTemplateRepository flightTemplateRepository;
//	
//	@MockBean
//	private AirportRepository airportRepository;
//	
//	@MockBean
//	private ItineraryRepository itineraryRepository;
//
//	@MockBean
//	private PlaneRepository planeRepository;
	
	private List<AirportDto> airportDtos;

	private AirportDto airportDto;

	@Before
	public void init() {
		airportDto = getAirportDto();
		airportDtos = getAirportDtos();
	}

	@Test
	@WithMockUser(username = "user3", password = "password", roles={"CREW"})
	public void getAirportById_airportWithIdDoesNotExist_throwServiceException() throws Exception, ServiceException {

		Mockito.when(this.airportService.getAirportById(66)).thenThrow(ServiceException.class);

		this.mockMvc.perform(get("/airports/{id}", 66)).andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	@WithMockUser(username = "user3", password = "password", roles={"COMPANY_MANAGER"})
	public void getAirportById_airportWithIdDoesExist_returnResponseEntityOfAirportDto() throws Exception {

		Mockito.when(this.airportService.getAirportById(1)).thenReturn(airportDto);

		this.mockMvc.perform(get("/airports/{id}", 1)).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$.id", equalTo(1))).andExpect(jsonPath("$.address", equalTo("airport address")))
		.andExpect(jsonPath("$.airportCode", equalTo("KVJ"))).andExpect(jsonPath("$.city", equalTo("airport city")))
		.andExpect(jsonPath("$.name", equalTo("name")));
	}

	@Test
	@WithMockUser(username = "user3", password = "password", roles={"CREW"})
	public void getAllAirports_callSuccessful_returnResponseEntityOfAirportDtoList() throws Exception {

		Mockito.when(this.airportService.getAllAirports()).thenReturn(airportDtos);

		this.mockMvc.perform(get("/airports")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", equalTo(1)))
		.andExpect(jsonPath("$[0].address", equalTo("airport address")))
		.andExpect(jsonPath("$[0].airportCode", equalTo("KVJ"))).andExpect(jsonPath("$[0].city", equalTo("airport city")))
		.andExpect(jsonPath("$[0].name", equalTo("name"))).andExpect(jsonPath("$[1].id", equalTo(2)))
		.andExpect(jsonPath("$[1].address", equalTo("address2"))).andExpect(jsonPath("$[1].airportCode", equalTo("CBF")))
		.andExpect(jsonPath("$[1].city", equalTo("Paris"))).andExpect(jsonPath("$[1].name", equalTo("Airport number 2")));

		Mockito.verify(this.airportService, Mockito.times(1)).getAllAirports();
	}

	@Test
	@WithMockUser(username = "user3", password = "password", roles={"ADMIN"})
	public void insertAirport_airportDtoNotValid_throwValidationException() throws Exception {

		Mockito.doThrow(ValidationException.class).when(this.airportValidator).validateForInsert(airportDto);

		this.airportService.insertAirport(airportDto);

		this.mockMvc
				.perform(post("/airports").contentType(APPLICATION_JSON_UTF8)
						.content(convertObjectToJsonBytes(this.airportDto)))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "user3", password = "password", roles={"ADMIN"})
	public void insertAirport_airportDtoNotValid_throwServiceException() throws Exception {

		Mockito.doNothing().when(this.airportValidator).validateForInsert(airportDto);
		
		Mockito.doThrow(ServiceException.class).when(this.airportService).insertAirport(airportDto);

		this.mockMvc
				.perform(post("/airports").contentType(APPLICATION_JSON_UTF8)
						.content(convertObjectToJsonBytes(this.airportDto)))
				.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	@WithMockUser(username = "user3", password = "password", roles={"COMPANY_MANAGER"})	
	public void insertAirport_airportDtoIsValid_returnInsertedAirport() throws Exception {

		airportDto.setId(null);
		
		Mockito.doNothing().when(this.airportValidator).validateForInsert(airportDto);

		Mockito.when(this.airportService.insertAirport(airportDto)).thenReturn(getAirportDto());

		this.mockMvc
		.perform(post("/airports").contentType(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(this.airportDto)))
		.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", equalTo(1)))
		.andExpect(jsonPath("$.address", equalTo("airport address"))).andExpect(jsonPath("$.airportCode", equalTo("KVJ")))
		.andExpect(jsonPath("$.city", equalTo("airport city"))).andExpect(jsonPath("$.name", equalTo("name")));
	}

	@Test
	@WithMockUser(username = "user3", password = "password", roles={"ADMIN"})
	public void updateAirport_airportDtoNotValid_throwValidationException() throws Exception {

		Mockito.doThrow(ValidationException.class).when(this.airportValidator).validateForUpdate(airportDto);
		
		this.airportService.updateAirport(airportDto);

		this.mockMvc
				.perform(put("/airports").contentType(APPLICATION_JSON_UTF8)
						.content(convertObjectToJsonBytes(this.airportDto)))
				.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "user3", password = "password", roles={"COMPANY_MANAGER"})
	public void updateAirport_airportDtoNotValid_throwServiceException() throws Exception {

		Mockito.doNothing().when(this.airportValidator).validateForUpdate(airportDto);
		
		Mockito.doThrow(ServiceException.class).when(this.airportService).updateAirport(airportDto);

		this.mockMvc
				.perform(put("/airports").contentType(APPLICATION_JSON_UTF8)
						.content(convertObjectToJsonBytes(this.airportDto)))
				.andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	@WithMockUser(username = "user3", password = "password", roles={"COMPANY_MANAGER"})
	public void updateAirport_airportDtoIdValid_returnUpdatedAirport() throws Exception {

		AirportDto updatedAirport = getAirportDto();
		updatedAirport.setAddress("Updated address");

		Mockito.doNothing().when(this.airportValidator).validateForUpdate(updatedAirport);
		
		Mockito.when(this.airportService.updateAirport(airportDto)).thenReturn(updatedAirport);

		this.mockMvc
		.perform(put("/airports").contentType(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(this.airportDto)))
		.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id", equalTo(1)))
		.andExpect(jsonPath("$.address", equalTo("Updated address"))).andExpect(jsonPath("$.airportCode", equalTo("KVJ")))
		.andExpect(jsonPath("$.city", equalTo("airport city"))).andExpect(jsonPath("$.name", equalTo("name")));
	}

	@Test
	@WithMockUser(username = "user3", password = "password", roles={"ADMIN"})
	public void removeAirport_airportWithIdDoesNotExist_throwServiceException() throws Exception, ServiceException {

		Mockito.doThrow(ServiceException.class).when(this.airportService).removeAirport(22);

		this.mockMvc.perform(delete("/airports/{id}", 22)).andDo(print()).andExpect(status().isInternalServerError());
	}

	@Test
	@WithMockUser(username = "user3", password = "password", roles={"ADMIN"})
	public void removeAirport_airportWithIdDoesExist_removeAirportSuccessfully() throws Exception {

		this.mockMvc.perform(delete("/airports/{id}", 1)).andDo(print()).andExpect(status().isOk());
	}

	private List<AirportDto> getAirportDtos() {

		final List<AirportDto> airportDtoList = new ArrayList<>();

		final AirportDto airportDto = new AirportDto();
		airportDto.setId(1);
		airportDto.setAddress("airport address");
		airportDto.setAirportCode("KVJ");
		airportDto.setCity("airport city");
		airportDto.setName("name");

		final AirportDto airportDto2 = new AirportDto();
		airportDto2.setId(2);
		airportDto2.setAddress("address2");
		airportDto2.setAirportCode("CBF");
		airportDto2.setCity("Paris");
		airportDto2.setName("Airport number 2");

		airportDtoList.add(airportDto);
		airportDtoList.add(airportDto2);

		return airportDtoList;
	}

	private AirportDto getAirportDto() {

		final AirportDto airportDto = new AirportDto();

		airportDto.setId(1);
		airportDto.setAddress("airport address");
		airportDto.setAirportCode("KVJ");
		airportDto.setCity("airport city");
		airportDto.setName("name");

		return airportDto;
	}

	private byte[] convertObjectToJsonBytes(final Object object) throws IOException {

		final ObjectMapper mapper = new ObjectMapper();

		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		return mapper.writeValueAsBytes(object);
	}

}
