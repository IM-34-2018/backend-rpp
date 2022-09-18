package rppstart.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rppstart.jpa.Student;
import rppstart.jpa.Departman;
import rppstart.repository.StudentRepository;


@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	public List<Student> getAll(){
		return studentRepository.findAll();
	}
	
	public Optional<Student> findById(Integer id) {
		return studentRepository.findById(id);
	}
	
	public List<Student> findByDepartman(Departman departman) {
		return studentRepository.findByDepartman(departman);
	}
	
	public List<Student> findByPrezime(String prezime) {
        return studentRepository.findByPrezimeContainingIgnoreCase(prezime);
    }
	
	 
	public Student save(Student student) {
		return studentRepository.save(student);
	}
	
	public boolean existsById(Integer id) {
		return studentRepository.existsById(id);
	}
	
	public void deleteById(Integer id) {
		studentRepository.deleteById(id);
	}

}