package com.elab.grocery_list.Model;

public class Grocery
{
    private String name;
    private  String qty;
    private String date;
    private int id;

    public Grocery() {
    }

    public Grocery(String name, String qty, String date, int id) {
        this.name = name;
        this.qty = qty;
        this.date = date;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
