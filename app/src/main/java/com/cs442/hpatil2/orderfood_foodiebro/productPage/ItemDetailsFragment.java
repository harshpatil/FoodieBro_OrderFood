package com.cs442.hpatil2.orderfood_foodiebro.productPage;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs442.hpatil2.orderfood_foodiebro.homePage.MainActivity;
import com.cs442.hpatil2.orderfood_foodiebro.model.Dish;
import com.cs442.hpatil2.orderfood_foodiebro.R;

/**
 * Created by HarshPatil on 10/2/16.
 */
public class ItemDetailsFragment extends Fragment{
    View rootView;

    public ItemDetailsFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         rootView = inflater.inflate(R.layout.item_details_view, container, false);
        return rootView;
    }

    public void updateContent(Dish item,final int position) {

        TextView nameDetailsPage = (TextView) rootView.findViewById(R.id.NameDetailsPage);
        TextView priceDetailsPage = (TextView) rootView.findViewById(R.id.PriceDetailsPage);
        TextView descriptionDetailsPage = (TextView) rootView.findViewById(R.id.DescriptionDetailsPage);
        ImageView imageDetailsPage = (ImageView) rootView.findViewById(R.id.ImageDetailsPage);

        nameDetailsPage.setText(item.getName());
        priceDetailsPage.setText(" x Price : $" + item.getPrice());
        descriptionDetailsPage.setText("Description : " + item.getDescription());
        imageDetailsPage.setImageResource(item.getImageId());
        Button button = (Button) rootView.findViewById(R.id.BackButton);

        final EditText editText = (EditText) rootView.findViewById(R.id.EditQtyDetailsPage);
        editText.setText(String.valueOf(item.getQty()));
        editText.setSelection(editText.getText().length());

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

               /* if(editText.getText().toString().length() != 0) {
                    dishList.get(position - 1).setQty(Integer.parseInt(editText.getText().toString()));
                }
                else
                    dishList.get(position - 1).setQty(0);*/
                Toast.makeText(getActivity(), "Quantity of has been added", Toast.LENGTH_LONG).show();
                ((MainActivity)getActivity()).updateQuantity(editText.getText().toString(),position);
            }
        });
    }
}
