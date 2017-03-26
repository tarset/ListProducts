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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dvdev.horodynskyjdemo.R;
import com.dvdev.horodynskyjdemo.adapters.ProductAdapter;
import com.dvdev.horodynskyjdemo.models.Product;
import com.dvdev.horodynskyjdemo.models.Products;
import com.dvdev.horodynskyjdemo.controllers.MainController;
import com.dvdev.horodynskyjdemo.resources.Constants;
import com.dvdev.horodynskyjdemo.view.MainView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements MainView, AdapterView.OnItemClickListener {

    private Products products;
    private MainController controller;

    private BaseAdapter adapter;
    private SharedPreferences.Editor sharedPrefEditor;

    private ListView lvProducts;
    private TextView tvMessageEmptyListProducts;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        products = new Products();
        controller = new MainController(this, products);

        sharedPrefEditor = this.getPreferences(Context.MODE_PRIVATE).edit();

        tvMessageEmptyListProducts = (TextView) findViewById(R.id.tvMessageEmptyListProducts);
        tvMessageEmptyListProducts.setVisibility(View.GONE);
        lvProducts = (ListView) findViewById(R.id.lvProducts);
        adapter = new ProductAdapter(MainActivity.this, products.getListProducts());
        lvProducts.setAdapter(adapter);
        lvProducts.setOnItemClickListener(this);
        registerForContextMenu(lvProducts);

        controller.switchVisibleListProducts();
    }

    @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        controller.onItemClick(position);
    }

    @Override public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_product, menu);
    }

    @Override public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();

        return controller.onContextItemSelected(item.getItemId(), info.position);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        controller.onOptionsItemSelected(item.getItemId());

        return super.onOptionsItemSelected(item);
    }

    @Override public void intentProductActivityAdd() {
        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_NAME_FOR_SELECT_ACTION_ADD_OR_EDIT,
                Constants.INTENT_EXTRA_VALUE_ACTION_ADD);
        intent.putExtra(Constants.INTENT_EXTRA_NAME_LIST_PRODUCTS_IN_JSON,
                controller.getListProductsInJson());
        startActivityForResult(intent, 1);
    }

    @Override public void intentProductActivityEdit(String nameProduct) {
        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
        intent.putExtra(Constants.INTENT_EXTRA_NAME_FOR_SELECT_ACTION_ADD_OR_EDIT,
                Constants.INTENT_EXTRA_VALUE_ACTION_EDIT);
        intent.putExtra(Constants.INTENT_EXTRA_NAME_LIST_PRODUCTS_IN_JSON,
                controller.getListProductsInJson());
        intent.putExtra(Constants.INTENT_EXTRA_NAME_PRODUCT, nameProduct);
        startActivityForResult(intent, 2);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;

        controller.onActivityResult(requestCode, data.getStringExtra(Constants.INTENT_EXTRA_NAME_PRODUCT));
    }

    @Override public void saveProductsInStorage() {
        setListProductsInStorage(products.convertToJson());
    }

    @Override public void showToast(String toastMessage) {
        Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
    }

    @Override public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override public String getListProductsWithStorage() {
        return this.getPreferences(Context.MODE_PRIVATE).
                getString(Constants.SHARED_PREFERENCES_KEY_STORAGE_PRODUCTS, "");
    }

    @Override public void setListProductsInStorage(String json) {
        sharedPrefEditor.putString(Constants.SHARED_PREFERENCES_KEY_STORAGE_PRODUCTS, json);
        sharedPrefEditor.apply();
    }

    @Override public void setListViewVisible() {
        tvMessageEmptyListProducts.setVisibility(View.GONE);
        lvProducts.setVisibility(View.VISIBLE);
    }

    @Override public void setListViewGone() {
        tvMessageEmptyListProducts.setVisibility(View.VISIBLE);
        lvProducts.setVisibility(View.GONE);
    }

    public void addTestData(View view) {
        String[] tmp = {"Молоко", "Печиво", "Багет", "Мука", "Морозиво", "Цукор", "Картопля",
                "Батон", "Риба", "Гриби", "Огірки", "Спагетті", "Вода", "Свинина",
                "Курячі стегна", "Майонез", "Помідори", "Сіль"};
        for (String s : tmp)
            products.getListProducts().add(new Product(s, new Random().nextBoolean()));

        setListViewVisible();
        updateAdapter();
        saveProductsInStorage();
    }
}
