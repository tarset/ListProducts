package com.dvdev.horodynskyjdemo.sotrs;

import com.dvdev.horodynskyjdemo.R;
import com.dvdev.horodynskyjdemo.objects.Product;

import java.util.Comparator;

public class SortListControllers implements Comparator<Product> {
    private int typeSort = 0;

    public SortListControllers(int typeSort) {
        this.typeSort = typeSort;
    }

    @Override
    public int compare(Product o1, Product o2) {
        switch (typeSort) {
            case R.string.type_method_sort_az:
                return sortAZ(o1, o2);
            case R.string.type_method_sort_za:
                return sortZA(o1, o2);
            case R.string.type_method_sort_purchased:
                return sortPurchased(o1, o2);
            case R.string.type_method_sort_not_purchased:
                return sortNotPurchased(o1, o2);
            case R.string.type_method_sort_remote_all_selection_item_in_listview:
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
