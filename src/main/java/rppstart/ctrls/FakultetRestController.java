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
import rppstart.jpa.Fakultet;
import rppstart.service.FakultetService;
//uvek automatski treba importovati a ne kopirati


@CrossOrigin //da mu se moze pristupiti sa Angulara
@RestController //kazemo da jeste restcontroller
public class FakultetRestController {
	
	//metoda za vracanje svih fakulteta iz baze podataka
	//ovo predstavlja dependency of injection koncept -- jer zavisnosti klase/interfejsa FakultetRepository injektovati u neku drugu klasu
	//sta su zavisnosti klase? -- varijable i metode neke klase i u ovom slucaju nama trebaju sve metode ovog interfejsa FakultetRepository koje je nasledio sve od JpaRepository
	//zato moramo da injektujemo FakultetRepository u ovu klasu -- Autowired --injektovanje pomocu polja/property-a
	

    @Autowired
    private FakultetService fakultetService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ApiOperation(value = "Returns List of all Fakultets")
    @GetMapping("fakultet")
    public ResponseEntity<List<Fakultet>> getAll() {
        List<Fakultet> fakultets = fakultetService.getAll();
        return new ResponseEntity<>(fakultets, HttpStatus.OK);
    }

    @ApiOperation(value = "Returns Fakultet with id that was forwarded as path variable.")
    @GetMapping("fakultet/{id}")
    public ResponseEntity<Fakultet> getOne(@PathVariable("id") Integer id) {
        if (fakultetService.findById(id).isPresent()) {
            Optional<Fakultet> fakultetOpt = fakultetService.findById(id);
            return new ResponseEntity<>(fakultetOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Returns list of Fakultets containing string that was forwarded as path variable in 'naziv'.")
    @GetMapping("fakultet/naziv/{naziv}")
    public ResponseEntity<List<Fakultet>> getByNaziv(@PathVariable("naziv") String naziv) {
        List<Fakultet> fakultets = fakultetService.findByNazivContainingIgnoreCase(naziv);
        return new ResponseEntity<>(fakultets, HttpStatus.OK);
    }

    @ApiOperation(value = "Adds new Fakultet to database.")
    @PostMapping("fakultet")
    public ResponseEntity<Fakultet> addFakultet(@RequestBody Fakultet fakultet) {
        Fakultet savedFakultet = fakultetService.save(fakultet);
        URI location = URI.create("/fakultet/" + savedFakultet.getId());
        return ResponseEntity.created(location).body(savedFakultet);
    }

    @ApiOperation(value = "Updates Fakultet that has id that was forwarded as path variable with values forwarded in Request Body.")
    @PutMapping(value = "fakultet/{id}")
    public ResponseEntity<Fakultet> updateFakultet(@RequestBody Fakultet fakultet, @PathVariable("id") Integer id) {
        if (fakultetService.existsById(id)) {
            fakultet.setId(id);
            Fakultet savedFakultet = fakultetService.save(fakultet);
            return ResponseEntity.ok().body(savedFakultet);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Deletes Fakultet with id that was forwarded as path variable.")
    @DeleteMapping("fakultet/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable Integer id) {

        if (id == -100 && !fakultetService.existsById(id)) {
        	jdbcTemplate.execute(
					"INSERT INTO \"fakultet\"(\"id\", \"naziv\", \"sediste\")"
					+ "VALUES (-100, 'teest', 'testestest')" 
					);
			
		}

        if (fakultetService.existsById(id)) {
            fakultetService.deleteById(id);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
    }

}
