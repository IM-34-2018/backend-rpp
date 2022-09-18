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
import rppstart.jpa.Status;
import rppstart.service.StatusService;

@CrossOrigin
@RestController //mora odma anotacija da bi IofCont. Spring odmah naravio bean
public class StatusRestController {


	
	@Autowired
	private StatusService statusService;
				//tip					//naziv
	
	@Autowired
	private JdbcTemplate jdbcTemplate; //kroz jdbc mozemo raditi direktno izvrsavanje sql upita //npr kad neko obrise id 100 da se automatski vrati nazad
	
	
	@ApiOperation(value = "Returns List of all Status")
    @GetMapping("status")
    public ResponseEntity<List<Status>> getAll() {
        List<Status> statuss = statusService.getAll();
        return new ResponseEntity<>(statuss, HttpStatus.OK);
    }

	@ApiOperation(value = "Returns Status with id that was forwarded as path variable.")
    @GetMapping("status/{id}")
    public ResponseEntity<Status> getOne(@PathVariable("id") Integer id) {
        if (statusService.findById(id).isPresent()) {
            Optional<Status> statusOpt = statusService.findById(id);
            return new ResponseEntity<>(statusOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
	
    @ApiOperation(value = "Returns list of Statuss containing string that was forwarded as path variable in 'naziv'.")
    @GetMapping("status/naziv/{naziv}")
    public ResponseEntity<List<Status>> getByNaziv(@PathVariable("naziv") String naziv) {
        List<Status> statuss = statusService.findByNazivContainingIgnoreCase(naziv);
        return new ResponseEntity<>(statuss, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds new Status to database.")
    @PostMapping("status")
    public ResponseEntity<Status> addStatus(@RequestBody Status status) {
        Status savedStatus = statusService.save(status);
        URI location = URI.create("/status/" + savedStatus.getId());
        return ResponseEntity.created(location).body(savedStatus);
    }

    @ApiOperation(value = "Updates Status that has id that was forwarded as path variable with values forwarded in Request Body.")
    @PutMapping(value = "status/{id}")
    public ResponseEntity<Status> updateStatus(@RequestBody Status status, @PathVariable("id") Integer id) {
        if (statusService.existsById(id)) {
            status.setId(id);
            Status savedStatus = statusService.save(status);
            return ResponseEntity.ok().body(savedStatus);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Deletes Status with id that was forwarded as path variable.")
    @DeleteMapping("status/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {
        if (id == -100 && !statusService.existsById(id)) {
        	jdbcTemplate.execute(
					"INSERT INTO \"status\"(\"id\", \"naziv\", \"oznaka\")"
					+ "VALUES (-100, 'teest', 'testestest')" 
					);
			
		}

        if (statusService.existsById(id)) {
            statusService.deleteById(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
    }

}
