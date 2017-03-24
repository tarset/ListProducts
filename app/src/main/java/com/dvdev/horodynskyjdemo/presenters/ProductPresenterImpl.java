package com.dvdev.horodynskyjdemo.presenters;

import com.dvdev.horodynskyjdemo.models.Product;
import com.dvdev.horodynskyjdemo.resources.Constants;
import com.dvdev.horodynskyjdemo.view.ProductView;

import java.util.ArrayList;

public class ProductPresenterImpl implements ProductPresenter {

    private ProductView productView;
    private Constants constants;

    private ArrayList<Product> productsArrayList;
    private String nameProduct;
    private boolean errorEmpty = false;
    private boolean errorDuplicate = false;

    public ProductPresenterImpl(ProductView productView) {
        this.productView = productView;
        constants = new Constants();

        productsArrayList = new ArrayList<>();
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
    public void calculationOfResults(String keyAction, ArrayList<Product> productsArrayList, String nameProduct) {
        this.nameProduct = nameProduct;
        this.productsArrayList = productsArrayList;

        checkProductEmpty();
        checkProductDuplicate();

        if (!errorEmpty && !errorDuplicate)
            onSuccess(keyAction);
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
    public void checkProductDuplicate() {
        for (Product product : productsArrayList)
            if (product.getName().equals(nameProduct)) {
                errorDuplicate = true;
                onProductDuplicateError();
                break;
            } else
                errorDuplicate = false;
    }

    @Override
    public void onProductEmptyError() {
        productView.setProductEmptyError();
    }

    @Override
    public void onProductDuplicateError() {
        productView.setProductDuplicateError();
    }

    @Override
    public void onSuccess(String keyAction) {
        if (keyAction.equals(constants.INTENT_EXTRA_VALUE_ACTION_ADD))
            productView.sendingToParentAddition();
        if (keyAction.equals(constants.INTENT_EXTRA_VALUE_ACTION_EDIT))
            productView.sendingToParentEdits();
    }
}
