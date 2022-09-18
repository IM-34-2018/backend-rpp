package rppstart.service;


	import java.util.List;
	import java.util.Optional;

	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

import rppstart.jpa.Status;
import rppstart.repository.StatusRepository;



	@Service
	public class StatusService {
		
		
		@Autowired
		private StatusRepository statusRepository;
		
		public List<Status> getAll(){
			return statusRepository.findAll();
		}
		
		public Optional<Status> findById(Integer id) {
			return statusRepository.findById(id);
		}
		
		public List<Status> findByNazivContainingIgnoreCase(String naziv) {
	        return statusRepository.findByNazivContainingIgnoreCase(naziv);
	    }
		
		public Status save(Status status) {
			return statusRepository.save(status);
		}
		
		public boolean existsById(Integer id) {
			return statusRepository.existsById(id);
		}
		
		public void deleteById(Integer id) {
			statusRepository.deleteById(id);
		}

	}


