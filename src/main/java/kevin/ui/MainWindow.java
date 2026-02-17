package kevin.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.application.Platform;

import java.io.IOException;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private kevin.Kevin kevin;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image kevinImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Kevin instance + welcome message*/
    public void setKevin(kevin.Kevin k) {
        kevin = k;

        String welcomeMessage = kevin.getResponse("hi");

        dialogContainer.getChildren().add(
                DialogBox.getKevinDialog(welcomeMessage, kevinImage)
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Kevin's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim().toLowerCase(); // standardise lowercase

        // Smooth delayed exit
        if (input.trim().equalsIgnoreCase("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> Platform.exit());
            delay.play();
        }

        // To open the help window
        if (input.equalsIgnoreCase("help")) {
            boolean opened = openHelpWindow();

            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getKevinDialog(
                            opened ? "Here is the help window! BARK." : "Oops! I couldn't open the help window.",
                            kevinImage)
            );

            userInput.clear();
            return;
        }

        // Not help or bye, proceed with rest of commands
        String response = kevin.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getKevinDialog(response, kevinImage)
        );
        userInput.clear();
    }

    private Stage helpStage;

    private boolean openHelpWindow() {
        try {
            if (helpStage != null && helpStage.isShowing()) {
                helpStage.toFront();
                helpStage.requestFocus();
                return true;
            }

            FXMLLoader loader = new FXMLLoader(MainWindow.class.getResource("/view/HelpWindow.fxml"));
            Parent root = loader.load();

            helpStage = new Stage();
            helpStage.setTitle("Kevin Help");
            helpStage.setScene(new Scene(root));

            // make it feel “attached” to your main window
            helpStage.initOwner(dialogContainer.getScene().getWindow());

            helpStage.show();

            // force it to front (prevents “opened but I can't see it”)
            helpStage.toFront();
            helpStage.requestFocus();

            return true;
        } catch (Exception e) {
            e.printStackTrace(); // IMPORTANT while debugging
            return false;
        }
    }

}
