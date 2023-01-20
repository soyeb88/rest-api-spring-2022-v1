package org.ahmed.init.test;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;

import org.ahmed.init.model.Employee;
import org.ahmed.init.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
* <h1>EmployeeControllerTest</h1> program implements an application that
* <p><b>Note:</b>Simply Junit test on CRUD operation to the standard output.</p>
*
* @author  Soyeb Ahmed
* @version 1.0
* @since   2023-01-18 
*/
@WebMvcTest
public class EmployeeControllerTestUnitTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	/**
	   * This method is test to add random employee. 
	   * @param null
	   * @return null.
	   */
	@Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{

        // given - precondition or setup
		
		Employee employee = Employee.builder().firstName("Soyeb").lastName("Ahmed").emailId("soo@gmail.com").build();
              	
        given(employeeRepository.save(any(Employee.class))).willAnswer((invocation)-> invocation.getArgument(0));
        
        ResultActions response = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));
        
        response.andDo(print()).
        andExpect(status().isCreated()).andExpect(status().isCreated())
        .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
        .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
        .andExpect(jsonPath("$.emailId", is(employee.getEmailId())));
        
	}
	
	// JUnit test for Get All employees REST API
	@Test
	public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{
		// given - precondition or setup
		List<Employee> listOfEmployees = new ArrayList<Employee>();
		listOfEmployees.add(Employee.builder().firstName("Soyeb").lastName("Ahmed").emailId("soyeb88@gmail.com").build());
		listOfEmployees.add(Employee.builder().firstName("Mahfuz").lastName("Kanon").emailId("soyeb88@gmail.com").build());
		
		given(employeeRepository.findAll()).willReturn(listOfEmployees);
		
		// when -  action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/api/v1/employees"));
		
		// then - verify the output
		response.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.size()", is(listOfEmployees.size())));
	}
		
	// positive scenario - valid employee id
	// JUnit test for GET employee by id REST API
	@Test
	public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception{
		// given - precondition or setup
		long employeeId = 1L;
		
		Employee employee = Employee.builder().firstName("Soyeb").lastName("Ahmed").emailId("s@gmail.com").build();
		given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));
		
		// when -  action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}",employeeId));
		
		// then - verify the output
		response.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(employee.getLastName())))
				.andExpect(jsonPath("$.emailId", is(employee.getEmailId())));
	}
	
	// negative scenario - valid employee id
    // JUnit test for GET employee by id REST API
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .emailId("ramesh@gmail.com")
                .build();
        given(employeeRepository.findById(employeeId)).willReturn(Optional.empty());

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }
    
 // JUnit test for update employee REST API - positive scenario
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .emailId("ramesh@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Ram")
                .lastName("Jadhav")
                .emailId("ram@gmail.com")
                .build();
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(savedEmployee));
        given(employeeRepository.save(any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/v1/employees/{id}", employeeId)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(updatedEmployee)));


        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.emailId", is(updatedEmployee.getEmailId())));
    }

    
 // JUnit test for update employee REST API - negative scenario
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .emailId("ramesh@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Ram")
                .lastName("Jadhav")
                .emailId("ram@gmail.com")
                .build();
        given(employeeRepository.findById(employeeId)).willReturn(Optional.empty());
        given(employeeRepository.save(any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/v1/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

// JUnit test for delete employee REST API
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception{
        // given - precondition or setup
        long employeeId = 40L;
        
        Employee employee = Employee.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .emailId("ramesh@gmail.com")
                .build();
        given(employeeRepository.findById(employeeId)).willReturn(Optional.of(employee));
        willDoNothing().given(employeeRepository).delete(employee);

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(delete("/api/v1/employees/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

}

/*
 * JavaDoc: https://www.tutorialspoint.com/java/java_documentation.htm
 *tutorial: https://www.javaguides.net/2022/03/spring-boot-unit-testing-crud-rest-api-with-junit-and-mockito.html
 Error Solution - Lombok:https://stackoverflow.com/questions/50991619/the-method-builder-is-undefined-for-the-type-builderexample
 */
