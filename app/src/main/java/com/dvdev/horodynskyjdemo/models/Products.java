package com.dvdev.horodynskyjdemo.models;

import com.dvdev.horodynskyjdemo.storage.JsonConservationObtainingProducts;

import java.util.ArrayList;

public class Products {

    private ArrayList<Product> listProducts;

    private JsonConservationObtainingProducts jsonManager;

    public Products() {
        listProducts = new ArrayList<>();
        jsonManager = new JsonConservationObtainingProducts();
    }

    public ArrayList<Product> getListProducts() {
        return listProducts;
    }

    public String convertToJson() {
        return jsonManager.setProducts(listProducts);
    }

    public void convertWithJson(String json) {
        listProducts = jsonManager.getProducts(json);
    }
}
