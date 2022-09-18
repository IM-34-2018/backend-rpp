package rppstart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rppstart.jpa.Departman;

public interface DepartmanRepository extends JpaRepository<Departman, Integer>{
	
	List<Departman> findByNazivContainingIgnoreCase(String naziv); 
}
