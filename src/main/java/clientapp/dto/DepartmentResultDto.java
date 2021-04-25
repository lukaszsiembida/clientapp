package clientapp.dto;

import lombok.Data;

@Data
public class DepartmentResultDto {

    private Long departmentId;
    private String departmentName;
    private boolean authenticated;

}
