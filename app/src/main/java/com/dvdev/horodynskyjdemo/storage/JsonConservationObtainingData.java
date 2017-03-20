package com.dvdev.horodynskyjdemo.storage;

import com.dvdev.horodynskyjdemo.models.Product;
import com.dvdev.horodynskyjdemo.models.Products;
import com.dvdev.horodynskyjdemo.resurses.KeysForGenerationJson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonConservationObtainingData {
    private JSONObject products;
    private JSONObject dataList;
    private JSONArray dataNameItemList;
    private JSONArray dataStateItemList;

    public void setDataList(String s) {
        if (!s.isEmpty()) jsonDeserialayzer(s);
    }

    public String saveDataList() {
        jsonSerialayzer();
        return dataList.toString();
    }

    private void jsonSerialayzer() {
        products = new JSONObject();
        dataList = new JSONObject();
        dataNameItemList = new JSONArray();
        dataStateItemList = new JSONArray();

        for (int i = 0; i < Products.data.size(); i++) {
            dataNameItemList.add(i, Products.data.get(i).getName());
            dataStateItemList.add(i, Products.data.get(i).isPruchased());
        }
        products.put(KeysForGenerationJson.NAME_PRODUCT, dataNameItemList);
        products.put(KeysForGenerationJson.STATE_PRODUCT, dataStateItemList);
        dataList.put(KeysForGenerationJson.PRODUCT, products);
    }

    private void jsonDeserialayzer(String s) {
        JSONParser parser = new JSONParser();
        try {
            dataList = (JSONObject) parser.parse(s);
            products = (JSONObject) dataList.get(KeysForGenerationJson.PRODUCT);
            dataNameItemList = (JSONArray) products.get(KeysForGenerationJson.NAME_PRODUCT);
            dataStateItemList = (JSONArray) products.get(KeysForGenerationJson.STATE_PRODUCT);
            for (int i = 0; i < dataNameItemList.size(); i++) {
                Products.data.add(new Product((String) dataNameItemList.get(i), (Boolean) dataStateItemList.get(i)));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
