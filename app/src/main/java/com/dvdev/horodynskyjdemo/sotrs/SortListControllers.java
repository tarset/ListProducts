package com.dvdev.horodynskyjdemo.sotrs;

import com.dvdev.horodynskyjdemo.objects.Product;
import com.dvdev.horodynskyjdemo.resurses.TypeMethodSort;

import java.util.Comparator;

public class SortListControllers implements Comparator<Product> {
    private String typeSort = "";

    public SortListControllers(String typeSort) {
        this.typeSort = typeSort;
    }

    @Override
    public int compare(Product o1, Product o2) {
        switch (typeSort) {
            case TypeMethodSort.AZ:
                return o1.getName().compareTo(o2.getName());
            case TypeMethodSort.ZA:
                return o2.getName().compareTo(o1.getName());
            case TypeMethodSort.PURCHASED:
                return Boolean.compare(o2.isPruchased(), o1.isPruchased());
            case TypeMethodSort.NOT_PURCHASED:
                return Boolean.compare(o1.isPruchased(), o2.isPruchased());
        }
        return -1;
    }
}
