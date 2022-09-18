package rppstart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rppstart.jpa.Student;
import rppstart.jpa.Departman;

public interface StudentRepository extends JpaRepository<Student, Integer>{
	
	List<Student> findByDepartman(Departman departman);
	List<Student> findByPrezimeContainingIgnoreCase(String prezime);
	//get metoda - Naziv obelezja - Zanemari mala i velika slova
}
