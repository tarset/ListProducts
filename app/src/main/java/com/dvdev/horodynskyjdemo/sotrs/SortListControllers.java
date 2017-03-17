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
                return sortAZ(o1, o2);
            case TypeMethodSort.ZA:
                return sortZA(o1, o2);
            case TypeMethodSort.PURCHASED:
                return sortPurchased(o1, o2);
            case TypeMethodSort.NOT_PURCHASED:
                return sortNotPurchased(o1, o2);
            case TypeMethodSort.REMOVE_ALL_SELECTION_ITEM_IN_LISTVIEW:
                return RemoveActionSelectionItem(o1, o2);
        }
        return -1;
    }

    private int sortAZ(Product o1, Product o2) {
        String str1 = o1.getName();
        String str2 = o2.getName();
        return str1.compareTo(str2);
    }

    private int sortZA(Product o1, Product o2) {
        String str1 = o1.getName();
        String str2 = o2.getName();
        return str2.compareTo(str1);
    }

    private int sortNotPurchased(Product o1, Product o2) {
        if (o1.isPruchased() && o2.isPruchased()) return 0;
        else if (o1.isPruchased() && !o2.isPruchased()) return 1;
        else return -1;
    }

    private int sortPurchased(Product o1, Product o2) {
        if (!o1.isPruchased() && !o2.isPruchased()) return 0;
        else if (!o1.isPruchased() && o2.isPruchased()) return 1;
        else return -1;
    }

    private int RemoveActionSelectionItem(Product o1, Product o2) {
        o1.setPruchased(false);
        o2.setPruchased(false);
        return 0;
    }
}
