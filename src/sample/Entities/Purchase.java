package sample.Entities;

import sample.Enum.Status;
import sample.Utils.DBUtils;

import java.util.ArrayList;

public class Purchase {

    private String id;
    private ArrayList<Item> itemList;
    private Trader mTrader;
    private Status status;
    private String payDate;
    private String sendDate;
    private String approvedDate;


    public Purchase(Trader mTrader) {
        itemList = new ArrayList<>();
        this.mTrader = mTrader;
    }

    public Purchase(String id, Trader mTrader, Status status, String sendDate, String approvedDate,String payDate) {
        this.id = id;
        this.mTrader = mTrader;
        this.status = status;
        this.sendDate = sendDate;
        this.approvedDate = approvedDate;
        this.payDate = payDate;
        itemList = new ArrayList<>();
    }

    public Purchase(String id, ArrayList<Item> itemList, Trader mTrader, Status status) {
        this.id = id;
        this.itemList = itemList;
        this.mTrader = mTrader;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void addItem(Item item) {
        itemList.add(item);
    }

    public Trader getmTrader() {
        return mTrader;
    }

    public void setmTrader(Trader mTrader) {
        this.mTrader = mTrader;
    }

    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getTotal() {
        double total = 0;
        for (Item item : itemList) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    @Override
    public String toString() {
        return  mTrader.getName() + "   " + status + "   " + sendDate + "   " + approvedDate + "   items: "
                + DBUtils.getDb().getPurchaseItems(getId()).size() + "   "
                + getItemTotal(getId()) ;
    }

    public double getItemTotal(String id) {
        double total = 0.0;
        ArrayList<PurchaseLine> lines = DBUtils.getDb().getPurchaseLinesForOrder(id);
        for (PurchaseLine line : lines) {
            Item itm = DBUtils.getDb().getItemFromId(line.getItemId());
            total += line.getQuantity() * itm.getPrice();
        }
        return total;
    }
}
