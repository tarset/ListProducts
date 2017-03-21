package com.dvdev.horodynskyjdemo.sorts;

import com.dvdev.horodynskyjdemo.models.Product;

import java.util.Comparator;

public class SortProducts {
    public Comparator<Product> sortAZ() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            }
        };
    }

    public Comparator<Product> sortPurchased() {
        return new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o2.isPruchased().compareTo(o1.isPruchased());
            }
        };
    }
}
