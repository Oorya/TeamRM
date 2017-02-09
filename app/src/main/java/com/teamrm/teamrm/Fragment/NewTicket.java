package com.teamrm.teamrm.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.teamrm.teamrm.Activities.HomeScreen;
import com.teamrm.teamrm.Adapter.GenericPrefListAdapter;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;
import com.teamrm.teamrm.Interfaces.TicketStateStringable;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Type.Region;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlCamera;
import com.teamrm.teamrm.Utility.UtlFirebase;
import com.teamrm.teamrm.Utility.UtlImage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

public class NewTicket extends Fragment implements AdapterView.OnItemSelectedListener, FireBaseAble{

    private Spinner selectCompany, selectProduct, selectCategory, selectRegion;
    public static ImageView imageView1, imageView2;
    public static Bitmap img1, img2;
    private Company selectedCompany;
    private Product selectedProduct;
    private Category selectedCategory;
    private Region selectedRegion;
    private EditText ticketAddress, ticketPhone, descriptionShort, descriptionLong;
    GenericPrefListAdapter listCompanyAdapter;
    GenericPrefListAdapter listProductAdapter;
    GenericPrefListAdapter listCategoryAdapter;
    GenericPrefListAdapter listRegionAdapter;
    private List<GenericKeyValueTypeable> companiesList = new ArrayList<>();
    private List<GenericKeyValueTypeable> productList = new ArrayList<>();
    private List<GenericKeyValueTypeable> categoryList = new ArrayList<>();
    private List<GenericKeyValueTypeable> regionList = new ArrayList<>();

    private Button btnSubmitTicket;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    public static int imgClick = 0;
    public static UtlCamera utlCamera;

    private static final int PERMISSION_CALLBACK_CONSTANT = 101;
    private static final int REQUEST_PERMISSION_SETTING = 102;
    private boolean sentToSettings = false;
    Context context;

    public NewTicket() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (UserSingleton.getInstance().getUserStatus()){
            case UserSingleton.STATUS_CLIENT:
                UtlFirebase.getAllClientCompanies(this);
                break;

            case UserSingleton.STATUS_ADMIN:
            case UserSingleton.STATUS_TECH:
                UtlFirebase.getCurrentCompany(this);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_new_ticket, container, false);
        context = this.getContext();
        getActivity().findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);

        imageView1 = (ImageView)view.findViewById(R.id.photoChooser1);
        imageView2 = (ImageView)view.findViewById(R.id.photoChooser2);
        ticketAddress = (EditText)view.findViewById(R.id.txtAddress);
        ticketPhone = (EditText)view.findViewById(R.id.txtPhone);
        descriptionShort = (EditText)view.findViewById(R.id.descriptionShort);
        descriptionLong = (EditText)view.findViewById(R.id.descriptionLong);

        selectCompany = (Spinner) view.findViewById(R.id.selectCompanySpinner);
        selectProduct = (Spinner) view.findViewById(R.id.selectProductSpinner);
        selectCategory = (Spinner) view.findViewById(R.id.selectCategoryASpinner);
        selectRegion = (Spinner) view.findViewById(R.id.selectRegionSpinner);

        utlCamera=new UtlCamera(getContext(),getActivity());

        selectProduct.setEnabled(false);
        selectCategory.setEnabled(false);
        selectRegion.setEnabled(false);

        pref = getContext().getSharedPreferences("strImg",MODE_PRIVATE);
        editor=pref.edit();




        selectCompany.setOnItemSelectedListener(this);


        btnSubmitTicket = (Button)view.findViewById(R.id.btnSubmitTicket);
        btnSubmitTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEntries()){
                    submitTicket(view);
                }
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgClick = 1;
                getPermission();
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgClick = 2;
                getPermission();
            }
        });
        return view;
    }

    private void getPermission() {
        String[] permissionList = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(getActivity(),permissionList,108);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.w("Permission new ticket", "new ticket");
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
        {
            Log.w("Permission new ticket", "INSIDE IF");
            utlCamera.selectImage();
        }
        else
        {

        }
        Log.d("REQUEST  ",requestCode+"");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Activity a = getActivity();
            if(a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        Log.e("RESULT","RESULT");
        Toast.makeText(getContext(), "ON ACTIVITY RESULT", Toast.LENGTH_SHORT).show();
        /*if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
        switch (parent.getId()){
            case R.id.selectCompanySpinner:
                selectCompany.setSelection(position);
                selectedCompany = (Company)selectCompany.getSelectedItem();
                setSpinnerAdapters();
                break;
            case R.id.selectProductSpinner:
            selectProduct.setSelection(position);
            selectedProduct = (Product)selectProduct.getSelectedItem();
            break;

            case R.id.selectCategoryASpinner:
            selectCategory.setSelection(position);
            selectedCategory = (Category)selectCategory.getSelectedItem();
            break;

            case R.id.selectRegionSpinner:
            selectRegion.setSelection(position);
            selectedRegion = (Region)selectRegion.getSelectedItem();
            break;
        }
    }

    private void setSpinnerAdapters() {
        UtlFirebase.getProducts(selectedCompany.getCompanyId(), this);
        UtlFirebase.getCategories(selectedCompany.getCompanyId(), this);
        UtlFirebase.getRegions(selectedCompany.getCompanyId(), this);
        selectProduct.setEnabled(true);
        selectCategory.setEnabled(true);
        selectRegion.setEnabled(true);
        selectProduct.setOnItemSelectedListener(this);
        selectCategory.setOnItemSelectedListener(this);
        selectRegion.setOnItemSelectedListener(this);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean checkEntries(){
        //add method
        //TODO: add check if not exist user address, phone -> ask if to save?
        return true;
    }

    private void submitTicket(View view){

        //Calendar cal = Calendar.getInstance(); // creates calendar
        if (UserSingleton.getInstance().getUserAddress().isEmpty()){
            new MaterialDialog.Builder(getContext())
                    .title(R.string.label_dialog_save_user_detail)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            UserSingleton.getInstance().setUserAddress(ticketAddress.getText().toString());
                        }
                    })
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
        } else {
            if (UserSingleton.getInstance().getUserPhone().isEmpty()){
                new MaterialDialog.Builder(getContext())
                        .title(R.string.label_add_category)
                        .input("", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                Toast.makeText(getContext(),  input.toString(), Toast.LENGTH_SHORT).show();
                                UtlFirebase.addCategory(UserSingleton.getInstance().getAssignedCompanyID(), input.toString());
                            }
                        })
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //Toast.makeText(getContext(), "positive", Toast.LENGTH_SHORT).show();
                            }
                        })
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
            } else {

        String uid = UUID.randomUUID().toString();
        Ticket newTicket = new Ticket(UserSingleton.getInstance().getUserID(), this.ticketPhone.getText().toString(), this.ticketAddress.getText().toString(), uid, selectedCompany.getCompanyId(),
                this.selectedProduct, this.selectedCategory, this.selectedRegion, this.descriptionShort.getText().toString(), this.descriptionLong.getText().toString(),
                img1 != null ? UtlImage.bitmap2string(img1):"error", img2 != null ? UtlImage.bitmap2string(img2):"error");
        UtlFirebase.addTicket(newTicket);
        newTicket.updateTicketStateString(TicketStateStringable.STATE_A01, newTicket);
        Toast.makeText(getContext(), "Success opening ticket " + newTicket.getTicketNumber(), Toast.LENGTH_LONG).show();
        ((HomeScreen) getActivity()).onDrawerItemSelected(view, 0);
    }}}

    @Override
    public void companyListCallback(List<GenericKeyValueTypeable> companies) {
        companiesList = companies;
        listCompanyAdapter = new GenericPrefListAdapter(context, companiesList);
        selectCompany.setAdapter(listCompanyAdapter);
        setSpinnerAdapters();
    }

    @Override
    public void productListCallback(List<Product> products) {
        productList.clear();
        productList.addAll(products);
        listProductAdapter = new GenericPrefListAdapter(context, productList);
        selectProduct.setAdapter(listProductAdapter);


    }

    @Override
    public void categoryListCallback(List<Category> categories) {
        categoryList.clear();
        categoryList.addAll(categories);
        listCategoryAdapter = new GenericPrefListAdapter(context, categoryList);
        selectCategory.setAdapter(listCategoryAdapter);

    }

    @Override
    public void regionListCallback(List<Region> regions) {
        regionList.clear();
        regionList.addAll(regions);
        listRegionAdapter = new GenericPrefListAdapter(context, regionList);
        selectRegion.setAdapter(listRegionAdapter);
    }

    @Override
    public void ticketLiteListCallback(List<TicketLite> ticketLites) {

    }

    @Override
    public void resultTicket(Ticket ticket) {

    }

    @Override
    public void resultUser(Users user) {

    }

    @Override
    public void ticketListCallback(List<Ticket> ticket) {

    }

    @Override
    public void resultBoolean(boolean bool) {

    }
}

