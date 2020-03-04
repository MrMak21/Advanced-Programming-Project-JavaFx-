package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import sample.Database.H2JDBCDriver;
import sample.Entities.Item;
import sample.Entities.Purchase;

public class Main extends Application {

    Button btn1,btn2;
    Parent root;
    Scene scene2,scene1;
    H2JDBCDriver db;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layouts/new_item.fxml"));
        root = loader.load();
        primaryStage.setTitle("Hello World");
        scene1 = new Scene(root, 600, 400);
        primaryStage.setScene(scene1);
        primaryStage.show();

//        Controller sampleController = (Controller) loader.getController();
//        sampleController.setStage(primaryStage);

        setDatabase();
//        initializeViews();
    }

    private void setDatabase() {
        db = new H2JDBCDriver();
    }

    private void initializeViews() {



        ListView<Purchase> list = (ListView<Purchase>) root.lookup("#list");
        list.getItems().addAll(db.getSentPurchases());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
