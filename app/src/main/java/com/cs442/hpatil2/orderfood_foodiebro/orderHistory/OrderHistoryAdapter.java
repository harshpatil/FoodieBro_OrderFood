package com.cs442.hpatil2.orderfood_foodiebro.orderHistory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cs442.hpatil2.orderfood_foodiebro.R;
import com.cs442.hpatil2.orderfood_foodiebro.model.Orders;

import java.util.List;

/**
 * Created by HarshPatil on 10/6/16.
 */
public class OrderHistoryAdapter extends ArrayAdapter<Orders> {

    Context context;
    int resource;

    public OrderHistoryAdapter(Context context, int resource, List<Orders> ordersList){

        super(context, resource, ordersList);
        this.context = context;
        this.resource = resource;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        RelativeLayout ordersView;
        final Orders orders = getItem(position);

        if(convertView == null){
            ordersView = new RelativeLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater layoutInflater = (LayoutInflater)getContext().getSystemService(inflater);
            layoutInflater.inflate(resource, ordersView, true);
        }
        else {
            ordersView = (RelativeLayout) convertView;
        }

        TextView displayOrderItems = (TextView)ordersView.findViewById(R.id.ItemIdsHistoryPage);
        TextView displayOrderDate = (TextView)ordersView.findViewById(R.id.DateOfOrderHistoryPage);
        TextView displayOrderAmount = (TextView)ordersView.findViewById(R.id.TotalOrderAmountHistoryPage);
        TextView displayOrderItemNames = (TextView)ordersView.findViewById(R.id.ItemNamesHistoryPage);

        displayOrderDate.setText("Date : "+ orders.getDate());
        displayOrderItems.setText("Item Ids : "+ orders.getItemIds());
        displayOrderAmount.setText("Amount : $"+ String.valueOf(orders.getTotalOrderAmount()));
        displayOrderItemNames.setText("Items : " + orders.getNames());

        return ordersView;
    }
}
