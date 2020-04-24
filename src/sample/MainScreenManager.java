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

public class MainScreenManager extends Manager {

    private Stage stage;
    private FXMLLoader loader;
    private Scene scene1;
    private Parent root;
    H2JDBCDriver db;
    FileManager fm;

    ListView<Purchase> list;

    private Button btnTrader,btnItem,btnOrder,btnOrderDetails,btnPay,btnHistory;

    public MainScreenManager(Stage stage) {
        this.stage = stage;
        initializeViews();
    }

    @Override
    public void initializeViews() {
        setUpView();
//        db = DBUtils.getDb();
        fm = new FileManager();

        root = stage.getScene().getRoot();
        btnTrader = (Button) root.lookup("#btn_New_Trader");
        btnItem = (Button) root.lookup("#btn_New_Item");
        btnOrder = (Button) root.lookup("#btn_New_Order");
        btnOrderDetails = (Button) root.lookup("#btnOrderDetails");
        btnPay = (Button) root.lookup("#btn_pay_orders");
        btnHistory = (Button) root.lookup("#btn_history_orders");


        list = (ListView<Purchase>) root.lookup("#list");
        list.getItems().addAll(fm.getPendingPurchases());

        btnPay.setText("Pay orders: " + fm.getPayPurchases().size());

        setUpListeners();
    }

    @Override
    public void setUpView() {
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

    @Override
    public void setUpListeners() {
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

        btnPay.setOnAction( v -> {
            PaymentCenterManager pcm = new PaymentCenterManager(stage);
        });

        btnHistory.setOnAction(v -> {
            HistoryManager hm = new HistoryManager(stage);
        });
    }

    @Override
    public void onBackPressed() {

    }

    private void purchaseDetails(Purchase purchase) {
        purchase.setItemList(fm.getPurchaseItems(purchase.getId()));
        OrderDetailManager omg = new OrderDetailManager(stage,purchase);
    }


}
