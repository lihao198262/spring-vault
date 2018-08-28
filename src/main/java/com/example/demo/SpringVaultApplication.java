package com.example.demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.repository.configuration.EnableVaultRepositories;
import org.springframework.vault.support.VaultResponse;

import com.example.demo.repositories.Person;
import com.example.demo.repositories.PersonRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableVaultRepositories
public class SpringVaultApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringVaultApplication.class, args);
	}

	@Autowired
	DataSource dataSource;

	@PostConstruct
	private void postConstruct() throws Exception {

		System.out.println("0 ##########################");

		try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {

			ResultSet resultSet = statement.executeQuery("SELECT CURRENT_USER();");
			resultSet.next();

			System.out.println("Connection works with User: " + resultSet.getString(1));

			resultSet.close();
		}

		System.out.println("0 ##########################");
	}

	@Value("${mykey}")
	String mykey;

	@PostConstruct
	private void postConstruct1() {
		System.out.println("1 ##########################");
		System.out.println(mykey);
		System.out.println("1 ##########################");
	}

	@Autowired
	PersonRepository repository;

	@Autowired
	VaultTemplate template;

	@PostConstruct
	private void postConstruct2() {

		System.out.println("2 ##########################");

		Person person = new Person();
		person.setId("heisenberg");
		person.setFirstname("Walter");
		person.setLastname("White");
		person.setSsn("1234");

		repository.save(person);

		log.info("Wrote data to Vault");

		VaultResponse response = template.read("secret/person/heisenberg");

		log.info("Retrieved data {} from Vault via Template API", response.getData());

		Optional<Person> loaded = repository.findById(person.getId());
		log.info("Retrieved data {} from Vault via Repository", loaded.get());

		System.out.println("2 ##########################");
	}
}
