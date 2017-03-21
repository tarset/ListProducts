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
import com.dvdev.horodynskyjdemo.models.Product;
import com.dvdev.horodynskyjdemo.resources.Constants;
import com.dvdev.horodynskyjdemo.storage.JsonConservationObtainingProducts;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    private Constants constants;
    private JsonConservationObtainingProducts jsonManager;

    private ArrayList<Product> arrListProducts;
    private int arrListProductsSelectedItem = -1;

    private EditText edtNameProduct;
    private TextView tvMessageErrors;
    private Button btnActionAddEditNewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        initializationGlobalObject();

        String keyAction = getIntent().getStringExtra(constants.INTENT_EXTRA_NAME_FOR_SELECT_ACTION_ADD_OR_EDIT);
        if (keyAction.equals(constants.INTENT_EXTRA_VALUE_ACTION_ADD)) {
            setTitleActivity(getString(R.string.label_activity_product_add));
            btnActionAddEditNewProduct.setText(getString(R.string.btn_confirm_action_new_or_edited_product_add));
            arrListProducts = jsonManager.getProducts(getIntent().getStringExtra(constants.INTENT_EXTRA_NAME_PRODUCTS));
        } else if (keyAction.equals(constants.INTENT_EXTRA_VALUE_ACTION_EDIT)) {
            setTitleActivity(getString(R.string.label_activity_product_edit));
            btnActionAddEditNewProduct.setText(getString(R.string.btn_confirm_action_new_or_edited_product_edit));
            arrListProductsSelectedItem = Integer.parseInt(getIntent().getStringExtra(constants.INTENT_EXTRA_NAME_POSITION_FOR_EDIT));
            arrListProducts = jsonManager.getProducts(getIntent().getStringExtra(constants.INTENT_EXTRA_NAME_PRODUCTS));
            edtNameProduct.setText(arrListProducts.get(arrListProductsSelectedItem).getName());
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
        jsonManager = new JsonConservationObtainingProducts();
        constants = new Constants();
        tvMessageErrors = (TextView) findViewById(R.id.tvMessageErrors);
        tvMessageErrors.setVisibility(View.GONE);
        edtNameProduct = (EditText) findViewById(R.id.edtNameProduct);
        btnActionAddEditNewProduct = (Button) findViewById(R.id.btnConfirmActionNewOrEditedProduct);
    }

    public void actionCancelNewItem(View view) {
        finish();
    }

    private void actionsAddNewEditedItem() {
        if (arrListProductsSelectedItem == -1) { //Якщо позиція не була передана в активність
            arrListProducts.add(new Product(edtNameProduct.getText().toString(), false));
            Intent intent = new Intent();
            intent.putExtra(constants.INTENT_EXTRA_NAME_PRODUCT_FOR_EDIT,
                    jsonManager.setProducts(arrListProducts));
            setResult(RESULT_OK, intent);
        } else {
            Intent intent = new Intent();
            arrListProducts.get(arrListProductsSelectedItem).setName(edtNameProduct.getText().toString());
            intent.putExtra(constants.INTENT_EXTRA_NAME_PRODUCT_FOR_EDIT,
                    jsonManager.setProducts(arrListProducts));
            setResult(RESULT_OK, intent);
        }

        finish();
    }

    public void actionAddNewOrEditedItem(View view) {
        boolean boolDoubleProduct = false;
        for (Product product: arrListProducts)
            if (product.getName().equals(edtNameProduct.getText().toString()))
                boolDoubleProduct = true;

        if (edtNameProduct.getText().toString().equals("")) { //Перевірка на дурачка
            tvMessageErrors.setVisibility(View.VISIBLE);
        } else if (boolDoubleProduct) { //перевірка на повторні дані
            tvMessageErrors.setText(getString(R.string.tv_message_about_double_product));
            tvMessageErrors.setVisibility(View.VISIBLE);
        } else {
            actionsAddNewEditedItem();
        }
    }
}