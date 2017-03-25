package com.dvdev.horodynskyjdemo.controllers;

import com.dvdev.horodynskyjdemo.R;
import com.dvdev.horodynskyjdemo.models.Product;
import com.dvdev.horodynskyjdemo.models.Products;
import com.dvdev.horodynskyjdemo.resources.Constants;
import com.dvdev.horodynskyjdemo.view.ProductView;

public class ProductController {

    private ProductView productView;
    private Constants constants;
    private Products products;

    public ProductController(ProductView productView) {
        this.productView = productView;
        products = new Products();
        constants = new Constants();
    }

    public void onClick(int id) {
        if (id == R.id.btnProductCancel)
            productView.finishActivity();
        if (id == R.id.btnProductValid)
            productView.setCalculationOfResults();
    }

    public void setNamesActionBarButton(String keyAction) {
        if (keyAction.equals(constants.INTENT_EXTRA_VALUE_ACTION_ADD)) {
            productView.setNamesActionBarButtonAddition();
        } else if (keyAction.equals(constants.INTENT_EXTRA_VALUE_ACTION_EDIT)) {
            productView.setNamesActionBarButtonEdits();
        }
    }

    public void calculationOfResults(String listProductsInJson, String nameProduct) {
        products.convertWithJson(listProductsInJson);

        boolean errorEmpty = checkProductEmpty(nameProduct);
        boolean errorDuplicate = checkProductDuplicate(nameProduct);

        if (!errorEmpty && !errorDuplicate)
            onSuccess();
    }

    private boolean checkProductEmpty(String nameProduct) {
        if (nameProduct.equals("")) {
            onProductEmptyError();
            return true;
        }
        return false;
    }

    private boolean checkProductDuplicate(String nameProduct) {
        for (Product s : products.getListProducts())
            if (s.getName().equals(nameProduct)) {
                onProductDuplicateError();
                return true;
            }
        return false;
    }

    private void onProductEmptyError() {
        productView.setProductEmptyError();
    }

    private void onProductDuplicateError() {
        productView.setProductDuplicateError();
    }

    private void onSuccess() {
        productView.sendingToParent();
    }
}
