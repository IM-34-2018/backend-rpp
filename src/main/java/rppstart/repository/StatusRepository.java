package rppstart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rppstart.jpa.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {
	
	List<Status> findByNazivContainingIgnoreCase(String naziv);  //metoda za pronalazenje po nazivu

}
