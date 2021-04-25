package clientapp.controller;

import clientapp.table.DepartmentTableModel;
import clientapp.table.EmployeeTableModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {


    private static final String ADD_EMPLOYEE_FXML = "/fxml/addEmployee.fxml";

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<EmployeeTableModel> employeeTableView;

    @FXML
    private TableView<DepartmentTableModel> departmentTableView;

    public MainViewController() {
    }

    @FXML
    public void onActionButtonAdd() {
        Alert alertButtonAdd = new Alert(Alert.AlertType.INFORMATION);
        alertButtonAdd.setTitle("Baza danych");
        alertButtonAdd.setHeaderText("Element został dodany do bazy danych");
        alertButtonAdd.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeDepartmentTableView();
        initializeEmployeeTableView();
        initializeAddEmployeeButton();
    }

     private void initializeAddEmployeeButton() {
         addButton.setOnAction((x) -> {
             Stage addEmployeeStage = new Stage();
             addEmployeeStage.initStyle(StageStyle.UNDECORATED);
             addEmployeeStage.initModality(Modality.APPLICATION_MODAL);
             try {
                Parent addEmployeeParent = FXMLLoader.load(getClass().getResource(ADD_EMPLOYEE_FXML));
                Scene scene = new Scene(addEmployeeParent, 500, 400);
                addEmployeeStage.setScene(scene);
                addEmployeeStage.show();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         });
     }
    private void initializeDepartmentTableView() {
        departmentTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn departmentIdColumn = new TableColumn("No");
        departmentIdColumn.setPrefWidth(5);
        departmentIdColumn.setCellValueFactory(new PropertyValueFactory<DepartmentTableModel, Long>("departmentId"));

        TableColumn departmentNameColumn = new TableColumn("Nazwa departamentu");
        departmentNameColumn.setMinWidth(20);
        departmentNameColumn.setCellValueFactory(new PropertyValueFactory<DepartmentTableModel, String>("departmentName"));

        departmentTableView.getColumns().addAll(departmentIdColumn, departmentNameColumn);

        ObservableList<DepartmentTableModel> departmentData = FXCollections.observableArrayList();
        loadDepartmentData(departmentData);
        departmentTableView.setItems(departmentData);
    }

    private void initializeEmployeeTableView() {
        employeeTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn employeeIdColumn = new TableColumn("No");
        employeeIdColumn.setPrefWidth(5);
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTableModel, Long>("employeeId"));

        TableColumn firstNameColumn = new TableColumn("Imię");
        firstNameColumn.setMinWidth(20);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTableModel, String>("firstName"));

        TableColumn lastNameColumn = new TableColumn("Nazwisko");
        lastNameColumn.setMinWidth(20);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTableModel, String>("lastName"));

        TableColumn peselColumn = new TableColumn("Pesel");
        peselColumn.setMinWidth(20);
        peselColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTableModel, String>("pesel"));

        TableColumn salaryColumn = new TableColumn("Pensja");
        salaryColumn.setMinWidth(20);
        salaryColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTableModel, Integer>("salary"));

        employeeTableView.getColumns().addAll(employeeIdColumn, firstNameColumn, lastNameColumn, peselColumn, salaryColumn);

        ObservableList<EmployeeTableModel> employeeData = FXCollections.observableArrayList();
        loadEmployeeData(employeeData);
        employeeTableView.setItems(employeeData);
    }

    private void loadEmployeeData(ObservableList<EmployeeTableModel> employeeData) {
        employeeData.add(new EmployeeTableModel(1L, "Szymon", "Pawełek", "82949487363", 3000));
        employeeData.add(new EmployeeTableModel(2L, "Julia", "Gulia", "85949487363", 4000));
        employeeData.add(new EmployeeTableModel(3L, "Szymon", "Guzik", "92349487363", 5000));
    }

    private void loadDepartmentData(ObservableList<DepartmentTableModel> departmentData) {
        departmentData.add(new DepartmentTableModel(1L, "IT"));
        departmentData.add(new DepartmentTableModel(2L, "HR"));
    }
}


