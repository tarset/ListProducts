package com.dvdev.horodynskyjdemo.storage;

import com.dvdev.horodynskyjdemo.models.Product;
import com.dvdev.horodynskyjdemo.resources.Constants;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;

public class JsonConservationObtainingProducts {
    private JSONObject jsonObjProducts;
    private JSONObject jsonObjProduct;
    private JSONArray jsonArrNameProduct;
    private JSONArray jsonArrStateProduct;

    private Constants constants;

    private ArrayList<Product> arrListProducts;

        public JsonConservationObtainingProducts(ArrayList<Product> arrListProducts) {
            this.arrListProducts = arrListProducts;
            constants = new Constants();
    }

    public ArrayList<Product> setJsonObjProduct(String s) {
        if (!s.isEmpty()) {
            jsonDeserializer(s);
            return arrListProducts;
        } else {
            //TODO: Змінити на нормальне
            arrListProducts.add(new Product("Пусто", false));
            return arrListProducts;
        }
    }

    public String saveDataList() {
        jsonSerializer();
        return jsonObjProduct.toString();
    }

    private void jsonSerializer() {
        jsonObjProducts = new JSONObject();
        jsonObjProduct = new JSONObject();
        jsonArrNameProduct = new JSONArray();
        jsonArrStateProduct = new JSONArray();

        for (int i = 0; i < arrListProducts.size(); i++) {
            jsonArrNameProduct.add(i, arrListProducts.get(i).getName());
            jsonArrStateProduct.add(i, arrListProducts.get(i).isPruchased());
        }
        jsonObjProducts.put(constants.GENERATION_JSON_NAME_PRODUCT, jsonArrNameProduct);
        jsonObjProducts.put(constants.GENERATION_JSON_STATE_PRODUCT, jsonArrStateProduct);
        jsonObjProduct.put(constants.GENERATION_JSON_PRODUCT, jsonObjProducts);
    }

    private void jsonDeserializer(String s) {
        JSONParser parser = new JSONParser();
        try {
            jsonObjProduct = (JSONObject) parser.parse(s);
            jsonObjProducts = (JSONObject) jsonObjProduct.get(constants.GENERATION_JSON_PRODUCT);
            jsonArrNameProduct = (JSONArray) jsonObjProducts.get(constants.GENERATION_JSON_NAME_PRODUCT);
            jsonArrStateProduct = (JSONArray) jsonObjProducts.get(constants.GENERATION_JSON_STATE_PRODUCT);
            for (int i = 0; i < jsonArrNameProduct.size(); i++) {
                arrListProducts.add(new Product((String) jsonArrNameProduct.get(i), (Boolean) jsonArrStateProduct.get(i)));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
