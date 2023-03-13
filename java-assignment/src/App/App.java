package App;
// GUI
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application{
    public static void main(String[] args) {
        launch(args);

    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        
        stage.setTitle("Clinic System");
        stage.setScene(new Scene(root, 900, 650));
        stage.show();
    }
}