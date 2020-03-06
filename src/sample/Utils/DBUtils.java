package sample.Utils;

import sample.Database.H2JDBCDriver;

public class DBUtils {

    private static H2JDBCDriver database;

    public static H2JDBCDriver getDb() {
        if (database != null) {
            return database;
        } else {
            database = new H2JDBCDriver();
            return database;
        }
    }
}
