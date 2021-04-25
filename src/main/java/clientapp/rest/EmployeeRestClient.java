package clientapp.rest;

import clientapp.controller.AddEmployeeController;
import clientapp.dto.EmployeeDto;
import clientapp.handler.SavedEmployeeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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

    public void saveEmployee(EmployeeDto dto, SavedEmployeeHandler handler) {
        ResponseEntity<EmployeeDto> responseEntity = restTemplate.postForEntity(POST_EMPLOYEES_URL, dto, EmployeeDto.class);
        if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
            handler.handle();
        } else {
            logger.warn("Użytkownik nie został zapisany.");
        }
    }
}
