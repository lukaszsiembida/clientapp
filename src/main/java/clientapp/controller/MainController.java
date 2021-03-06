package clientapp.controller;

import clientapp.dto.DepartmentDto;
import clientapp.dto.EmployeeDto;
import clientapp.dto.SeekTextDto;
import clientapp.rest.DepartmentRestClient;
import clientapp.rest.EmployeeRestClient;
import clientapp.rest.SeekRestClient;
import clientapp.table.DepartmentTableModel;
import clientapp.table.EmployeeTableModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    Logger logger = LoggerFactory.getLogger(MainController.class);
    private static final String ADD_EMPLOYEE_FXML = "/fxml/addEmployee.fxml";
    private Stage stage;

    private final DepartmentRestClient departmentRestClient;
    private final EmployeeRestClient employeeRestClient;
    private final SeekRestClient seekRestClient;

    public MainController() {
        departmentRestClient = new DepartmentRestClient();
        employeeRestClient = new EmployeeRestClient();
        seekRestClient = new SeekRestClient();
    }

    private final ObservableList<DepartmentTableModel> departmentData = FXCollections.observableArrayList();
    private final ObservableList<EmployeeTableModel> employeeData = FXCollections.observableArrayList();
    private final SeekTextDto seekText = new SeekTextDto();

    @FXML
    private TextField seekField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TableView<EmployeeTableModel> employeeTableView;

    @FXML
    private TableView<DepartmentTableModel> departmentTableView;

    @FXML
    public void onActionButtonAdd() {
        Alert alertButtonAdd = new Alert(Alert.AlertType.INFORMATION);
        alertButtonAdd.setTitle("Baza danych");
        alertButtonAdd.setHeaderText("Element zosta?? dodany do bazy danych");
        alertButtonAdd.showAndWait();
    }

    @FXML
    void onActionButtonDelete(ActionEvent event) {
        Alert alertButtonDelete = new Alert(Alert.AlertType.INFORMATION);
        alertButtonDelete.setTitle("Baza danych");
        alertButtonDelete.setHeaderText("Element zosta?? usuni??ty z bazy danych");
        alertButtonDelete.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initializeDepartmentTableView();
        initializeEmployeeTableView();
        initializeAddEmployeeButton();
        initializeDeleteButton();
        dynamicSeek();
    }

    private void initializeAddEmployeeButton() {
        addButton.setOnAction((x) -> {
            Stage addEmployeeStage = new Stage();
            addEmployeeStage.initOwner(stage.getOwner());
            addEmployeeStage.initModality(Modality.APPLICATION_MODAL);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ADD_EMPLOYEE_FXML));
                Parent addEmployeeParent = fxmlLoader.load();
                ((AddEmployeeController) fxmlLoader.getController()).setStage(addEmployeeStage);
                Scene scene = new Scene(addEmployeeParent, 500, 450);
                addEmployeeStage.setScene(scene);
                addEmployeeStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
            loadDepartmentData();
            loadEmployeeData();
        });

    }

    private void initializeDeleteButton() {
        deleteButton.setOnAction((x) -> {
            DepartmentTableModel selectedDepartment = departmentTableView.getSelectionModel().getSelectedItem();
            EmployeeTableModel selectedEmployee = employeeTableView.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                selectedDepartment = null;
                employeeRestClient.deleteEmployee(selectedEmployee.getEmployeeId());
                logger.debug("Usuni??cie rekordu dzia??u!");
            }
            if (selectedDepartment != null) {
                departmentRestClient.deleteDepartment(selectedDepartment.getDepartmentId());
                logger.debug("Usuni??cie rekordu dzia??u!");
            }
            loadDepartmentData();
            loadEmployeeData();
        });
    }

    private void initializeDepartmentTableView() {
        departmentTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn departmentIdColumn = new TableColumn("No");
        departmentIdColumn.setPrefWidth(5);
        departmentIdColumn.setCellValueFactory(new PropertyValueFactory<DepartmentTableModel, Long>("departmentId"));

        TableColumn departmentNameColumn = new TableColumn("Nazwa dzia??u");
        departmentNameColumn.setMinWidth(20);
        departmentNameColumn.setCellValueFactory(new PropertyValueFactory<DepartmentTableModel, String>("departmentName"));

        departmentTableView.getColumns().addAll(departmentIdColumn, departmentNameColumn);

        loadDepartmentData();
        logger.debug("Za??adowanie danych do widoku tabeli dzia??u");
    }

    private void initializeEmployeeTableView() {
        employeeTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn employeeIdColumn = new TableColumn("No");
        employeeIdColumn.setPrefWidth(5);
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<EmployeeTableModel, Long>("employeeId"));

        TableColumn firstNameColumn = new TableColumn("Imi??");
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
        loadEmployeeData();
        logger.debug("Za??adowanie danych do widoku tabeli pracownika");

    }

    private void loadEmployeeData() {
        departmentTableView.setOnMouseClicked(d -> {
            for (DepartmentTableModel departmentTable : departmentTableView.getSelectionModel().getSelectedItems()) {
                List<EmployeeDto> employeeDtos = employeeRestClient.getEmployees();
                List<EmployeeTableModel> employeeTableModels = employeeDtos.stream()
                        .filter(e -> departmentTable.getDepartmentName().equals(e.getDepartmentName()))
                        .map(EmployeeTableModel::of)
                        .collect(Collectors.toList());
                this.employeeData.clear();
                this.employeeData.addAll(employeeTableModels);
                employeeTableView.setItems(employeeData);
            }
        });
    }

    private void loadDepartmentData() {
        Thread thread = new Thread(() -> {
            List<DepartmentDto> departmentDtos = departmentRestClient.getDepartments();
            List<DepartmentTableModel> departmentTableModels = departmentDtos.stream().map(DepartmentTableModel::of).collect(Collectors.toList());
            this.departmentData.clear();
            this.departmentData.addAll(departmentTableModels);
            departmentTableView.setItems(departmentData);
        });
        thread.start();
    }

    public void setStage(Stage primaryStage) {
        this.stage = primaryStage;

    }

    public void dynamicSeek() {
              seekField.textProperty().addListener((observable) -> {
                  if(seekField.getText()==null || seekField.getText().equals("")){
                      this.employeeData.clear();
                  } else {
                      this.seekText.setSeekText(seekField.getText());
                      logger.debug("Odebranie listy pracownik??w z backendu.");
                      List<EmployeeDto> seekList = seekRestClient.getSeekListEmployees(seekText);
                      List<EmployeeTableModel> employeeTableModel = seekList.stream().map(EmployeeTableModel::of).collect(Collectors.toList());
                      this.employeeData.clear();
                      this.employeeData.addAll(employeeTableModel);
                  }
                  employeeTableView.setItems(employeeData);
              });
    }
}


