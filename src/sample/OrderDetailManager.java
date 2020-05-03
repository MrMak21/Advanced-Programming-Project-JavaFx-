package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import sample.Entities.Purchase;


import java.io.IOException;

public class OrderDetailManager extends Manager {

    private Stage stage;
    private FXMLLoader loader;
    private Scene scene1;
    private Parent root;
//    H2JDBCDriver db;
    FileManager fm;

    private Purchase purchase;

    Button btnBack,btnAccept,btnDecline;
    Label trader,total;
    ListView itemList;

    public OrderDetailManager(Stage stage, Purchase purchase) {
        this.stage = stage;
        this.purchase = purchase;
        initializeViews();
    }

    @Override
    public void initializeViews() {
        setUpView();
//        db = DBUtils.getDb();
        fm = new FileManager();

        btnBack = (Button) stage.getScene().getRoot().lookup("#back_btn");
        btnAccept = (Button) stage.getScene().getRoot().lookup("#btn_accept");
        btnDecline = (Button) stage.getScene().getRoot().lookup("#btn_decline");
        trader = (Label) stage.getScene().getRoot().lookup("#trader_label");
        total = (Label) stage.getScene().getRoot().lookup("#label_total");
        itemList = (ListView) stage.getScene().getRoot().lookup("#order_details_item_list");

        trader.setText("Trader: " + purchase.getmTrader().getName());
        total.setText("Total items: " + purchase.getItemList().size());

        itemList.getItems().addAll(fm.getPurchaseItems(purchase.getId()));

        setUpListeners();
    }

    @Override
    public void setUpView() {
        loader = new FXMLLoader(getClass().getResource("layouts/order_details.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle(purchase.getmTrader().getName() + " - " + purchase.getItemList().size() +  " items");
        scene1 = new Scene(root, 600, 400);
        stage.setScene(scene1);
        stage.show();
    }

    @Override
    public void setUpListeners() {
        btnBack.setOnAction(v -> {
            onBackPressed();
        });

        btnAccept.setOnAction(v -> {
            acceptOrder(purchase);
        });

        btnDecline.setOnAction(v -> {
            declineOrder(purchase);
        });
    }

    private void acceptOrder(Purchase purchase) {
//        db.acceptOrder(purchase);
        fm.acceptOrder(purchase);
        onBackPressed();
    }

    private void declineOrder(Purchase purchase) {
//        db.declineOrder(purchase);
        fm.declineOrder(purchase);
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        MainScreenManager msm = new MainScreenManager(stage);
    }
}
