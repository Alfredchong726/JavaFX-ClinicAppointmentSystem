package Controller;

// GENERAL
import java.net.URL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
// GUI
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
// CUSTOM CLASSES
import readFile.readFile;

public class SuperAdmin implements Initializable {
    @FXML
    private AnchorPane addStaffPanel;

    @FXML
    private PasswordField addStaffPassword;

    @FXML
    private TextField addStaffUsername;

    @FXML
    private Button clearBtn;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Button createBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private AnchorPane removeStaffPanel;

    @FXML
    private Button addStaffBtn;

    @FXML
    private Button deleteStaffBtn;

    @FXML
    private ComboBox<String> staffNameComboBox;

    @FXML
    private TextField staffPassword;

    @FXML
    private TextField staffUsername;
    
    @FXML
    private Button signOutBtn;

    private double x = 0;
    private double y = 0;

    private readFile readfile = new readFile();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addStaffBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #2b5876, #4e4376);");
        try {
            setItemsToStaffNameComboBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ============================================ ADD STAFF PANEL
    @FXML
    public void createStaff() throws IOException {
        Alert alert;
        if (confirmPassword.getText().equals(addStaffPassword.getText())) {
            String content = addStaffUsername.getText() + ";" + confirmPassword.getText();
            readfile.addAccount("clinicStaff.txt", content);
            clearInput();
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Create successfully!!!");
            alert.showAndWait();
        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("The confirm password field is not same with the password field");
            alert.showAndWait();
        }
    }

    @FXML
    public void clearInput() {
        addStaffUsername.setText(null);
        addStaffPassword.setText(null);
        confirmPassword.setText(null);
    }

    // ========================================== REMOVE STAFF PANEL
    @FXML
    public void setItemsToStaffNameComboBox() throws IOException {
        ObservableList<String> listData = FXCollections.observableArrayList();
        ArrayList<String[]> returnValue = readfile.readData("clinicStaff.txt");
        for (String[] value : returnValue) {
            listData.add(value[0]);
        }

        staffNameComboBox.setItems(listData);
    }

    @FXML
    public void fillAllInputs() throws IOException {
        ArrayList<String[]> returnValue = readfile.readData("clinicStaff.txt");
        String staffName = staffNameComboBox.getSelectionModel().getSelectedItem();
        for (String[] value : returnValue) {
            if (value[0].equals(staffName)) {
                staffUsername.setText(value[0]);
                staffPassword.setText(value[1]);
            }
        }
    }
    
    @FXML
    private void removeStaff() throws IOException {
        String content = staffUsername.getText() + ";" + staffPassword.getText();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to remove this staff");
        Optional<ButtonType> option = alert.showAndWait();
        
        if (option.get().equals(ButtonType.OK)) {
            readfile.deleteStaffAcc(content);
            staffUsername.setText(null);
            staffPassword.setText(null);
            setItemsToStaffNameComboBox();
        }
    }

    // ============================================ HANDLING WHOLE PAGE ACTIONS
    @FXML
    public void switchPanel(ActionEvent event) throws IOException {
        if (event.getSource() == addStaffBtn) {
            addStaffPanel.setVisible(true);
            removeStaffPanel.setVisible(false);

            addStaffBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #2b5876, #4e4376);");
            deleteStaffBtn.setStyle("-fx-background-color: transparent;");

        } else if (event.getSource() == deleteStaffBtn) {
            addStaffPanel.setVisible(false);
            removeStaffPanel.setVisible(true);

            addStaffBtn.setStyle("-fx-background-color: transparent;");
            deleteStaffBtn.setStyle("-fx-background-color: linear-gradient(to bottom right, #2b5876, #4e4376);");
            setItemsToStaffNameComboBox();

        }
    }

    @FXML
    public void logout() {
        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);

            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                signOutBtn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("SuperAdminLogin.fxml"));
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

    // Close Staff Page
    @FXML
    public void close() {
        System.exit(0);
    }

    // Minimize Admin Page
    @FXML
    public void minimize() {
        Stage stage = (Stage) addStaffPanel.getScene().getWindow();
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
        Stage stage = (Stage) addStaffPanel.getScene().getWindow();
        stage.setX(event.getSceneX() - x);
        stage.setY(event.getSceneY() - y);

        stage.setOpacity(.8);
    }

    // Handling Mouse Released
    @FXML
    public void handleMouseReleased(MouseEvent event) {
        Stage stage = (Stage) addStaffPanel.getScene().getWindow();
        stage.setOpacity(1);
    }
}
