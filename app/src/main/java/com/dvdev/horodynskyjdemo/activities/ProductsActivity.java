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
import com.dvdev.horodynskyjdemo.resources.Constants;
import com.dvdev.horodynskyjdemo.sorts.SortProducts;
import com.dvdev.horodynskyjdemo.storage.JsonConservationObtainingProducts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ProductsActivity extends AppCompatActivity {
    private ArrayList<Product> arrListProducts = new ArrayList<>();

    private Constants constants;
    private JsonConservationObtainingProducts save;
    private ProductAdapter adapter;
    private SortProducts sortProducts;

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
        obtainingProducts();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveDataListAndUpdateAdapter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        String name;
        switch (requestCode) {
            //TODO: Розібрати, чому case не приймає значення змінних
            case 1:
                name = data.getStringExtra(constants.EXTRA_INTENT_NAME_PRODUCT_FOR_EDIT);
                arrListProducts.add(new Product(name, false));
                break;
            case 2:
                name = data.getStringExtra(constants.EXTRA_INTENT_NAME_PRODUCT_FOR_EDIT);
                int position = Integer.parseInt(data.getStringExtra(constants.EXTRA_INTENT_NAME_POSITION_FOR_EDIT));
                arrListProducts.get(position).setName(name);
                break;
        }
    }

    private void obtainingProducts() {
        save = new JsonConservationObtainingProducts(arrListProducts);
        arrListProducts = save.setJsonObjProduct(this.getPreferences(Context.MODE_PRIVATE).
                getString(constants.SHARED_PREFERENCES_KEY_STORAGE_PRODUCTS, ""));
    }

    private void settingListProducts() {
        lvProducts.setAdapter(adapter);
        registerForContextMenu(lvProducts);
    }

    private void initializationGlobalObject() {
        constants = new Constants();
        adapter = new ProductAdapter(ProductsActivity.this, arrListProducts);
        sortProducts = new SortProducts();
        lvProducts = (ListView) findViewById(R.id.lvProducts);
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
                //TODO: Придумати як порівнювати введений елемент до всіх що вже є
                //Записує і передає позицію елемента який потрібно відредагувати
                //Передає ключове значення, в залежності від якого відбувається редагування продукту
                Intent intent = new Intent(ProductsActivity.this, ProductActivity.class);
                intent.putExtra(constants.EXTRA_INTENT_NAME_FOR_SELECT_ACTION_ADD_OR_EDIT,
                        constants.EXTRA_INTENT_VALUE_ACTION_EDIT);
                intent.putExtra(constants.EXTRA_INTENT_NAME_PRODUCT_FOR_EDIT,
                        arrListProducts.get(info.position).getName());
                intent.putExtra(constants.EXTRA_INTENT_NAME_POSITION_FOR_EDIT,
                        info.position);
                startActivityForResult(intent, constants.ON_ACTIVITY_RESULT_EDIT);
                return true;
            case R.id.contextMenuRemoveProduct:
                arrListProducts.remove(info.position);
                adapter.notifyDataSetChanged();
                return true;
        }
        saveDataListAndUpdateAdapter();
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAdd:
                //Передає ключове значення, від якого відбувається додавання нового продукту
                Intent intent = new Intent(ProductsActivity.this, ProductActivity.class);
                intent.putExtra(constants.EXTRA_INTENT_NAME_FOR_SELECT_ACTION_ADD_OR_EDIT,
                        constants.EXTRA_INTENT_VALUE_ACTION_ADD);
                startActivityForResult(intent, constants.ON_ACTIVITY_RESULT_ADD);
                break;
            case R.id.menuClearList:
                for (Product product: arrListProducts)
                    if (product.isPruchased()) product.setPruchased(false);
                break;
            case R.id.menuNewList:
                arrListProducts = new ArrayList<>();
                break;
            case R.id.sortAz:
                Collections.sort(arrListProducts, sortProducts.sortAZ());
                break;
            case R.id.sortZa:
                Collections.sort(arrListProducts, Collections.reverseOrder(sortProducts.sortAZ()));
                break;
            case R.id.sortPurchased:
                Collections.sort(arrListProducts, sortProducts.sortPurchased());
                break;
            case R.id.sortNotPurchased:
                Collections.sort(arrListProducts, Collections.reverseOrder(sortProducts.sortPurchased()));
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
            arrListProducts.add(new Product(s, new Random().nextBoolean()));

        saveDataListAndUpdateAdapter();
    }

    private void saveDataListAndUpdateAdapter() {
        adapter.update(arrListProducts);
        String s = save.saveDataList();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(constants.SHARED_PREFERENCES_KEY_STORAGE_PRODUCTS, s);
        editor.apply();
    }
}
