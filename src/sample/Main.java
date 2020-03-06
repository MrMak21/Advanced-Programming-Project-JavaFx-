package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import sample.Database.H2JDBCDriver;
import sample.Utils.DBUtils;

public class Main extends Application {


    private H2JDBCDriver db;

    @Override
    public void start(Stage primaryStage) {
        setDatabase();
        MainScreenManager mng = new MainScreenManager(primaryStage);
    }

    private void setDatabase() {
        db = DBUtils.getDb();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
