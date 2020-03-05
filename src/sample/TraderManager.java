package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Database.H2JDBCDriver;
import sample.Entities.Trader;
import sample.Utils.DBUtils;
import sample.Utils.TextUtils;

import java.io.IOException;
import java.util.UUID;

public class TraderManager {

    private Button btnAdd;
    private TextField traderName,traderCompany;

    private Stage stage;
    private FXMLLoader loader;
    private Scene scene1;
    private Parent root;
    H2JDBCDriver db;

    public TraderManager(Stage stage) {
        this.stage = stage;
        initializeViews();
    }

    private void initializeViews() {
        setUpView();
        db = DBUtils.getDb();

        btnAdd = (Button) stage.getScene().lookup("#btn_add_trader");
        traderName = (TextField) stage.getScene().lookup("#name_input");
        traderCompany = (TextField) stage.getScene().lookup("#company_input");

        setUpListeners();
    }

    private void setUpView() {
        loader = new FXMLLoader(getClass().getResource("layouts/new_trader.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("New Trader");
        scene1 = new Scene(root, 600, 400);
        stage.setScene(scene1);
    }

    private void setUpListeners() {
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(traderName.getText());
                if (TextUtils.isNullOrEmpty(traderName.getText()) && TextUtils.isNullOrEmpty(traderCompany.getText())) {
                    System.out.println("Please fill in all the fields");
                } else {
                    Trader tr = new Trader(UUID.randomUUID().toString(),traderName.getText(),traderCompany.getText());
                    db.addTrader(tr);
                    MainScreenManager mgr = new MainScreenManager(stage, db); //Go to main screen
                }
            }
        });
    }


}
