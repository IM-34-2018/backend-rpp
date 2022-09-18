package rppstart;


import java.util.Arrays;


import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


@SpringBootApplication //cim sacuvamo izmene sam ponovo pokrene aplikaciju -- anotacija za injektovanje konfiguracija (pokretanje aplikacije)-- ova se postavlja na klasi koja sluzi za pokretanje aplikacije i ta klasa se mora nalaziti u root paketu (ovde rppstart)  -- ovo je kombinacija 3 anotacije 1. configuration (klasa koja definise spring bean-ove) 2. anable atuocofiguration (kreirace sprin bean-ove i jos nesto) 3. component scan (oznacava gde ce se traziti klase metode itd.)
public class RppStartApplication {

	public static void main(String[] args) { //Pocetna tacka izvrsavanja programa
		SpringApplication.run(RppStartApplication.class, args);
	}
	
	//metoda da prilikom starta app sve spring bean-ove (spring objekte) kojima upravlja spring inversion of control da ispise svaki od njih na konzoli da bi videli na konzoli sta to sve spring radi za nas da bi mogli koristimo
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
			//CommandLineRunner klasa
			//commandLineRunner metoda
			//ApplicationContext parametar je zaduzen za ucitavanje definicije bean-ova i medjusobno povezivanje bean-ova i prosledjivanje bean-ova kada su u nekoj klasi potrebni, kao neke zavisnosti
					return args -> {
						System.out.println("Beans provided by Spring Boot:");
						//sad vadimo te beanove
						String[]  beanNames = ctx.getBeanDefinitionNames();
						Arrays.sort(beanNames);
						for(String beanName: beanNames) {
						System.out.println(beanName);
						};
					};
	}

}
	

