package clientapp.controller;

import clientapp.dto.EmployeeDto;
import clientapp.rest.EmployeeRestClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {

    Logger logger = LoggerFactory.getLogger(AddEmployeeController.class);
    private  final EmployeeRestClient employeeRestClient;

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

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeCancelButton();
        initializeSaveButton();
    }

    private void initializeSaveButton() {
        saveButton.setOnAction((x) -> {
            EmployeeDto dto = createEmployeeDto();
            ResponseEntity responseEntity = employeeRestClient.saveEmployee(dto);
            if(ResponseEntity.ok().equals(responseEntity)){
                logger.debug("Zapisano dane pracownika");
            } else {
               logger.debug("Zapis danych nie powiódł się");
            }
            stage.close();
    });}

    private EmployeeDto createEmployeeDto() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String pesel = peselTextField.getText();
        Double salary = Double.valueOf(salaryTextField.getText());
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setPesel(pesel);
        dto.setSalary(salary);
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
