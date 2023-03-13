package Controller;

// GENERAL
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
// GUI
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import readFile.readFile;

// CUSTOM CLASSES
import App.getData;

public class patientController implements Initializable {
    @FXML
    private TextField DOB;

    @FXML
    private DatePicker Date;

    @FXML
    private ComboBox<String> Time;

    @FXML
    private TextField allergies;

    @FXML
    private ImageView appointmentPanelImage;

    @FXML
    private TextArea assesmentAndPlan;

    @FXML
    private Button bookAppointmentBtn;

    @FXML
    private AnchorPane bookAppointmentPanel;

    @FXML
    private Button cancelAppointment;

    @FXML
    private TextField chiefComplaint;

    @FXML
    private Button confirmBtn;

    @FXML
    private Button dashBoardBtn;

    @FXML
    private AnchorPane dashboardPanel;

    @FXML
    private AnchorPane docImage;

    @FXML
    private ComboBox<String> docName;

    @FXML
    private TextField historyOfPresentIllness;

    @FXML
    private TextField medicalRecordNum;

    @FXML
    private Button medicalRecordsBtn;

    @FXML
    private AnchorPane medicalRecordsPanel;

    @FXML
    private TextField medications;

    @FXML
    private Label nextAppointmentDoctor;

    @FXML
    private Label nextAppointmentDate;

    @FXML
    private Label nextAppointmentDay;
    @FXML
    private Label previousAppointmentDate;

    @FXML
    private Label previousAppointmentDay;

    @FXML
    private Label previousAppointmentDoctor;

    @FXML
    private Label previousAppointmentTime;

    @FXML
    private Label nextAppointmentTime;

    @FXML
    private TextArea pastMedicalHistory;

    @FXML
    private TextArea physicalExam;

    @FXML
    private Button signout;

    @FXML
    private Label userName;

    // WHOLE PAGE
    private double x = 0;
    private double y = 0;
    private Image image;
    private String DoctorName;
    private readFile readfile = new readFile();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        try {
            showMedicalRecords();
            addItemsToDoctorComboBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            setNextAppointmentInfo();
            setPreviousAppointmentInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dashBoardBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #36d1dc, #5b86e5);");
    }

    // ================================ FOR DASHBOARD PANEL
    @FXML
    public void setNextAppointmentInfo() throws IOException {
        ArrayList<String[]> returnValue = readfile.readData("appointment.txt");
        boolean found = false;
        for (String[] value : returnValue) {
            if (value[1].equals(getData.username)
                    && LocalDate.parse(value[5], DateTimeFormatter.ISO_DATE).isAfter(LocalDate.now())) {
                found = true;
                DoctorName = value[2];
            }
            if (found) {
                nextAppointmentDoctor.setText(value[2]);
                nextAppointmentDate.setText(value[5]);
                nextAppointmentDay.setText(value[3]);
                nextAppointmentTime.setText(value[4]);
            } else {
                nextAppointmentDoctor.setText("None");
                nextAppointmentDate.setText("None");
                nextAppointmentDay.setText("None");
                nextAppointmentTime.setText("None");

            }
        }
    }

    @FXML
    public void setPreviousAppointmentInfo() throws IOException {
        ArrayList<String[]> returnValue = readfile.readData("appointment.txt");
        boolean found = false;
        for (String[] value : returnValue) {
            if (value[1].equals(getData.username)
                    && LocalDate.parse(value[5], DateTimeFormatter.ISO_DATE).isBefore(LocalDate.now())) {
                found = true;
            }
            if (found) {
                previousAppointmentDoctor.setText(value[2]);
                previousAppointmentDate.setText(value[5]);
                previousAppointmentDay.setText(value[3]);
                previousAppointmentTime.setText(value[4]);
            } else {
                previousAppointmentDoctor.setText("None");
                previousAppointmentDate.setText("None");
                previousAppointmentDay.setText("None");
                previousAppointmentTime.setText("None");
                cancelAppointment.setDisable(true);

            }
        }
    }

    @FXML
    public void cancelAppointment() throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to cancel this appointment?");
        Optional<ButtonType> option = alert.showAndWait();
        
        if (option.get().equals(ButtonType.OK)) {
            readfile.updateStatusForAppointment(getData.userID, DoctorName, "cancel");
        }
    }

    // ================================ FOR MEDICAL RECORDS PANEL
    // Showing medical records
    @FXML
    public void showMedicalRecords() throws IOException {
        ArrayList<String[]> returnValue = readfile.readData("patientRecords.txt");
        String randomNumbers = ThreadLocalRandom.current().nextInt(100000000, 1000000000) + "";
        for (String[] value : returnValue) {
            if (value[1].equals(getData.username)) {
                medicalRecordNum.setText(randomNumbers);
                DOB.setText(value[2]);
                chiefComplaint.setText(value[4]);
                historyOfPresentIllness.setText(value[5]);
                pastMedicalHistory.setText(value[6]);
                medications.setText(value[7]);
                allergies.setText(value[8]);
                physicalExam.setText(value[9]);
                assesmentAndPlan.setText(value[10]);
            }
        }
    }

    // =============================== FOR BOOK APPOINTMENT PANEL
    // Add Doctor options to combo box
    @FXML
    public void addItemsToDoctorComboBox() throws IOException {
        ObservableList<String> listData = FXCollections.observableArrayList();
        ArrayList<String[]> returnValue = readfile.readData("timetable.txt");
        for (String[] value : returnValue) {
            listData.addAll(value[1]);
        }

        docName.setItems(listData);
    }
    
    // Used to update doctor image after user select options from combo box
    @FXML
    public void updateDoctorImage() throws IOException {
        ArrayList<String[]> returnValue = readfile.readData("timetable.txt");

        String DoctorName = docName.getSelectionModel().getSelectedItem();
        for (String[] value : returnValue) {
            if (value[1].equals(DoctorName)) {
                String imageUri = "file:" + value[2];
                image = new Image(imageUri, 130, 170, false, true);
                appointmentPanelImage.setImage(image);
            }
        }
    }

    // Make some options in date picker disabled based on the doctor's working days
    @FXML
    public void setSpecificOptionsToDatePicker() throws IOException {
        updateDoctorImage();
        List<DayOfWeek> workingDays = new ArrayList<>();
        Map<String, Map<String, List<String>>> returnValue = readfile.onlyForTimeTableFile();
        if (docName.getSelectionModel().getSelectedItem() != null) {
            String DoctorName = docName.getSelectionModel().getSelectedItem();
            for (String key : returnValue.keySet()) {
                if (key.contains(DoctorName)) {
                    Map<String, List<String>> innerMap = returnValue.get(key);
                    for (String innerMapKey : innerMap.keySet()) {
                        String day = innerMapKey;
                        workingDays.add(DayOfWeek.valueOf(day.toUpperCase()));
                    }
                }
            }
            Date.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    if (date == null || date.isBefore(LocalDate.now())) {
                        setDisable(true);
                        setStyle("-fx-background-color: #ffc0cb;");
                    } else {
                        DayOfWeek day = date.getDayOfWeek();
                        if (!workingDays.contains(day)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }
                    }
                }
            });
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select a doctor first!!!");
            alert.showAndWait();
        }

    }

    // After user choose a dote, then only update to time combo box
    @FXML
    public void setItemToTimeComboBox() throws IOException {
        ObservableList<String> times = FXCollections.observableArrayList();
        Map<String, Map<String, List<String>>> returnValue = readfile.onlyForTimeTableFile();

        if (Date.getValue() != null) {
            String DoctorName = docName.getSelectionModel().getSelectedItem();
            String dateSelected = Date.getValue().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
            for (String key : returnValue.keySet()) {
                if (key.contains(DoctorName)) {
                    Map<String, List<String>> innerMap = returnValue.get(key);
                    List<String> time = innerMap.get(dateSelected);
                    for (String value : time) {
                        times.add(value);
                    }
                    Time.setItems(times);
                    break;
                }
            }
        }
    }

    @FXML
    public void confirmBtnClicked() throws IOException {
        Alert alert;
        alert = new Alert(AlertType.CONFIRMATION);
        String DoctorName = docName.getSelectionModel().getSelectedItem();
        String date = Date.getValue() + "";
        String day = Date.getValue().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        String time = Time.getSelectionModel().getSelectedItem();
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText(
                "Are sure you you want to book " + time + " " + date + " on " + day + " with Doctor " + DoctorName);
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get().equals(ButtonType.OK)) {
            String PatientID = getData.userID;
            String timetableContent = day + ", " + time;
            String appointmentContent = PatientID + ";" + getData.username + ";" + DoctorName + ";" + day + ";" + time + ";" + date
                    + ";pending";
            readfile.addAppointment(DoctorName, day, time, timetableContent, appointmentContent);
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Book Appointment!!!");
            Date.setValue(null);
            Time.getSelectionModel().clearSelection();
        }
    }

    // ==========================FOR LEFT HAND SIDE PANEL
    // Handling for switching to different panels
    @FXML
    public void switchPanel(ActionEvent event) throws IOException {
        if (event.getSource() == medicalRecordsBtn) {
            medicalRecordsPanel.setVisible(true);
            bookAppointmentPanel.setVisible(false);
            dashboardPanel.setVisible(false);

            medicalRecordsBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #36d1dc, #5b86e5);");
            bookAppointmentBtn.setStyle("-fx-background-color: transparent;");
            dashBoardBtn.setStyle("-fx-background-color: transparent;");

        } else if (event.getSource() == bookAppointmentBtn) {
            medicalRecordsPanel.setVisible(false);
            bookAppointmentPanel.setVisible(true);
            dashboardPanel.setVisible(false);

            medicalRecordsBtn.setStyle("-fx-background-color: transparent;");
            bookAppointmentBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #36d1dc, #5b86e5);");
            dashBoardBtn.setStyle("-fx-background-color: transparent;");

        } else if (event.getSource() == dashBoardBtn) {
            medicalRecordsPanel.setVisible(false);
            bookAppointmentPanel.setVisible(false);
            dashboardPanel.setVisible(true);

            medicalRecordsBtn.setStyle("-fx-background-color: transparent;");
            bookAppointmentBtn.setStyle("-fx-background-color: transparent;");
            dashBoardBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #36d1dc, #5b86e5);");

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
                signout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("PatientLogin.fxml"));
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

    // ============================================ HANDLING WHOLE PAGE ACTIONS
    // Close Staff Page
    @FXML
    public void close() {
        System.exit(0);
    }

    // Minimize Staff Page
    @FXML
    public void minimize() {
        Stage stage = (Stage) dashboardPanel.getScene().getWindow();
        stage.setIconified(true);
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

    @FXML
    public void displayUsername() {
        String username = getData.username;
        userName.setText(username.substring(0, 1).toUpperCase() + username.substring(1));
    }
}
