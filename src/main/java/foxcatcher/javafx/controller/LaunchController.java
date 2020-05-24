package foxcatcher.javafx.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Vector;

@Slf4j
public class LaunchController {

    @Inject
    private FXMLLoader fxmlLoader;

    @FXML
    private TextField player1NameTextField;

    @FXML
    private TextField player2NameTextField;

    @FXML
    private Label errorLabel;

    public void startAction(ActionEvent actionEvent) throws IOException {
        if (player1NameTextField.getText().isEmpty() || player2NameTextField.getText().isEmpty()) {
            errorLabel.setText("Enter your names!");
        } else {
            fxmlLoader.setLocation(getClass().getResource("/fxml/game.fxml"));
            Parent root = fxmlLoader.load();

            Vector<String>playerNames=new Vector<String>();
            playerNames.add("");
            playerNames.add(player1NameTextField.getText());
            playerNames.add(player2NameTextField.getText());
            fxmlLoader.<GameController>getController().setPlayerNames(playerNames);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            log.info("The player1 name is set to {}, player2 name is set to {} loading game scene", player1NameTextField.getText(),player2NameTextField.getText());
        }
    }

}
