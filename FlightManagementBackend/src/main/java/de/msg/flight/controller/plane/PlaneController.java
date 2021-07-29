package de.msg.flight.controller.plane;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.msg.flight.dto.PlaneDto;
import de.msg.flight.service.ServiceException;
import de.msg.flight.service.plane.IPlaneService;
import de.msg.flight.validation.PlaneValidator;
import de.msg.flight.validation.ValidationException;

@RequestMapping("/planes")
@RestController
@CrossOrigin
public class PlaneController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlaneController.class);

	@Autowired
	private IPlaneService planeService;

	@Autowired
	private PlaneValidator planeValidator;

	@GetMapping
	public ResponseEntity<List<PlaneDto>> getAllPlanes() {

		LOGGER.debug("Retrieving all planes...");

		return ResponseEntity.ok().body(this.planeService.getAllPlanes());

	}

	@GetMapping("/{id}")
	public ResponseEntity<PlaneDto> getPlaneById(@PathVariable final Integer id) throws ServiceException {

		LOGGER.debug("Retrieving plane with id={}", id);

		return ResponseEntity.ok().body(this.planeService.getPlaneById(id));

	}

	@PostMapping
	public ResponseEntity<PlaneDto> addPlane(@RequestBody final PlaneDto planeDto)
			throws ValidationException, ServiceException {

		this.planeValidator.validateForInsert(planeDto);

		LOGGER.debug("Inserting plane with name={}", planeDto.getName());

		return ResponseEntity.ok().body(this.planeService.insertPlane(planeDto));

	}

	@PutMapping
	public ResponseEntity<PlaneDto> updatePlane(@RequestBody final PlaneDto planeDto)
			throws ValidationException, ServiceException {

		this.planeValidator.validateForUpdate(planeDto);

		LOGGER.debug("Inserting plane with id={}", planeDto.getId());

		return ResponseEntity.ok().body(this.planeService.updatePlane(planeDto));

	}

	@DeleteMapping
	public ResponseEntity<Void> deletePlane(@RequestBody final PlaneDto planeDto)
			throws ValidationException, ServiceException {

		this.planeValidator.validateForDelete(planeDto);

		this.planeService.deletePlane(planeDto);

		LOGGER.debug("Deleting plane with id={}", planeDto.getId());

		return ResponseEntity.ok().build();

	}

	@PostMapping("/company")
	public ResponseEntity<List<PlaneDto>> getPlanesByCompany(@RequestBody final Integer companyId)
			throws ValidationException {

		this.planeValidator.validatePlanesRetrievalByCompanyId(companyId);

		LOGGER.debug("Retrieving planes for company with id={}", companyId);

		return ResponseEntity.ok().body(this.planeService.getPlanesByCompanyId(companyId));

	}

}
