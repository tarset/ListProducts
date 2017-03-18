package com.dvdev.horodynskyjdemo.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.dvdev.horodynskyjdemo.R;
import com.dvdev.horodynskyjdemo.objects.Product;
import com.dvdev.horodynskyjdemo.objects.Products;

public class ProductAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;

    public ProductAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Products.data.size();
    }

    @Override
    public Object getItem(int position) {
        return Products.data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = layoutInflater.inflate(R.layout.item, parent, false);

        Product product = Products.data.get(position);

        CheckBox cbProduct = (CheckBox) view.findViewById(R.id.stateItem);
        LinearLayout itemProducts = (LinearLayout) view.findViewById(R.id.item);

        cbProduct.setText(product.getName());
        //При активному чекбоксі закреслює його текст, в протилежному випадку робить звичайним
        if (product.isPruchased())
            cbProduct.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        else
            cbProduct.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
        cbProduct.setChecked(product.isPruchased());

        //При зміні стану чекбокса перезаписує його в об'єкт Product
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Products.data.get(position).isPruchased())
                    Products.data.get(position).setPruchased(false);
                else
                    Products.data.get(position).setPruchased(true);
                notifyDataSetChanged();
            }
        };

        itemProducts.setOnClickListener(onClickListener);
        cbProduct.setOnClickListener(onClickListener);

        return view;
    }
}
