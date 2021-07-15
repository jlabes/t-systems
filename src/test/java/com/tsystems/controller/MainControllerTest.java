package com.tsystems.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.tsystems.TestConfig;
import com.tsystems.domain.Employee;
import com.tsystems.domain.EmployeeDto;
import com.tsystems.domain.Employer;
import com.tsystems.domain.User;
import com.tsystems.repository.EmployeeRepository;
import com.tsystems.service.EmployeeService;
import com.tsystems.service.EmployerService;

@AutoConfigureJsonTesters
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = MainController.class)
@Import(TestConfig.class)
public class MainControllerTest {

	@MockBean
	private EmployeeService employeeService;

	@MockBean
	private HttpSession session;

	@Autowired
	private JacksonTester<EmployeeDto> jsonRequest;

	@Autowired
	private JacksonTester<Employee> jsonResponse;

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private Employer employer;
	
	public void testGetAllEmployee() {
		//pagina 369
	}

	@Test
	public void testAddEmployee() throws Exception {
		
		EmployeeDto employeeDto = new EmployeeDto("Tsystems", "Tsystems", 30, 'M', 2200, true);

		when(session.getAttribute(eq("currentUser"))).thenReturn(new User(employer));
		
		MockHttpServletResponse response = mockMvc.perform(post("/employee/register")
				.contentType(MediaType.APPLICATION_JSON).content(jsonRequest.write(employeeDto).getJson())).andReturn()
				.getResponse();
		
		assertEquals(response.getStatus(), status().is3xxRedirection());
		assertEquals(response.getRedirectedUrl(), "/employees");
		
	}

}
