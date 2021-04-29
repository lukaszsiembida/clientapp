package clientapp.rest;

import clientapp.dto.DepartmentDto;
import clientapp.handler.ProcessFinishedHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class DepartmentRestClient {

    Logger logger = LoggerFactory.getLogger(DepartmentRestClient.class);
    private static final String DEPARTMENTS_URL = "http://localhost:8080/departments";
    private static final String POST_DEPARTMENT_URL = "http://localhost:8080/departments/add";
    private final RestTemplate restTemplate;

    public DepartmentRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<DepartmentDto> getDepartments(){
        ResponseEntity<DepartmentDto[]> departmentsResponseEntity =
                restTemplate.getForEntity(DEPARTMENTS_URL, DepartmentDto[].class);
        return Arrays.asList(departmentsResponseEntity.getBody());
    }

    public void saveDepartment(DepartmentDto dto, ProcessFinishedHandler handler) {
        ResponseEntity<DepartmentDto> responseEntity = restTemplate.postForEntity(POST_DEPARTMENT_URL, dto, DepartmentDto.class);
        if(HttpStatus.OK.equals(responseEntity.getStatusCode())){
            handler.handle();
            logger.debug("Udany zapis dto");
        } else {
            throw new RuntimeException("Nie można zapisać dto: " + dto);
        }
    }
    public void deleteDepartment(Long departmentId) {
        restTemplate.delete(DEPARTMENTS_URL+"/"+ departmentId);
    }
}
