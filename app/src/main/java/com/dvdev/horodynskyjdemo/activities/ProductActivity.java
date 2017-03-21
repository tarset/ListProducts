package com.dvdev.horodynskyjdemo.activities;

import android.app.ActionBar;
import android.content.Intent;
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
import com.dvdev.horodynskyjdemo.resources.Constants;

public class ProductActivity extends AppCompatActivity {
    private boolean boolAdd = false; //індетифікатор для вибору дії кнопки (Додати/Редагувати)

    private Constants constants;

    private EditText edtNameProduct;
    private TextView tvMessageAboutEmpty;
    private Button btnActionAddEditNewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        initializationGlobalObject();

        String keyAction = getIntent().getStringExtra(constants.EXTRA_INTENT_NAME_FOR_SELECT_ACTION_ADD_OR_EDIT);
        if (keyAction.equals(constants.EXTRA_INTENT_VALUE_ACTION_ADD)) {
            boolAdd = true;
            setTitleActivity(getString(R.string.label_activity_product_add));
            btnActionAddEditNewProduct.setText(getString(R.string.btn_confirm_action_new_or_edited_product_add));

        } else if (keyAction.equals(constants.EXTRA_INTENT_VALUE_ACTION_EDIT)) {
            boolAdd = false;
            setTitleActivity(getString(R.string.label_activity_product_edit));
            btnActionAddEditNewProduct.setText(getString(R.string.btn_confirm_action_new_or_edited_product_edit));
            edtNameProduct.setText(getIntent().getStringExtra(constants.EXTRA_INTENT_NAME_PRODUCT_FOR_EDIT));
        }
    }

    private void setTitleActivity(String title) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }

    private void initializationGlobalObject() {
        constants = new Constants();
        tvMessageAboutEmpty = (TextView) findViewById(R.id.tvMessageAboutEmpty);
        tvMessageAboutEmpty.setVisibility(View.GONE);
        edtNameProduct = (EditText) findViewById(R.id.edtNameProduct);
        btnActionAddEditNewProduct = (Button) findViewById(R.id.btnConfirmActionNewOrEditedProduct);
    }

    public void actionCancelNewItem(View view) {
        finish();
    }

    public void actionAddNewOrEditedItem(View view) {
        if (!edtNameProduct.getText().toString().equals("")) { //Перевірка на дурачка
            if (boolAdd) {
                Intent intent = new Intent();
                intent.putExtra(constants.EXTRA_INTENT_NAME_PRODUCT_FOR_EDIT,
                        edtNameProduct.getText().toString());
                setResult(RESULT_OK, intent);
            } else {
                Intent intent = new Intent();
                intent.putExtra(constants.EXTRA_INTENT_NAME_PRODUCT_FOR_EDIT,
                        edtNameProduct.getText().toString());
                intent.putExtra(constants.EXTRA_INTENT_NAME_POSITION_FOR_EDIT,
                        getIntent().getStringExtra(constants.EXTRA_INTENT_NAME_POSITION_FOR_EDIT));
                setResult(RESULT_OK, intent);
            }

            finish();
        } else
            tvMessageAboutEmpty.setVisibility(View.VISIBLE);
    }
}