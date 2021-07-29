package de.msg.flight.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.msg.flight.persistence.model.Plane;

@Repository
public interface PlaneRepository extends JpaRepository<Plane, Integer> {

	@Query("select p from Plane p where p.company.id = ?1")
	List<Plane> getPlanesByCompanyId(Integer companyId);

}
