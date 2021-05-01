package clientapp.rest;

import clientapp.dto.EmployeeDto;
import clientapp.dto.SeekTextDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SeekRestClient {

    Logger logger = LoggerFactory.getLogger(SeekRestClient.class);
    private static final String SEEK_URL = "http://localhost:8080/employees/seek";
    private final RestTemplate restTemplate;

    public SeekRestClient() {
        this.restTemplate = new RestTemplate();
    }

    public List<EmployeeDto> getSeekListEmployees(SeekTextDto dto){
        ResponseEntity<EmployeeDto[]> employeesResponseEntity =
                restTemplate.postForEntity(SEEK_URL, dto, EmployeeDto[].class);
        logger.debug("Otrzymanie wyników wyszukiwania");
        return Arrays.asList(Objects.requireNonNull(employeesResponseEntity.getBody()));
    }
}
