package rppstart.ctrls;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //koristi se samo na nivou klase kombinacija 2 anotacije
				//1. kotroler anotacija -- za oznaku da se radi o klasi koja ce biti spring controler i koja se moze koristiti u spring nvc-u
				//2. response body ano. -- rezultat izvrsavanja smestice se u neki response body koji se isporucuje u nekom unapred definisanom formatu u ovom slucaju JSON
public class HelloRestController {

	//anotacija se moze dodati i na nivou metode
	@RequestMapping("/") //mapira web zahteve na odredjene metode/klase u zagradi bi trebao biti URL(putanja) do ove metode
	public String helloWorld() {
		return "Hello World!";
	}
	
	@RequestMapping("zbir") //ovo sluzi da promenim URL localhost/8083/zbir
	
	public String zbir() {
		long x = Math.round(Math.random() * 10);
		long y = Math.round(Math.random() * 10);
		return x + " + "+ y + " = " + (x+y);
	}
	
	
}
