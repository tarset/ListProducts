package com.dvdev.horodynskyjdemo.sotrs;

import com.dvdev.horodynskyjdemo.objects.Item;

import java.util.Comparator;

public class NotPurchasedSort implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        if (o1.isPruchased() && o2.isPruchased()) return 0;
        else if (o1.isPruchased() && !o2.isPruchased()) return 1;
        else return -1;
    }
}
