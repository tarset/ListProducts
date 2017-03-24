package com.dvdev.horodynskyjdemo.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ArrayList<Product> arrListProducts = new ArrayList<>();

    private Constants constants;
    private JsonConservationObtainingProducts jsonManager;
    private ProductAdapter adapter;
    private SortProductsController sortProductsController;

    private ListView lvProducts;
    private TextView tvMessageEmptyListProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializationGlobalObject();
        settingListProducts();

        adapter.notifyDataSetChanged();
    }

    private void initializationGlobalObject() {
        constants = new Constants();
        arrListProducts = productsObtainingWithJson();
        adapter = new ProductAdapter(MainActivity.this, arrListProducts);
        tvMessageEmptyListProducts = (TextView) findViewById(R.id.tvMessageEmptyListProducts);
        tvMessageEmptyListProducts.setVisibility(View.GONE);
        sortProductsController = new SortProductsController();
        lvProducts = (ListView) findViewById(R.id.lvProducts);
    }

    private void settingListProducts() {
        lvProducts.setAdapter(adapter);
        registerForContextMenu(lvProducts);
        lvProducts.setOnItemClickListener(this);
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
                saveProducts();
                return true;
            case R.id.contextMenuRemoveProduct:
                arrListProducts.remove(info.position);
                saveProducts();
                return true;
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;
        switch (requestCode) {
            case 1:
                arrListProducts.add(new Product(data.getStringExtra(constants.
                        INTENT_EXTRA_NAME_NEW_PRODUCT), false));
                break;
            case 2:
                arrListProducts.get(Integer.parseInt(data.getStringExtra(constants.
                        INTENT_EXTRA_NAME_POSITION_FOR_EDIT))).
                        setName(data.getStringExtra(constants.INTENT_EXTRA_NAME_EDITED_PRODUCT));
                break;
        }

        saveProducts();
    }

    private void intentProductActivityAdd() {
        //Передає список продуктів у json
        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
        intent.putExtra(constants.INTENT_EXTRA_NAME_FOR_SELECT_ACTION_ADD_OR_EDIT,
                constants.INTENT_EXTRA_VALUE_ACTION_ADD);
        intent.putExtra(constants.INTENT_EXTRA_NAME_PRODUCTS,
                jsonManager.setProducts(arrListProducts));
        intent.putExtra(constants.INTENT_EXTRA_NAME_POSITION_FOR_EDIT,
                String.valueOf(-1));
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
                    "Cписок продуктів порожній ¯\\_(ツ)_/¯",
                    Toast.LENGTH_SHORT);
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
                else arrListProducts.clear();
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
        if (arrListProducts.isEmpty()) {
            tvMessageEmptyListProducts.setVisibility(View.VISIBLE);
            lvProducts.setVisibility(View.GONE);
        } else {
            tvMessageEmptyListProducts.setVisibility(View.GONE);
            lvProducts.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
        productsConservationInJson();
    }

    public ArrayList<Product> productsObtainingWithJson() {
        jsonManager = new JsonConservationObtainingProducts();
        return jsonManager.getProducts(this.getPreferences(Context.MODE_PRIVATE).
                getString(constants.SHARED_PREFERENCES_KEY_STORAGE_PRODUCTS, ""));
    }

    public void productsConservationInJson() {
        String s = jsonManager.setProducts(arrListProducts);
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(constants.SHARED_PREFERENCES_KEY_STORAGE_PRODUCTS, s);
        editor.apply();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (arrListProducts.get(position).isPruchased()) arrListProducts.get(position).setPruchased(false);
        else arrListProducts.get(position).setPruchased(true);
        saveProducts();
    }
}
