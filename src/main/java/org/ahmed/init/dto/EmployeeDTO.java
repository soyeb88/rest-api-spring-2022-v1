package org.ahmed.init.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class EmployeeDTO {

	private long id;
	@NotNull(message = "{employee.name.absent}")
	@Pattern(regexp="[A-Za-z]+( [A-Za-z]+)*", message="{employee.name.invalid}")
	private String firstName;
	private String lastName;
	@Email(message = "{employee.emailid.invalid}")
	@NotNull(message = "{employee.emailid.absent}")
	private String emailId;
	
	@Override
	public String toString() {
		return "EmployeeDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ "]";
	}

	
}
