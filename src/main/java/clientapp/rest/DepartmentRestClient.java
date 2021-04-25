package clientapp.rest;

import clientapp.dto.DepartmentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class DepartmentRestClient {

    private static final String GET_DEPARTMENTS_URL = "http://localhost:8080/departments";
    private final RestTemplate restTemplate;

    public DepartmentRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<DepartmentDto> getDepartments(){
        ResponseEntity<DepartmentDto[]> departmentsResponseEntity =
                restTemplate.getForEntity(GET_DEPARTMENTS_URL, DepartmentDto[].class);
        return Arrays.asList(departmentsResponseEntity.getBody());
    }

}
