package com.dvdev.horodynskyjdemo.controllers;

import com.dvdev.horodynskyjdemo.resources.Constants;
import com.dvdev.horodynskyjdemo.view.ProductView;

public class ProductControllerImpl implements ProductController {

    private ProductView productView;
    private Constants constants;

    private String nameProduct;
    private boolean errorEmpty = false;

    public ProductControllerImpl(ProductView productView) {
        this.productView = productView;
        constants = new Constants();
    }

    @Override
    public void setNamesActionBarButton(String keyAction) {
        if (keyAction.equals(constants.INTENT_EXTRA_VALUE_ACTION_ADD)) {
            productView.setNamesActionBarButtonAddition();
        } else if (keyAction.equals(constants.INTENT_EXTRA_VALUE_ACTION_EDIT)) {
            productView.setNamesActionBarButtonEdits();
        }
    }

    @Override
    public void calculationOfResults(String keyAction, String nameProduct) {
        this.nameProduct = nameProduct;

        checkProductEmpty();

        if (!errorEmpty)
            onSuccess();
    }

    @Override
    public void checkProductEmpty() {
        if (nameProduct.equals("")) {
            errorEmpty = true;
            onProductEmptyError();
        } else
            errorEmpty = false;
    }

    @Override
    public void onProductEmptyError() {
        productView.setProductEmptyError();
    }

    @Override
    public void onSuccess() {
            productView.sendingToParent();
    }
}
