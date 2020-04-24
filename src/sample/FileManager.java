package sample;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sample.Entities.Item;
import sample.Entities.Purchase;
import sample.Entities.PurchaseLine;
import sample.Entities.Trader;
import sample.Utils.DateUtils;
import sample.Utils.StatusUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class FileManager {

    private ArrayList<Item> mItems;
    private ArrayList<Trader> mTraders;
    private ArrayList<Purchase> mPurchases;
    private ArrayList<PurchaseLine> mPurchaseLines;

    private File itemsFile = new File("items.json");
    private File tradersFile = new File("traders.json");
    private File purchasesFile = new File("purchases.json");
    private File purchaseLinesFile = new File("purchaseLines.json");

    public FileManager() {

        mItems = new ArrayList<>();
        mTraders = new ArrayList<>();
        mPurchases = new ArrayList<>();
        mPurchaseLines = new ArrayList<>();

        checkItemsFile();
        checkTradersFile();
        checkPurchasesFile();
        checkPurchaseLinesFile();
    }

    private void checkPurchasesFile() {

        if (!purchasesFile.exists()) {
            try {
                purchasesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkPurchaseLinesFile() {

        if (!purchaseLinesFile.exists()) {
            try {
                purchaseLinesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkItemsFile() {
        if (!itemsFile.exists()) {
            try {
                itemsFile.createNewFile();

                //First write to items file

                ArrayList items = new ArrayList();
                items.add(new Item("1","Item 1","#0001",0,15.3));
                items.add(new Item("2","Item 2 - test","#0002",0,8.5));
                items.add(new Item("3","Item 3","#0003",0,7));

                writeItems(items);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkTradersFile() {
        if (!tradersFile.exists()) {
            try {
                tradersFile.createNewFile();

                //First write to traders file

                ArrayList traders = new ArrayList();
                traders.add(new Trader("1","Trader 1","Company 1"));
                traders.add(new Trader("2","Trader 2 - test","Company 2"));
                traders.add(new Trader("3","Trader 3","Company 3"));

                writeTraders(traders);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //Section Items

    private ArrayList<Item> readItems() {
        JSONParser parser = new JSONParser();
        ArrayList<Item> itemsList = new ArrayList<>();

        try {
            FileReader reader = new FileReader(itemsFile);


            JSONArray items = (JSONArray) parser.parse(reader);
            Iterator<JSONObject> iterator = items.iterator();
            while (iterator.hasNext()) {
                JSONObject obj = iterator.next();

                String id = obj.get("Id").toString();
                String name = obj.get("ItemName").toString();
                String code = obj.get("Code").toString();
                int quantity = (int)((long) obj.get("Quantity"));
                Double price = (Double) obj.get("Price");

                Item item = new Item(id,name,code,quantity,price);
                itemsList.add(item);
            }

            return itemsList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return itemsList;
    }


    private void writeItems(ArrayList<Item> list) {

        try {
            FileWriter fileWriter = new FileWriter(itemsFile);

            JSONArray arr = new JSONArray();
            for (Item item : list) {
                JSONObject obj = new JSONObject();
                obj.put("Id",item.getId());
                obj.put("ItemName",item.getName());
                obj.put("Code",item.getCode());
                obj.put("Quantity",item.getQuantity());
                obj.put("Price",item.getPrice());

                arr.add(obj);
            }

            fileWriter.write(arr.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //End section Items


    //Section Traders

    private void writeTraders(ArrayList<Trader> list) {
        try {
            FileWriter fileWriter = new FileWriter(tradersFile);

            JSONArray arr = new JSONArray();
            for (Trader trader : list) {
                JSONObject obj = new JSONObject();
                obj.put("Id",trader.getId());
                obj.put("TraderName",trader.getName());
                obj.put("TraderCompany",trader.getCompany());

                arr.add(obj);
            }

            fileWriter.write(arr.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private ArrayList<Trader> readTraders() {
        JSONParser parser = new JSONParser();
        ArrayList<Trader> tradersList = new ArrayList<>();

        try {
            FileReader reader = new FileReader(tradersFile);


            JSONArray traders = (JSONArray) parser.parse(reader);
            Iterator<JSONObject> iterator = traders.iterator();
            while (iterator.hasNext()) {
                JSONObject obj = iterator.next();

                String id = obj.get("Id").toString();
                String name = obj.get("TraderName").toString();
                String company = obj.get("TraderCompany").toString();

                Trader trader = new Trader(id,name,company);
                tradersList.add(trader);
            }

            return tradersList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tradersList;
    }

    //End section Traders


    //Section Purchases

    private void writePurchases(ArrayList<Purchase> list) {
        try {
            FileWriter fileWriter = new FileWriter(purchasesFile);

            JSONArray arr = new JSONArray();
            for (Purchase purchase : list) {
                JSONObject obj = new JSONObject();
                obj.put("Id",purchase.getId());
                obj.put("TraderId",purchase.getmTrader().getId());
                obj.put("Status", StatusUtils.fromStatus(purchase.getStatus()));
                obj.put("SentDate",purchase.getSendDate());
                obj.put("ApprovedDate",purchase.getApprovedDate());
                obj.put("PayDate",purchase.getPayDate());

                arr.add(obj);
            }

            fileWriter.write(arr.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private ArrayList<Purchase> readPurchases() {
        JSONParser parser = new JSONParser();
        ArrayList<Purchase> purchaseList = new ArrayList<>();

        try {
            FileReader reader = new FileReader(purchasesFile);


            JSONArray purchases = (JSONArray) parser.parse(reader);
            Iterator<JSONObject> iterator = purchases.iterator();
            while (iterator.hasNext()) {
                JSONObject obj = iterator.next();

                String id = obj.get("Id").toString();
                String traderId = obj.get("TraderId").toString();
                int status = (int) ((long) obj.get("Status"));
                String sentDate = null;
                String approvedDate = null;
                String payDate = null;
                if (obj.get("SentDate") != null){
                     sentDate = obj.get("SentDate").toString();
                }
                if (obj.get("ApprovedDate") != null) {
                    approvedDate = obj.get("ApprovedDate").toString();
                }
                if (obj.get("PayDate") != null) {
                    payDate = obj.get("PayDate").toString();
                }

                Purchase purchase = new Purchase(id,getTraderById(traderId),StatusUtils.fromInt(status),sentDate,approvedDate,payDate);
                purchaseList.add(purchase);
            }

            return purchaseList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return purchaseList;
    }

    //End section Purchases


    //Section PurchaseLines

    private void writePurchasesLines(ArrayList<PurchaseLine> list) {
        try {
            FileWriter fileWriter = new FileWriter(purchaseLinesFile);

            JSONArray arr = new JSONArray();
            for (PurchaseLine purchaseLine : list) {
                JSONObject obj = new JSONObject();
                obj.put("Id",purchaseLine.getPurchaseLineId());
                obj.put("itemId",purchaseLine.getItemId());
                obj.put("purchaseId", purchaseLine.getPurchaseId());
                obj.put("quantity",purchaseLine.getQuantity());

                arr.add(obj);
            }

            fileWriter.write(arr.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private ArrayList<PurchaseLine> readPurchaseLines() {
        JSONParser parser = new JSONParser();
        ArrayList<PurchaseLine> purchaseLineList = new ArrayList<>();

        try {
            FileReader reader = new FileReader(purchaseLinesFile);


            JSONArray purchaseLines = (JSONArray) parser.parse(reader);
            Iterator<JSONObject> iterator = purchaseLines.iterator();
            while (iterator.hasNext()) {
                JSONObject obj = iterator.next();

                String id = obj.get("Id").toString();
                String itemId = obj.get("itemId").toString();
                String purchaseId = obj.get("purchaseId").toString();
                int quantity = (int)( (long) obj.get("quantity"));

                PurchaseLine purchaseLine = new PurchaseLine(id,itemId,purchaseId,quantity);
                purchaseLineList.add(purchaseLine);
            }

            return purchaseLineList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return purchaseLineList;
    }

    //End section PurchaseLines







    /**  Queries section starts here  */

    public ArrayList<Item> getAllItems() {
        return readItems();
    }

    public ArrayList<Trader> getAllTraders() {
        return readTraders();
    }


    public ArrayList<Purchase> getHistoryPurchases() {
        mPurchases = readPurchases();

        if (mPurchases.size() == 0) {
            return mPurchases;
        }

        ArrayList<Purchase> sortList = new ArrayList<>();

        for (Purchase purchase : mPurchases) {
            int status = StatusUtils.fromStatus(purchase.getStatus());
            if (status != 1 && status != 2 && status != 3) {
                sortList.add(purchase);
            }
        }
        return sortList;
    }

    public ArrayList<Purchase> getPendingPurchases() {
        mPurchases = readPurchases();

        if (mPurchases.size() == 0) {
            return mPurchases;
        }

        ArrayList<Purchase> sortList = new ArrayList<>();

        for (Purchase purchase : mPurchases) {
            int status = StatusUtils.fromStatus(purchase.getStatus());
            if (status == 2 ) {
                sortList.add(purchase);
            }
        }
        return sortList;
    }

    public Trader getTraderById(String id) {
      mTraders = readTraders();

        for (Trader trader : mTraders) {
            if (trader.getId().equals(id)) {
                return trader;
            }
        }

        return null;
    }

    public ArrayList<PurchaseLine> getPurchaseLinesForOrder(String id) {
        mPurchaseLines = readPurchaseLines();

        if (mPurchaseLines.size() == 0) {
            return mPurchaseLines;
        }

        ArrayList<PurchaseLine> sortList = new ArrayList<>();

        for (PurchaseLine purchaseLine : mPurchaseLines) {
            if ( purchaseLine.getPurchaseId().equals(id) ) {
                sortList.add(purchaseLine);
            }
        }
        return sortList;
    }


    public Item getItemFromId(String id) {
        mItems = readItems();

        for (Item item : mItems) {
            if (item.getId().equals(id)) {
                return item;
            }
        }

        return null;
    }

    public ArrayList<Item> getPurchaseItems(String id) {
        ArrayList<PurchaseLine> mLines = getPurchaseLinesForOrder(id);

        ArrayList<Item> mPurchaseItems = new ArrayList<>();
        for (PurchaseLine line : mLines) {
            Item itm = getItemFromId(line.getItemId());
            mPurchaseItems.add(itm);
        }

        return mPurchaseItems;
    }

    public ArrayList<Purchase> getPayPurchases() {
        mPurchases = readPurchases();

        ArrayList<Purchase> sortList = new ArrayList<>();

        for (Purchase purchase : mPurchases) {
            int status = StatusUtils.fromStatus(purchase.getStatus());
            if (status == 3) {
                sortList.add(purchase);
            }
        }
        return sortList;
    }



    public void addItem(Item item) {
        mItems = readItems();

        mItems.add(item);
        writeItems(mItems);
    }

    public void addTrader(Trader trader) {
        mTraders = readTraders();

        mTraders.add(trader);
        writeTraders(mTraders);
    }

    public void addPurchase(Purchase purchase) {
        mPurchases = readPurchases();

        mPurchases.add(purchase);
        writePurchases(mPurchases);

        for (Item item : purchase.getItemList()) {
            PurchaseLine line = new PurchaseLine(UUID.randomUUID().toString(),item.getId(),purchase.getId(),item.getQuantity());
            addPurchaseLine(line);
        }
    }


    public void addPurchaseLine(PurchaseLine line) {
        mPurchaseLines = readPurchaseLines();

        mPurchaseLines.add(line);
        writePurchasesLines(mPurchaseLines);
    }

    public void acceptOrder(Purchase purchase) {
        mPurchases = readPurchases();

        for (Purchase mPurchase : mPurchases) {
            if (mPurchase.getId().equals(purchase.getId())) {
                mPurchase.setStatus(StatusUtils.fromInt(3));
                mPurchase.setApprovedDate(DateUtils.getDate());
            }
        }

        writePurchases(mPurchases);
    }

    public void declineOrder(Purchase purchase) {
        mPurchases = readPurchases();

        for (Purchase mPurchase : mPurchases) {
            if (mPurchase.getId().equals(purchase.getId())) {
                mPurchase.setStatus(StatusUtils.fromInt(5));
                mPurchase.setApprovedDate(DateUtils.getDate());
            }
        }

        writePurchases(mPurchases);
    }

    public void payOrder(Purchase purchase) {
        mPurchases = readPurchases();

        for (Purchase mPurchase : mPurchases) {
            if (mPurchase.getId().equals(purchase.getId())) {
                mPurchase.setStatus(StatusUtils.fromInt(4));
                mPurchase.setPayDate(DateUtils.getDate());
            }
        }

        writePurchases(mPurchases);
    }

    public Purchase getPurchaseFromId(String id) {
        mPurchases = readPurchases();

        for (Purchase purchase : mPurchases) {
            if (purchase.getId().equals(id)) {
                return purchase;
            }
        }
        return null;
    }



}
