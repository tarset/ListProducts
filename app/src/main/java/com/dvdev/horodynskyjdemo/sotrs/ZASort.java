package com.dvdev.horodynskyjdemo.sotrs;

import com.dvdev.horodynskyjdemo.objects.Item;

import java.util.Comparator;

public class ZASort implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        String str1 = o1.getName();
        String str2 = o2.getName();
        return str2.compareTo(str1);
    }
}
