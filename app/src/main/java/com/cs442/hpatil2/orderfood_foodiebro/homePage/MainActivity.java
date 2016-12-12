package com.cs442.hpatil2.orderfood_foodiebro.homePage;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs442.hpatil2.orderfood_foodiebro.constants.Constants;
import com.cs442.hpatil2.orderfood_foodiebro.model.Dish;
import com.cs442.hpatil2.orderfood_foodiebro.R;
import com.cs442.hpatil2.orderfood_foodiebro.calculateTotal.CalculateTotalActivity;
import com.cs442.hpatil2.orderfood_foodiebro.checkout.CheckoutActivity;
import com.cs442.hpatil2.orderfood_foodiebro.orderHistory.OrderHistoryActivity;
import com.cs442.hpatil2.orderfood_foodiebro.productPage.ItemDetailsActivity;
import com.cs442.hpatil2.orderfood_foodiebro.productPage.ItemDetailsFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Dish> menuList = new ArrayList<Dish>();
    private MenuAdapter menuAdapter;
    int itemId = 1;
    ListView menuListView;
    float totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        if(intent.getParcelableArrayListExtra(Constants.MENU_ITEM_LIST) !=null){
            menuList = intent.getParcelableArrayListExtra(Constants.MENU_ITEM_LIST);

        }else{
            fetchData();
            ItemDetailsFragment detailsFragment = (ItemDetailsFragment) getFragmentManager().findFragmentById(R.id.ItemDetailsFragment);
            if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT && detailsFragment!=null) {
                detailsFragment.updateContent(menuList.get(0),0);
            }
        }

        FragmentManager fragmentManager = getFragmentManager();
        MenuListFragment menuListFragment = (MenuListFragment) fragmentManager.findFragmentById(R.id.MenuListFragment);
        menuAdapter = new MenuAdapter(this, R.layout.menu_list_view, menuList);
        menuListFragment.setListAdapter(menuAdapter);
        menuListView = menuListFragment.getListView();
        menuAdapter.notifyDataSetChanged();

        // Used start activity for Result in this method
        calculateTotal();

        Button checkoutButton = (Button) findViewById(R.id.ConfirmButton);
        checkoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickCheckoutButton();
            }
        });

        Button historyButton = (Button)findViewById(R.id.historyButton);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickHistoryButton();
            }
        });
    }

    public void calculateTotal(){

        Intent intent = new Intent(this, CalculateTotalActivity.class);
        intent.putParcelableArrayListExtra(Constants.MENU_ITEM_LIST, menuList);
        // Starting activity to calculate total amount and return
        startActivityForResult(intent,1);
        menuAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            float total = data.getFloatExtra(Constants.TOTAL_AMOUNT,0);
            TextView displayTotalAmount = (TextView)findViewById(R.id.TotalAmount);
            String displayQtyAndPrice = "Total Amount = $" + total;
            displayTotalAmount.setText(displayQtyAndPrice);
            totalAmount = total;
        }
    }
    public void onClickCheckoutButton(){

        boolean proceed = false;
        ArrayList<Dish> itemsInCart = new ArrayList<Dish>();
        for(int i=0; i<menuList.size(); i++){
            if(menuList.get(i).getQty() > 0){
                itemsInCart.add(menuList.get(i));
                proceed = true;
            }
        }
        if(proceed == true){
            Intent intent = new Intent(this, CheckoutActivity.class);
            intent.putParcelableArrayListExtra(Constants.CART_ITEM_LIST ,itemsInCart);
            intent.putExtra(Constants.TOTAL_AMOUNT, totalAmount);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Pleasea add quantity by clicking > ", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickHistoryButton(){

        Intent intent = new Intent(this, OrderHistoryActivity.class);
        intent.putParcelableArrayListExtra(Constants.MENU_ITEM_LIST, menuList);
        startActivity(intent);
    }

    public void onClickQtyButton(int position){
        ItemDetailsFragment detailsFragment = (ItemDetailsFragment) getFragmentManager().findFragmentById(R.id.ItemDetailsFragment);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT || detailsFragment==null) {
            Intent intent = new Intent(this, ItemDetailsActivity.class);
            intent.putExtra(Constants.MENU_NAME, menuAdapter.getItem(position).getName());
            intent.putExtra(Constants.MENU_DESCRIPTION, menuAdapter.getItem(position).getDescription());
            intent.putExtra(Constants.MENU_PRICE, menuAdapter.getItem(position).getPrice());
            intent.putExtra(Constants.MENU_IMAGE_ID, menuAdapter.getItem(position).getImageId());
            intent.putExtra(Constants.MENU_ITEM_QTY, menuAdapter.getItem(position).getQty());
            intent.putExtra(Constants.MENU_ITEM_POS, menuAdapter.getItem(position).getItemId());
            intent.putParcelableArrayListExtra(Constants.MENU_ITEM_LIST, menuList);
            startActivity(intent);
        }else{
            detailsFragment.updateContent(menuAdapter.getItem(position),position);
        }
    }

    public void updateQuantity(String newQuantity,int position){
        int qty = 0 ;
        if(newQuantity.length()>0){
            qty = Integer.parseInt(newQuantity);
        }
        menuAdapter.getItem(position).setQty(qty);
        calculateTotal();
        menuAdapter.notifyDataSetChanged();

    }

    public void fetchData(){
        Dish menu1 = new Dish("Dosa",20.25f,"Rice, Potato, Ghee \n \n Taste:Medium Spicy", itemId++, R.drawable.dosa, 0);
        menuList.add(menuList.size(), menu1);
        Dish menu2 = new Dish("Burger",10.5f,"Bun, Potato Patty, Cheese \n \nTaste:Medium Spicy", itemId++, R.drawable.burger, 0);
        menuList.add(menuList.size(), menu2);
        Dish menu8 = new Dish("Wedges",4.25f,"Potato, Salt, Oil \n \nTaste:Medium Spicy", itemId++, R.drawable.wedges, 0);
        menuList.add(menuList.size(), menu8);
        Dish menu3 = new Dish("Pohe",5.75f,"Pohe, Onion, Groundnut \n \nTaste:Medium Spicy", itemId++, R.drawable.pohe, 0);
        menuList.add(menuList.size(), menu3);
        Dish menu9 = new Dish("Samosa",2.5f,"Potato, Green Peas, Maida \n \nTaste:Medium Spicy", itemId++, R.drawable.samosa, 0);
        menuList.add(menuList.size(), menu9);
        Dish menu5 = new Dish("Biriyani",6.25f,"Rice, Spices, Vegetables \n \nTaste:Very Spicy", itemId++, R.drawable.biriyani, 0);
        menuList.add(menuList.size(), menu5);
        Dish menu6 = new Dish("Jalebi",1.5f,"Maida, Sugar, Kesar \n \nTaste:Sweet", itemId++, R.drawable.jalebi, 0);
        menuList.add(menuList.size(), menu6);
        Dish menu7 = new Dish("Jaamun",2.25f,"Maida, Sugar, Ghee \n \nTaste:Sweet", itemId++, R.drawable.jaamun, 0);
        menuList.add(menuList.size(), menu7);
        Dish menu10 = new Dish("Bhakarwadi",2.00f,"Maida, Sugar, Red Chilli, Sesame Seeds \n \nTaste:Medium Spicy", itemId++, R.drawable.bhakarwadi, 0);
        menuList.add(menuList.size(), menu10);
        Dish menu4 = new Dish("Paneer",11.5f,"Paneer, Indian spices, Tomato \n \nTaste:Medium Spicy", itemId++, R.drawable.paneer, 0);
        menuList.add(menuList.size(), menu4);
    }
}
