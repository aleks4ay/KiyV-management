package fill_status;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainForStatus extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/fxml/fill_status.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setTitle("KIY-V Production Management 1.0 ");
        primaryStage.setScene(scene);
//        primaryStage.setMinHeight(700.0);
//        primaryStage.setMaxHeight(900.0);
//        primaryStage.setMinWidth(1600);

        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icon.png")));
        primaryStage.show();
    }

}






