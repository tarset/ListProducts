package com.dvdev.horodynskyjdemo.controllers;

import com.dvdev.horodynskyjdemo.R;
import com.dvdev.horodynskyjdemo.models.Product;
import com.dvdev.horodynskyjdemo.models.Products;
import com.dvdev.horodynskyjdemo.view.MainView;

import java.util.Collections;
import java.util.Comparator;

public class MainController {

    private MainView mainView;
    private Products products;
    private SortProductsController sortProductsController;
    private String toastMessageEmptyListProduct = "Cписок продуктів порожній ¯\\_(ツ)_/¯";
    private int positionForEdits;

    public MainController(MainView mainView, Products products) {
        this.mainView = mainView;
        this.products = products;
        this.products.convertWithJson(this.mainView.getListProductsWithStorage());
        sortProductsController = new SortProductsController();
    }

    public String getListProductsInJson() {
        return products.convertToJson();
    }

    public void onItemClick(int position) {
        if (products.getListProducts().get(position).isPruchased())
            products.getListProducts().get(position).setPruchased(false);
        else products.getListProducts().get(position).setPruchased(true);

        saveProducts();
    }

    public void onActivityResult(int requestCode, String nameProduct) {
        switch (requestCode) {
            case 1:
                products.getListProducts().add(new Product(nameProduct, false));
                break;
            case 2:
                products.getListProducts().get(positionForEdits).setName(nameProduct);
                break;
        }

        saveProducts();
    }

    public boolean onContextItemSelected(int id, int position) {
        positionForEdits = position;
        if (id == R.id.contextMenuEditProduct)
            mainView.intentProductActivityEdit(products.getListProducts().get(position).getName());
        if (id == R.id.contextMenuRemoveProduct)
            products.getListProducts().remove(position);

        saveProducts();
        return true;
    }

    public void onOptionsItemSelected(int id) {
        if (id == R.id.menuAdd)
            mainView.intentProductActivityAdd();
        if (id == R.id.sortAz)
            sortListProducts(sortProductsController.sortAZ());
        if (id == R.id.sortZa)
            sortListProducts(Collections.reverseOrder(sortProductsController.sortAZ()));
        if (id == R.id.sortPurchased)
            sortListProducts(sortProductsController.sortPurchased());
        if (id == R.id.sortNotPurchased)
            sortListProducts(Collections.reverseOrder(sortProductsController.sortPurchased()));
        if (id == R.id.menuClearList) {
            if (products.getListProducts().isEmpty())
                mainView.showToast(toastMessageEmptyListProduct);
            else for (Product product : products.getListProducts())
                if (product.isPruchased()) product.setPruchased(false);
        }
        if (id == R.id.menuNewList) {
            if (products.getListProducts().isEmpty())
                mainView.showToast(toastMessageEmptyListProduct);
            else products.getListProducts().clear();
        }

        saveProducts();
    }

    public void switchVisibleListProducts() {
        if (products.getListProducts().isEmpty()) mainView.setListViewGone();
        else mainView.setListViewVisible();
    }

    private void saveProducts() {
        switchVisibleListProducts();

        mainView.updateAdapter();
        mainView.saveProductsInStorage();
    }

    private void sortListProducts(Comparator<? super Product> comparator) {
        if (products.getListProducts().isEmpty()) mainView.showToast(toastMessageEmptyListProduct);
        else Collections.sort(products.getListProducts(), comparator);
    }
}
