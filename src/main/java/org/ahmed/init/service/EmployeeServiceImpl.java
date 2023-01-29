package org.ahmed.init.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.ahmed.init.dto.EmployeeDTO;
import org.ahmed.init.exception.ResourceNotFoundException;
import org.ahmed.init.model.Employee;
import org.ahmed.init.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("employeeService")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	//@Autowired
	private EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Autowired
	Environment environment;

	@Override
	public List<EmployeeDTO> getAllEmployees() throws ResourceNotFoundException {

		Iterable<Employee> employees = employeeRepository.findAll();
		List<EmployeeDTO> employeeDTOs = new ArrayList<>();
		employees.forEach(employee -> {
			EmployeeDTO empl = new EmployeeDTO();
			empl.setId(employee.getId());
			empl.setFirstName(employee.getFirstName());
			empl.setLastName(employee.getLastName());
			empl.setEmailId(employee.getEmailId());
			employeeDTOs.add(empl);
		});

		if (employeeDTOs.isEmpty()) {
			throw new ResourceNotFoundException(environment.getProperty("Service.EMPLOYEES_NOT_FOUND"));
		}
		return employeeDTOs;
	}

	@Override
	public EmployeeDTO getEmployee(Long employeeId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException(
				environment.getProperty("Service.EMPLOYEE_NOT_FOUND") + employeeId));

		
		EmployeeDTO employeeDTO = new EmployeeDTO();
		employeeDTO.setId(employee.getId());
		employeeDTO.setFirstName(employee.getFirstName());
		employeeDTO.setLastName(employee.getLastName());
		employeeDTO.setEmailId(employee.getEmailId());
		
		return employeeDTO;
		
	}

	@Override
	public Long addEmloyee(EmployeeDTO employeeDTO) throws ResourceNotFoundException {

		Employee employee = new Employee();
		employee.setFirstName(employeeDTO.getFirstName());
		employee.setLastName(employeeDTO.getLastName());
		employee.setEmailId(employeeDTO.getEmailId());

		Employee employee2 = employeeRepository.save(employee);

		return employee2.getId();
	}

	@Override
	public long updateEmployee(Long employeeId, Employee employeeDetails) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ResourceNotFoundException(environment.getProperty("Service.EMPLOYEE_NOT_FOUND ") + employeeId));

		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());

		employeeRepository.save(employee);

		return employee.getId();
	}

	@Override
	public long deleteEmployee(Long employeeId) throws ResourceNotFoundException {

		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ResourceNotFoundException(environment.getProperty("Service.EMPLOYEES_NOT_FOUND")));

		employeeRepository.delete(employee);

		return employee.getId();

	}
	
}
