package com.dvdev.horodynskyjdemo.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dvdev.horodynskyjdemo.R;
import com.dvdev.horodynskyjdemo.objects.Product;
import com.dvdev.horodynskyjdemo.objects.Products;
import com.dvdev.horodynskyjdemo.resurses.NameAndValueForExtraIntent;

public class CreatingEditingItemListViewActivity extends AppCompatActivity {
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

        String keyAction = getIntent().getStringExtra(NameAndValueForExtraIntent.KEY_FOR_SELECT_ACTION_ADD_OR_EDIT);
        if (keyAction.equals(NameAndValueForExtraIntent.VALUE_ACTION_ADD)) {
            add = true;
            setTitle(R.string.label_activity_new_product);
            buttonActionAddNewOrEditedNewItem.setText(R.string.text_button_for_added_new_product);

        } else if(keyAction.equals(NameAndValueForExtraIntent.VALUE_ACTION_EDIT)) {
            add = false;
            setTitle(R.string.label_activity_edit_product);
            buttonActionAddNewOrEditedNewItem.setText(R.string.text_button_for_edit_product);
            editNameItem.setText(Products.data.get(Integer.parseInt(getIntent().
                    getStringExtra(getString(R.string.name_position_for_intent)))).getName());

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
                Products.data.add(new Product(editNameItem.getText().toString(), false));
            else
                Products.data.get(Integer.parseInt(getIntent().
                        getStringExtra(NameAndValueForExtraIntent.KEY_POSITION_FOR_EDIT))).
                        setName(editNameItem.getText().toString());

            finish();
        } else
            messageAboutEmpty.setVisibility(View.VISIBLE);
    }
}