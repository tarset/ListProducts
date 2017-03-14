package com.dvdev.horodynskyjdemo.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.dvdev.horodynskyjdemo.R;
import com.dvdev.horodynskyjdemo.activitys.MainActivity;
import com.dvdev.horodynskyjdemo.objects.Item;

public class ListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public ListAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return MainActivity.data.size();
    }

    @Override
    public Object getItem(int position) {
        return MainActivity.data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = layoutInflater.inflate(R.layout.item, parent, false);

        Item item = MainActivity.data.get(position);

        TextView nameItem = (TextView) view.findViewById(R.id.nameItem);
        CheckBox stateItem = (CheckBox) view.findViewById(R.id.stateItem);

        nameItem.setText(item.getName());
        if (item.isPruchased())
            nameItem.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        else
            nameItem.setPaintFlags(Paint.LINEAR_TEXT_FLAG);
        stateItem.setChecked(item.isPruchased());

        //TODO: Відрефакторити цих слухачів
        nameItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.data.get(position).isPruchased())
                    MainActivity.data.get(position).setPruchased(false);
                else
                    MainActivity.data.get(position).setPruchased(true);
                updateResults();
            }
        });

        stateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.data.get(position).isPruchased())
                    MainActivity.data.get(position).setPruchased(false);
                else
                    MainActivity.data.get(position).setPruchased(true);
                updateResults();
            }
        });
        return view;
    }

    private Item getItemList(int position) {
        return ((Item) getItem(position));
    }

    public void updateResults() {
        notifyDataSetChanged();
    }
}
