//package sample;
//
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ListView;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class SecondController {
//
//    Stage primaryStage;
//    private Parent root;
//    private Scene scene2;
//
//    @FXML
//    private Button btn2;
//
//    public SecondController() {
//        initialize();
//    }
//
//    private void initialize() {
//        //            ListView<String> list = new ListView<String>();
//        root = primaryStage.getScene().getRoot();
//        ListView<String> list = (ListView<String>) root.lookup("#list");
//        list.getItems().addAll("AEK","Paok","Osfp");
//    }
//
//    @FXML
//    private void setScene1() throws IOException {
//        System.out.println("Set scene 1");
//        if (primaryStage != null) {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("layouts/sample.fxml"));
//            root = loader.load();
//            scene2 = new Scene(root, 300, 275);
//            primaryStage.setScene(scene2);
//
//            Controller sampleController = (Controller) loader.getController();
//            sampleController.setStage(primaryStage);
//        }
//    }
//
//    public void setStage(Stage stage) {
//        primaryStage = stage;
//    }
//}
