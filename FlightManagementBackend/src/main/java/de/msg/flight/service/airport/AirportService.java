package de.msg.flight.service.airport;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import de.msg.flight.dto.AirportDto;
import de.msg.flight.mapper.airport.AirportMapper;
import de.msg.flight.persistence.model.Airport;
import de.msg.flight.persistence.repository.AirportRepository;
import de.msg.flight.service.ServiceException;

@Service
public class AirportService implements IAirportService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AirportService.class);

	@Autowired
	private AirportRepository airportRepository;

	@Override
	public AirportDto getAirportById(final Integer airportId) throws ServiceException {

		final Optional<Airport> optionalAirport = this.airportRepository.findById(airportId);

		if (!optionalAirport.isPresent()) {
			LOGGER.error("Airport with id {} does not exist", airportId);

			throw new ServiceException("Could not get the airport with id " + airportId);
		}

		LOGGER.info("Airport with id {} was successfully retrieved", airportId);

		return mapAirportToAirportDto(optionalAirport.get());
	}

	@Override
	public List<AirportDto> getAllAirports() {

		return this.airportRepository.findAll().stream().map(this::mapAirportToAirportDto).collect(Collectors.toList());
	}

	@Override
	public AirportDto insertAirport(final AirportDto airportDto) throws ServiceException {

		final Airport newAirport = insert(airportDto);

		LOGGER.info("Successfully inserted Airport with id= {}", airportDto.getId());

		return mapAirportToAirportDto(newAirport);
	}

	@Override
	public void removeAirport(final Integer airportId) throws ServiceException {

		try {
			this.airportRepository.deleteById(airportId);
		} catch (final EmptyResultDataAccessException ex) {
			LOGGER.error("Cannot delete the airport with id {}. None such airport was found", airportId);

			throw new ServiceException("The airport with the id " + airportId + " does not exist", ex);
		}

		LOGGER.info("Airport with id {} was successfully deleted", airportId);
	}

	@Override
	public AirportDto updateAirport(final AirportDto airportDto) throws ServiceException {

		final Airport updatedAirport = update(airportDto);

		LOGGER.info("Successfully updated Airport with id= {}", airportDto.getId());

		return mapAirportToAirportDto(updatedAirport);

	}

	private Airport insert(final AirportDto airportDto) throws ServiceException {
		try {
			return this.airportRepository.save(mapAirportDtoToAirport(airportDto));

		} catch (final DataIntegrityViolationException e) {
			LOGGER.error("Failed to insert Airport with code={} because of unique db integrity constraint violation",
					airportDto.getAirportCode());

			throw new ServiceException("Failed to insert the airport. Another one with the code: "
					+ airportDto.getAirportCode() + " already exists", e);
		}

	}

	private Airport update(final AirportDto airportDto) throws ServiceException {

		try {
			return this.airportRepository.save(mapAirportDtoToAirport(airportDto));

		} catch (final DataIntegrityViolationException e) {
			LOGGER.error("Failed to update the airport with code={} due to unique constraints",
					airportDto.getAirportCode());

			throw new ServiceException("Failed to update the airport. Another one with the code: "
					+ airportDto.getAirportCode() + " already exists", e);
		}
	}

	private AirportDto mapAirportToAirportDto(final Airport airport) {

		return AirportMapper.MAPPER.mapAirportToAirportDto(airport);
	}

	private Airport mapAirportDtoToAirport(final AirportDto airportDto) {

		return AirportMapper.MAPPER.mapAirportDtoToAirport(airportDto);
	}

}
