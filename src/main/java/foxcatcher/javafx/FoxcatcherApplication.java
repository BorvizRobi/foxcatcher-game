package foxcatcher.javafx;

import com.gluonhq.ignite.guice.GuiceContext;
import com.google.inject.AbstractModule;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import java.util.List;

@Slf4j
public class FoxcatcherApplication extends Application {



    @Inject
    private FXMLLoader fxmlLoader= new FXMLLoader();

    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("Starting application...");
        //context.init();
        fxmlLoader.setLocation(getClass().getResource("/fxml/launch.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Foxcatcher Game");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
