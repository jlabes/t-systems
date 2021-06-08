package com.tsystems.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tsystems.model.Employer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EmployerServiceTest {
	
	@Autowired
	private EmployerService service;

	@Test
	public void verifyIfEmployersAreNotNull() {

		List<Employer> employers = service.getEmployers();

		assertEquals(false, employers.isEmpty());

		assertEquals(2, employers.size());

		employers.forEach(p -> assertEquals(true, p != null));
	}
}
