package clientapp.dto;

import lombok.Data;

@Data
public class EmployeeDto {

    private Long employeeId;
    private String firstName;
    private String lastName;
    private String pesel;
    private Integer salary;

}
