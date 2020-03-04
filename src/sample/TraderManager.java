package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class TraderManager {

    private Button btnAdd;
    private TextField traderName,traderCompany;

    private Stage stage;
    private FXMLLoader loader;
    private Scene scene1;
    private Parent root;

    public TraderManager(Stage stage) {
        this.stage = stage;
        initializeViews();
    }

    private void initializeViews() {
        btnAdd = (Button) stage.getScene().lookup("#btn_add_trader");
        traderName = (TextField) stage.getScene().lookup("#name_input");
        traderCompany = (TextField) stage.getScene().lookup("#company_input");

        setUpListeners();
    }

    private void setUpListeners() {
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println(traderName.getText());
                loader = new FXMLLoader(getClass().getResource("layouts/layout.fxml"));
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setTitle("New Trader");
                scene1 = new Scene(root, 600, 400);
                stage.setScene(scene1);

            }
        });
    }


}
