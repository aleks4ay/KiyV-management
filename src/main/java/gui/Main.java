package gui;

import data.DataControl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import name.UserName;

public class Main extends Application {

    public static void main(String[] args) {
        DataControl.setCurrentProfile("manager");
//        DataControl.setCurrentProfile("designer");
//        DataControl.setCurrentProfile("factory");
//        DataControl.setCurrentProfile("director");
//        DataControl.setCurrentProfile("shipment");
//        DataControl.setCurrentProfile("developer");

        UserName.writeTimeChange(DataControl.getCurrentProfile());
        launch(args);
//        launch("Client name", "email");

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/fxml/pane_kb.fxml"));
/*        if (DataControl.getCurrentProfile().equals("designer")) {
            root = FXMLLoader.load(getClass().getResource("/fxml/pane_kb.fxml"));
        }
        else {
            root = FXMLLoader.load(getClass().getResource("/fxml/pane.fxml"));
        }*/
        Scene scene = new Scene(root);
        primaryStage.setTitle("KIY-V Production Management 1.0 " + DataControl.getCurrentProfile());
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(760.0);
//        primaryStage.setMaxHeight(900.0);
        primaryStage.setMinWidth(1200);


        primaryStage.getScene().getStylesheets().add("/css/StyleForPaneOne.css");
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icon.png")));
//        primaryStage.getScene().getStylesheets().add("/css/StyleOne.css");
        primaryStage.show();
    }

}






