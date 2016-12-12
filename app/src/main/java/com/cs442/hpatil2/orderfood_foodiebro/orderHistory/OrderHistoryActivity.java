package com.cs442.hpatil2.orderfood_foodiebro.orderHistory;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.cs442.hpatil2.orderfood_foodiebro.R;
import com.cs442.hpatil2.orderfood_foodiebro.constants.Constants;
import com.cs442.hpatil2.orderfood_foodiebro.helper.DBHelper;
import com.cs442.hpatil2.orderfood_foodiebro.homePage.MainActivity;
import com.cs442.hpatil2.orderfood_foodiebro.model.Dish;
import com.cs442.hpatil2.orderfood_foodiebro.model.Orders;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ArrayList<Orders> ordersList;
    OrderHistoryAdapter orderHistoryAdapter;
    ListView orderHistoryListView;
    ArrayList<Dish> menuItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        Intent intent = getIntent();
        menuItemList = intent.getParcelableArrayListExtra(Constants.MENU_ITEM_LIST);

        dbHelper = new DBHelper(this);
        ordersList = dbHelper.getAllOrders();
        FragmentManager fragmentManager = getFragmentManager();
        OrderHistoryFragment orderHistoryFragment = (OrderHistoryFragment) fragmentManager.findFragmentById(R.id.OrderHistoryFragment);
        orderHistoryAdapter = new OrderHistoryAdapter(this, R.layout.order_history_fragment, ordersList);
        orderHistoryFragment.setListAdapter(orderHistoryAdapter);
        orderHistoryListView = orderHistoryFragment.getListView();
        orderHistoryAdapter.notifyDataSetChanged();

        Button backButton = (Button)findViewById(R.id.BackButtonHistoryPage);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBackButton();
            }
        });
    }

    public void onClickBackButton(){

        Intent intent = new Intent(this, MainActivity.class);
        intent.putParcelableArrayListExtra(Constants.MENU_ITEM_LIST, menuItemList);
        startActivity(intent);
    }
}
