package clientapp.controller;

import clientapp.dto.EmployeeDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {

    @FXML
    private BorderPane addEmployeeBorderPane;

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
            performTextFieldData();
        });
    }

    private void performTextFieldData() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String pesel = peselTextField.getText();
        Integer salary = Integer.valueOf(salaryTextField.getText());
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setPesel(pesel);
        dto.setSalary(salary);
    }


    private void initializeCancelButton() {
        cancelButton.setOnAction((x) -> {
            stage.close();
        });
    }

    public void setStage(Stage addEmployeeStage) {
        this.stage = addEmployeeStage;
    }
}
