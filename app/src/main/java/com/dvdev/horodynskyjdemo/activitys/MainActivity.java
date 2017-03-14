package com.dvdev.horodynskyjdemo.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.dvdev.horodynskyjdemo.R;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_ACTION = "action";
    public static final String ATTRIBUTE_INTENT_ADD = "add";
    public static final String ATTRIBUTE_INTENT_EDIT = "edit";

    public static String NAME_ITEM = "";



    private ListView listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializationGlobalObject();
    }

    private void initializationGlobalObject() {
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
            case R.id.menu_action_sort:

                break;
            case R.id.menu_action_clear_list:

                break;
            case R.id.menu_action_new_list:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
