package com.dvdev.horodynskyjdemo.controllers;

import com.dvdev.horodynskyjdemo.R;
import com.dvdev.horodynskyjdemo.models.Product;
import com.dvdev.horodynskyjdemo.models.Products;
import com.dvdev.horodynskyjdemo.view.MainView;

import java.util.Collections;
import java.util.Comparator;

public class MainControllerImpl implements MainController {
    private MainView mainView;
    private Products products;
    private SortProductsController sortProductsController;
    private String toastMessageEmptyListProduct = "Cписок продуктів порожній ¯\\_(ツ)_/¯";
    private String toastMessageDuplicateNameInListProduct = "Такий продукт вже є у списку";
    private int positionForEdits;

    public MainControllerImpl(MainView mainView, Products products) {
        this.mainView = mainView;
        this.products = products;
        sortProductsController = new SortProductsController();
    }

    @Override public void onItemClick(int position) {
        if (products.getListProducts().get(position).isPruchased())
            products.getListProducts().get(position).setPruchased(false);
        else products.getListProducts().get(position).setPruchased(true);

        mainView.updateAdapter();
        mainView.saveProductsInStorage();
    }

    @Override public boolean onContextItemSelected(int id, int position) {
        switch (id) {
            case R.id.contextMenuEditProduct:
                positionForEdits = position;
                mainView.intentProductActivityEdit(position);

                saveProducts();
                return true;
            case R.id.contextMenuRemoveProduct:
                products.getListProducts().remove(position);

                saveProducts();
                return true;
        }
        return false;
    }

    @Override
    public void switchVisibleListProducts() {
        if (products.getListProducts().isEmpty()) mainView.setListViewGone();
        else mainView.setListViewVisible();
    }

    private void saveProducts() {
        switchVisibleListProducts();

        mainView.updateAdapter();
        mainView.saveProductsInStorage();
    }

    @Override public void onOptionsItemSelected(int id) {
        switch (id) {
            case R.id.menuAdd:
                mainView.intentProductActivityAdd();
                break;
            case R.id.menuClearList:
                if (products.getListProducts().isEmpty())
                    mainView.showToast(toastMessageEmptyListProduct);
                else for (Product product : products.getListProducts())
                    if (product.isPruchased()) product.setPruchased(false);
                break;
            case R.id.menuNewList:
                if (products.getListProducts().isEmpty())
                    mainView.showToast(toastMessageEmptyListProduct);
                else products.getListProducts().clear();
                break;
            case R.id.sortAz:
                sortListProducts(sortProductsController.sortAZ());
                break;
            case R.id.sortZa:
                sortListProducts(Collections.reverseOrder(sortProductsController.sortAZ()));
                break;
            case R.id.sortPurchased:
                sortListProducts(sortProductsController.sortPurchased());
                break;
            case R.id.sortNotPurchased:
                sortListProducts(Collections.reverseOrder(sortProductsController.sortPurchased()));
                break;
        }

        saveProducts();
    }

    @Override public void onActivityResult(int requestCode, String nameProduct) {
        boolean duplicate = false;
        for (Product s : products.getListProducts())
            if (s.getName().equals(nameProduct)) {
                mainView.showToast(toastMessageDuplicateNameInListProduct);
                duplicate = true;
                break;
            }

        if (!duplicate)
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

    private void sortListProducts(Comparator<? super Product> comparator) {
        if (products.getListProducts().isEmpty()) mainView.showToast(toastMessageEmptyListProduct);
        else Collections.sort(products.getListProducts(), comparator);
    }
}
