package clientapp.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

   /* @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField peselTextField;

    @FXML
    private TextField salaryTextField;*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeCancelButton();
    }

    private void initializeCancelButton() {
       cancelButton.setOnAction((x) -> {});
        getStage().close();
    }

    private Stage getStage(){
        return (Stage) addEmployeeBorderPane.getScene().getWindow();
    }
}
