package com.teamrm.teamrm.Fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;

import com.teamrm.teamrm.Adapter.CategoryAdapter;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSettingsDefineCategory extends Fragment {

    final static String TAG = ":::Settings:Category:::";
    public RecyclerView categoryView;
    protected List<Category> categoryList = new ArrayList<>();
    CategoryAdapter categoryAdapter;
    FloatingActionButton floatBtn;

    public AdminSettingsDefineCategory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_settings_define_category, container, false);
        floatBtn = (FloatingActionButton) view.findViewById(R.id.floatBtn);
        floatBtn.hide();

        /*categoryList = UtlFirebase.getCategories(UserSingleton.getInstance().getUserCompany()); //TODO:fix this
        Log.d(TAG, "initDone true, list="+categoryList.toString());*/

        categoryList.add(new Category("100", "תקלה"));
        categoryList.add(new Category("101", "התקנה"));
        categoryList.add(new Category("102", "הסרה"));
        categoryList.add(new Category("103", "איסוף"));



        categoryView = (RecyclerView) view.findViewById(R.id.categoryRecyclerView);
        categoryView.setLayoutManager(new LinearLayoutManager(getContext()));
        categoryAdapter = new CategoryAdapter(getContext(), categoryList);
        categoryView.setAdapter(categoryAdapter);
        categoryView.postDelayed(new Runnable() {
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
