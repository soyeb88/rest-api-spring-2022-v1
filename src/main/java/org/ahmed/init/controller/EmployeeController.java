package org.ahmed.init.controller;

import java.util.List;

import org.ahmed.init.dto.EmployeeDTO;
import org.ahmed.init.exception.ResourceNotFoundException;
import org.ahmed.init.model.Employee;
import org.ahmed.init.service.EmployeeService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@CrossOrigin(origins="http://localhost:3000/") //-->buillt in run
//@CrossOrigin(origins = "http://localhost:8080/") // -->Local Machin
//@CrossOrigin(origins="http://192.168.1.46/")	  //-->Local Router
//@CrossOrigin(origins="http://98.15.45.185:9090/") //-->Internet

@RestController
@RequestMapping(value = "/api/v1/")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	
	@Autowired
	private Environment environment;

	@GetMapping(value = "/employees")
	public ResponseEntity<List<EmployeeDTO>> getAllEmployee() throws ResourceNotFoundException {
		List<EmployeeDTO> employeeList = employeeService.getAllEmployees();
		return new ResponseEntity<>(employeeList, HttpStatus.OK);
	}

	@GetMapping(value = "/employees/{id}")
	public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) throws ResourceNotFoundException{

		EmployeeDTO employeeDTO = employeeService.getEmployee(id);				
		return new ResponseEntity<>(employeeDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/employees")
	public ResponseEntity<String> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) throws ResourceNotFoundException{
		
		Long employeeId = employeeService.addEmloyee(employeeDTO);
		String successMessage = environment.getProperty("API.INSERT_SUCCESS") + employeeId;
		
		return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/employees/{id}")
	public ResponseEntity<String> updateEmployeeById(@PathVariable Long id, @RequestBody Employee employeeDetails) 
			throws ResourceNotFoundException{


		long employeeId = employeeService.updateEmployee(id, employeeDetails);		
		String successMessage = environment.getProperty("API.UPDATE_SUCCESS") + employeeId;		
		return new ResponseEntity<>(successMessage, HttpStatus.OK);
	}

	@DeleteMapping(value = "/employees/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable Long id) throws ResourceNotFoundException{
		
		String successMessage = environment.getProperty("API.DELETE_SUCCESS") + employeeService.deleteEmployee(id);		
		return new ResponseEntity<String>(successMessage, HttpStatus.OK);
	}

}
