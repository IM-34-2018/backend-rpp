package rppstart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rppstart.jpa.Fakultet;
import rppstart.repository.FakultetRepository;


@Service
public class FakultetService {
	
	@Autowired
	private FakultetRepository fakultetRepository;
	
	public List<Fakultet> getAll(){
		return fakultetRepository.findAll();
	}
	
	public Optional<Fakultet> findById(Integer id) {
		return fakultetRepository.findById(id);
	}
	
	public List<Fakultet> findByNazivContainingIgnoreCase(String naziv) {
        return fakultetRepository.findByNazivContainingIgnoreCase(naziv);
    }
	
	public Fakultet save(Fakultet fakultet) {
		return fakultetRepository.save(fakultet);
	}
	
	public boolean existsById(Integer id) {
		return fakultetRepository.existsById(id);
	}
	
	public void deleteById(Integer id) {
		fakultetRepository.deleteById(id);
	}
	

}