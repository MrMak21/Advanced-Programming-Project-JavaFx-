package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import sample.Entities.Purchase;


import java.io.IOException;

public class HistoryManager extends Manager {

    private Stage stage;
    private FXMLLoader loader;
    private Scene scene1;
    private Parent root;
//    H2JDBCDriver db;
    FileManager fm;

    ListView historyList,itemsList;
    Button back;
    Label traderLabel,sentLabel,approvedLabel,payDate;

    public HistoryManager(Stage stage) {
        this.stage = stage;
        initializeViews();
    }

    @Override
    public void initializeViews() {
        setUpView();
//        db = DBUtils.getDb();
        fm = new FileManager();

        historyList = (ListView) stage.getScene().getRoot().lookup("#history_list");
        itemsList = (ListView) stage.getScene().getRoot().lookup("#history_item_list");
        back = (Button) stage.getScene().getRoot().lookup("#history_btn_back");
        traderLabel = (Label) stage.getScene().getRoot().lookup("#history_trader");
        sentLabel = (Label) stage.getScene().getRoot().lookup("#history_sent_date");
        approvedLabel = (Label) stage.getScene().getRoot().lookup("#history_approved_date");
        payDate = (Label) stage.getScene().getRoot().lookup("#history_pay_date");

        historyList.getItems().addAll(fm.getHistoryPurchases());

        setUpListeners();
    }

    @Override
    public void setUpView() {
        loader = new FXMLLoader(getClass().getResource("layouts/history_orders.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("History");
        scene1 = new Scene(root, 750, 450);
        stage.setScene(scene1);
        stage.show();
    }

    @Override
    public void setUpListeners() {
        historyList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Purchase purchase = ((Purchase) newValue);

                itemsList.getItems().clear();
//                itemsList.getItems().addAll(db.getPurchaseItems(purchase.getId()));
                itemsList.getItems().addAll(fm.getPurchaseItems(purchase.getId()));

                traderLabel.setText("Trader: " + purchase.getmTrader().getName());
                sentLabel.setText("Sent: " + purchase.getSendDate());
                approvedLabel.setText("Approved: " + purchase.getApprovedDate());
                payDate.setText("Paid: " + purchase.getPayDate());
            }
        });

        back.setOnAction(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        MainScreenManager mnm = new MainScreenManager(stage);
    }
}
