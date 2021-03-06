package com.dvdev.horodynskyjdemo.models;

/**
 * Об'єкт даних для пункта у ListView
 */
public class Product {

    private String name;
    private boolean pruchased;

    public Product(String name, boolean pruchased) {
        this.name = name;
        this.pruchased = pruchased;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isPruchased() {
        return pruchased;
    }

    public void setPruchased(boolean pruchased) {
        this.pruchased = pruchased;
    }
}