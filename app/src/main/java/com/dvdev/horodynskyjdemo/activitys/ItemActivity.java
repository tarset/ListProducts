package com.dvdev.horodynskyjdemo.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dvdev.horodynskyjdemo.R;

public class ItemActivity extends AppCompatActivity {
    private String INTENT_VALUE;

    private EditText editNameItem;
    private Button buttonActionCanceNewItem;
    private Button buttonActionAddNewOrEditedNewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        initializationGlobalObject();
        //TODO: Потрібно ще ставити по центрі заголовок активності

        INTENT_VALUE = getIntent().getStringExtra(MainActivity.KEY_ACTION);
        if (INTENT_VALUE.equals(MainActivity.ATTRIBUTE_INTENT_ADD)) {
            setTitle("Новий продукт");
            buttonActionAddNewOrEditedNewItem.setText("Додати");

        } else if(INTENT_VALUE.equals(MainActivity.ATTRIBUTE_INTENT_EDIT)) {
            setTitle("Редагування продукту");
            buttonActionAddNewOrEditedNewItem.setText("Редагувати");

        }
    }

    private void initializationGlobalObject() {
        editNameItem = (EditText) findViewById(R.id.editNameItem);
        buttonActionCanceNewItem = (Button) findViewById(R.id.cancel_action_new_item);
        buttonActionAddNewOrEditedNewItem = (Button) findViewById(R.id.confirm_action_new_or_edited_item);
    }

    public void actionCancelNewItem(View view) {
        finish();
    }

    public void actionAddNewOrEditedItem(View view) {
    }
}