package com.dvdev.horodynskyjdemo.view;

public interface ProductView {
    void setNamesActionBarButtonAddition();

    void setNamesActionBarButtonEdits();

    void sendingToParentAddition();

    void sendingToParentEdits();

    void setProductEmptyError();

    void setProductDuplicateError();

    void setTitleActivity(String title);
}
