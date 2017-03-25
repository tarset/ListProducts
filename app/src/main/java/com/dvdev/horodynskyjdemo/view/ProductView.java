package com.dvdev.horodynskyjdemo.view;

public interface ProductView {
    void setCalculationOfResults();

    void setNamesActionBarButtonAddition();

    void setNamesActionBarButtonEdits();

    void sendingToParent();

    void setProductEmptyError();

    void setProductDuplicateError();

    void setTitleActivity(String title);

    void finishActivity();
}
