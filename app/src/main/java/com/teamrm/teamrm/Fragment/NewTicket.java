package com.teamrm.teamrm.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Utility.UtlCamera;

import java.util.Calendar;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

public class NewTicket extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner selectProduct, selectCategoryA, selectRegion;
    public static ImageView imageView1, imageView2;
    private String product, category, region;
    private EditText address, phone, desShort, desLong;
    //Spinner selectCategoryB;
    private Button btnSubmitTicket;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private int imgClick = 0;
    private boolean isAllow = false;

    private static final int PERMISSION_CALLBACK_CONSTANT = 101;
    private static final int REQUEST_PERMISSION_SETTING = 102;
    private boolean sentToSettings = false;
    public static UtlCamera utlCamera;

    public NewTicket() {}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_ticket, container, false);

        imageView1 = (ImageView)view.findViewById(R.id.photoChooser1);
        imageView2 = (ImageView)view.findViewById(R.id.photoChooser2);
        address = (EditText)view.findViewById(R.id.txtAddress);
        phone = (EditText)view.findViewById(R.id.txtPhone);
        desShort = (EditText)view.findViewById(R.id.descriptionShort);
        desLong = (EditText)view.findViewById(R.id.descriptionLong);
        selectProduct = (Spinner) view.findViewById(R.id.selectProductSpinner);
        selectCategoryA = (Spinner) view.findViewById(R.id.selectCategoryASpinner);
        selectRegion = (Spinner) view.findViewById(R.id.selectRegionSpinner);
        utlCamera=new UtlCamera(getContext(),getActivity());

        pref = getContext().getSharedPreferences("strImg",MODE_PRIVATE);
        editor=pref.edit();

        ArrayAdapter<String> listProductAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_view, getResources().getStringArray(R.array.product_list));
        ArrayAdapter<String> listCategoryAAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_view, getResources().getStringArray(R.array.category_a_list));
        ArrayAdapter<String> listRegionAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_view, getResources().getStringArray(R.array.region_list));

        listProductAdapter.setDropDownViewResource(R.layout.spinner_row);
        listCategoryAAdapter.setDropDownViewResource(R.layout.spinner_row);
        listRegionAdapter.setDropDownViewResource(R.layout.spinner_row);

        selectProduct.setAdapter(listProductAdapter);
        selectProduct.setOnItemSelectedListener(this);

        selectCategoryA.setAdapter(listCategoryAAdapter);
        selectCategoryA.setOnItemSelectedListener(this);

        selectRegion.setAdapter(listRegionAdapter);
        selectRegion.setOnItemSelectedListener(this);

        btnSubmitTicket = (Button)view.findViewById(R.id.btnSubmitTicket);
        btnSubmitTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEntries()){
                    submitTicket();
                }
            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //imgClick=1;

                if (isAllow)
                {
                    utlCamera.selectImage();
                }
                else
                {
                    getPermission();
                }

                Toast.makeText(getContext(), "IMAGE 1", Toast.LENGTH_SHORT).show();

            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //imgClick=2;
                //getPermission();
                if (isAllow)
                {
                    utlCamera.selectImage();
                }
                else
                {
                    getPermission();
                }
                Toast.makeText(getContext(), "IMAGE 2", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void getPermission() {
        String[] permissionList = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(getActivity(),permissionList,108);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.w("Permission new ticket", "new ticket");
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
        {
            Log.w("Permission new ticket", "INSIDE IF");
            isAllow=true;
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
            case R.id.selectProductSpinner:
                selectProduct.setSelection(position);
                product = selectProduct.getItemAtPosition(position).toString();
            break;

            case R.id.selectCategoryASpinner:
                selectCategoryA.setSelection(position);
                category = selectCategoryA.getItemAtPosition(position).toString();
                break;

            case R.id.selectRegionSpinner:
                selectRegion.setSelection(position);
                region = selectRegion.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean checkEntries(){
        //add method
        return true;
    }

    private void submitTicket(){
        Calendar cal = Calendar.getInstance(); // creates calendar
        Ticket ticket = new Ticket(product,category,region,address.getText().toString(),phone.getText().toString(),
                desShort.getText().toString(),desLong.getText().toString(),"error","error",getUUID());
        ticket.saveTicket();
        address.setText("");
        phone.setText("");
        desShort.setText("");
        desLong.setText("");
    }

    private String getUUID()
    {
        //create a unique UUID
        UUID idOne = UUID.randomUUID();
        //returning the UUID
        return idOne.toString();
    }


}

