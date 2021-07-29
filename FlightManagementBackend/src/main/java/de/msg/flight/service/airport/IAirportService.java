package de.msg.flight.service.airport;

import java.util.List;

import de.msg.flight.dto.AirportDto;
import de.msg.flight.service.ServiceException;

public interface IAirportService {

	/**
	 * Get airport with specified id from db and convert to dto
	 * 
	 * @param airportId the id of the airport
	 * @return the airportDto with that id
	 * @throws ServiceException when there is no airport with that id in the
	 *                          database
	 */
	public AirportDto getAirportById(Integer airportId) throws ServiceException;

	/**
	 * get all airports from the db and convert them to dto
	 * 
	 * @return list of airportDtos
	 */
	public List<AirportDto> getAllAirports();

	/**
	 * insert an airport into db if all fields are not null and not empty
	 * 
	 * @param airportDto the airportDto we want to insert
	 * @return the airport inserted, converted to airportDto, if no exception is
	 *         thrown
	 * @throws ServiceException when the airport has an existing code or an id
	 *                          specified of any null or empty other field
	 */
	public AirportDto insertAirport(AirportDto airportDto) throws ServiceException;

	/**
	 * remove the airport with the given id from the database
	 * 
	 * @param airportId the id of the airport we want to remove
	 * @throws ServiceException when no airport with this id exists
	 */
	public void removeAirport(Integer airportId) throws ServiceException;

	/**
	 * convert airportDto to entity and update that airport in the database
	 * 
	 * @param airportDto the airport mapped to dto we want to update
	 * @return the updated airport, converted to airportDto, if no exception is
	 *         thrown
	 * @throws ServiceException if id is not specified or any field is empty or null
	 *                          or airport code is updated to an existing one
	 */
	public AirportDto updateAirport(AirportDto airportDto) throws ServiceException;

}