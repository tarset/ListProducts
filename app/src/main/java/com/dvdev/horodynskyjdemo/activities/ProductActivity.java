package com.dvdev.horodynskyjdemo.activities;

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
import com.dvdev.horodynskyjdemo.controllers.ProductController;
import com.dvdev.horodynskyjdemo.controllers.ProductControllerImpl;
import com.dvdev.horodynskyjdemo.resources.Constants;
import com.dvdev.horodynskyjdemo.view.ProductView;

public class ProductActivity extends AppCompatActivity implements ProductView, View.OnClickListener {

    private Constants constants;
    private ProductController presenter;

    private EditText edtNameProduct;
    private Button btnProductValid;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        edtNameProduct = (EditText) findViewById(R.id.edtNameProduct);
        btnProductValid = (Button) findViewById(R.id.btnProductValid);
        btnProductValid.setOnClickListener(this);
        findViewById(R.id.btnProductCancel).setOnClickListener(this);

        constants = new Constants();
        presenter = new ProductControllerImpl(this);

        presenter.setNamesActionBarButton(
                getIntent().getStringExtra(constants.INTENT_EXTRA_NAME_FOR_SELECT_ACTION_ADD_OR_EDIT));
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProductCancel:
                finish();
                break;
            case R.id.btnProductValid:
                presenter.calculationOfResults(
                        getIntent().getStringExtra(constants.INTENT_EXTRA_NAME_FOR_SELECT_ACTION_ADD_OR_EDIT),
                        edtNameProduct.getText().toString());
                break;
        }
    }

    @Override public void setNamesActionBarButtonAddition() {
        setTitleActivity(getString(R.string.label_activity_product_add));
        btnProductValid.setText(getString(R.string.btn_confirm_action_new_or_edited_product_add));
    }

    @Override public void setNamesActionBarButtonEdits() {
        setTitleActivity(getString(R.string.label_activity_product_edit));
        btnProductValid.setText(getString(R.string.btn_confirm_action_new_or_edited_product_edit));

        edtNameProduct.setText(getIntent().getStringExtra(constants.INTENT_EXTRA_NAME_PRODUCT));
    }

    @Override public void sendingToParent() {
        Intent intent = new Intent();
        intent.putExtra(constants.INTENT_EXTRA_NAME_PRODUCT,
                edtNameProduct.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override public void setProductEmptyError() {
        edtNameProduct.setError(getString(R.string.tv_message_about_empty));
    }

    @Override public void setTitleActivity(String title) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(title);
        textView.setTextSize(20);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        textView.setTypeface(null, Typeface.BOLD);
        getSupportActionBar().setDisplayOptions(getSupportActionBar().DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }
}