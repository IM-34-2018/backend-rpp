 package rppstart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rppstart.jpa.Fakultet;

public interface FakultetRepository extends JpaRepository<Fakultet, Integer>{
//sta mora da sadrzi svaki repository?		//interfejs //tabela/klasa //tip podatka kljuca
	//repository je skladiste, u ovom slucaju fakulteta
	//na osnovu njega mozemo zvati metode CRUD
	//mozemo da koristimo predefinisane metode
	
	//apstraktna metoda -- ne treba public apstract jer su sve metode apstraktne
	List<Fakultet> findByNazivContainingIgnoreCase(String naziv);
					//get metoda - Naziv obelezja - Zanemari mala i velika slova
}
