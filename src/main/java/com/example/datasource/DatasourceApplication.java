package com.example.datasource;

import com.example.datasource.person.*;
import com.example.datasource.task.Task;
import com.example.datasource.task.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DatasourceApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private TaskRepository taskRepository;

	public static void main(String[] args) {
		SpringApplication.run(DatasourceApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Person p = new Person();
		p.setName("Leonan");

		Task t = new Task();
		t.setName("Task 1");

		this.personRepository.save(p);
		this.taskRepository.save(t);
	}
}
