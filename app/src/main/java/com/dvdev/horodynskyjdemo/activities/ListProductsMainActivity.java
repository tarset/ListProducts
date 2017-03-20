package com.dvdev.horodynskyjdemo.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dvdev.horodynskyjdemo.R;
import com.dvdev.horodynskyjdemo.adapters.ProductAdapter;
import com.dvdev.horodynskyjdemo.models.Product;
import com.dvdev.horodynskyjdemo.models.Products;
import com.dvdev.horodynskyjdemo.resurses.KeysForSharedPreferences;
import com.dvdev.horodynskyjdemo.resurses.NameValueForExtraIntent;
import com.dvdev.horodynskyjdemo.sotrs.SortListControllers;
import com.dvdev.horodynskyjdemo.storage.JsonConservationObtainingData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static com.dvdev.horodynskyjdemo.models.Products.data;

public class ListProductsMainActivity extends AppCompatActivity {
    private JsonConservationObtainingData save;
    private ProductAdapter adapter;

    private ListView lvProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        obtainingProducts();

        initializationGlobalObject();
        settingListProducts();
    }

    private void obtainingProducts() {
        save = new JsonConservationObtainingData();
        save.setDataList(this.getPreferences(Context.MODE_PRIVATE).
                getString(KeysForSharedPreferences.KEY_STORAGE_DATA_LISTVIEW, ""));
    }

    private void settingListProducts() {
        lvProducts.setAdapter(adapter);
        registerForContextMenu(lvProducts);
    }

    private void initializationGlobalObject() {
        adapter = new ProductAdapter(ListProductsMainActivity.this);
        lvProducts = (ListView) findViewById(R.id.list_item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        switch (item.getItemId()) {
            case R.id.edit:
                //Записує і передає позицію елемента який потрібно відредагувати
                //Передає ключове значення, в залежності від якого відбувається додавання нового або редагування продукту
                String posForEdit = String.valueOf(info.position);
                Intent intent = new Intent(ListProductsMainActivity.this, CreatingEditingItemListProductsActivity.class);
                intent.putExtra(NameValueForExtraIntent.NAME_FOR_SELECT_ACTION_ADD_OR_EDIT,
                        NameValueForExtraIntent.VALUE_ACTION_EDIT);
                intent.putExtra(NameValueForExtraIntent.NAME_POSITION_FOR_EDIT, posForEdit);
                startActivityForResult(intent, 1);
                return true;
            case R.id.delete:
                data.remove(info.position);
                adapter.notifyDataSetChanged();
                return true;
        }
        saveDataListAndUpdateAdapter();
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_add:
                //Передає ключове значення, в залежності від якого відбувається додавання нового або редагування продукту
                Intent intent = new Intent(ListProductsMainActivity.this, CreatingEditingItemListProductsActivity.class);
                intent.putExtra(NameValueForExtraIntent.NAME_FOR_SELECT_ACTION_ADD_OR_EDIT,
                        NameValueForExtraIntent.VALUE_ACTION_ADD);
                startActivityForResult(intent, 1);
                break;
            case R.id.menu_action_clear_list:
                for (Product product: data)
                    if (product.isPruchased()) product.setPruchased(false);
                break;
            case R.id.menu_action_new_list:
                data = new ArrayList<>();
                break;
            case R.id.sort_az:
                Collections.sort(data, Products.sortAZ());
                break;
            case R.id.sort_za:
                Collections.sort(data, Collections.reverseOrder(Products.sortAZ()));
                break;
            case R.id.sort_purchased:
                Collections.sort(data, Products.sortPurchased());
                break;
            case R.id.sort_not_purchased:
                Collections.sort(data, Collections.reverseOrder(Products.sortPurchased()));
                break;
        }
        saveDataListAndUpdateAdapter();
        return super.onOptionsItemSelected(item);
    }

    public void addTestData(View view) {
        String[] tmp = {"Молоко", "Печиво", "Багет", "Мука", "Морозиво", "Цукор", "Картопля",
                "Батон", "Риба", "Гриби", "Огірки", "Спагетті", "Вода", "Свинина",
                "Курячі стегна", "Майонез", "Помідори", "Сіль"};
        for (String s: tmp)
            data.add(new Product(s, new Random().nextBoolean()));

        saveDataListAndUpdateAdapter();
    }

    private void saveDataListAndUpdateAdapter() {
        adapter.notifyDataSetChanged();
        String s = save.saveDataList();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KeysForSharedPreferences.KEY_STORAGE_DATA_LISTVIEW, s);
        editor.apply();
    }
}
