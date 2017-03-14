package com.dvdev.horodynskyjdemo.sotrs;

import com.dvdev.horodynskyjdemo.objects.Item;

import java.util.Comparator;

public class RevomeActionSelectionItem implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        o1.setPruchased(false);
        o2.setPruchased(false);
        return 0;
    }
}