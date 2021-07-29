package de.msg.flight.service.plane;

import java.util.List;

import de.msg.flight.dto.PlaneDto;
import de.msg.flight.service.ServiceException;

public interface IPlaneService {

	/**
	 * gets a Plane based on id
	 *
	 * @param planeId
	 * @return PlaneDto
	 * @throws ServiceException
	 */

	PlaneDto getPlaneById(Integer planeId) throws ServiceException;

	/**
	 * gets all Planes from db
	 *
	 * @return List of PlaneDtos
	 */

	List<PlaneDto> getAllPlanes();

	/**
	 * updates a Plane
	 *
	 * @param planeDto
	 * @return updated Plane as PlaneDto
	 * @throws ServiceException
	 */

	PlaneDto updatePlane(PlaneDto planeDto) throws ServiceException;

	/**
	 * deletes a Plane
	 *
	 * @param planeDto
	 * @return deleted Plane as PlaneDto
	 * @throws ServiceException
	 *
	 */
	PlaneDto deletePlane(PlaneDto planeDto) throws ServiceException;

	/**
	 * inserts a new Plane in the db
	 *
	 * @param planeDto
	 * @return newly inserted Plane as a PlaneDto
	 * @throws ServiceException
	 */

	PlaneDto insertPlane(PlaneDto planeDto) throws ServiceException;

	/**
	 * gets all Planes by a Company
	 *
	 * @param companyId
	 * @return List of PlaneDto
	 */

	List<PlaneDto> getPlanesByCompanyId(Integer companyId);

}
