package Controller;

// General
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

// GUI
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;

// Custom Classes
import App.getData;
import AppointmentData.AppointmentData;
import readFile.readFile;
import timeSlotData.timeSlotData;

public class staffDashboardController implements Initializable {

    // ========================================================Left side of the Page
    @FXML
    private Label username;

    @FXML
    private Button SignOutBtn;

    @FXML
    private Button dashboardBtn;

    @FXML
    private Button requestsBtn;

    @FXML
    private Button timeSlotInfoBtn;

    @FXML
    private Button medicalRecordsBtn;

    // ======================================================== Dashboard Panel
    // Elements
    @FXML
    private AreaChart<String, Double> dashboardChart;

    @FXML
    private Label availableDoctor;

    @FXML
    private Label totalCustomers;

    @FXML
    private Label totalIncome;

    // ======================================================== Time Slot Info Panel
    // Elements
    @FXML
    private TextField searchBar;

    @FXML
    private Button importBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private ImageView docImage;

    @FXML
    private TableView<timeSlotData> timeSlotInfoTableView;

    @FXML
    private TableColumn<timeSlotData, String> dayColumn;

    @FXML
    private TableColumn<timeSlotData, String> timeColumn;

    @FXML
    private ComboBox<String> doctorNameComboBox;

    @FXML
    private TextField docIdTextField;

    @FXML
    private TextField docNameTextField;

    @FXML
    private TextField dayTextField;

    @FXML
    private TextField timeTextField;

    // ======================================================== Request Panel
    // Elements
    @FXML
    private TableView<AppointmentData> staffTableView;

    @FXML
    private TableColumn<AppointmentData, String> BookingDate;

    @FXML
    private TableColumn<AppointmentData, String> BookingTime;

    @FXML
    private TableColumn<AppointmentData, String> DoctorName;

    @FXML
    private TableColumn<AppointmentData, String> PatientID;

    @FXML
    private TableColumn<AppointmentData, String> PatientName;

    @FXML
    private ComboBox<String> requestDocName;

    @FXML
    private ComboBox<String> requestPatientId;

    @FXML
    private ComboBox<String> requestPatientName;

    @FXML
    private Button approve;

    @FXML
    private Button cancel;

    @FXML
    private ComboBox<String> requestStatus;

    // ==================================================== Medical Records Panel
    @FXML
    private ComboBox<String> medicalRecordPatientName;

    @FXML
    private TextField historyOfPresentIllness;

    @FXML
    private TextField medicalRecordNumber;

    @FXML
    private TextField DOB;

    @FXML
    private TextField cheifComplaint;

    @FXML
    private TextArea pastMedicalHistory;

    @FXML
    private TextField Medication;

    @FXML
    private TextField allergies;

    @FXML
    private TextArea physicalExam;

    @FXML
    private TextArea assesmentAndPlan;

    // ======================================================== Whole Page
    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private AnchorPane requestPanel;

    @FXML
    private AnchorPane dashboardPanel;

    @FXML
    private AnchorPane timeSlotInfoPanel;

    @FXML
    private AnchorPane medicalRecordsPanel;

    // WHOLE PAGE
    private double x = 0;
    private double y = 0;

    // TIME SLOT INFO PANEL
    private Image image;
    private readFile readfile = new readFile();
    private List<String> docNames = new ArrayList<String>();
    private ObservableList<timeSlotData> addTimeSlotList;
    private ObservableList<String> options;

    private ObservableList<AppointmentData> requestList;

    // ======================================================== Initialize when the
    // stage is started
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        dashboardBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #2193b0, #6dd5ed);");
        ObservableList<String> list = FXCollections.observableArrayList("pending", "approve", "cancel");
        requestStatus.setItems(list);

        // DashBoard Panel
        try {
            setAllInfoForDownSide();
            dashBoardChart();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Time Slot Info Panel
        try {
            ArrayList<String[]> data = readfile.readData("timetable.txt");
            for (String[] docData : data) {
                if (docData[1] != null) {
                    docNames.add(docData[1]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        options = FXCollections.observableArrayList(docNames);
        doctorNameComboBox.setItems(options);
        try {
            timeSlotInfoListData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // REQUEST PANEL
        try {
            requestDocNameSetItems();
            requestPatientIdSetItems();
            requestPatientNameSetItems();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // MEDICAL RECORDS PANEL
        try {
            setItemsToMedicalRecordsComboBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ======================================================== FOR DASHBOARD PANEL

    @FXML
    public void dashBoardChart() throws IOException {
        dashboardChart.getData().clear();
        List<LocalDate> date = new ArrayList<LocalDate>();
        LocalDate currentDate = LocalDate.now();

        ArrayList<String[]> returnValue = readfile.readData("patientRecords.txt");

        XYChart.Series<String, Double> chart = new XYChart.Series<String, Double>();

        for (int i = 0; i <= returnValue.size(); i++) {
            date.add(currentDate.plusDays(i));
        }

        for (int i = 0; i < returnValue.size(); i++) {
            chart.getData().add(new XYChart.Data<String, Double>(date.get(i).toString(),
                    Double.parseDouble(returnValue.get(i)[11])));
        }

        dashboardChart.getData().add(chart);
    }

    @FXML
    public void setAllInfoForDownSide() throws IOException {
        Integer numOfPatient = readfile.countNumberOfData("patient.txt");
        Integer numOfDoctor = readfile.countNumberOfData("timetable.txt");
        double totalincome = readfile.countTotalIncome();

        availableDoctor.setText(String.valueOf(numOfDoctor));
        totalIncome.setText("RM " + String.valueOf(totalincome));
        totalCustomers.setText(String.valueOf(numOfPatient));
    }

    // =============================================== FOR TIME SLOT INFO PANEL
    // SetUp the TableView in Time Slot Info Panel
    @FXML
    public void timeSlotInfoListData() throws IOException {
        if (doctorNameComboBox.getSelectionModel().getSelectedItem() == null
                || doctorNameComboBox.getSelectionModel().getSelectedItem() == "") {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select a doctor name");
        } else {
            addTimeSlotList = timeSlotInfoData(doctorNameComboBox.getSelectionModel().getSelectedItem());
            dayColumn.setCellValueFactory(new PropertyValueFactory<>("Days"));
            timeColumn.setCellValueFactory(new PropertyValueFactory<>("Times"));
            timeSlotInfoTableView.setItems(addTimeSlotList);
        }
    }

    // Add the data to text field after item is selected in table view
    @FXML
    public void timeSlotInfoSelect() {
        timeSlotData timeslotdata = timeSlotInfoTableView.getSelectionModel().getSelectedItem();
        int num = timeSlotInfoTableView.getSelectionModel().getSelectedIndex();

        if (num - 1 < -1) {
            return;
        }

        docIdTextField.setText(String.valueOf(timeslotdata.getDoctorID()));
        docNameTextField.setText(String.valueOf(timeslotdata.getDoctorName()));
        dayTextField.setText(String.valueOf(timeslotdata.getDays()));
        timeTextField.setText(String.valueOf(timeslotdata.getTimes()));

        String uri = "file:" + timeslotdata.getImage();

        image = new Image(uri, 137, 155, false, true);
        docImage.setImage(image);
        getData.path = timeslotdata.getImage();
    }

    // Collecting the data to update table view
    @FXML
    public ObservableList<timeSlotData> timeSlotInfoData(String DoctorName) throws IOException {
        Map<String, Map<String, List<String>>> returnValue = readfile.onlyForTimeTableFile();
        timeSlotData timeslotdata;

        ObservableList<timeSlotData> listData = FXCollections.observableArrayList();

        for (String key : returnValue.keySet()) {
            if (key.contains(DoctorName)) {
                String docId = key.split(",")[0];
                String docName = key.split(",")[1];
                String docimage = key.split(",")[2];

                String uri = "file:" + docimage;
                image = new Image(uri, 137, 155, false, true);
                docImage.setImage(image);
                ;

                Map<String, List<String>> innerMap = returnValue.get(key);
                for (String innerMapKey : innerMap.keySet()) {
                    String day = innerMapKey;
                    String time = Arrays.toString(innerMap.get(innerMapKey).toArray()).replace("[", "").replace("]",
                            "");
                    timeslotdata = new timeSlotData(docId, docName, day, time, docimage);
                    listData.add(timeslotdata);
                }
            }
        }

        return listData;
    }

    // Add the text fields data to txt file
    @FXML
    public void timeSlotInfoAdd() throws IOException {

        if (docIdTextField.getText().isEmpty() || docNameTextField.getText().isEmpty()
                || dayTextField.getText().isEmpty() || timeTextField.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the blank fields");
            alert.showAndWait();
        } else {
            String docId = docIdTextField.getText();
            String day = dayTextField.getText();
            String time = timeTextField.getText();

            String content = day + "," + time;
            readfile.writeData("timetable.txt", docId, content);

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added");
            alert.showAndWait();

            timeSlotInfoListData();
        }

    }

    // Used to update the time slot info
    @FXML
    public void timeSlotInfoUpdate() throws IOException {
        if (docIdTextField.getText().isEmpty() || docNameTextField.getText().isEmpty()
                || dayTextField.getText().isEmpty() || timeTextField.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the blank fields");
            alert.showAndWait();
        } else {
            String docId = docIdTextField.getText();
            String day = dayTextField.getText();
            String time = timeTextField.getText();

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to update this information ?");
            Optional<ButtonType> options = alert.showAndWait();

            if (options.get().equals(ButtonType.OK)) {
                String content = day + "," + time;
                readfile.UpdateData("timetable.txt", docId, day, content);

                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully updated!!!");

                timeSlotInfoListData();
            }
        }
    }

    // Delete time slot from the file
    @FXML
    public void timeSlotInfoDelete() {
        try {
            if (docIdTextField.getText().isEmpty() || docNameTextField.getText().isEmpty()
                    || dayTextField.getText().isEmpty() || timeTextField.getText().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
            } else {
                String docId = docIdTextField.getText();
                String docName = docNameTextField.getText();
                String day = dayTextField.getText();
                String time = timeTextField.getText();
                String image = getData.path;

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this information ?");
                Optional<ButtonType> options = alert.showAndWait();

                if (options.get().equals(ButtonType.OK)) {
                    String content = docId + ";" + docName + ";" + image + ";" + day + ";" + time;
                    readfile.UpdateData("timetable.txt", docId, day, content);
                    readfile.UpdateData("timetable.txt", docId, day, content);
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully deleted!!!");

                    timeSlotInfoListData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Used to clear all input
    @FXML
    public void timeSlotInfoClear() {
        docIdTextField.setText("");
        docNameTextField.setText("");
        dayTextField.setText("");
        timeTextField.setText("");
        getData.path = "";
    }

    // Handling import image from local
    @FXML
    public void timeSLotInfoImportImage() {
        FileChooser open = new FileChooser();
        open.setTitle("Import Image File");
        open.getExtensionFilters().add(new ExtensionFilter("Image File", "*.jpg", "*.png"));

        File file = open.showOpenDialog(dashboardPanel.getScene().getWindow());

        if (file != null) {
            image = new Image(file.toURI().toString(), 137, 155, false, true);
            docImage.setImage(image);

            getData.path = file.getAbsolutePath();
        }
    }

    // Handling search feature
    @FXML
    public void timeSlotInfoSearch() {
        try {
            addTimeSlotList = timeSlotInfoData(doctorNameComboBox.getSelectionModel().getSelectedItem());
            FilteredList<timeSlotData> filter = new FilteredList<>(addTimeSlotList, e -> true);
            searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                filter.setPredicate(predicateTimeSlotData -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String searchKey = newValue.toLowerCase();

                    System.out.println(predicateTimeSlotData.getDays().toString().toLowerCase());
                    if (predicateTimeSlotData.getDoctorID().toString().toLowerCase().contains(searchKey)) {
                        return true;
                    } else if (predicateTimeSlotData.getDays().toString().toLowerCase().contains(searchKey)) {
                        return true;
                    } else if (predicateTimeSlotData.getTimes().toString().contains(searchKey)) {
                        return true;
                    } else
                        return false;

                });
            });

            SortedList<timeSlotData> sortList = new SortedList<>(filter);
            sortList.comparatorProperty().bind(timeSlotInfoTableView.comparatorProperty());
            System.out.println(Arrays.deepToString(sortList.toArray()));
            timeSlotInfoTableView.setItems(sortList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ====================================== FOR REQUESTS PANEL
    @FXML
    public void requestDocNameSetItems() throws IOException {
        ArrayList<String[]> returnValue = readfile.readData("appointment.txt");
        List<String> checkRepeated = new ArrayList<String>();
        ObservableList<String> listData = FXCollections.observableArrayList();

        for (String[] data : returnValue) {
            if (!checkRepeated.contains(data[2])) {
                checkRepeated.add(data[2]);
                listData.add(data[2]);
            }
        }

        requestDocName.setItems(listData);
    }

    @FXML
    public void requestPatientIdSetItems() throws IOException {
        ArrayList<String[]> returnValue = readfile.readData("appointment.txt");
        List<String> checkRepeated = new ArrayList<String>();
        ObservableList<String> listData = FXCollections.observableArrayList();

        for (String[] data : returnValue) {
            if (!checkRepeated.contains(data[0])) {
                checkRepeated.add(data[0]);
                listData.addAll(data[0]);
            }
        }

        requestPatientId.setItems(listData);
    }

    @FXML
    public void requestPatientNameSetItems() throws IOException {
        ArrayList<String[]> returnValue = readfile.readData("appointment.txt");
        List<String> checkRepeated = new ArrayList<String>();
        ObservableList<String> listData = FXCollections.observableArrayList();

        for (String[] data : returnValue) {
            if (!checkRepeated.contains(data[1])) {
                checkRepeated.add(data[1]);
                listData.addAll(data[1]);
            }
        }

        requestPatientName.setItems(listData);
    }

    @FXML
    public ObservableList<AppointmentData> requestListData(String status, String dName, String PatientId,
            String PatientName) throws IOException {
        ArrayList<String[]> returnValue = readfile.readData("appointment.txt");
        ObservableList<AppointmentData> listData = FXCollections.observableArrayList();
        staffTableView.getItems().clear();
        if (status != null) {
            for (String[] data : returnValue) {
                if (data[6].equals(status)) {
                    listData.add(new AppointmentData(data[0], data[1], data[2], data[3], data[4]));
                }
                approve.setDisable(false);
                cancel.setDisable(false);
            }
        } else if (dName != null) {
            for (String[] data : returnValue) {
                if (data[2].equals(dName)) {
                    listData.add(new AppointmentData(data[0], data[1], data[2], data[3], data[4]));
                }
                approve.setDisable(true);
                cancel.setDisable(true);
            }
        } else if (PatientId != null) {
            for (String[] data : returnValue) {
                if (data[0].equals(PatientId)) {
                    listData.add(new AppointmentData(data[0], data[1], data[2], data[3], data[4]));
                }
                approve.setDisable(true);
                cancel.setDisable(true);
            }
        } else if (PatientName != null) {
            for (String[] data : returnValue) {
                if (data[1].equals(PatientName)) {
                    listData.add(new AppointmentData(data[0], data[1], data[2], data[3], data[4]));
                }
                approve.setDisable(true);
                cancel.setDisable(true);
            }
        }
        return listData;
    }

    @FXML
    public void requestShowListData(String status, String dName, String PatientId, String patientName)
            throws IOException {
        requestList = requestListData(status, dName, PatientId, patientName);
        PatientID.setCellValueFactory(new PropertyValueFactory<>("PatientId"));
        PatientName.setCellValueFactory(new PropertyValueFactory<>("PatientName"));
        DoctorName.setCellValueFactory(new PropertyValueFactory<>("DoctorName"));
        BookingDate.setCellValueFactory(new PropertyValueFactory<>("BookingDay"));
        BookingTime.setCellValueFactory(new PropertyValueFactory<>("BookingTime"));

        staffTableView.setItems(requestList);
    }

    @FXML
    public void requestApprove() throws IOException {
        if (staffTableView.getSelectionModel().getSelectedItem() != null) {
            AppointmentData requestData = staffTableView.getSelectionModel().getSelectedItem();
            int num = staffTableView.getSelectionModel().getSelectedIndex();

            if (num - 1 < -1) {
                return;
            }

            String PatientID = requestData.getPatientId();
            String DoctorName = requestData.getDoctorName();

            readfile.updateStatusForAppointment(PatientID, DoctorName, "approve");
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select a row first then only click the approve button");
        }
        requestShowListData("pending", null, null, null);
    }

    @FXML
    public void requestCancel() throws IOException {
        if (staffTableView.getSelectionModel().getSelectedItem() != null) {
            AppointmentData requestData = staffTableView.getSelectionModel().getSelectedItem();
            int num = staffTableView.getSelectionModel().getSelectedIndex();

            if (num - 1 < -1) {
                return;
            }

            String PatientID = requestData.getPatientId();
            String DoctorName = requestData.getDoctorName();

            readfile.updateStatusForAppointment(PatientID, DoctorName, "cancel");
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select a row first then only click the approve button");
        }
        requestShowListData("pending", null, null, null);
    }

    @FXML
    public void updateListData() throws IOException {
        String selectedOption = requestStatus.getSelectionModel().getSelectedItem();
        requestDocName.getSelectionModel().clearSelection();
        requestPatientId.getSelectionModel().clearSelection();
        requestPatientName.getSelectionModel().clearSelection();
        requestShowListData(selectedOption, null, null, null);
    }

    @FXML
    public void updateListDataByDocName() throws IOException {
        String selectedOption = requestDocName.getSelectionModel().getSelectedItem();
        requestStatus.getSelectionModel().clearSelection();
        requestPatientId.getSelectionModel().clearSelection();
        requestPatientName.getSelectionModel().clearSelection();
        requestShowListData(null, selectedOption, null, null);
    }

    @FXML
    public void updateListDataByPatientId() throws IOException {
        String selectedOption = requestPatientId.getSelectionModel().getSelectedItem();
        requestStatus.getSelectionModel().clearSelection();
        requestDocName.getSelectionModel().clearSelection();
        requestPatientName.getSelectionModel().clearSelection();
        requestShowListData(null, null, selectedOption, null);
    }

    @FXML
    public void updateListDataByPatientName() throws IOException {
        String selectedOption = requestPatientName.getSelectionModel().getSelectedItem();
        requestStatus.getSelectionModel().clearSelection();
        requestDocName.getSelectionModel().clearSelection();
        requestPatientId.getSelectionModel().clearSelection();
        requestShowListData(null, null, null, selectedOption);
    }

    // ========================================= FOR MEDICAL RECORDS PANEL
    @FXML
    public void setItemsToMedicalRecordsComboBox() throws IOException {
        ObservableList<String> data = FXCollections.observableArrayList();
        ArrayList<String[]> returnValue = readfile.readData("patientRecords.txt");
        for (String[] value : returnValue) {
            data.add(value[1]);
        }
        medicalRecordPatientName.setItems(data);
    }

    @FXML
    public void updateMedicalRecords() throws IOException {
        ArrayList<String[]> returnValue = readfile.readData("patientRecords.txt");
        String randomNumbers = ThreadLocalRandom.current().nextInt(100000000, 1000000000) + "";

        String patientUserNameString = medicalRecordPatientName.getSelectionModel().getSelectedItem();
        for (String[] value : returnValue) {
            if (value[1].equals(patientUserNameString)) {
                medicalRecordNumber.setText(randomNumbers);
                DOB.setText(value[2]);
                cheifComplaint.setText(value[4]);
                historyOfPresentIllness.setText(value[5]);
                pastMedicalHistory.setText(value[6]);
                Medication.setText(value[7]);
                allergies.setText(value[8]);
                physicalExam.setText(value[9]);
                assesmentAndPlan.setText(value[10]);
            }
        }
    }

    // ========================================== FOR LEFT HAND SIDE PANEL
    // Show the username for different users
    @FXML
    public void displayUsername() {
        String userName = getData.username;
        username.setText(userName.substring(0, 1).toUpperCase() + userName.substring(1));
    }

    // Handling for switching to different panels
    @FXML
    public void switchPanel(ActionEvent event) throws IOException {
        if (event.getSource() == dashboardBtn) {
            dashboardPanel.setVisible(true);
            timeSlotInfoPanel.setVisible(false);
            requestPanel.setVisible(false);
            medicalRecordsPanel.setVisible(false);

            dashboardBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #2193b0, #6dd5ed);");
            timeSlotInfoBtn.setStyle("-fx-background-color: transparent;");
            requestsBtn.setStyle("-fx-background-color: transparent;");
            medicalRecordsBtn.setStyle("-fx-background-color: transparent;");

            setAllInfoForDownSide();
            dashBoardChart();

        } else if (event.getSource() == timeSlotInfoBtn) {
            dashboardPanel.setVisible(false);
            timeSlotInfoPanel.setVisible(true);
            requestPanel.setVisible(false);
            medicalRecordsPanel.setVisible(false);

            dashboardBtn.setStyle("-fx-background-color: transparent;");
            timeSlotInfoBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #2193b0, #6dd5ed);");
            requestsBtn.setStyle("-fx-background-color: transparent;");
            medicalRecordsBtn.setStyle("-fx-background-color: transparent;");

            timeSlotInfoListData();

        } else if (event.getSource() == requestsBtn) {
            dashboardPanel.setVisible(false);
            timeSlotInfoPanel.setVisible(false);
            requestPanel.setVisible(true);
            medicalRecordsPanel.setVisible(false);

            dashboardBtn.setStyle("-fx-background-color: transparent;");
            timeSlotInfoBtn.setStyle("-fx-background-color: transparent;");
            requestsBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #2193b0, #6dd5ed);");
            medicalRecordsBtn.setStyle("-fx-background-color: transparent;");

            requestDocNameSetItems();
            requestPatientIdSetItems();
            requestPatientNameSetItems();

        } else if (event.getSource() == medicalRecordsBtn) {
            dashboardPanel.setVisible(false);
            timeSlotInfoPanel.setVisible(false);
            requestPanel.setVisible(false);
            medicalRecordsPanel.setVisible(true);

            dashboardBtn.setStyle("-fx-background-color: transparent;");
            timeSlotInfoBtn.setStyle("-fx-background-color: transparent;");
            requestsBtn.setStyle("-fx-background-color: transparent;");
            medicalRecordsBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #2193b0, #6dd5ed);");
        }
    }

    // Logout Staff Page
    @FXML
    public void logout() {
        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);

            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                SignOutBtn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("StaffLogin.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Minimize Staff Page
    @FXML
    public void minimize() {
        Stage stage = (Stage) dashboardPanel.getScene().getWindow();
        stage.setIconified(true);
    }

    // ============================================ HANDLING WHOLE PAGE ACTIONS
    // ====================================
    // Close Staff Page
    @FXML
    public void close() {
        System.exit(0);
    }

    // Handling Mouse Pressed
    @FXML
    public void handleMousePressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    // Handling Mouse Dragged
    @FXML
    public void handleMouseDragged(MouseEvent event) {
        Stage stage = (Stage) dashboardPanel.getScene().getWindow();
        stage.setX(event.getSceneX() - x);
        stage.setY(event.getSceneY() - y);

        stage.setOpacity(.8);
    }

    // Handling Mouse Released
    @FXML
    public void handleMouseReleased(MouseEvent event) {
        Stage stage = (Stage) dashboardPanel.getScene().getWindow();
        stage.setOpacity(1);
    }
}