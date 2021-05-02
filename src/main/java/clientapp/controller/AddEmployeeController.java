package clientapp.controller;

import clientapp.dto.DepartmentDto;
import clientapp.dto.EmployeeDto;
import clientapp.factory.PopupFactory;
import clientapp.rest.DepartmentRestClient;
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
    private final EmployeeRestClient employeeRestClient;
    private final DepartmentRestClient departmentRestClient;

    private final PopupFactory popupFactory;

    public AddEmployeeController() {
        this.departmentRestClient = new DepartmentRestClient();
        this.employeeRestClient = new EmployeeRestClient();
        this.popupFactory = new PopupFactory();

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
            boolean isEmployeeDataFieldsEmpty = firstNameTextField.getText().isEmpty() ||
                    lastNameTextField.getText().isEmpty() ||
                    peselTextField.getText().isEmpty() || departmentNameTextField.getText().isEmpty();
            boolean isDepartmentNameFieldNotEmpty = (firstNameTextField.getText().isEmpty() ||
                    lastNameTextField.getText().isEmpty() ||
                    peselTextField.getText().isEmpty()) && !departmentNameTextField.getText().isEmpty();
            boolean isSalaryFieldParseDouble = salaryTextField.getText().matches("^\\d*.\\d{2}");
            boolean isPeselCorrect = peselTextField.getText().matches("[0-9]{11}");
            boolean isPeselNotUnique = employeeRestClient.getEmployees().stream()
                    .map(EmployeeDto::getPesel).anyMatch(e -> e.equals(peselTextField.getText()));
            boolean isDepartmentNotUnique = departmentRestClient.getDepartments().stream()
                    .map(DepartmentDto::getDepartmentName).anyMatch(d -> d.equals(departmentNameTextField.getText()));
            if (!isEmployeeDataFieldsEmpty && isSalaryFieldParseDouble && isPeselCorrect && !isPeselNotUnique) {
                EmployeeDto employeeDto = createEmployeeDto();
                employeeRestClient.saveEmployee(employeeDto, () -> {
                    Stage infoPopup = popupFactory.createInfoPopup("Pracownik został pomyślnie zapisany", () -> {
                        Platform.runLater(() -> {
                            stage.close();
                        });
                    });
                    infoPopup.show();
                });
        } else if (isDepartmentNameFieldNotEmpty && !isDepartmentNotUnique) {
            DepartmentDto departmentDto = createDepartmentDto();
            departmentRestClient.saveDepartment(departmentDto, () -> {
                Stage infoPopup = popupFactory.createInfoPopup("Dział został pomyślnie zapisany", () -> {
                    Platform.runLater(() -> {
                        stage.close();
                    });
                });
                infoPopup.show();
            });
        } else {
                String infoText ="";
                if(isEmployeeDataFieldsEmpty){
                    infoText += "Uzupełnij brakujące pola.\n";
                }
                if(!isSalaryFieldParseDouble){
                    infoText += "Pensja w nieprawidłowym formacie.\n";
                }
                if (!isPeselCorrect){
                    infoText += "Pesel w nieprawidłowym formacie.\n";
                }
                if (isPeselNotUnique){
                    infoText += "Pesel nie jest unikalny.\n";
                }
                if(isDepartmentNameFieldNotEmpty && isDepartmentNotUnique){
                    infoText = "Nie można dodać działu.\nDział o takiej nazwie już istnieje.";
                }
                popupFactory.createInfoPopup(infoText).show();
            }
    });}

    private DepartmentDto createDepartmentDto() {
        String departmentName = departmentNameTextField.getText();
        DepartmentDto dto = new DepartmentDto();
        dto.setDepartmentName(departmentName);
        return dto;
    }

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
