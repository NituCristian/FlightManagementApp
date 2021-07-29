package de.msg.flight.service.flighttemplate;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import de.msg.flight.dto.AirportDto;
import de.msg.flight.dto.FlightTemplateDto;
import de.msg.flight.dto.PlaneDto;
import de.msg.flight.persistence.model.Airport;
import de.msg.flight.persistence.model.FlightTemplate;
import de.msg.flight.persistence.model.Plane;
import de.msg.flight.persistence.repository.FlightTemplateRepository;
import de.msg.flight.service.ServiceException;
import de.msg.flight.validation.ValidationException;

@RunWith(MockitoJUnitRunner.class)
public class FlightTemplateServiceTest {

	@InjectMocks
	private FlightTemplateService flightTemplateService;

	@Mock
	private FlightTemplateRepository flightTemplateRepository;

	private FlightTemplate flightTemplate;

	private FlightTemplateDto flightTemplateDto;

	private List<FlightTemplateDto> flightTemplateDtos;

	private List<FlightTemplate> flightTemplates;

	@Before()
	public void init() {

		this.flightTemplate = initializeFlightTemplate();
		this.flightTemplateDto = flightTemplateMapper(this.flightTemplate);

		this.flightTemplateDtos = new ArrayList<>();
		this.flightTemplateDtos.add(this.flightTemplateDto);

		this.flightTemplates = new ArrayList<>();
		this.flightTemplates.add(this.flightTemplate);
	}

	@Test
	public void getFlightTemplateById_flightTemplateDoesExist_returnsFlightTemplate() throws ValidationException {

		Mockito.when(this.flightTemplateRepository.findById(Mockito.eq(1)))
				.thenReturn(Optional.of(this.flightTemplate));

		final FlightTemplateDto retrievedFlightTemplate = this.flightTemplateService
				.getFlightTemplateById(this.flightTemplate.getId());

		assertEquals(this.flightTemplateDto, retrievedFlightTemplate);
	}

	@Test(expected = ValidationException.class)
	public void getFlightTemplateById_flightTemplateDoesNotExist_throwsServiceException() throws ValidationException {

		Mockito.when(this.flightTemplateRepository.findById(Mockito.eq(100))).thenReturn(Optional.empty());

		this.flightTemplateService.getFlightTemplateById(100);
	}

	@Test
	public void getAllFlightTemplates_returnsListOfFlightTemplateDtos() {
		Mockito.when(this.flightTemplateRepository.findAll()).thenReturn(this.flightTemplates);

		final List<FlightTemplateDto> retrievedList = this.flightTemplateService.getAllFlightTemplates();

		assertEquals(this.flightTemplateDto.getId(), retrievedList.get(0).getId());

		assertEquals(retrievedList, this.flightTemplateDtos);
	}

	@Test
	public void insertFlightTemplate_dtoIsValidForInsert_returnsTheNewFlightTemplateDto() throws ServiceException {

		final FlightTemplate newFlightTemplate = initializeFlightTemplate();

		this.flightTemplate.setId(null);

		this.flightTemplateDto.setId(null);

		Mockito.when(this.flightTemplateRepository.save(Mockito.eq(this.flightTemplate))).thenReturn(newFlightTemplate);

		final FlightTemplateDto insertedFlightTemplate = this.flightTemplateService
				.insertFlightTemplate(this.flightTemplateDto);

		this.flightTemplateDto.setId(newFlightTemplate.getId());

		assertEquals(this.flightTemplateDto, insertedFlightTemplate);
	}

	@Test
	public void updateFlightTemplate_dtoIsValid_returnsUpdatedFlightTemplateDto() {

		final FlightTemplate updatedFlightTemplate = initializeFlightTemplate();

		this.flightTemplate.setName("UpdatedName");

		Mockito.when(this.flightTemplateRepository.save(Mockito.eq(updatedFlightTemplate)))
				.thenReturn(this.flightTemplate);

		this.flightTemplateDto.setName("UpdatedName");

		final FlightTemplateDto updatedFlightTemplateDto = this.flightTemplateService
				.updateFlightTemplate(flightTemplateMapper(updatedFlightTemplate));

		assertEquals(this.flightTemplateDto, updatedFlightTemplateDto);
	}

	@Test
	public void removeFlightTemplate_idValid_deletesFlightTemplate() throws ServiceException {

		this.flightTemplateService.removeFlightTemplate(this.flightTemplate.getId());

		Mockito.verify(this.flightTemplateRepository).deleteById(Mockito.eq(this.flightTemplate.getId()));
	}

	@Test(expected = ServiceException.class)
	public void removeFlightTemplate_flightTemplateWithGivenIdDoesNotExist_deletesFlightTemplate()
			throws ServiceException {

		Mockito.doThrow(EmptyResultDataAccessException.class).when(this.flightTemplateRepository)
				.deleteById(Mockito.eq(1000));

		this.flightTemplateService.removeFlightTemplate(1000);

		Mockito.verify(this.flightTemplateRepository).deleteById(Mockito.eq(1000));
	}

	private FlightTemplate initializeFlightTemplate() {
		final FlightTemplate flightTemplate = new FlightTemplate();

		final Airport arrivalAirport = new Airport();
		arrivalAirport.setId(1);
		arrivalAirport.setAirportCode("OTP");
		arrivalAirport.setAddress("Calea Bucureștilor nr. 224, Otopeni, județul Ilfov");
		arrivalAirport.setCity("Bucuresti");

		final Airport departureAirport = new Airport();
		departureAirport.setId(1);
		departureAirport.setAirportCode("CLJ");
		departureAirport.setAddress("Adresa");
		departureAirport.setCity("Cluj-Napoca");

		final Plane plane = new Plane();
		plane.setId(1);
		plane.setCode("GSFUJGBLIK");
		plane.setName("Airbus300Blue");
		plane.setModel("Airbus300");
		plane.setNumberOfPassengers(200);

		flightTemplate.setId(1);
		flightTemplate.setName("Template 1");
		flightTemplate.setArrivalTime(LocalTime.parse("12:00:00"));
		flightTemplate.setDepartureTime(LocalTime.parse("12:00:00"));
		flightTemplate.setArrivalAirport(arrivalAirport);
		flightTemplate.setDepartureAirport(departureAirport);
		flightTemplate.setPlane(plane);

		return flightTemplate;
	}

	private FlightTemplateDto flightTemplateMapper(final FlightTemplate flightTemplate) {

		final FlightTemplateDto dto = new FlightTemplateDto();

		dto.setId(flightTemplate.getId());
		dto.setName(flightTemplate.getName());
		dto.setArrivalTime(flightTemplate.getArrivalTime());
		dto.setDepartureTime(flightTemplate.getDepartureTime());
		dto.setArrivalAirport(airportMapper(flightTemplate.getArrivalAirport()));
		dto.setDepartureAirport(airportMapper(flightTemplate.getDepartureAirport()));
		dto.setPlane(planeMapper(flightTemplate.getPlane()));

		return dto;
	}

	private AirportDto airportMapper(final Airport airport) {

		final AirportDto dto = new AirportDto();

		dto.setId(airport.getId());
		dto.setAirportCode(airport.getAirportCode());
		dto.setAddress(airport.getAddress());
		dto.setName(airport.getName());
		dto.setCity(airport.getCity());

		return dto;
	}

	private PlaneDto planeMapper(final Plane plane) {

		final PlaneDto dto = new PlaneDto();

		dto.setId(plane.getId());
		dto.setCode(plane.getCode());
		dto.setModel(plane.getModel());
		dto.setName(plane.getName());
		dto.setNumberOfPassengers(plane.getNumberOfPassengers());

		return dto;
	}

}
