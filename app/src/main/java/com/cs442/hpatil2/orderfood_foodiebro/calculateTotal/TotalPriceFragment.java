package com.cs442.hpatil2.orderfood_foodiebro.calculateTotal;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs442.hpatil2.orderfood_foodiebro.R;

/**
 * Created by HarshPatil on 10/2/16.
 */
public class TotalPriceFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.place_order_fragment, container, false);
        return rootView;
    }

}
