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
import android.widget.Toast;

import com.dvdev.horodynskyjdemo.R;
import com.dvdev.horodynskyjdemo.adapters.ProductAdapter;
import com.dvdev.horodynskyjdemo.models.Product;
import com.dvdev.horodynskyjdemo.resources.Constants;
import com.dvdev.horodynskyjdemo.controllers.SortProductsController;
import com.dvdev.horodynskyjdemo.storage.JsonConservationObtainingProducts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Product> arrListProducts = new ArrayList<>();

    private Constants constants;
    private JsonConservationObtainingProducts jsonManager;
    private ProductAdapter adapter;
    private SortProductsController sortProductsController;

    private ListView lvProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        initializationGlobalObject();
        settingListProducts();
    }

    @Override
    protected void onStart() {
        super.onStart();
        arrListProducts = productsObtainingWithJson();
        arrListProducts = adapter.update(arrListProducts);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveProducts();
    }

    private void initializationGlobalObject() {
        constants = new Constants();
        adapter = new ProductAdapter(MainActivity.this, arrListProducts);
        sortProductsController = new SortProductsController();
        lvProducts = (ListView) findViewById(R.id.lvProducts);
    }

    private void settingListProducts() {
        lvProducts.setAdapter(adapter);
        registerForContextMenu(lvProducts);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_product, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        switch (item.getItemId()) {
            case R.id.contextMenuEditProduct:
                intentProductActivityEdit(info.position);
                return true;
            case R.id.contextMenuRemoveProduct:
                arrListProducts.remove(info.position);
                adapter.notifyDataSetChanged();
                return true;
        }
        saveProducts();
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        arrListProducts = productsObtainingWithJson(data.getStringExtra(constants.
                INTENT_EXTRA_NAME_PRODUCT_FOR_EDIT));
        saveProducts();
    }

    private void intentProductActivityAdd() {
        //Передає список продуктів у json
        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
        intent.putExtra(constants.INTENT_EXTRA_NAME_FOR_SELECT_ACTION_ADD_OR_EDIT,
                constants.INTENT_EXTRA_VALUE_ACTION_ADD);
        intent.putExtra(constants.INTENT_EXTRA_NAME_PRODUCTS,
                jsonManager.setProducts(arrListProducts));
        startActivityForResult(intent, constants.ON_ACTIVITY_RESULT_ADD);
    }

    private void intentProductActivityEdit(int position) {
        //Передає список продуктів у json та вибрану позицію
        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
        intent.putExtra(constants.INTENT_EXTRA_NAME_FOR_SELECT_ACTION_ADD_OR_EDIT,
                constants.INTENT_EXTRA_VALUE_ACTION_EDIT);
        intent.putExtra(constants.INTENT_EXTRA_NAME_PRODUCTS,
                jsonManager.setProducts(arrListProducts));
        intent.putExtra(constants.INTENT_EXTRA_NAME_POSITION_FOR_EDIT,
                String.valueOf(position));
        startActivityForResult(intent, constants.ON_ACTIVITY_RESULT_EDIT);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
            Toast toastListClear = Toast.makeText(MainActivity.this,
                    "Ваш список продуктів пустий ¯\\_(ツ)_/¯",
                    Toast.LENGTH_LONG);
        switch (item.getItemId()) {
            case R.id.menuAdd:
                intentProductActivityAdd();
                break;
            case R.id.menuClearList:
                if (arrListProducts.isEmpty()) toastListClear.show();
                else for (Product product : arrListProducts) if (product.isPruchased())
                    product.setPruchased(false);
                break;
            case R.id.menuNewList:
                if (arrListProducts.isEmpty()) toastListClear.show();
                else arrListProducts = new ArrayList<>();
                break;
            case R.id.menuSort:

                break;
            case R.id.sortAz:
                if (arrListProducts.isEmpty()) toastListClear.show();
                else Collections.sort(arrListProducts, sortProductsController.sortAZ());
                break;
            case R.id.sortZa:
                if (arrListProducts.isEmpty()) toastListClear.show();
                else Collections.sort(arrListProducts, Collections.reverseOrder(sortProductsController.sortAZ()));
                break;
            case R.id.sortPurchased:
                if (arrListProducts.isEmpty()) toastListClear.show();
                else Collections.sort(arrListProducts, sortProductsController.sortPurchased());
                break;
            case R.id.sortNotPurchased:
                if (arrListProducts.isEmpty()) toastListClear.show();
                else Collections.sort(arrListProducts, Collections.reverseOrder(sortProductsController.sortPurchased()));
                break;
        }
        saveProducts();
        return super.onOptionsItemSelected(item);
    }

    public void addTestData(View view) {
        String[] tmp = {"Молоко", "Печиво", "Багет", "Мука", "Морозиво", "Цукор", "Картопля",
                "Батон", "Риба", "Гриби", "Огірки", "Спагетті", "Вода", "Свинина",
                "Курячі стегна", "Майонез", "Помідори", "Сіль"};
        for (String s : tmp)
            arrListProducts.add(new Product(s, new Random().nextBoolean()));

        saveProducts();
    }

    private void saveProducts() {
        arrListProducts = adapter.update(arrListProducts);

        productsConservationInJson();
    }

    public ArrayList<Product> productsObtainingWithJson() {
        jsonManager = new JsonConservationObtainingProducts();
        return jsonManager.getProducts(this.getPreferences(Context.MODE_PRIVATE).
                getString(constants.SHARED_PREFERENCES_KEY_STORAGE_PRODUCTS, ""));
    }

    public ArrayList<Product> productsObtainingWithJson(String s) {
        jsonManager = new JsonConservationObtainingProducts();
        return jsonManager.getProducts(s);
    }

    public void productsConservationInJson() {
        String s = jsonManager.setProducts(arrListProducts);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(constants.SHARED_PREFERENCES_KEY_STORAGE_PRODUCTS, s);
        editor.apply();
    }
}
