package clientapp.table;

import clientapp.dto.DepartmentDto;
import clientapp.dto.EmployeeDto;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class DepartmentTableModel {

    private final SimpleLongProperty departmentId;
    private final SimpleStringProperty departmentName;

    public DepartmentTableModel(Long departmentId, String departmentName) {
        this.departmentId = new SimpleLongProperty(departmentId);
        this.departmentName = new SimpleStringProperty(departmentName);
    }

    public long getDepartmentId() {
        return departmentId.get();
    }

    public String getDepartmentName() {
        return departmentName.get();
    }

    public static DepartmentTableModel of(DepartmentDto dto) {
        return new DepartmentTableModel(dto.getDepartmentId(), dto.getDepartmentName());
    }
}
