package de.msg.flight.service.flighttemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import de.msg.flight.dto.FlightTemplateDto;
import de.msg.flight.mapper.flighttemplate.FlightTemplateMapper;
import de.msg.flight.persistence.model.FlightTemplate;
import de.msg.flight.persistence.repository.FlightTemplateRepository;
import de.msg.flight.service.ServiceException;
import de.msg.flight.validation.ValidationException;

@Service
public class FlightTemplateService implements IFlightTemplateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FlightTemplateService.class);

	@Autowired
	private FlightTemplateRepository flightTemplateRepository;

	@Override
	public FlightTemplateDto getFlightTemplateById(final Integer flightTemplateId) throws ValidationException {

		final Optional<FlightTemplate> flightTemplate = this.flightTemplateRepository.findById(flightTemplateId);

		if (!flightTemplate.isPresent()) {
			LOGGER.error("Failed to retrieve flight template with id {}. Flight Template does not exist.",
					flightTemplateId);

			throw new ValidationException("Failed to retrieve flight template with id " + flightTemplateId
					+ ". Flight Template does not exist.");
		}

		LOGGER.info("Successfully retrieved flight template with id {}", flightTemplateId);

		return mapFlightTemplateToFlightTemplateDto(flightTemplate.get());
	}

	@Override
	public List<FlightTemplateDto> getAllFlightTemplates() {

		return this.flightTemplateRepository.findAll().stream()
				.map(FlightTemplateMapper.MAPPER::mapFlightTemplateToFlightTemplateDto).collect(Collectors.toList());
	}

	@Override
	public FlightTemplateDto insertFlightTemplate(final FlightTemplateDto flightTemplateDto) {

		final FlightTemplate flightTemplate = this.flightTemplateRepository
				.save(mapFlightTemplateDtoToFlightTemplate(flightTemplateDto));

		LOGGER.info("Successfully inserted flight template with id {}.", flightTemplate.getId());

		return mapFlightTemplateToFlightTemplateDto(flightTemplate);
	}

	@Override
	public FlightTemplateDto updateFlightTemplate(final FlightTemplateDto flightTemplateDto) {

		final FlightTemplate flightTemplate = this.flightTemplateRepository
				.save(mapFlightTemplateDtoToFlightTemplate(flightTemplateDto));

		LOGGER.info("Successfully updated flight template with id {} .", flightTemplate.getId());

		return mapFlightTemplateToFlightTemplateDto(flightTemplate);
	}

	@Override
	public void removeFlightTemplate(final Integer flightTemplateId) throws ServiceException {

		try {
			this.flightTemplateRepository.deleteById(flightTemplateId);
		} catch (final EmptyResultDataAccessException e) {
			LOGGER.error("Failed to delete flight template with id={}. None was found.", flightTemplateId);

			throw new ServiceException("Failed to delete flight template with id=" + flightTemplateId + ". None was found.");
		}

		LOGGER.info("Successfully deleted airport with id={}", flightTemplateId);
	}

	private FlightTemplateDto mapFlightTemplateToFlightTemplateDto(final FlightTemplate flightTemplate) {

		return FlightTemplateMapper.MAPPER.mapFlightTemplateToFlightTemplateDto(flightTemplate);
	}

	private FlightTemplate mapFlightTemplateDtoToFlightTemplate(final FlightTemplateDto flightTemplateDto) {

		return FlightTemplateMapper.MAPPER.mapFlightTemplateDtoToFlightTemplate(flightTemplateDto);
	}
}
