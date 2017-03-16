package com.dvdev.horodynskyjdemo.objects;

/**
 * Об'єкт даних для пункта у ListView, зберігає назву та стан CheckBox'а
 */
public class Product {
    private String name = "";
    private boolean pruchased = false;

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

    public boolean isPruchased() {
        return pruchased;
    }

    public void setPruchased(boolean pruchased) {
        this.pruchased = pruchased;
    }
}
