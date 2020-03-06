package sample.Entities;

public class PurchaseLine {

    private String purchaseLineId;

    private String itemId;
    private String purchaseId;
    private int quantity;

    public PurchaseLine(String purchaseLineId, String itemId, String purchaseId) {
        this.purchaseLineId = purchaseLineId;
        this.itemId = itemId;
        this.purchaseId = purchaseId;
    }

    public PurchaseLine(String purchaseLineId, String itemId, String purchaseId, int quantity) {
        this.purchaseLineId = purchaseLineId;
        this.itemId = itemId;
        this.purchaseId = purchaseId;
        this.quantity = quantity;
    }

    public String getPurchaseLineId() {
        return purchaseLineId;
    }

    public void setPurchaseLineId(String purchaseLineId) {
        this.purchaseLineId = purchaseLineId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
