package com.dvdev.horodynskyjdemo.controllers;

public interface ProductController {
    void setNamesActionBarButton(String keyAction);

    void calculationOfResults(String keyAction, String nameProduct);

    void checkProductEmpty();

    void onProductEmptyError();

    void onSuccess();
}
