package com.dvdev.horodynskyjdemo.objects;

import android.util.Log;

import java.util.ArrayList;

/**
 * Колекція обєктів даних пунктів ListView
 */
public class Products {
    public static ArrayList<Product> data = new ArrayList<>();

    public static void logProducts() {
        for (Product product: data)
            Log.d("Product", "Name: " + product.getName() + "\t\tCheckBox: " + product.isPruchased());
    }
}
