package clientapp.rest;

import clientapp.dto.EmployeeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class EmployeeRestClient {

    Logger logger = LoggerFactory.getLogger(EmployeeRestClient.class);
    private static final String GET_EMPLOYEES_URL = "http://localhost:8080/employees";
    private static final String POST_EMPLOYEES_URL = "http://localhost:8080/employees/add";
    private final RestTemplate restTemplate;

    public EmployeeRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<EmployeeDto> getEmployees(){
        ResponseEntity<EmployeeDto[]> employeesResponseEntity =
                restTemplate.getForEntity(GET_EMPLOYEES_URL, EmployeeDto[].class);
        return Arrays.asList(employeesResponseEntity.getBody());
    }

    public ResponseEntity saveEmployee(EmployeeDto dto) {
        ResponseEntity<EmployeeDto> responseEntity = restTemplate.postForEntity(POST_EMPLOYEES_URL, dto, EmployeeDto.class);
        return responseEntity;
    }
}
