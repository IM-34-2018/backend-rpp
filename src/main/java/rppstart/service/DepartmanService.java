package rppstart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rppstart.jpa.Departman;
import rppstart.repository.DepartmanRepository;


@Service
public class DepartmanService {
	
	@Autowired
	private DepartmanRepository departmanRepository;
	
	public List<Departman> getAll(){
		return departmanRepository.findAll();
	}
	
	public Optional<Departman> findById(Integer id) {
		return departmanRepository.findById(id);
	}
	
	public List<Departman> findByNazivContainingIgnoreCase(String naziv) {
        return departmanRepository.findByNazivContainingIgnoreCase(naziv);
    }
	
	public Departman save(Departman departman) {
		return departmanRepository.save(departman);
	}
	
	public boolean existsById(Integer id) {
		return departmanRepository.existsById(id);
	}
	
	public void deleteById(Integer id) {
		departmanRepository.deleteById(id);
	}

}