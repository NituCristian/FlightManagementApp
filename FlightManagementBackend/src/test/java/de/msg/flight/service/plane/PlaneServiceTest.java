package de.msg.flight.service.plane;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import de.msg.flight.dto.PlaneDto;
import de.msg.flight.mapper.plane.PlaneMapper;
import de.msg.flight.persistence.model.Company;
import de.msg.flight.persistence.model.Plane;
import de.msg.flight.persistence.repository.PlaneRepository;
import de.msg.flight.service.ServiceException;

@RunWith(MockitoJUnitRunner.class)
public class PlaneServiceTest {

	@InjectMocks
	private PlaneService planeService;

	@Mock
	private PlaneRepository planeRepository;

	@Test
	public void getById_companyExists_returnsCompanyDto() throws ServiceException {

		Mockito.when(this.planeRepository.findById(1)).thenReturn(this.createCompletePlane());

		assertEquals(createCompletePlaneDto(), this.planeService.getPlaneById(1));

	}

	@Test
	public void getById_companyDoesNotExist_returnsNull() throws ServiceException {

		assertNull(this.planeService.getPlaneById(2));
	}

	@Test
	public void update_planeDtoIsValid_isSuccessful() throws ServiceException {

		final PlaneDto planeDtoToUpdate = this.createCompletePlaneDto();

		Mockito.when(this.planeRepository.save(Mockito.eq(this.createCompletePlane().get())))
				.thenReturn(createCompletePlane().get());

		assertEquals(createCompletePlaneDto(), this.planeService.updatePlane(planeDtoToUpdate));

	}

	@Test(expected = ServiceException.class)
	public void update_planeViolatesUniqueConstraint_throwsServiceException() throws ServiceException {

		final PlaneDto planeDtoToUpdate = this.createCompletePlaneDto();

		Mockito.when(this.planeRepository.save(Mockito.eq(this.createCompletePlane().get())))
				.thenThrow(DataIntegrityViolationException.class);

		this.planeService.updatePlane(planeDtoToUpdate);

	}

	@Test
	public void insert_planeDtoIsValid_isSuccessful() throws ServiceException {

		final PlaneDto planeDtoToInsert = this.createIncompletePlaneDto();

		Mockito.when(this.planeRepository.save(Mockito.eq(this.createPlaneForInsert())))
				.thenReturn(this.createCompletePlane().get());

		assertEquals(this.createCompletePlaneDto(), this.planeService.insertPlane(planeDtoToInsert));

	}

	@Test(expected = ServiceException.class)
	public void insert_planeViolatesUniqueConstraint_throwsServiceException() throws ServiceException {

		final PlaneDto planeDtoToInsert = this.createIncompletePlaneDto();

		Mockito.when(this.planeRepository.save(Mockito.eq(this.createPlaneForInsert())))
				.thenThrow(DataIntegrityViolationException.class);

		this.planeService.insertPlane(planeDtoToInsert);

	}

	@Test
	public void delete_planeDtoIsValid_isSuccessful() throws ServiceException {

		final PlaneDto planeDtoToDelete = this.createCompletePlaneDto();

		Mockito.doNothing().when(this.planeRepository).deleteById(1);

		assertEquals(this.createCompletePlaneDto(), this.planeService.deletePlane(planeDtoToDelete));

	}

	@Test(expected = ServiceException.class)
	public void delete_planeDoesNotExist_throwsServiceException() throws ServiceException {

		final PlaneDto planeDtoToDelete = this.createCompletePlaneDto();

		Mockito.doThrow(EmptyResultDataAccessException.class).when(this.planeRepository).deleteById(1);

		this.planeService.deletePlane(planeDtoToDelete);

	}

	private Optional<Plane> createCompletePlane() {

		final Plane plane = new Plane();
		plane.setCode("ABCD");
		plane.setCompany(this.createCompleteCompany());
		plane.setId(1);
		plane.setModel("Airbus 500");
		plane.setNumberOfPassengers(200);

		return Optional.ofNullable(plane);
	}

	private Company createCompleteCompany() {

		final Company company = new Company();
		company.setAddress("address");
		company.setEmail("mail@mail.com");
		company.setName("name");
		company.setPhone("+50936538992");
		company.setId(1);

		return company;
	}

	private Plane createPlaneForInsert() {

		final Plane plane = new Plane();
		plane.setCode("ABCD");
		plane.setCompany(this.createCompleteCompany());
		plane.setModel("Airbus 500");
		plane.setNumberOfPassengers(200);

		return plane;
	}

	private PlaneDto createCompletePlaneDto() {
		return mapPlaneToPlaneDto(this.createCompletePlane().get());
	}

	private PlaneDto createIncompletePlaneDto() {
		final Plane completePlane = this.createCompletePlane().get();
		completePlane.setId(null);
		return mapPlaneToPlaneDto(completePlane);
	}

	private PlaneDto mapPlaneToPlaneDto(final Plane plane) {

		return PlaneMapper.MAPPER.mapPlaneToPlaneDto(plane);
	}

}
