package sample.Entities;

public class Trader {

    private String name;
    private String company;
    private String id;

    public Trader(String name, String company) {
        this.name = name;
        this.company = company;
    }

    public Trader(String id, String name, String company) {
        this.name = name;
        this.company = company;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
