package com.dvdev.horodynskyjdemo.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dvdev.horodynskyjdemo.R;
import com.dvdev.horodynskyjdemo.models.Product;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<Product> arrListProducts;

    public ProductAdapter(Context context, ArrayList<Product> productsArrayList) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrListProducts = productsArrayList;
    }

    @Override
    public int getCount() {
        return arrListProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return arrListProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = layoutInflater.inflate(R.layout.custom_item_product, parent, false);

        Product product = arrListProducts.get(position);

        CheckBox chBoxProduct = (CheckBox) view.findViewById(R.id.chBoxStateProduct);
        LinearLayout linLayProduct = (LinearLayout) view.findViewById(R.id.linLayProduct);
        TextView tvProduct = (TextView) view.findViewById(R.id.tvNameProduct);

        tvProduct.setText(product.getName());
        //При активному чекбоксі закреслює його текст, в протилежному випадку робить звичайним
        if (product.isPruchased())
            tvProduct.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        else
            tvProduct.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
        chBoxProduct.setChecked(product.isPruchased());

        //При зміні стану чекбокса перезаписує його в об'єкт Product
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrListProducts.get(position).isPruchased())
                    arrListProducts.get(position).setPruchased(false);
                else
                    arrListProducts.get(position).setPruchased(true);
                notifyDataSetChanged();
            }
        };

        linLayProduct.setOnClickListener(onClickListener);
        chBoxProduct.setOnClickListener(onClickListener);
        tvProduct.setOnClickListener(onClickListener);

        return view;
    }

    public void update(ArrayList<Product> productsArrayList) {
        this.arrListProducts = productsArrayList;
        notifyDataSetChanged();
    }
}
