package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    ListView<Purchase> list;

    private Button btnTrader,btnItem,btnOrder,btnOrderDetails;

    public MainScreenManager(Stage stage) {
        this.stage = stage;
        initializeViews();
    }

    private void initializeViews() {
        setUpView();
        db = DBUtils.getDb();
        root = stage.getScene().getRoot();
        btnTrader = (Button) root.lookup("#btn_New_Trader");
        btnItem = (Button) root.lookup("#btn_New_Item");
        btnOrder = (Button) root.lookup("#btn_New_Order");
        btnOrderDetails = (Button) root.lookup("#btnOrderDetails");


        list = (ListView<Purchase>) root.lookup("#list");
        list.getItems().addAll(db.getPendingPurchases());

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

        btnItem.setOnAction(v -> {
            ItemManager mng = new ItemManager(stage); //Go to new item screen
        });

        btnOrder.setOnAction(v -> {
            OrderManager omng = new OrderManager(stage); //Go to new item screen
        });


        btnOrderDetails.setOnAction(v -> {
            if (list.getSelectionModel().getSelectedItem() != null) {
                purchaseDetails(list.getSelectionModel().getSelectedItem());
            } else {
                System.out.println("Please select order to proceed");
            }
        });
    }

    private void purchaseDetails(Purchase purchase) {
        OrderDetailManager omg = new OrderDetailManager(stage,purchase);
    }


}
