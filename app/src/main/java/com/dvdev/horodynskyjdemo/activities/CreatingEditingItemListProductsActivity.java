package com.dvdev.horodynskyjdemo.activities;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dvdev.horodynskyjdemo.R;
import com.dvdev.horodynskyjdemo.models.Product;
import com.dvdev.horodynskyjdemo.models.Products;
import com.dvdev.horodynskyjdemo.resurses.NameValueForExtraIntent;

public class CreatingEditingItemListProductsActivity extends AppCompatActivity {
    private boolean add = false; //індетифікатор для вибору дії кнопки (Додати/Редагувати)

    private EditText etNameProduct;
    private TextView tvMessageAboutEmpty;
    private Button btnActionAddEditNewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_editing_item_listview);

        initializationGlobalObject();



        String keyAction = getIntent().getStringExtra(NameValueForExtraIntent.NAME_FOR_SELECT_ACTION_ADD_OR_EDIT);
        if (keyAction.equals(NameValueForExtraIntent.VALUE_ACTION_ADD)) {
            add = true;
            setTitleActivity("Новий продукт");
            btnActionAddEditNewProduct.setText("Додати");

        } else if (keyAction.equals(NameValueForExtraIntent.VALUE_ACTION_EDIT)) {
            add = false;
            setTitleActivity("Редагування продукту");
            btnActionAddEditNewProduct.setText("Редагувати");
            etNameProduct.setText(Products.data.get(Integer.parseInt(getIntent().
                    getStringExtra(NameValueForExtraIntent.NAME_POSITION_FOR_EDIT))).getName());
        }
    }

    private void setTitleActivity(String title) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }

    private void initializationGlobalObject() {
        tvMessageAboutEmpty = (TextView) findViewById(R.id.messageAboutEmpty);
        tvMessageAboutEmpty.setVisibility(View.GONE);
        etNameProduct = (EditText) findViewById(R.id.editNameItem);
        btnActionAddEditNewProduct = (Button) findViewById(R.id.confirm_action_new_or_edited_item);
    }

    public void actionCancelNewItem(View view) {
        finish();
    }

    public void actionAddNewOrEditedItem(View view) {
        if (!etNameProduct.getText().toString().equals("")) { //Перевірка на дурачка
            if (add)
                Products.data.add(new Product(etNameProduct.getText().toString(), false));
            else
                Products.data.get(Integer.parseInt(getIntent().
                        getStringExtra(NameValueForExtraIntent.NAME_POSITION_FOR_EDIT))).
                        setName(etNameProduct.getText().toString());

            finish();
        } else
            tvMessageAboutEmpty.setVisibility(View.VISIBLE);
    }
}