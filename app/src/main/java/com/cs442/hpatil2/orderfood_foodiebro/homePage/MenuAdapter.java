package com.cs442.hpatil2.orderfood_foodiebro.homePage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cs442.hpatil2.orderfood_foodiebro.R;
import com.cs442.hpatil2.orderfood_foodiebro.model.Dish;
import com.cs442.hpatil2.orderfood_foodiebro.productPage.ItemDetailsActivity;

import java.util.List;

/**
 * Created by HarshPatil on 10/2/16.
 */
public class MenuAdapter extends ArrayAdapter<Dish> {

    int resource;
    Context context;

    public MenuAdapter(Context context, int resource, List<Dish> menuList){

        super(context, resource, menuList);
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LinearLayout dishView;
        final Dish dish = getItem(position);

        String name = dish.getName();
        float price = dish.getPrice();
        int qty = dish.getQty();
        String qtyPrice = "Qty:" + qty + " x $" + price;
        int itemId = dish.getItemId();
        int imageId = dish.getImageId();

        if(convertView == null){
            dishView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(inflater);
            layoutInflater.inflate(resource, dishView, true);
        }else {
            dishView = (LinearLayout) convertView;
        }

        TextView countView = (TextView)dishView.findViewById(R.id.itemNumber);
        TextView dishName = (TextView)dishView.findViewById(R.id.itemName);
        TextView dishQtyAndPrice = (TextView)dishView.findViewById(R.id.itemQtyAndPrice);

        ImageView imageView = (ImageView)dishView.findViewById(R.id.dishImage);
        imageView.setImageResource(imageId);

//        final CheckBox checkBox = (CheckBox)dishView.findViewById(R.id.checkBox);
//        checkBox.setOnCheckedChangeListener((MainActivity)context);
//        checkBox.post(new Runnable() {
//            @Override
//            public void run() {
//                checkBox.setChecked(dish.isSelected());
//            }
//        });

        View view = dishView.findViewById(R.id.detailsButtonHomePage);
        Button qtyButton = (Button)dishView.findViewById(R.id.detailsButtonHomePage);
        qtyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)context).onClickQtyButton(position);
            }
        });

        dishQtyAndPrice.setText(qtyPrice);
        dishName.setText(name);
        countView.setText(String.valueOf(itemId));

        return dishView;
    }
}
