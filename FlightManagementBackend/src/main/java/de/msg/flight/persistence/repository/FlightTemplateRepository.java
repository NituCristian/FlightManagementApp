package de.msg.flight.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.msg.flight.persistence.model.FlightTemplate;

@Repository
public interface FlightTemplateRepository extends JpaRepository<FlightTemplate, Integer> {

}
