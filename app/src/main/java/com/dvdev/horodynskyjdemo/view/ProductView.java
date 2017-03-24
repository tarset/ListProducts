package com.dvdev.horodynskyjdemo.view;

public interface ProductView {
    void setNamesActionBarButtonAddition();

    void setNamesActionBarButtonEdits();

    void sendingAdditionToParent();

    void sendingEditsToParent();

    void setProductEmptyError();

    void setProductDuplicateError();

    void setTitleActivity(String title);
}
