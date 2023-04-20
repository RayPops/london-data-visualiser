import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import java.awt.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 * This class is the main class of the application.
 * It loads the mainframe.fxml file and sets the scene.
 *
 * @author Rayan Popat, James Coward and Shicheng Li.
 */
public class COVIDGui extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = getClass().getResource("mainframe.fxml");
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);

        String css = this.getClass().getResource("styling.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("London COVID-19 Visualiser");
        stage.getIcons().add(new Image("file:coronavirus.png"));
        stage.setScene(scene);
        stage.show();

    }

    /**
     * This method is the main method of the application.
     * It just calls the launch method, which launches the application.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
} 