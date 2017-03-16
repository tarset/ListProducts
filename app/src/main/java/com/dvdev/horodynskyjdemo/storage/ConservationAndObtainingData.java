package com.dvdev.horodynskyjdemo.storage;

import android.util.Log;

import com.dvdev.horodynskyjdemo.objects.Product;
import com.dvdev.horodynskyjdemo.objects.Products;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConservationAndObtainingData {
    private JSONObject products;
    private JSONObject dataList;
    private JSONArray dataNameItemList;
    private JSONArray dataStateItemList;

    public void setDataList(String s) {
        if (!s.isEmpty())
            JSONdeserialayzer(s);
    }

    public String saveDataList() {
        JSONserialayzer();
        return dataList.toString();
    }

    private void JSONserialayzer() {
        products = new JSONObject();
        dataList = new JSONObject();
        dataNameItemList = new JSONArray();
        dataStateItemList = new JSONArray();
        for (int i = 0; i < Products.data.size(); i++) {
            dataNameItemList.add(i, Products.data.get(i).getName());
            dataStateItemList.add(i, Products.data.get(i).isPruchased());
        }
        products.put("NameProducts", dataNameItemList);
        products.put("StateProducts", dataStateItemList);
        dataList.put("Products", products);
        Log.d("JSON", dataList.toString());
    }

    private void JSONdeserialayzer(String s) {
        JSONParser parser = new JSONParser();
        try {
            dataList = (JSONObject) parser.parse(s);
            products = (JSONObject) dataList.get("Products");
            dataNameItemList = (JSONArray) products.get("NameProducts");
            dataStateItemList = (JSONArray) products.get("StateProducts");
            for (int i = 0; i < dataNameItemList.size(); i++) {
                Products.data.add(new Product((String) dataNameItemList.get(i), (Boolean) dataStateItemList.get(i)));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
