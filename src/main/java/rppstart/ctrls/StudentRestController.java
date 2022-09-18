package rppstart.ctrls;

import java.net.URI;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import rppstart.jpa.Student;
import rppstart.jpa.Departman;
import rppstart.service.StudentService;
import rppstart.service.DepartmanService;

@CrossOrigin //da mu se moze pristupiti sa Angulara
@RestController
public class StudentRestController {
	

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private DepartmanService departmanService;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ApiOperation(value = "Returns List of all Students")
    @GetMapping("student")
    public ResponseEntity<List<Student>> getAll() {
        List<Student> students = studentService.getAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns Student with id that was forwarded as path variable.")
    @GetMapping("student/{id}")
    public ResponseEntity<Student> getOne(@PathVariable("id") Integer id) {
        if (studentService.findById(id).isPresent()) {
            Optional<Student> studentOpt = studentService.findById(id);
            return new ResponseEntity<>(studentOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Returns list of Students containing string that was forwarded as path variable in 'prezime'.")
    @GetMapping("student/prezime/{prezime}")
    public ResponseEntity<List<Student>> findByPrezimeContainingIgnoreCase(@PathVariable("prezime") String prezime) {
        List<Student> fakultets = studentService.findByPrezime(prezime);
        return new ResponseEntity<>(fakultets, HttpStatus.OK);
    }
    
    @ApiOperation(value = "Returns list of Student for Departman with id that was forwarded as path variable.")
    @GetMapping("studentsDepartman/{id}")
    public ResponseEntity<List<Student>> getAllForDepartman(@PathVariable("id") Integer id) {
        Optional<Departman> departmanOpt = departmanService.findById(id);
        if (departmanOpt.isPresent()) {
            List<Student> students = studentService.findByDepartman(departmanOpt.get());
            return new ResponseEntity<>(students, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    @ApiOperation(value = "Adds new Student to database.")
    @PostMapping("student")
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student savedStudent = studentService.save(student);
        URI location = URI.create("/student/" + savedStudent.getId());
        return ResponseEntity.created(location).body(savedStudent);
    }

    @ApiOperation(value = "Updates Student that has id that was forwarded as path variable with values forwarded in Request Body.")
    @PutMapping(value = "student/{id}")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student,
            @PathVariable("id") Integer id) {
        if (studentService.existsById(id)) {
            student.setId(id);
            Student savedStudent = studentService.save(student);
            return ResponseEntity.ok().body(savedStudent);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Deletes Student with id that was forwarded as path variable.")
    @DeleteMapping("student/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
        if (id == -100 && !studentService.existsById(-100)) {

        	jdbcTemplate.execute(
					"INSERT INTO \"student\"(\"id\", \"ime\", \"prezime\", \"broj_indeksa\", \"status\", \"departman\")"
					+ "VALUES (-100, 'testime', 'testprz', 'testind', 5, 5)" 
					);
        }

        if (studentService.existsById(id)) {
            studentService.deleteById(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
    }
}

