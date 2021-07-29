package de.msg.flight.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.msg.flight.persistence.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
