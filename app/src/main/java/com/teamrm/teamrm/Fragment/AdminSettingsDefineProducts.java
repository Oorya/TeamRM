package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

import com.teamrm.teamrm.Adapter.ProductAdapter;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSettingsDefineProducts extends Fragment {

    final static String TAG = ":::Settings:Products:::";
    public RecyclerView productView;
    protected List<Product> productList = new ArrayList<>();
    ProductAdapter productAdapter;
    FloatingActionButton floatBtn;
    
    
    public AdminSettingsDefineProducts() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_settings_define_generic, container, false);
        floatBtn = (FloatingActionButton) view.findViewById(R.id.floatBtn);
        floatBtn.hide();

        /*productList = UtlFirebase.getProducts(UserSingleton.getInstance().getUserCompany()); //TODO:fix this
        Log.d(TAG, "initDone true, list="+productList.toString());*/

        productList.add(new Product("100", "טוסטר משולשים"));
        productList.add(new Product("101", "מיטה זוגית"));
        productList.add(new Product("102", "שתיקת הכבשים"));
        productList.add(new Product("103", "קמחא דפסחא"));

        productView = (RecyclerView) view.findViewById(R.id.prefRecyclerView);
        productView.setLayoutManager(new LinearLayoutManager(getContext()));
        productAdapter = new ProductAdapter(getContext(), productList);
        productView.setAdapter(productAdapter);
        productView.postDelayed(new Runnable() {
            @Override
            public void run() {
                floatBtn.show();
                floatBtn.setScaleX(.25f);
                floatBtn.setScaleY(.25f);
                floatBtn.animate().scaleX(1).scaleY(1).setInterpolator(new BounceInterpolator()).start();
            }
        }, 250);

        return view;


    }

}
