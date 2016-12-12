package com.cs442.hpatil2.orderfood_foodiebro.checkout;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs442.hpatil2.orderfood_foodiebro.constants.Constants;
import com.cs442.hpatil2.orderfood_foodiebro.helper.DBHelper;
import com.cs442.hpatil2.orderfood_foodiebro.homePage.MainActivity;
import com.cs442.hpatil2.orderfood_foodiebro.model.Dish;
import com.cs442.hpatil2.orderfood_foodiebro.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CheckoutActivity extends AppCompatActivity {

    int position;
    ArrayList<Dish> cartItemList;
    CheckoutAdapter checkoutAdapter;
    ListView checkoutListView;
    float totalAmount;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);

        setContentView(R.layout.activity_checkout);

        Intent intent = getIntent();
        cartItemList = intent.getParcelableArrayListExtra(Constants.CART_ITEM_LIST);
        totalAmount = intent.getFloatExtra(Constants.TOTAL_AMOUNT, 1);

        FragmentManager fragmentManager = getFragmentManager();
        CheckoutFragment checkoutFragment = (CheckoutFragment) fragmentManager.findFragmentById(R.id.CheckoutFragment);
        checkoutAdapter = new CheckoutAdapter(this, R.layout.checkout_fragment, cartItemList);
        checkoutFragment.setListAdapter(checkoutAdapter);
        checkoutListView = checkoutFragment.getListView();
        checkoutAdapter.notifyDataSetChanged();

        TextView displayTotalAmount = (TextView) findViewById(R.id.TotalAmountInCart);
        String totalAmountText = "Total Amount = $" + String.valueOf(totalAmount);
        displayTotalAmount.setText(totalAmountText);

        Button placeOrderButton = (Button)findViewById(R.id.PlaceOderButtonCheckoutPage);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPlaceOrderButton();
            }
        });
    }

    /*
     * This method will store order data into sqlLite database
     */
    public void onClickPlaceOrderButton(){

//        dbHelper.deleteDatabaseAndTable();
//        SQLiteDatabase foodieBroDatabase = openOrCreateDatabase(Constants.DATABASE_NAME, Context.MODE_PRIVATE, null);
//        dbHelper.onCreate(foodieBroDatabase);
        String itemIds ="#";
        String itemNames = "";
        for(int i=0; i<cartItemList.size(); i++){
            itemIds = itemIds + cartItemList.get(i).getItemId();
            itemNames = itemNames + cartItemList.get(i).getName();
            if(i!=cartItemList.size()-1){
                itemIds = itemIds+", #";
                itemNames = itemNames + ", ";
            }
        }

        long currentTime = System.currentTimeMillis();
        Date currentDate = new Date(currentTime);
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyy HH:mm:ss");
        String dateString = format.format(currentDate);

        dbHelper.insertOrder(itemIds, itemNames, dateString, totalAmount);

        Toast.makeText(checkoutAdapter.getContext(), "Your order has been placed !!!! \n" + "Total amount : $" + totalAmount, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
