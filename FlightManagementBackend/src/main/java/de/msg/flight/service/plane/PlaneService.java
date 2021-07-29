package de.msg.flight.service.plane;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import de.msg.flight.dto.PlaneDto;
import de.msg.flight.mapper.plane.PlaneMapper;
import de.msg.flight.persistence.model.Plane;
import de.msg.flight.persistence.repository.PlaneRepository;
import de.msg.flight.service.ServiceException;

@Service
public class PlaneService implements IPlaneService {

	@Autowired
	private PlaneRepository planeRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(PlaneService.class);

	@Override
	public PlaneDto getPlaneById(final Integer planeId) throws ServiceException {

		final Optional<Plane> plane = this.planeRepository.findById(planeId);

		if (!plane.isPresent()) {
			LOGGER.error("Plane with id {} does not exist", planeId);

			throw new ServiceException("Could not get the plane with id " + planeId);
		}

		LOGGER.info("Plane with id {} was successfully retrieved", planeId);

		return mapPlaneToPlaneDto(plane.get());
	}

	@Override
	public List<PlaneDto> getAllPlanes() {

		return this.planeRepository.findAll().stream().map(this::mapPlaneToPlaneDto).collect(Collectors.toList());
	}

	@Override
	public List<PlaneDto> getPlanesByCompanyId(final Integer companyId) {
	
		final List<Plane> planes = this.planeRepository.getPlanesByCompanyId(companyId);
	
		LOGGER.info("Successfully retrieved Planes for Company with id= {}", companyId);
	
		return planes.stream().map(this::mapPlaneToPlaneDto).collect(Collectors.toList());
	}

	@Override
	public PlaneDto updatePlane(final PlaneDto planeDto) throws ServiceException {

		final Plane updatedPlane = update(planeDto);

		LOGGER.info("Successfully updated Plane with id= {}", planeDto.getId());

		return mapPlaneToPlaneDto(updatedPlane);

	}

	@Override
	public PlaneDto deletePlane(final PlaneDto planeDto) throws ServiceException {

		delete(planeDto);

		LOGGER.info("Successfully deleted Plane with id= {}", planeDto.getId());

		return planeDto;
	}

	@Override
	public PlaneDto insertPlane(final PlaneDto planeDto) throws ServiceException {

		final Plane insertedPlane = insert(planeDto);

		LOGGER.info("Successfully inserted Plane with id= {}", insertedPlane.getId());

		return mapPlaneToPlaneDto(insertedPlane);

	}

	private void delete(final PlaneDto planeDto) throws ServiceException {

		try {
			this.planeRepository.deleteById(planeDto.getId());
		} catch (final EmptyResultDataAccessException ex) {
			LOGGER.error("Failed to delete Company with id= {}. None was found", planeDto.getId());

			throw new ServiceException("No Company with id= " + planeDto.getId() + " exists");
		}
	}

	private Plane insert(final PlaneDto planeDto) throws ServiceException {

		try {
			return this.planeRepository.save(mapPlaneDtoToPlane(planeDto));
		} catch (final DataIntegrityViolationException e) {
			LOGGER.error("Failed to insert Plane because of unique db integrity constraint violation");

			throw new ServiceException(
					"Failed to insert the edited Plane. Another with code: " + planeDto.getCode() + " already exists");
		}
	}

	private Plane update(final PlaneDto planeDto) throws ServiceException {

		try {
			return this.planeRepository.save(mapPlaneDtoToPlane(planeDto));
		} catch (final DataIntegrityViolationException e) {
			LOGGER.error("Failed to update Plane with id= {}, due to db unique integrity constraint violation",
					planeDto.getId());

			throw new ServiceException(
					"Failed to update the edited Plane. Another with code: " + planeDto.getCode() + " already exists");
		}
	}

	private PlaneDto mapPlaneToPlaneDto(final Plane plane) {

		return PlaneMapper.MAPPER.mapPlaneToPlaneDto(plane);
	}

	private Plane mapPlaneDtoToPlane(final PlaneDto planeDto) {

		return PlaneMapper.MAPPER.mapPlaneDtoToPlane(planeDto);
	}

}
