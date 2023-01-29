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
import static org.mockito.BDDMockito.*;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.junit.jupiter.api.Test;
import org.ahmed.init.dto.EmployeeDTO;
import org.ahmed.init.exception.ResourceNotFoundException;
import org.ahmed.init.model.Employee;
import org.ahmed.init.service.EmployeeService;
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
	private EmployeeService employeeService;
	//private EmployeeRepository employeeRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	/**
	   * This method is test to add random employee. 
	   * @param null
	   * @return null.
	   */
	@Test
	@WithMockUser(username = "devs", password = "password", roles = "ADMIN")
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{

		//any(EmployeeDTO.class);
		
        // given - precondition or setup
		
		EmployeeDTO employeeDTO = EmployeeDTO.builder().firstName("Soyeb").lastName("Ahmed").emailId("soo@gmail.com").build();
          
		
        given(employeeService.addEmloyee(employeeDTO)).willAnswer((invocation)-> invocation.getArgument(0));
        
        ResultActions response = mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDTO)));
        
        System.out.println(employeeDTO);
        
        response.andExpect(status().isCreated())
        		.andDo(print());
        
	}
	
	// JUnit test for Get All employees REST API
	@Test
	@WithMockUser(username = "devs", password = "password", roles = "ADMIN")
	public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeesList() throws Exception{
		// given - precondition or setup
		List<EmployeeDTO> listOfEmployees = new ArrayList<EmployeeDTO>();
		listOfEmployees.add(EmployeeDTO.builder().firstName("Soyeb").lastName("Ahmed").emailId("soyeb88@gmail.com").build());
		listOfEmployees.add(EmployeeDTO.builder().firstName("Mahfuz").lastName("Kanon").emailId("soyeb88@gmail.com").build());
		
		given(employeeService.getAllEmployees()).willReturn(listOfEmployees);
		
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
	@WithMockUser(username = "devs", password = "password", roles = "ADMIN")
	public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception{
		// given - precondition or setup
		long employeeId = 1L;
		
		EmployeeDTO employeeDTO = EmployeeDTO.builder().firstName("Soyeb").lastName("Ahmed").emailId("s@gmail.com").build();
		given(employeeService.getEmployee(employeeId)).willReturn(employeeDTO);
		
		// when -  action or the behaviour that we are going test
		ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}",employeeId));
		
		// then - verify the output
		response.andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.firstName", is(employeeDTO.getFirstName())))
				.andExpect(jsonPath("$.lastName", is(employeeDTO.getLastName())))
				.andExpect(jsonPath("$.emailId", is(employeeDTO.getEmailId())));
	}
	
	// negative scenario - valid employee id
    // JUnit test for GET employee by id REST API
	
	@Test
    @WithMockUser(username = "devs", password = "password", roles = "ADMIN")
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        EmployeeDTO employeeDTO = EmployeeDTO.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .emailId("ramesh@gmail.com")
                .build();
        given(employeeService.getEmployee(employeeId)).willThrow(new ResourceNotFoundException("message"));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}", employeeId));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }
    
    
 // JUnit test for update employee REST API - positive scenario
    @Test
    @WithMockUser(username = "devs", password = "password", roles = "ADMIN")
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        EmployeeDTO savedEmployee = EmployeeDTO.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .emailId("ramesh@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Ram")
                .lastName("Jadhav")
                .emailId("ram@gmail.com")
                .build();
    
        given(employeeService.getEmployee(employeeId)).willReturn(savedEmployee);
        given(employeeService.updateEmployee(employeeId ,updatedEmployee))
        					.willAnswer((invocation)-> invocation.getArgument(0));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/v1/employees/{id}", employeeId)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(updatedEmployee)));


        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());
    }

    /*
 // JUnit test for update employee REST API - negative scenario
    @Test
    @WithMockUser(username = "devs", password = "password", roles = "ADMIN")
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        EmployeeDTO savedEmployee = EmployeeDTO.builder()
                .firstName("Ramesh")
                .lastName("Fadatare")
                .emailId("ramesh@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Ram")
                .lastName("Jadhav")
                .emailId("ram@gmail.com")
                .build();
        
             
        given(employeeService.getEmployee(employeeId)).willThrow(new ResourceNotFoundException("message"));
        given(employeeService.updateEmployee(employeeId, updatedEmployee)).willThrow(new ResourceNotFoundException("message"));
        	//.willAnswer((invocation)-> invocation.getArgument(0));

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/v1/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }
	*/
    
// JUnit test for delete employee REST API
    @Test
    @WithMockUser(username = "devs", password = "password", roles = "ADMIN")
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception{
        // given - precondition or setup
        long employeeId = 40L;
        
        given(employeeService.deleteEmployee(employeeId)).willReturn(40L);

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
