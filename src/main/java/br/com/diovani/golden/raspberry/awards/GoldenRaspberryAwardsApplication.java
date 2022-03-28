package br.com.diovani.golden.raspberry.awards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.diovani.golden.raspberry.awards.services.ImportedMovieService;

@SpringBootApplication
public class GoldenRaspberryAwardsApplication implements CommandLineRunner {

	@Autowired
	private ImportedMovieService movieService;

	public static void main(String[] args) {
		SpringApplication.run(GoldenRaspberryAwardsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		movieService.load();
	}

}
