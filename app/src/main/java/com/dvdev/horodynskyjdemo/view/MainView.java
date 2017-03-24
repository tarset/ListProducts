package com.dvdev.horodynskyjdemo.view;

public interface MainView {
    void saveProductsInStorage();

    void showToast(String toastMessage);

    void updateAdapter();

    void intentProductActivityEdit(int position);

    void intentProductActivityAdd();

    String getListProductsWithStorage();

    void setListProductsInStorage(String json);

    void setListViewVisible();

    void setListViewGone();
}
