package sample.Entities;

import sample.Enum.Status;

import java.util.ArrayList;

public class Purchase {

    private String id;
    private ArrayList<Item> itemList;
    private Trader mTrader;
    private Status status;
    private String payDate;
    private String deliveryDate;


    public Purchase(Trader mTrader) {
        itemList = new ArrayList<>();
        this.mTrader = mTrader;
    }

    public Purchase(String id, Trader mTrader, Status status, String payDate, String deliveryDate) {
        this.id = id;
        this.mTrader = mTrader;
        this.status = status;
        this.payDate = payDate;
        this.deliveryDate = deliveryDate;
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

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @Override
    public String toString() {
        return  mTrader.getName() + "   " + status + "   " + payDate + "   " + deliveryDate;
    }
}
