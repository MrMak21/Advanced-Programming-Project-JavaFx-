package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Database.H2JDBCDriver;
import sample.Entities.Item;
import sample.Entities.Purchase;
import sample.Entities.Trader;
import sample.Enum.Status;
import sample.Utils.DBUtils;
import sample.Utils.TextUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class OrderManager extends Manager {

    private Stage stage;
    private FXMLLoader loader;
    private Scene scene1;
    private Parent root;
    H2JDBCDriver db;

    Label itemLabel,traderLabel,totalLabel,errorLabel;
    Button btnPlaceOrder,btnAddItem,btnBack;
    ListView itemList,traderList;
    TextField quantity;

    private ArrayList<Item> shoppingList;

    public OrderManager(Stage stage) {
        this.stage = stage;
        initializeViews();
    }

    @Override
    public void initializeViews() {
        setUpView();
        db = DBUtils.getDb();

        btnPlaceOrder = (Button) stage.getScene().lookup("#btnOrder");
        btnAddItem = (Button) stage.getScene().lookup("#btnAddItem");
        btnBack = (Button) stage.getScene().lookup("#btn_order_Back");
        itemLabel = (Label) stage.getScene().lookup("#item_label");
        traderLabel = (Label) stage.getScene().lookup("#trader_label");
        totalLabel = (Label) stage.getScene().lookup("#items_total");
        errorLabel = (Label) stage.getScene().lookup("#error_label");
        quantity = (TextField) stage.getScene().lookup("#quantity_input");

        itemList = (ListView) stage.getScene().lookup("#item_list");
        traderList = (ListView) stage.getScene().lookup("#traders_list");


        itemList.getItems().addAll(db.getAllItems());
        traderList.getItems().addAll(db.getAllTraders());

        shoppingList = new ArrayList<>();


        setUpListeners();
    }

    @Override
    public void setUpListeners() {
        itemList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                itemLabel.setText("Item: " + ((Item) newValue).getName());
                errorLabel.setText("");
            }
        });

        traderList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                traderLabel.setText("Trader: " + ((Trader) newValue).getName());
            }
        });

        btnAddItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!TextUtils.isNullOrEmpty(quantity.getText())) {
                    Item selected = (Item) itemList.getSelectionModel().getSelectedItem();
                    selected.setQuantity(Integer.valueOf(quantity.getText()));
                    addItemToList(selected);
                    totalLabel.setText("Total items: " + shoppingList.size());
                    errorLabel.setText("");
                    quantity.setText("");
                } else {
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("Please add quantity");
                }
            }
        });

        btnPlaceOrder.setOnAction(v -> {
            placeOrder();
        });

        btnBack.setOnAction(v -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        MainScreenManager msm = new MainScreenManager(stage);
    }

    private void placeOrder() {
        if (shoppingList.size() > 0 && traderList.getSelectionModel().getSelectedItem() != null ) {
            String traderId = ((Trader) traderList.getSelectionModel().getSelectedItem()).getId();
            Purchase pur = new Purchase(UUID.randomUUID().toString(),shoppingList,db.getTraderById(traderId), Status.PENDING);
            db.addPurchase(pur);
            MainScreenManager mng = new MainScreenManager(stage);
        } else {
            System.out.println("Please select items & trader");
        }
    }

    private void addItemToList(Item item) {
        shoppingList.add(item);
    }

    @Override
    public void setUpView() {
        loader = new FXMLLoader(getClass().getResource("layouts/new_order.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("New order");
        scene1 = new Scene(root, 600, 400);
        stage.setScene(scene1);
    }
}
