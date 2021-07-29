package de.msg.flight.service.airport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import de.msg.flight.dto.AirportDto;
import de.msg.flight.persistence.model.Airport;
import de.msg.flight.persistence.repository.AirportRepository;
import de.msg.flight.service.ServiceException;
import de.msg.flight.validation.AirportValidator;

@RunWith(MockitoJUnitRunner.class)
public class AirportServiceTest {

	@InjectMocks
	private AirportService airportService;

	@Mock
	private AirportRepository airportRepository;

	@Mock
	private AirportValidator airportValidator;

	private Airport airport;

	private AirportDto airportDtoToGet;

	private List<AirportDto> airportDtoList;

	private List<Airport> airportList;

	@Before
	public void init() {
		airport = getAirport();
		airportDtoToGet = getAirportDto();
		airportDtoList = getAirportDtoList();
		airportList = getAirportList();
	}

	@Test(expected = ServiceException.class)
	public void getAirportById_airportDoesNotExist_throwServiceException() throws ServiceException {

		airportService.getAirportById(airportDtoToGet.getId());
	}

	@Test
	public void getAirportById_airportWithIdDoesExist_returnAirportDto() throws ServiceException {

		Mockito.when(airportRepository.findById(2)).thenReturn(Optional.of(airport));

		AirportDto retrievedAirport = this.airportService.getAirportById(airport.getId());

		assertEquals(airportDtoToGet, retrievedAirport);
	}

	@Test
	public void getAllAirports_notEmptyList_returnAirportDtoList() {

		Mockito.when(airportRepository.findAll()).thenReturn(airportList);

		List<AirportDto> retrievedAirportDtoList = this.airportService.getAllAirports();

		assertTrue(listsAreEquals(retrievedAirportDtoList, airportDtoList));
	}

	@Test
	public void getAllAirports_emptyList_returnAirportDtoList() {

		List<Airport> emptyAirportList = new ArrayList<>();
		List<AirportDto> emptyAirportDtoList = new ArrayList<>();

		Mockito.when(airportRepository.findAll()).thenReturn(emptyAirportList);

		List<AirportDto> retrievedAirportDtoList = this.airportService.getAllAirports();

		assertTrue(listsAreEquals(retrievedAirportDtoList, emptyAirportDtoList));
	}

	@Test(expected = ServiceException.class)
	public void insertAirport_airportDtoNotValidToInsert_throwDataIntegrityViolationException()
			throws ServiceException {

		Mockito.when(this.airportRepository.save(airport)).thenThrow(DataIntegrityViolationException.class);

		this.airportService.insertAirport(airportDtoToGet);
	}

	@Test
	public void insertAirport_airportDtoInsertedSuccessfully_returnAirportDto() throws ServiceException {

		airportDtoToGet.setId(null);
		airport.setId(null);

		Mockito.when(airportRepository.save(Mockito.eq(airport))).thenReturn(getAirport());

		AirportDto retrievedAirport = this.airportService.insertAirport(airportDtoToGet);

		airportDtoToGet.setId(getAirport().getId());

		assertEquals(airportDtoToGet, retrievedAirport);
	}

	@Test(expected = ServiceException.class)
	public void updateAirport_airportDtoNotValidToUpdate_throwDataIntegrityViolationException()
			throws ServiceException {

		Mockito.when(this.airportRepository.save(airport)).thenThrow(DataIntegrityViolationException.class);

		this.airportService.updateAirport(airportDtoToGet);
	}

	@Test
	public void updateAirport_airportDtoUpdatedSuccessfully_returnAirportDto() throws ServiceException {

		Airport updatedAirport = getAirport();
		updatedAirport.setAddress("new");

		Mockito.when(this.airportRepository.save(Mockito.eq(airport))).thenReturn(updatedAirport);

		AirportDto retrievedAirport = this.airportService.updateAirport(airportDtoToGet);

		airportDtoToGet.setAddress("new");

		assertEquals(airportDtoToGet, retrievedAirport);
	}

	@Test(expected = ServiceException.class)
	public void removeAirport_airportWithSpecifiedIdDoesNotExist_throwEmptyResultDataAccessException()
			throws ServiceException {

		Mockito.doThrow(EmptyResultDataAccessException.class).when(this.airportRepository)
		.deleteById(Mockito.eq(airport.getId()));

		this.airportService.removeAirport(airport.getId());
	}

	@Test
	public void removeAirport_airportWithSpecifiedIdDoesExist_airportDtoDeletedSuccessfully() throws ServiceException {

		this.airportService.removeAirport(airport.getId());

		Mockito.verify(this.airportRepository).deleteById(Mockito.eq(airport.getId()));
	}

	private boolean listsAreEquals(List<AirportDto> firstList, List<AirportDto> secondList) {

		if (firstList.size() != secondList.size()) {
			return false;
		}

		for (int i = 0; i < firstList.size(); i++) {
			if (!firstList.get(i).equals(secondList.get(i))) {
				return false;
			}
		}

		return true;
	}

	private Airport getAirport() {

		Airport airport = new Airport();

		airport.setId(2);
		airport.setAddress("airport address");
		airport.setAirportCode("KVJ");
		airport.setCity("airport city");
		airport.setName("name");

		return airport;
	}

	private AirportDto getAirportDto() {

		AirportDto airportDto = new AirportDto();

		airportDto.setId(2);
		airportDto.setAddress("airport address");
		airportDto.setAirportCode("KVJ");
		airportDto.setCity("airport city");
		airportDto.setName("name");

		return airportDto;
	}

	private List<AirportDto> getAirportDtoList() {

		final List<AirportDto> airportDtoList = new ArrayList<>();
		AirportDto airportDto = getAirportDto();
		AirportDto airportDto2 = new AirportDto();

		airportDto2.setId(4);
		airportDto2.setAddress("address2");
		airportDto2.setAirportCode("CBF");
		airportDto2.setCity("Paris");
		airportDto2.setName("Airport number 2");

		airportDtoList.add(airportDto);
		airportDtoList.add(airportDto2);

		return airportDtoList;
	}

	private List<Airport> getAirportList() {

		final List<Airport> airportList = new ArrayList<>();

		Airport airport1 = new Airport();
		airport1.setId(2);
		airport1.setAddress("airport address");
		airport1.setAirportCode("KVJ");
		airport1.setCity("airport city");
		airport1.setName("name");

		Airport airport2 = new Airport();
		airport2.setId(4);
		airport2.setAddress("address2");
		airport2.setAirportCode("CBF");
		airport2.setCity("Paris");
		airport2.setName("Airport number 2");

		airportList.add(airport1);
		airportList.add(airport2);

		return airportList;
	}
}
