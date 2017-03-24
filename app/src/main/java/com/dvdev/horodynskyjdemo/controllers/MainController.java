package com.dvdev.horodynskyjdemo.controllers;

public interface MainController {
    void onItemClick(int position);

    boolean onContextItemSelected(int id, int position);

    void switchVisibleListProducts();

    void onOptionsItemSelected(int id);

    void onActivityResult(int requestCode, String nameProduct);
}
