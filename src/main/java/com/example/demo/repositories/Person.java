package com.example.demo.repositories;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.vault.repository.mapping.Secret;

@Secret("person")
@Data
public class Person {

	@Id
	private String id;

	private String firstname;
	private String lastname;
	private String ssn;
}
