package clientapp.table;

import clientapp.dto.EmployeeDto;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmployeeTableModel {

    private final SimpleLongProperty employeeId;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty pesel;
    private final SimpleDoubleProperty salary;


    public EmployeeTableModel(Long id, String firstName, String lastName, String pesel, Double salary) {
        this.employeeId = new SimpleLongProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.pesel = new SimpleStringProperty(pesel);
        this.salary = new SimpleDoubleProperty(salary);
    }

    public Long getEmployeeId() {
        return employeeId.get();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public String getLastName() {
        return lastName.get();
    }

    public String getPesel() {
        return pesel.get();
    }

    public double getSalary() {
        return salary.get();
    }

    public static EmployeeTableModel of(EmployeeDto dto) {
        return new EmployeeTableModel(dto.getEmployeeId(), dto.getFirstName(),
                dto.getLastName(), dto.getPesel(), dto.getSalary());
    }
}
