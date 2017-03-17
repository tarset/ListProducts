package com.dvdev.horodynskyjdemo.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.dvdev.horodynskyjdemo.R;
import com.dvdev.horodynskyjdemo.objects.Product;
import com.dvdev.horodynskyjdemo.objects.Products;

public class AdapterForItemsProductInListView extends BaseAdapter {
    private LayoutInflater layoutInflater;

    public AdapterForItemsProductInListView(Context context) {
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

        CheckBox stateItem = (CheckBox) view.findViewById(R.id.stateItem);

        stateItem.setText(product.getName());
        //При активному чекбоксі закреслює його текст, в протилежному випадку робить звичайним
        if (product.isPruchased())
            stateItem.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        else
            stateItem.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
        stateItem.setChecked(product.isPruchased());

        stateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Перезаписує дійсний стан чекбокса при його зміні в об'єкт Product
                if (Products.data.get(position).isPruchased())
                    Products.data.get(position).setPruchased(false);
                else
                    Products.data.get(position).setPruchased(true);
                notifyDataSetChanged();
            }
        });

        return view;
    }

}
