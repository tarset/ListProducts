package com.dvdev.horodynskyjdemo.sotrs;

import com.dvdev.horodynskyjdemo.objects.Product;
import com.dvdev.horodynskyjdemo.resurses.KeysForMethodsSort;

import java.util.Comparator;

public class SortListControllers implements Comparator<Product> {
    private String typeSort = "";

    public SortListControllers(String typeSort) {
        this.typeSort = typeSort;
    }

    @Override
    public int compare(Product o1, Product o2) {
        switch (typeSort) {
            case KeysForMethodsSort.AZ:
                return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
            case KeysForMethodsSort.ZA:
                return o2.getName().toLowerCase().compareTo(o1.getName().toLowerCase());
            case KeysForMethodsSort.PURCHASED:
                return Boolean.compare(o2.isPruchased(), o1.isPruchased());
            case KeysForMethodsSort.NOT_PURCHASED:
                return Boolean.compare(o1.isPruchased(), o2.isPruchased());
        }
        return -1;
    }
}
