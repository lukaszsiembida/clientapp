package clientapp.rest;

import clientapp.dto.DepartmentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class DepartmentRestClient {

    private static final String DEPARTMENTS_URL = "http://localhost:8080/departments";
    private final RestTemplate restTemplate;

    public DepartmentRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<DepartmentDto> getDepartments(){
        ResponseEntity<DepartmentDto[]> departmentsResponseEntity =
                restTemplate.getForEntity(DEPARTMENTS_URL, DepartmentDto[].class);
        return Arrays.asList(departmentsResponseEntity.getBody());
    }

    public void deleteDepartment(Long departmentId) {
        restTemplate.delete(DEPARTMENTS_URL+"/"+ departmentId);
    }
}
