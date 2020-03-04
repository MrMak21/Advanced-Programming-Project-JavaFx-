//package sample;
//
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class Controller {
//
//    Stage primaryStage;
//    private Parent root;
//    private Scene scene2;
//
//    @FXML
//    private Button btn1;
//
//    public Controller() {
//    }
//
//    @FXML
//    private void setScene2() throws IOException {
//        System.out.println("Set scene 2");
//        if (primaryStage != null) {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("layouts/layout.fxml"));
//            root = loader.load();
//            scene2 = new Scene(root, 300, 275);
//            primaryStage.setScene(scene2);
//
//            SecondController sampleController2 = (SecondController) loader.getController();
//            sampleController2.setStage(primaryStage);
//        }
//    }
//
//
//
//    public void setStage(Stage stage) {
//        primaryStage = stage;
//    }
//}
