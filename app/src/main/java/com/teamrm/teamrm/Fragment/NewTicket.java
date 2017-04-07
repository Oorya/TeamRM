package com.teamrm.teamrm.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.teamrm.teamrm.Adapter.GenericPrefListAdapter;
import com.teamrm.teamrm.Adapter.PhotoAdapter;
import com.teamrm.teamrm.Adapter.RecyclerItemClickListener;
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
import com.teamrm.teamrm.Utility.RowSetLayout;
import com.teamrm.teamrm.Utility.UserSingleton;
import com.teamrm.teamrm.Utility.UtlFirebase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

import static android.content.Context.MODE_PRIVATE;

public class NewTicket extends Fragment implements AdapterView.OnItemSelectedListener, FireBaseAble {

    public static final String FRAGMENT_TRANSACTION = "NewTicket";
    private Spinner selectCompany, selectProduct, selectCategory, selectRegion;
    public static Uri imgUri1, imgUri2;
    private Company selectedCompany;
    private Product selectedProduct;
    private Category selectedCategory;
    private Region selectedRegion;
    private EditText ticketAddress, ticketPhone, descriptionShort, descriptionLong;
    private GenericPrefListAdapter listCompanyAdapter;
    private GenericPrefListAdapter listProductAdapter;
    private GenericPrefListAdapter listCategoryAdapter;
    private GenericPrefListAdapter listRegionAdapter;
    private List<GenericKeyValueTypeable> companiesList = new ArrayList<>();
    private List<GenericKeyValueTypeable> productList = new ArrayList<>();
    private List<GenericKeyValueTypeable> categoryList = new ArrayList<>();
    private List<GenericKeyValueTypeable> regionList = new ArrayList<>();

    private Button btnSubmitTicket;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String startTime;

    private static final int PERMISSION_CALLBACK_CONSTANT = 101;
    private static final int REQUEST_PERMISSION_SETTING = 102;
    private boolean sentToSettings = false;

    public static PhotoAdapter photoAdapter;
    public static ArrayList<String> selectedPhotos = new ArrayList<>();

    Context context;

    public NewTicket() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        companiesList.addAll(Company.getCompanyList());
        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            startTime = bundle.getString("NEW_TICKET", "error");
            //TODO WER IS START TIME SET SHALTY
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_new_ticket, container, false);
        RowSetLayout rowSetLayout = (RowSetLayout) view.findViewById(R.id.rowSet1);
        rowSetLayout.AlternateRowsBackground(rowSetLayout, R.color.listRow_alt, RowSetLayout.ALTER_ODD_ROWS);
        context = this.getContext();
        getActivity().findViewById(R.id.toolbar).findViewById(R.id.toolBarItem).setVisibility(View.VISIBLE);

        ticketAddress = (EditText) view.findViewById(R.id.txtAddress);
        ticketPhone = (EditText) view.findViewById(R.id.txtPhone);
        descriptionShort = (EditText) view.findViewById(R.id.descriptionShort);
        descriptionLong = (EditText) view.findViewById(R.id.descriptionLong);

        selectCompany = (Spinner) view.findViewById(R.id.selectCompanySpinner);
        selectProduct = (Spinner) view.findViewById(R.id.selectProductSpinner);
        selectCategory = (Spinner) view.findViewById(R.id.selectCategoryASpinner);
        selectRegion = (Spinner) view.findViewById(R.id.selectRegionSpinner);

        listCompanyAdapter = new GenericPrefListAdapter(context, companiesList);
        selectCompany.setAdapter(listCompanyAdapter);
        setSpinnerAdapters();

        selectProduct.setEnabled(false);
        selectCategory.setEnabled(false);
        selectRegion.setEnabled(false);

        pref = getContext().getSharedPreferences("strImg", MODE_PRIVATE);
        editor = pref.edit();
        selectCompany.setOnItemSelectedListener(this);
        btnSubmitTicket = (Button) view.findViewById(R.id.btnSubmitTicket);
        btnSubmitTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEntries()) {

                    submitTicket(view);
                }
            }
        });

        RecyclerView recyclerViewPhotos = (RecyclerView) view.findViewById(R.id.recycler_view_photos);
        photoAdapter = new PhotoAdapter(getContext(), selectedPhotos);

        recyclerViewPhotos.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recyclerViewPhotos.setAdapter(photoAdapter);

        recyclerViewPhotos.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(PhotoAdapter.MAX)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(getActivity());
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(getActivity());
                        }
                    }
                }));
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            Activity a = getActivity();
            if (a != null) a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
        switch (parent.getId()) {
            case R.id.selectCompanySpinner:
                selectCompany.setSelection(position);
                selectedCompany = (Company) selectCompany.getSelectedItem();
                setSpinnerAdapters();
                break;

            case R.id.selectProductSpinner:
                selectProduct.setSelection(position);
                selectedProduct = (Product) selectProduct.getSelectedItem();
                break;

            case R.id.selectCategoryASpinner:
                selectCategory.setSelection(position);
                selectedCategory = (Category) selectCategory.getSelectedItem();
                break;

            case R.id.selectRegionSpinner:
                selectRegion.setSelection(position);
                selectedRegion = (Region) selectRegion.getSelectedItem();
                break;
        }
    }

    private void setSpinnerAdapters() {
        if (selectedCompany == null) {
            selectedCompany = (Company) companiesList.get(0);
        }
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

    private boolean checkEntries() {
        //add method
        //TODO: add check if not exist user address, phone -> ask if to save?
        return true;
    }

    private void submitTicket(View view) {
        String uid = UUID.randomUUID().toString();
        Ticket newTicket = new Ticket(UserSingleton.getInstance().getUserID(), this.ticketPhone.getText().toString(), this.ticketAddress.getText().toString(), uid,
                selectedCompany.getCompanyId(), selectedCompany.getCompanyName(),
                this.selectedProduct, this.selectedCategory, this.selectedRegion, this.descriptionShort.getText().toString(), this.descriptionLong.getText().toString(),
                imgUri1 != null ? uid + "/pic1.jpg" : "error", imgUri2 != null ? uid + "/pic2.jpg" : "error", startTime);
        uploadPicture(uid);
        UtlFirebase.addTicket(newTicket);
        newTicket.updateTicketStateString(TicketStateStringable.STATE_A01, newTicket);
        //Toast.makeText(getContext(), "Success opening ticket " + newTicket.getTicketNumber(), Toast.LENGTH_LONG).show();
        //((HomeScreen) getActivity()).onDrawerItemSelected(view, 0);
        selectedPhotos.clear();
        getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void uploadPicture(String uid) {
        if (imgUri1 != null) {
            UtlFirebase.uploadFile(uid + "/pic1.jpg", imgUri1);
            imgUri1 = null;
        }
        if (imgUri2 != null) {
            UtlFirebase.uploadFile(uid + "/pic2.jpg", imgUri2);
            imgUri2 = null;
        }
    }

    @Override
    public void companyListCallback(List<Company> companies) {

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

