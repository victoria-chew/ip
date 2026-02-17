package kevin;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kevin.ui.MainWindow;

/**
 * A GUI for Kevin using FXML.
 */
public class Main extends Application {

    private kevin.Kevin kevin = new kevin.Kevin();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            //setting limit to window size
            stage.setMinHeight(220);
            stage.setMinWidth(417);

            fxmlLoader.<MainWindow>getController().setKevin(kevin); // inject the Kevin instance
            stage.setTitle("Kevin");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
