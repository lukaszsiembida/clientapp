package clientapp.dto;

import lombok.Data;

@Data
public class DepartmentDto {

    private Long departmentId;
    private String departmentName;


    @Override
    public String toString() {
        return departmentName;
    }
}
