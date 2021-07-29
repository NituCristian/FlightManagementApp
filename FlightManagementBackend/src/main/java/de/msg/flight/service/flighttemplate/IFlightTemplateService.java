package de.msg.flight.service.flighttemplate;

import java.util.List;

import de.msg.flight.dto.FlightTemplateDto;
import de.msg.flight.service.ServiceException;
import de.msg.flight.validation.ValidationException;

public interface IFlightTemplateService {

	/**
	 * Retrieves a flight template from database
	 *
	 * @param flightTemplateId the id of the flight template to be retrieved
	 * @return the dto of the retrieved entity
	 * @throws ValidationException the entitiy with the given id does not exist
	 */
	public FlightTemplateDto getFlightTemplateById(final Integer flightTemplateId) throws ValidationException;

	/**
	 * Retrieves all the flight templates from database
	 *
	 * @return a list of dtos of the flight templates
	 */
	public List<FlightTemplateDto> getAllFlightTemplates();

	/**
	 * Inserts a flight template to database
	 *
	 * @param flightTemplateDto the dto of the entity to be inserted
	 * @return the dto of the new entity
	 */
	public FlightTemplateDto insertFlightTemplate(FlightTemplateDto flightTemplateDto);

	/**
	 * Updates an existing flight template
	 *
	 * @param flightTemplateDto the dto of the entity to be updated
	 * @return the updated flight template dto
	 */
	public FlightTemplateDto updateFlightTemplate(FlightTemplateDto flightTemplateDto);

	/**
	 * Removes flight template from database
	 *
	 * @param flightTemplateId of flight template to be removed
	 * @throws ServiceException the flight template with the given id does not exist
	 */
	public void removeFlightTemplate(Integer flightTemplateId) throws ServiceException;

}
