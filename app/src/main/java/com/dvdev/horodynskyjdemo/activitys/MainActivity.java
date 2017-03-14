package com.dvdev.horodynskyjdemo.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dvdev.horodynskyjdemo.R;
import com.dvdev.horodynskyjdemo.adapters.ListAdapter;
import com.dvdev.horodynskyjdemo.objects.Item;
import com.dvdev.horodynskyjdemo.sotrs.AZSort;
import com.dvdev.horodynskyjdemo.sotrs.NotPurchasedSort;
import com.dvdev.horodynskyjdemo.sotrs.PurchasedSort;
import com.dvdev.horodynskyjdemo.sotrs.RevomeActionSelectionItem;
import com.dvdev.horodynskyjdemo.sotrs.ZASort;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Item> data;

    public static final String KEY_ACTION = "action";
    public static final String ATTRIBUTE_INTENT_ADD = "add";
    public static final String ATTRIBUTE_INTENT_EDIT = "edit";

    private static ListAdapter adapter;

    private ListView listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializationGlobalObject();

        listItem.setAdapter(adapter);
    }

    public static void updateList() {
        adapter.updateResults();
    }

    private void initializationGlobalObject() {
        data = new ArrayList<>();
        adapter = new ListAdapter(MainActivity.this);
        listItem = (ListView) findViewById(R.id.list_item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_add:
                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
                intent.putExtra(KEY_ACTION, ATTRIBUTE_INTENT_ADD);
                startActivity(intent);
                break;
            case R.id.menu_action_clear_list:
                Collections.sort(data, new RevomeActionSelectionItem());
                updateList();
                break;
            case R.id.menu_action_new_list:
                data = new ArrayList<>();
                updateList();
                break;
            case R.id.sort_az:
                Collections.sort(data, new AZSort());
                updateList();
                break;
            case R.id.sort_za:
                Collections.sort(data, new ZASort());
                updateList();
                break;
            case R.id.sort_purchased:
                Collections.sort(data, new PurchasedSort());
                updateList();
                break;
            case R.id.sort_not_purchased:
                Collections.sort(data, new NotPurchasedSort());
                updateList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
