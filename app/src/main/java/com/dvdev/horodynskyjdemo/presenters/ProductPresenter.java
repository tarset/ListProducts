package com.dvdev.horodynskyjdemo.presenters;

import com.dvdev.horodynskyjdemo.models.Product;

import java.util.ArrayList;

public interface ProductPresenter {
    void setNamesActionBarButton(String keyAction);

    void calculationOfResults(String keyAction, ArrayList<Product> productsArrayList, String nameProduct);

    void checkProductEmpty();

    void checkProductDuplicate();

    void onProductEmptyError();

    void onProductDuplicateError();

    void onSuccess(String keyAction);
}
