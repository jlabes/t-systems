package com.tsystems.controller;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tsystems.model.Employee;
import com.tsystems.service.EmployeeService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class EmployeeControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private EmployeeService service;

	private MockMvc mockMvc;
	
	@Before
	public void setupMockMvc() {

		mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}

	@After
	public void deleteAllEmployees() {
		
		service.deleteAllEmployees();
	}
	
	@Test
	@WithMockUser(username="johnny")
	public void verifyHomePage() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("index"));
	}

	@Test
	@WithMockUser(username="johnny")
	public void verifyListEmployees() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/employees")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("employees"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("employees"))
				.andExpect(MockMvcResultMatchers.model().attribute("employees", Matchers.is(Matchers.empty())));
	}

	@Test
	@WithMockUser(username="johnny")
	public void verifyEmployeeAdded() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.post("/employee/register")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).param("name", "Johnny").param("lastname", "Labes")
				.param("age", "23").param("gender", "M").param("salary", "2200").param("active", "true"))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andExpect(MockMvcResultMatchers.header().string("Location", "/employees"));

		Employee employee = new Employee();
		employee.setName("Johnny");
		employee.setLastname("Labes");
		employee.setAge(23);
		employee.setGender('M');
		employee.setSalary(2200);
		employee.setActive(true);

		mockMvc.perform(MockMvcRequestBuilders.get("/employees")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.model().attributeExists("employees"))
				.andExpect(MockMvcResultMatchers.model().attribute("employees", Matchers.hasSize(1)))
				.andExpect(MockMvcResultMatchers.model().attribute("employees",
						Matchers.contains(Matchers.samePropertyValuesAs(employee, "id", "employer"))));
	}

}
