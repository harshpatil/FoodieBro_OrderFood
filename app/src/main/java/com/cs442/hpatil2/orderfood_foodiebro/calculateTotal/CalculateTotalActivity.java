package com.cs442.hpatil2.orderfood_foodiebro.calculateTotal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.cs442.hpatil2.orderfood_foodiebro.constants.Constants;
import com.cs442.hpatil2.orderfood_foodiebro.homePage.MainActivity;
import com.cs442.hpatil2.orderfood_foodiebro.model.Dish;

import java.util.ArrayList;

/**
 * Created by HarshPatil on 10/2/16.
 */
/*
 * This Activity will started to calculate Total amount and return value
 */
public class CalculateTotalActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        float total = calculateTotal(intent.getParcelableArrayListExtra(Constants.MENU_ITEM_LIST));
        intent.putExtra(Constants.TOTAL_AMOUNT, total);
        setResult(RESULT_OK,intent);
        finish();
    }

    private float calculateTotal(ArrayList<Parcelable> menuList) {
        float totalAmount = 0;
        for(int i=0; i<menuList.size(); i++){
            Dish dish = (Dish) menuList.get(i);
            totalAmount = totalAmount + dish.getPrice() * dish.getQty();
        }
        return totalAmount;
    }
}
