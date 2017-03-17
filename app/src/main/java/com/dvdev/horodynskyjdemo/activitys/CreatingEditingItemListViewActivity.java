package com.dvdev.horodynskyjdemo.activitys;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dvdev.horodynskyjdemo.R;
import com.dvdev.horodynskyjdemo.objects.Product;
import com.dvdev.horodynskyjdemo.objects.Products;
import com.dvdev.horodynskyjdemo.resurses.NameAndValueForExtraIntent;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class CreatingEditingItemListViewActivity extends AppCompatActivity {
    private boolean add = false; //індетифікатор для вибору дії кнопки підтвердження операції

    private EditText editNameItem;
    private TextView messageAboutEmpty;
    private Button buttonActionAddOrEditNewItem;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creating_editing_item_listview);

        initializationGlobalObject();



        String keyAction = getIntent().getStringExtra(NameAndValueForExtraIntent.KEY_FOR_SELECT_ACTION_ADD_OR_EDIT);
        if (keyAction.equals(NameAndValueForExtraIntent.VALUE_ACTION_ADD)) {
            add = true;
            setTitleActivity("Новий продукт");
            buttonActionAddOrEditNewItem.setText("Додати");

        } else if (keyAction.equals(NameAndValueForExtraIntent.VALUE_ACTION_EDIT)) {
            add = false;
            setTitleActivity("Редагування продукту");
            buttonActionAddOrEditNewItem.setText("Редагувати");
            editNameItem.setText(Products.data.get(Integer.parseInt(getIntent().
                    getStringExtra(NameAndValueForExtraIntent.KEY_POSITION_FOR_EDIT))).getName());
        }
    }

    private void setTitleActivity(String title) {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }

    private void initializationGlobalObject() {
        messageAboutEmpty = (TextView) findViewById(R.id.messageAboutEmpty);
        messageAboutEmpty.setVisibility(View.GONE);
        editNameItem = (EditText) findViewById(R.id.editNameItem);
        buttonActionAddOrEditNewItem = (Button) findViewById(R.id.confirm_action_new_or_edited_item);
    }

    public void actionCancelNewItem(View view) {
        finish();
    }

    public void actionAddNewOrEditedItem(View view) {
        if (!editNameItem.getText().toString().equals("")) { //Перевірка на дурачка
            if (add)
                Products.data.add(new Product(editNameItem.getText().toString(), false));
            else
                Products.data.get(Integer.parseInt(getIntent().
                        getStringExtra(NameAndValueForExtraIntent.KEY_POSITION_FOR_EDIT))).
                        setName(editNameItem.getText().toString());

            finish();
        } else
            messageAboutEmpty.setVisibility(View.VISIBLE);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("CreatingEditingItemListView Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }
}