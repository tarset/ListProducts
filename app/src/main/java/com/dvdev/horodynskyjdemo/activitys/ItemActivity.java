package com.dvdev.horodynskyjdemo.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dvdev.horodynskyjdemo.R;
import com.dvdev.horodynskyjdemo.objects.Item;

public class ItemActivity extends AppCompatActivity {
    private String INTENT_VALUE;
    private boolean add = false;

    private EditText editNameItem;
    private TextView messageAboutEmpty;
    private Button buttonActionAddNewOrEditedNewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        initializationGlobalObject();
        //TODO: Потрібно ще ставити по центрі заголовок активності

        INTENT_VALUE = getIntent().getStringExtra(MainActivity.KEY_ACTION);
        if (INTENT_VALUE.equals(MainActivity.ATTRIBUTE_INTENT_ADD)) {
            add = true;
            setTitle("Новий продукт");
            buttonActionAddNewOrEditedNewItem.setText("Додати");

        } else if(INTENT_VALUE.equals(MainActivity.ATTRIBUTE_INTENT_EDIT)) {
            add = false;
            setTitle("Редагування продукту");
            buttonActionAddNewOrEditedNewItem.setText("Редагувати");

        }
    }

    private void initializationGlobalObject() {
        messageAboutEmpty = (TextView) findViewById(R.id.messageAboutEmpty);
        messageAboutEmpty.setVisibility(View.GONE);
        editNameItem = (EditText) findViewById(R.id.editNameItem);
        buttonActionAddNewOrEditedNewItem = (Button) findViewById(R.id.confirm_action_new_or_edited_item);
    }

    public void actionCancelNewItem(View view) {
        finish();
    }

    public void actionAddNewOrEditedItem(View view) {
        if (!editNameItem.getText().toString().equals("")) {
            if (add)
                MainActivity.data.add(new Item(editNameItem.getText().toString(), false));
            else
                MainActivity.data.get(Integer.parseInt(MainActivity.ATTRIBUTE_POSITION_FOR_EDIT)).
                        setName(editNameItem.getText().toString());
            MainActivity.updateList();
            finish();
        } else
            messageAboutEmpty.setVisibility(View.VISIBLE);
    }
}