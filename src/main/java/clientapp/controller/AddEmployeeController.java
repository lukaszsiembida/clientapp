package clientapp.controller;

import clientapp.dto.DepartmentDto;
import clientapp.dto.EmployeeDto;
import clientapp.rest.EmployeeRestClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {

    Logger logger = LoggerFactory.getLogger(AddEmployeeController.class);
    private  final EmployeeRestClient employeeRestClient;

    private DepartmentDto selected;

    public AddEmployeeController() {
        this.employeeRestClient = new EmployeeRestClient();
    }

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField peselTextField;

    @FXML
    private TextField salaryTextField;

    @FXML
    private TextField departmentNameTextField;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeCancelButton();
        initializeSaveButton();
    }

    private void initializeSaveButton() {
        saveButton.setOnAction((x) -> {
            EmployeeDto dto = createEmployeeDto();
            Thread thread = new Thread(() -> {
                employeeRestClient.saveEmployee(dto);
                Platform.runLater(() ->{
                    stage.close();
                });
            });
            thread.start();
    });}

    private EmployeeDto createEmployeeDto() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String pesel = peselTextField.getText();
        Double salary = Double.parseDouble(salaryTextField.getText());
        String departmentName = departmentNameTextField.getText();
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setPesel(pesel);
        dto.setSalary(salary);
        dto.setDepartmentName(departmentName);
        return dto;
    }

    private void initializeCancelButton() {
        cancelButton.setOnAction((x) -> {
            logger.debug("Anulowanie zapisu danych pracownika");
            stage.close();
        });
    }

    public void setStage(Stage addEmployeeStage) {
        this.stage = addEmployeeStage;
    }
}
