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
import sample.Entities.Item;
import sample.Utils.DBUtils;
import sample.Utils.TextUtils;
import java.io.IOException;
import java.util.UUID;

public class ItemManager extends Manager {

    private Stage stage;
    private FXMLLoader loader;
    private Scene scene1;
    private Parent root;
    H2JDBCDriver db;

    private Button btnAddItem,btnBack;
    private TextField itemName,itemCode,itemPrice;

    public ItemManager(Stage stage) {
        this.stage = stage;
        initializeViews();
    }

    @Override
    public void initializeViews() {
        setUpView();
        db = DBUtils.getDb();

        btnAddItem = (Button) stage.getScene().lookup("#btn_add_item");
        btnBack = (Button) stage.getScene().lookup("#btn_item_back");
        itemName = (TextField) stage.getScene().lookup("#name_input");
        itemCode = (TextField) stage.getScene().lookup("#code_input");
        itemPrice = (TextField) stage.getScene().lookup("#price_input");

        setUpListeners();
    }

    @Override
    public void setUpListeners() {
        btnAddItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (TextUtils.isNullOrEmpty(itemName.getText()) && TextUtils.isNullOrEmpty(itemCode.getText()) && TextUtils.isNullOrEmpty(itemPrice.getText())) {
                    System.out.println("Please fill in all the fields");
                } else {
                    Item item = new Item(UUID.randomUUID().toString(),itemName.getText(),itemCode.getText(),0,Double.valueOf(itemPrice.getText()));
                    db.addItem(item);
                    MainScreenManager mgr = new MainScreenManager(stage); //Go to main screen
                }
            }
        });

        btnBack.setOnAction(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        MainScreenManager msm = new MainScreenManager(stage);
    }

    @Override
    public void setUpView() {
        loader = new FXMLLoader(getClass().getResource("layouts/new_item.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("New item");
        scene1 = new Scene(root, 600, 400);
        stage.setScene(scene1);
    }


}
