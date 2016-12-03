package com.teamrm.teamrm.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.teamrm.teamrm.R;

public class NewTicket extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner selectProduct, selectCategoryA, selectRegion;
    //Spinner selectCategoryB;
    private Button btnSubmitTicket;



    public NewTicket() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_ticket, container, false);

        selectProduct = (Spinner) view.findViewById(R.id.selectProductSpinner);
        selectCategoryA = (Spinner) view.findViewById(R.id.selectCategoryASpinner);
        selectRegion = (Spinner) view.findViewById(R.id.selectRegionSpinner);

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

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        parent.getItemAtPosition(position);
        switch (parent.getId()){
            case R.id.selectProductSpinner:
                selectProduct.setSelection(position);
            break;

            case R.id.selectCategoryASpinner:
                selectCategoryA.setSelection(position);
            break;

            case R.id.selectRegionSpinner:
                selectRegion.setSelection(position);
            break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean checkEntries(){
        //add method
        return false;
    }

    private void submitTicket(){
        // add method
    }
}

