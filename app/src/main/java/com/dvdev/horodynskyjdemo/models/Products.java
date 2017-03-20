package com.dvdev.horodynskyjdemo.models;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Колекція обєктів даних пунктів ListView
 */
public class Products {
    public static ArrayList<Product> data = new ArrayList<>();

    public static Comparator<Product> sortAZ() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        };
    }

    public static Comparator<Product> sortPurchased() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o2.isPruchased().compareTo(o1.isPruchased());
            }
        };
    }
}
