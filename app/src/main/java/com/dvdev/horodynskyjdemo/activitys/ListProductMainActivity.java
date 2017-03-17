package com.dvdev.horodynskyjdemo.activitys;

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
import com.dvdev.horodynskyjdemo.adapters.AdapterForItemsProductInListView;
import com.dvdev.horodynskyjdemo.objects.Product;
import com.dvdev.horodynskyjdemo.objects.Products;
import com.dvdev.horodynskyjdemo.resurses.NameAndValueForExtraIntent;
import com.dvdev.horodynskyjdemo.resurses.TypeMethodSort;
import com.dvdev.horodynskyjdemo.sotrs.SortListControllers;
import com.dvdev.horodynskyjdemo.storage.ConservationAndObtainingData;

import java.util.ArrayList;
import java.util.Collections;

public class ListProductMainActivity extends AppCompatActivity {
    private ConservationAndObtainingData save;

    private AdapterForItemsProductInListView adapter;

    private ListView listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String s = sharedPref.getString("Save", "");

        save = new ConservationAndObtainingData();
        save.setDataList(s);

        initializationGlobalObject();
        settingListView();
    }

    private void settingListView() {
        listItem.setAdapter(adapter);
        registerForContextMenu(listItem);
    }

    private void initializationGlobalObject() {
        adapter = new AdapterForItemsProductInListView(ListProductMainActivity.this);
        listItem = (ListView) findViewById(R.id.list_item);
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
                String posForEdit = String.valueOf(info.position);
                Intent intent = new Intent(ListProductMainActivity.this, CreatingEditingItemListViewActivity.class);
                intent.putExtra(NameAndValueForExtraIntent.KEY_FOR_SELECT_ACTION_ADD_OR_EDIT,
                        NameAndValueForExtraIntent.VALUE_ACTION_EDIT);
                intent.putExtra(getString(R.string.name_position_for_intent), posForEdit);
                startActivityForResult(intent, 1);
                return true;
            case R.id.delete:
                Products.data.remove(info.position);
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
                Intent intent = new Intent(ListProductMainActivity.this, CreatingEditingItemListViewActivity.class);
                intent.putExtra(NameAndValueForExtraIntent.KEY_FOR_SELECT_ACTION_ADD_OR_EDIT,
                        NameAndValueForExtraIntent.VALUE_ACTION_ADD);
                startActivityForResult(intent, 1);
                break;
            case R.id.menu_action_clear_list:
                Collections.sort(Products.data, new SortListControllers(TypeMethodSort.REMOVE_ALL_SELECTION_ITEM_IN_LISTVIEW));
                break;
            case R.id.menu_action_new_list:
                Products.data = new ArrayList<>();
                break;
            case R.id.sort_az:
                Collections.sort(Products.data, new SortListControllers(TypeMethodSort.AZ));
                break;
            case R.id.sort_za:
                Collections.sort(Products.data, new SortListControllers(TypeMethodSort.ZA));
                break;
            case R.id.sort_purchased:
                Collections.sort(Products.data, new SortListControllers(TypeMethodSort.PURCHASED));
                break;
            case R.id.sort_not_purchased:
                Collections.sort(Products.data, new SortListControllers(TypeMethodSort.NOT_PURCHASED));
                break;
        }
        saveDataListAndUpdateAdapter();
        return super.onOptionsItemSelected(item);
    }

    public void addTestData(View view) {
        Products.data.add(new Product("Молоко", true));
        Products.data.add(new Product("Печиво", false));
        Products.data.add(new Product("Винішко", false));
        Products.data.add(new Product("Багет", false));
        Products.data.add(new Product("Мука", true));
        Products.data.add(new Product("Морозиво", false));
        Products.data.add(new Product("Мівіна", true));
        Products.data.add(new Product("Цукор", true));
        Products.data.add(new Product("Картопля", false));
        Products.data.add(new Product("Батон", false));
        Products.data.add(new Product("Хек", false));
        Products.data.add(new Product("Гриби", true));
        Products.data.add(new Product("Спагетті", false));
        Products.data.add(new Product("Вода", true));
        Products.data.add(new Product("Шоколадка", true));
        Products.data.add(new Product("Помідори", true));
        Products.data.add(new Product("Огірки", true));
        Products.data.add(new Product("Салат", false));
        Products.data.add(new Product("Майонез", true));
        Products.data.add(new Product("Свинина", false));
        saveDataListAndUpdateAdapter();
    }

    private void saveDataListAndUpdateAdapter() {
        adapter.notifyDataSetChanged();
        String s = save.saveDataList();

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Save", s);
        editor.commit();
    }
}
