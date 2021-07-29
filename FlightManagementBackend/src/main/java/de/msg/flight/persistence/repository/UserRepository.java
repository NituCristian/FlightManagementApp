package de.msg.flight.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.msg.flight.persistence.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.username= ?1 and u.password = ?2")
	public User findUserByUsernameAndPassword(String username, String password);

	@Query("select u from User u where u.username= ?1")
	public User findUserByUsername(String username);
}
