package clientapp.table;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class EmployeeTableModel {

    private final SimpleLongProperty employeeId;
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty pesel;
    private final SimpleIntegerProperty salary;


    public EmployeeTableModel(Long id, String firstName, String lastName, String pesel, Integer salary) {
        this.employeeId = new SimpleLongProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.pesel = new SimpleStringProperty(pesel);
        this.salary = new SimpleIntegerProperty(salary);
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

    public Integer getSalary() {
        return salary.get();
    }
}
