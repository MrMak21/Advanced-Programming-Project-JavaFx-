package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.Database.H2JDBCDriver;
import sample.Utils.DBUtils;

public class Main extends Application {

    Button btn1,btn2,btnTrader;
    Parent root;
    Scene scene2,scene1;
    static H2JDBCDriver db;
    FXMLLoader loader;
    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
//        stage = primaryStage;
//        loader = new FXMLLoader(getClass().getResource("layouts/layout.fxml"));
//        root = loader.load();
//        stage.setTitle("Hello World");
//        scene1 = new Scene(root, 600, 400);
//        stage.setScene(scene1);
//        stage.show();

//        Controller sampleController = (Controller) loader.getController();
//        sampleController.setStage(primaryStage);

        setDatabase();

        MainScreenManager mng = new MainScreenManager(primaryStage,db);
//        initializeViews();
    }

    private void setDatabase() {
        db = DBUtils.getDb();
    }

    private void initializeViews() {

//        btnTrader = (Button) root.lookup("#btn_New_Trader");
//
//        ListView<Purchase> list = (ListView<Purchase>) root.lookup("#list");
//        list.getItems().addAll(db.getSentPurchases());

//        setUpListeners();
    }

//    private void setUpListeners() {
//        btnTrader.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                loader = new FXMLLoader(getClass().getResource("layouts/new_trader.fxml"));
//                try {
//                    root = loader.load();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                stage.setTitle("New Trader");
//                scene1 = new Scene(root, 600, 400);
//                stage.setScene(scene1);
//                TraderManager traderManager = new TraderManager(stage);
//            }
//        });
//    }

    public static void main(String[] args) {
        launch(args);
    }
}
