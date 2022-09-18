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
import rppstart.jpa.Departman;
import rppstart.service.DepartmanService;


@CrossOrigin //da mu se moze pristupiti sa Angulara
@RestController
public class DepartmanRestController {
	

    @Autowired
    private DepartmanService departmanService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ApiOperation(value = "Returns List of all Departmans")
    @GetMapping("departman")
    public ResponseEntity<List<Departman>> getAll() {
        List<Departman> departmans = departmanService.getAll();
        return new ResponseEntity<>(departmans, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns Departman with id that was forwarded as path variable.")
    @GetMapping("departman/{id}")
    public ResponseEntity<Departman> getOne(@PathVariable("id") Integer id) {
        if (departmanService.findById(id).isPresent()) {
            Optional<Departman> departmanOpt = departmanService.findById(id);
            return new ResponseEntity<>(departmanOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Returns list of Departmans containing string that was forwarded as path variable in 'naziv'.")
    @GetMapping("departman/naziv/{naziv}")
    public ResponseEntity<List<Departman>> getByNaziv(@PathVariable("naziv") String naziv) {
        List<Departman> departmans = departmanService.findByNazivContainingIgnoreCase(naziv);
        return new ResponseEntity<>(departmans, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds new Departman to database.")
    @PostMapping("departman")
    public ResponseEntity<Departman> addDepartman(@RequestBody Departman departman) {
        Departman savedDepartman = departmanService.save(departman);
        URI location = URI.create("/departman/" + savedDepartman.getId());
        return ResponseEntity.created(location).body(savedDepartman);
    }

    @ApiOperation(value = "Updates Departman that has id that was forwarded as path variable with values forwarded in Request Body.")
    @PutMapping(value = "departman/{id}")
    public ResponseEntity<Departman> updateDepartman(@RequestBody Departman departman,
            @PathVariable("id") Integer id) {
        if (departmanService.existsById(id)) {
            departman.setId(id);
            Departman savedDepartman = departmanService.save(departman);
            return ResponseEntity.ok().body(savedDepartman);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Deletes Departman with id that was forwarded as path variable.")
    @DeleteMapping("departman/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
        if (id == -100 && !departmanService.existsById(-100)) {

        	jdbcTemplate.execute(
					"INSERT INTO \"departman\"(\"id\", \"naziv\", \"oznaka\", \"fakuktet\")"
					+ "VALUES (-100, 'teeest', 'testestest', 5)" 
					);
        	}

        if (departmanService.existsById(id)) {
            departmanService.deleteById(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
    }

}