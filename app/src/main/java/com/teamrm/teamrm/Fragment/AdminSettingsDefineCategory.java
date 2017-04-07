package com.teamrm.teamrm.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.teamrm.teamrm.Adapter.CategoryAdapter;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Type.Region;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdminSettingsDefineCategory extends Fragment implements FireBaseAble{

    final static String TAG = ":::Settings:Categories:::";
    List<Category> categoryList = new ArrayList<>();
    public RecyclerView categoryView;
    CategoryAdapter categoryAdapter;
    FloatingActionButton floatBtn;


    public AdminSettingsDefineCategory() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        UtlFirebase.getCategoriesForEdit(UserSingleton.getInstance().getAssignedCompanyID(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_settings_define_generic, container, false);
        floatBtn = (FloatingActionButton) view.findViewById(R.id.floatBtn);
        floatBtn.hide();
        categoryView = (RecyclerView) view.findViewById(R.id.prefRecyclerView);
        categoryView.setLayoutManager(new LinearLayoutManager(getContext()));


        categoryView.postDelayed(new Runnable() {
            @Override
            public void run() {
                floatBtn.show();
                floatBtn.setScaleX(.25f);
                floatBtn.setScaleY(.25f);
                floatBtn.animate().scaleX(1).scaleY(1).setInterpolator(new BounceInterpolator()).start();
            }
        }, 250);

        floatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCategory();
            }
        });

        return view;
    }

    private void addCategory() {
        new MaterialDialog.Builder(getContext())
                .title(R.string.label_add_category)
                .input("", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        //Toast.makeText(getContext(),  input.toString(), Toast.LENGTH_SHORT).show();
                        UtlFirebase.addCategory(UserSingleton.getInstance().getAssignedCompanyID(), input.toString());
                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        //Toast.makeText(getContext(), "positive", Toast.LENGTH_SHORT).show();
                    }
                })
                .inputRange(1, 24, Color.RED)
                .positiveText(R.string.label_button_save)
                .contentColorRes(R.color.textColor_primary)
                .contentGravity(GravityEnum.CENTER)
                .negativeText(R.string.label_button_cancel)
                .titleGravity(GravityEnum.END)
                .buttonsGravity(GravityEnum.END)
                .backgroundColorRes(R.color.app_bg)
                .titleColorRes(R.color.textColor_lighter)
                .positiveColorRes(R.color.colorPrimary)
                .negativeColorRes(R.color.colorPrimaryDark)
                .dividerColorRes(R.color.textColor_lighter)
                .show();
    }

    @Override
    public void resultTicket(Ticket ticket) {

    }

    @Override
    public void resultUser(Users user) {

    }

    @Override
    public void ticketListCallback(List<Ticket> tickets) {

    }

    @Override
    public void ticketLiteListCallback(List<TicketLite> ticketLites) {

    }

    @Override
    public void resultBoolean(boolean bool) {

    }

    @Override
    public void companyListCallback(List<Company> companies) {

    }

    @Override
    public void categoryListCallback(List<Category> categories) {
        categoryList=categories;
        categoryAdapter = new CategoryAdapter(getContext(), categoryList);
        categoryView.setAdapter(categoryAdapter);
    }

    @Override
    public void productListCallback(List<Product> products) {

    }

    @Override
    public void regionListCallback(List<Region> regions) {

    }
}
