package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import sample.Database.H2JDBCDriver;
import sample.Entities.Purchase;
import sample.Entities.Trader;
import sample.Utils.DBUtils;

import java.io.IOException;

public class MainScreenManager {

    private Stage stage;
    private FXMLLoader loader;
    private Scene scene1;
    private Parent root;
    H2JDBCDriver db;

    private Button btnTrader;

    public MainScreenManager(Stage stage, H2JDBCDriver db) {
        this.stage = stage;
        this.db = db;
        initializeViews();
    }

    private void initializeViews() {
        setUpView();
        root = stage.getScene().getRoot();
        btnTrader = (Button) root.lookup("#btn_New_Trader");

        ListView<Trader> list = (ListView<Trader>) root.lookup("#list");
        list.getItems().addAll(db.getAllTraders());

        setUpListeners();
    }

    private void setUpView() {
        loader = new FXMLLoader(getClass().getResource("layouts/layout.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Main screen");
        scene1 = new Scene(root, 600, 400);
        stage.setScene(scene1);
        stage.show();
    }

    private void setUpListeners() {
        btnTrader.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TraderManager traderManager = new TraderManager(stage); //Go to new trader screen
            }
        });
    }


}
