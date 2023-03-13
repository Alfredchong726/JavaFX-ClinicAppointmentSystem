package Controller;

// GUI
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import readFile.readFile;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
// General
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Custom Class
import App.getData;

public class GeneralController implements Initializable {

    @FXML
    private RadioButton MSStaff;

    @FXML
    private RadioButton MSPatient;

    @FXML
    private RadioButton MSAdmin;

    @FXML
    private TextField LoginUserName;

    @FXML
    private PasswordField LoginPassword;

    @FXML
    private Button Login;

    @FXML
    private Button close;

    @FXML
    private Button userBackBtn;

    @FXML
    private Button staffBackBtn;

    @FXML
    private Label errorLabel;

    @FXML
    private Label Greeting;

    @FXML
    private ImageView mainSceneImageVIew;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField userName;

    @FXML
    private Button userlogin;

    @FXML
    private TextField superAdminUsername;

    @FXML
    private PasswordField superAdminPassword;

    @FXML
    private TextField registerUsername;

    @FXML
    private PasswordField registerPassword;

    @FXML
    private PasswordField registerConfirmPassword;

    @FXML
    private Button superAdminLogin;

    private double x = 0;
    private double y = 0;
    private Image image;
    private readFile readfile = new readFile();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            addImageViewer();
        } catch (Exception e) {

        }
    }

    @FXML
    public void userTypeHandler() throws IOException {
        if (MSStaff.isSelected()) {
            Parent root = FXMLLoader.load(getClass().getResource("StaffLogin.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) MSStaff.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (MSPatient.isSelected()) {
            Parent root = FXMLLoader.load(getClass().getResource("PatientLogin.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) MSPatient.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else if (MSAdmin.isSelected()) {
            Parent root = FXMLLoader.load(getClass().getResource("SuperAdminLogin.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) MSPatient.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void staffLogin() throws IOException {
        Alert alert;
        readFile readfile = new readFile();
        ArrayList<String[]> returnValue = readfile.readData("clinicStaff.txt");
        boolean found = false;

        if (LoginUserName.getText().isEmpty() || LoginPassword.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            for (String[] data : returnValue) {
                if (LoginUserName.getText().equals(data[0]) && LoginPassword.getText().equals(data[1])) {
                    found = true;

                    getData.username = LoginUserName.getText();
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();

                    Login.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("Staff.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();
                }
            }
            if (!found) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Wrong username/password, please try again");
                alert.showAndWait();
            }
        }

    }

    @FXML
    public void superAdminlogin() throws IOException {
        Alert alert;
        readFile readfile = new readFile();
        ArrayList<String[]> returnValue = readfile.readData("superAdmin.txt");
        boolean found = false;

        if (superAdminUsername.getText().isEmpty() || superAdminPassword.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            for (String[] data : returnValue) {
                if (superAdminUsername.getText().equals(data[0]) && superAdminPassword.getText().equals(data[1])) {
                    found = true;

                    getData.username = superAdminUsername.getText();
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();

                    superAdminLogin.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("SuperAdmin.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();
                }
            }
            if (!found) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Wrong username/password, please try again");
                alert.showAndWait();
            }
        }

    }

    @FXML
    public void backToMainSceneFromStaffLogin() throws IOException {
        Login.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/App/MainScene.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void backToMainSceneFromUserLogin() throws IOException {
        userlogin.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/App/MainScene.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void backToMainSceneFromSuperAdminLogin() throws IOException {
        superAdminLogin.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/App/MainScene.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void userLogin() throws IOException {
        Alert alert;
        readFile readfile = new readFile();
        ArrayList<String[]> returnValue = readfile.readData("patient.txt");
        boolean found = false;

        if (userName.getText().isEmpty() || Password.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        } else {
            for (String[] data : returnValue) {
                if (userName.getText().equals(data[1]) && Password.getText().equals(data[2])) {
                    found = true;

                    getData.username = userName.getText();
                    getData.userID = data[0];
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login");
                    alert.showAndWait();

                    userlogin.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("Patient.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.initStyle(StageStyle.TRANSPARENT);

                    stage.setScene(scene);
                    stage.show();
                }
            }
            if (!found) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Wrong username/password, please try again");
                alert.showAndWait();
            }
        }

    }

    @FXML
    public void signup() throws IOException {
        userlogin.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void registration() throws IOException {
        Alert alert;
        if (registerUsername.getText() != null && !registerUsername.getText().equals("")) {
            if (registerPassword.getText().equals(registerConfirmPassword.getText())) {
                ArrayList<String[]> returnValue = readfile.readData("patient.txt");
                for (String[] value : returnValue) {
                    if (!Arrays.asList(value).contains(registerUsername.getText())) {

                        String lastID = returnValue.get(returnValue.size() - 1)[0];

                        String idPattern = "PT(\\d+)";
                        Pattern regex = Pattern.compile(idPattern);
                        Matcher matcher = regex.matcher(lastID);
                        String lastId = "";
                        if (matcher.find()) {
                            lastId = matcher.group(1);
                            System.out.println(lastId);
                        }
                        int lastIdInt = Integer.parseInt(lastId);
                        int newIdInt = lastIdInt + 1;
                        String newId = "PT" + String.format("%3d", newIdInt).replace(" ", lastId);

                        String username = registerUsername.getText();
                        String password = registerPassword.getText();

                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Are you sure you want to create an account with username " + username);
                        Optional<ButtonType> option = alert.showAndWait();
                        if (option.get().equals(ButtonType.OK)) {
                            readfile.addAccount("patient.txt", newId + ";" + username + ";" + password);
                            alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Information Message");
                            alert.setHeaderText(null);
                            alert.setContentText("Your account is created, you can go back to main page to login");
                            alert.showAndWait();

                            registerUsername.getScene().getWindow().hide();
                            Parent root = FXMLLoader.load(getClass().getResource("/App/MainScene.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(root);

                            stage.initStyle(StageStyle.TRANSPARENT);

                            stage.setScene(scene);
                            stage.show();
                            break;
                        }
                    } else {
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText(
                                "Please make sure the password field and confirm password field are the same!");
                        alert.showAndWait();
                        break;
                    }
                }
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("The username is existed, please change another username");
                alert.showAndWait();
            }
        } else {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please make sure username field is filled");
        }
    }

    @FXML
    public void addImageViewer() {
        String uri = "file:/home/cms/Downloads/family-gdd820d6aa_640.png";
        image = new Image(uri, 498, 650, false, true);
        mainSceneImageVIew.setImage(image);
    }

    @FXML
    public void handleMousePressed(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    public void handleMouseDragged(MouseEvent event) {
        Stage stage = (Stage) Login.getScene().getWindow();
        stage.setX(event.getSceneX() - x);
        stage.setY(event.getSceneY() - y);

        stage.setOpacity(.8);
    }

    @FXML
    public void handleMouseReleased(MouseEvent event) {
        Stage stage = (Stage) Login.getScene().getWindow();
        stage.setOpacity(1);
    }

    @FXML
    public void close() {
        System.exit(0);
    }

}