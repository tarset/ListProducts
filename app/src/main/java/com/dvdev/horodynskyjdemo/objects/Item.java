package com.dvdev.horodynskyjdemo.objects;

public class Item {
    private String name = "";
    private boolean pruchased = false;

    public Item(String name, boolean pruchased) {
        this.name = name;
        this.pruchased = pruchased;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPruchased() {
        return pruchased;
    }

    public void setPruchased(boolean pruchased) {
        this.pruchased = pruchased;
    }
}
