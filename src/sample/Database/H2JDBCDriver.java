package sample.Database;

import sample.Entities.Item;
import sample.Entities.Purchase;
import sample.Entities.PurchaseLine;
import sample.Entities.Trader;
import sample.Utils.StatusUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class H2JDBCDriver {

    // JDBC driver name and database URL
    private final String JDBC_DRIVER = "org.h2.Driver";
    private final String DB_URL = "jdbc:h2:~/test";
//    private final String DB_URL = "jdbc:h2:~/test;AUTO_SERVER=TRUE";

    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";

    private Statement stmt = null;
    private Connection conn = null;

    public H2JDBCDriver() {

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            String createItemsTable =  "CREATE TABLE IF NOT EXISTS ITEMS " +
                    "(id VARCHAR(255) NOT NULL, " +
                    " itemName VARCHAR(255), " +
                    " code VARCHAR(255), " +
                    " quantity INTEGER," +
                    " price DOUBLE, " +
                    " PRIMARY KEY ( id ))";

            executeUpdateWrapper(createItemsTable);


            String createTradersTable =  "CREATE TABLE IF NOT EXISTS TRADERS " +
                    "(id VARCHAR(255) NOT NULL, " +
                    " traderName VARCHAR(255), " +
                    " traderCompany VARCHAR(255), " +
                    " PRIMARY KEY ( id ))";

            executeUpdateWrapper(createTradersTable);

            String createPurchasesTable =  "CREATE TABLE IF NOT EXISTS PURCHASES " +
                    "(id VARCHAR(255) NOT NULL, " +
                    " traderId VARCHAR(255), " +
                    " status INTEGER, " +
                    " payDate VARCHAR(255)," +
                    " deliveryDate VARCHAR(255), " +
                    " PRIMARY KEY ( id ))";

            executeUpdateWrapper(createPurchasesTable);

            String createPurchaseLinesTable =  "CREATE TABLE IF NOT EXISTS PURCHASELINES " +
                    "(id VARCHAR(255) NOT NULL, " +
                    " itemId VARCHAR(255), " +
                    " purchaseId VARCHAR(255), " +
                    " quantity INTEGER, " +
                    " PRIMARY KEY ( id ))";

            executeUpdateWrapper(createPurchaseLinesTable);

            //Default values in db
            addItems();
            addTraders();

        } catch(Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

    private void addTraders() {
        trader1();
        trader2();
        trader3();
    }

    private void trader1() {
        String deleteSql = "DELETE FROM TRADERS " +
                " WHERE ID = '1'";
        executeUpdateWrapper(deleteSql);

        String insertTrader = "INSERT INTO TRADERS " +
        " VALUES ('1','Trader 1','Company 1') ";
        executeUpdateWrapper(insertTrader);
    }

    private void trader2() {
        String deleteSql = "DELETE FROM TRADERS " +
                " WHERE ID = '2'";
        executeUpdateWrapper(deleteSql);

        String insertTrader = "INSERT INTO TRADERS " +
                " VALUES ('2','Trader 2','Company 2') ";
        executeUpdateWrapper(insertTrader);
    }

    private void trader3() {
        String deleteSql = "DELETE FROM TRADERS " +
                " WHERE ID = '3'";
        executeUpdateWrapper(deleteSql);

        String insertTrader = "INSERT INTO TRADERS " +
                " VALUES ('3','Trader 3','Company 3') ";
        executeUpdateWrapper(insertTrader);
    }

    private void addItems() {
        item1();
        item2();
        item3();
    }

    private void item1() {
        String deleteSql = "DELETE FROM ITEMS " +
                " WHERE ID = '1'";

        executeUpdateWrapper(deleteSql);

        String insertItem = "INSERT INTO ITEMS " +
                " VALUES ('1','Item 1','#0001',0,15.3) ";

        executeUpdateWrapper(insertItem);


    }

    private void item2() {
        String deleteSql = "DELETE FROM ITEMS " +
                " WHERE ID = '2'";
        executeUpdateWrapper(deleteSql);


        String insertItem = "INSERT INTO ITEMS " +
                " VALUES ('2','Item 2','#0002',0,8.5) ";

        executeUpdateWrapper(insertItem);
    }

    private void item3() {
        String deleteSql = "DELETE FROM ITEMS " +
                " WHERE ID = '3'";
        executeUpdateWrapper(deleteSql);

        String insertItem = "INSERT INTO ITEMS " +
                " VALUES ('3','Item 3','#0003',0,7) ";
        executeUpdateWrapper(insertItem);
    }

    //Wrapper for execute query returns ResultSet
    private ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            try {
                rs = stmt.executeQuery(sql);
                return rs;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void executeUpdateWrapper(String sql) {
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            try {
                stmt.executeUpdate(sql);


            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Item> getAllItems() {
        String sql = "SELECT * FROM ITEMS ";
        ArrayList<Item> items = new ArrayList<>();

        ResultSet rs = executeQuery(sql);
        try {
            while (rs.next()) {
                String id = rs.getString("ID");
                String itemName = rs.getString("ITEMNAME");
                String code = rs.getString("CODE");
                int quantity = rs.getInt("QUANTITY");
                double price = rs.getDouble("PRICE");

                items.add(new Item(id,itemName,code,quantity,price));
            }

            stmt.close();
            conn.close();

            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Purchase> getSentPurchases() {
        String sql = "SELECT * FROM PURCHASES WHERE PURCHASES.STATUS = 1 ";
        ArrayList<Purchase> purchases = new ArrayList<>();

        ResultSet rs = executeQuery(sql);

        try {
            while (rs.next()) {
                String id = rs.getString("ID");
                String traderId = rs.getString("TRADERID");
                int status = rs.getInt("STATUS");
                String payDate = rs.getString("PAYDATE");
                String deliveryDate = rs.getString("DELIVERYDATE");

                purchases.add(new Purchase(id,getTraderById(traderId), StatusUtils.fromInt(status),payDate,deliveryDate));
            }

            stmt.close();
            conn.close();
            return purchases;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Purchase> getPendingPurchases() {
        String sql = "SELECT * FROM PURCHASES WHERE PURCHASES.STATUS = 2 ";
        ArrayList<Purchase> purchases = new ArrayList<>();

        ResultSet rs = executeQuery(sql);

        try {
            while (rs.next()) {
                String id = rs.getString("ID");
                String traderId = rs.getString("TRADERID");
                int status = rs.getInt("STATUS");
                String payDate = rs.getString("PAYDATE");
                String deliveryDate = rs.getString("DELIVERYDATE");

                purchases.add(new Purchase(id,getTraderById(traderId), StatusUtils.fromInt(status),payDate,deliveryDate));
            }

            stmt.close();
            conn.close();
            return purchases;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Trader> getAllTraders() {
        String sql = "SELECT * FROM TRADERS";
        ArrayList<Trader> traders = new ArrayList<>();

        ResultSet rs = executeQuery(sql);
        try {
            while (rs.next()) {
                String id = rs.getString("ID");
                String traderName = rs.getString("TRADERNAME");
                String traderCompany = rs.getString("TRADERCOMPANY");

                traders.add(new Trader(id,traderName,traderCompany));
            }

            stmt.close();
            conn.close();

            return traders;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Purchase getPurchaseFromId(String id) {
        String sql = "SELECT * FROM PURCHASES WHERE PURCHASES.ID = '" + id + "'";
        ResultSet rs = executeQuery(sql);

        try {
            if (rs.first()) {
                String purId = rs.getString("ID");
                String traderId = rs.getString("TRADERID");
                int status = rs.getInt("STATUS");
                String payDate = rs.getString("PAYDATE");
                String deliveryDate = rs.getString("DELIVERYDATE");

                Purchase pur = new Purchase(purId,getTraderById(traderId), StatusUtils.fromInt(status),payDate,deliveryDate);
                stmt.close();
                conn.close();
                return pur;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Trader getTraderById(String id) {
        String sql = "SELECT * FROM TRADERS WHERE TRADERS.ID = '" + id +"'";

        ResultSet rs = executeQuery(sql);
        try {
            if (rs.first()) {
                String trId = rs.getString("ID");
                String traderName = rs.getString("TRADERNAME");
                String traderCompany = rs.getString("TRADERCOMPANY");

                Trader mTrader = new Trader(trId,traderName,traderCompany);

                stmt.close();
                conn.close();

                return mTrader;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<PurchaseLine> getPurchaseLinesForOrder(String id) {
        String sql = "SELECT * FROM PURCHASELINES WHERE PURCHASELINES.PURCHASEID  = '" + id + "'";
        ArrayList<PurchaseLine> lines = new ArrayList<>();
        ResultSet rs = executeQuery(sql);

        try {
            while (rs.next()) {
                String purId = rs.getString("ID");
                String itemId = rs.getString("ITEMID");
                String purchaseId = rs.getString("PURCHASEID");
                int quantity = rs.getInt("QUANTITY");

                PurchaseLine pl = new PurchaseLine(purId,itemId,purchaseId,quantity);
                lines.add(pl);
            }

            stmt.close();
            conn.close();
            return lines;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Item getItemFromId(String id) {
        String sql = "SELECT * FROM ITEMS WHERE ITEMS.ID = '" + id + "'";
        ResultSet rs = executeQuery(sql);

        try {
            if (rs.first()) {
                String itemId = rs.getString("ID");
                String itemName = rs.getString("ITEMNAME");
                String code = rs.getString("CODE");
                int quantity = rs.getInt("QUANTITY");
                double price = rs.getDouble("PRICE");

                Item itm = new Item(itemId,itemName,code,quantity,price);

                stmt.close();
                conn.close();
                return itm;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addItem(Item item) {
        String sql = "INSERT INTO ITEMS " +
                " VALUES ('" + item.getId() + "','" + item.getName() +"','" + item.getCode() + "',0," + item.getPrice() +") ";

        executeUpdateWrapper(sql);
    }

    public void addTrader(Trader trader) {
        String sql = "INSERT INTO TRADERS " +
                " VALUES ('" + trader.getId() + "','" + trader.getName() +"','" + trader.getCompany() + "')";

        executeUpdateWrapper(sql);
    }

    public void addPurchase(Purchase purchase) {
        String sql = "INSERT INTO PURCHASES " +
                " VALUES ('" + purchase.getId() +"','" + purchase.getmTrader().getId() +"'," + StatusUtils.fromStatus(purchase.getStatus())+ "," + null + "," + null + "," + ")";

        executeUpdateWrapper(sql);
        for (Item item : purchase.getItemList()) {
            PurchaseLine line = new PurchaseLine(UUID.randomUUID().toString(),item.getId(),purchase.getId(),item.getQuantity());
            addPurchaseLine(line);
        }
    }

    public void addPurchaseLine(PurchaseLine line) {
        String sql = "INSERT INTO PURCHASELINES " +
                " VALUES ('" + line.getPurchaseLineId() +"','" + line.getItemId() +"','" + line.getPurchaseId() + "', " + line.getQuantity() + " )";

        executeUpdateWrapper(sql);
    }

    public void acceptOrder(Purchase purchase) {
        String sql = "UPDATE PURCHASES SET STATUS = 3 WHERE ID = '" + purchase.getId() + "'";

        executeUpdateWrapper(sql);
    }

    public void declineOrder(Purchase purchase) {
        String sql = "UPDATE PURCHASES SET STATUS = 5 WHERE ID = '" + purchase.getId() + "'";

        executeUpdateWrapper(sql);
    }

    public void payOrder(Purchase purchase) {
        String sql = "UPDATE PURCHASES SET STATUS = 4 WHERE ID = '" + purchase.getId() + "'";

        executeUpdateWrapper(sql);
    }

    public ArrayList<Item> getPurchaseItems(String id) {
        ArrayList<PurchaseLine> mLines = getPurchaseLinesForOrder(id);
        ArrayList<Item> mItems = new ArrayList<>();

        for (PurchaseLine mLine : mLines) {
            Item itm = getItemFromId(mLine.getItemId());
            mItems.add(itm);
        }
        return mItems;
    }
}

