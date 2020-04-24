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
import sample.Database.H2JDBCDriver;
import sample.Entities.Purchase;
import sample.Utils.DBUtils;

import java.io.IOException;

public class PaymentCenterManager extends Manager {

    private Stage stage;
    private FXMLLoader loader;
    private Scene scene1;
    private Parent root;
    H2JDBCDriver db;
    FileManager fm;

    ListView payList,itemsList;
    Button btnPay,back;
    Label traderLabel,sentLabel,approvedLabel;

    public PaymentCenterManager(Stage stage) {
        this.stage = stage;
        initializeViews();
    }

    @Override
    public void initializeViews() {
        setUpView();
//        db = DBUtils.getDb();
        fm = new FileManager();

        payList = (ListView) stage.getScene().getRoot().lookup("#pay_list");
        itemsList = (ListView) stage.getScene().getRoot().lookup("#pay_item_list");
        btnPay = (Button) stage.getScene().getRoot().lookup("#btn_pay");
        back = (Button) stage.getScene().getRoot().lookup("#btn_back");
        traderLabel = (Label) stage.getScene().getRoot().lookup("#pay_trader");
        sentLabel = (Label) stage.getScene().getRoot().lookup("#pay_sent_date");
        approvedLabel = (Label) stage.getScene().getRoot().lookup("#pay_approved_date");

        payList.getItems().addAll(fm.getPayPurchases());

        setUpListeners();
    }

    @Override
    public void setUpView() {
        loader = new FXMLLoader(getClass().getResource("layouts/payment_center.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Payment center");
        scene1 = new Scene(root, 750, 450);
        stage.setScene(scene1);
        stage.show();
    }

    @Override
    public void setUpListeners() {

        payList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                Purchase purchase = ((Purchase) newValue);

                itemsList.getItems().clear();
                itemsList.getItems().addAll(fm.getPurchaseItems(purchase.getId()));

                traderLabel.setText("Trader: " + purchase.getmTrader().getName());
                sentLabel.setText("Sent: " + purchase.getSendDate());
                approvedLabel.setText("Approved: " + purchase.getApprovedDate());
            }
        });

        btnPay.setOnAction(v -> {
            if (payList.getSelectionModel().getSelectedItem() != null){
//                db.payOrder((Purchase) payList.getSelectionModel().getSelectedItem());
                fm.payOrder((Purchase) payList.getSelectionModel().getSelectedItem());
                MainScreenManager msm = new MainScreenManager(stage);
            } else {
                System.out.println("Select purchase to pay");
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
