package com.teamrm.teamrm.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
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
import com.teamrm.teamrm.Utility.UtlImage;

import java.io.File;
import java.util.Calendar;
import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

public class NewTicket extends Fragment implements AdapterView.OnItemSelectedListener {

    private Spinner selectProduct, selectCategoryA, selectRegion;
    private ImageView imageView1, imageView2;
    private String product, category, region;
    private EditText address, phone, desShort, desLong;
    //Spinner selectCategoryB;
    private Button btnSubmitTicket;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private final static int SELECT_FILE = 1;
    private static final int FROM_CAMERA = 2;
    private int imgClick = 0;

    private static final int PERMISSION_CALLBACK_CONSTANT = 101;
    private static final int REQUEST_PERMISSION_SETTING = 102;
    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;

    private String picturePath = "";

    public NewTicket() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_ticket, container, false);

        imageView1 = (ImageView)view.findViewById(R.id.photoChooser1);
        imageView2 = (ImageView)view.findViewById(R.id.photoChooser2);
        address = (EditText)view.findViewById(R.id.txtAddress);
        phone = (EditText)view.findViewById(R.id.txtPhone);
        desShort = (EditText)view.findViewById(R.id.descriptionShortInput);
        desLong = (EditText)view.findViewById(R.id.descriptionLongInput);
        selectProduct = (Spinner) view.findViewById(R.id.selectProductSpinner);
        selectCategoryA = (Spinner) view.findViewById(R.id.selectCategoryASpinner);
        selectRegion = (Spinner) view.findViewById(R.id.selectRegionSpinner);

        permissionStatus = getActivity().getSharedPreferences("permissionStatus",getActivity().MODE_PRIVATE);

        pref = getContext().getSharedPreferences("strImg",MODE_PRIVATE);
        editor=pref.edit();

        if(!getPicSP("img1").equals("error")) imageView1.setImageBitmap(UtlImage.string2bitmap(getPicSP("img1")));
        if(!getPicSP("img2").equals("error")) imageView2.setImageBitmap(UtlImage.string2bitmap(getPicSP("img2")));

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
                imgClick=1;
                //selectImage();
                if(ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_PHONE_STATE)){
                        //Show Information about why you need the permission
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Need Permission");
                        builder.setMessage("This app needs phone permission.");
                        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},PERMISSION_CALLBACK_CONSTANT);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    } else if (permissionStatus.getBoolean(Manifest.permission.READ_PHONE_STATE,false)) {
                        //Previously Permission Request was cancelled with 'Dont Ask Again',
                        // Redirect to Settings after showing Information about why you need the permission
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Need Permission");
                        builder.setMessage("This app needs storage permission.");
                        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                sentToSettings = true;
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                                Toast.makeText(getActivity(), "Go to Permissions to Grant Phone", Toast.LENGTH_LONG).show();
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    }  else {
                        //just request the permission
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_CALLBACK_CONSTANT);
                    }
                    Toast.makeText(getContext(), "Permissions Required", Toast.LENGTH_SHORT).show();


                    SharedPreferences.Editor editor = permissionStatus.edit();
                    editor.putBoolean(Manifest.permission.READ_PHONE_STATE,true);
                    editor.commit();
                } else {
                    //You already have the permission, just go ahead.
                    proceedAfterPermission();
                }


                Toast.makeText(getContext(), "IMAGE 1", Toast.LENGTH_SHORT).show();

            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //imgClick=2;
                selectImage();
                Toast.makeText(getContext(), "IMAGE 2", Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSION_CALLBACK_CONSTANT){
            //check if all permissions are granted
            boolean allgranted = false;
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }


            if(allgranted){
                proceedAfterPermission();
            } else if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_PHONE_STATE)){
                Toast.makeText(getContext(), "Permissions Required", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Need Storage Permission");
                builder.setMessage("This app needs phone permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},PERMISSION_CALLBACK_CONSTANT);
                        selectImage();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(getActivity(),"Unable to get Permission",Toast.LENGTH_LONG).show();
            }
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
        }

        if (resultCode == RESULT_OK) {
            Log.e("OK","OK");

            if (requestCode == FROM_CAMERA) {
                File imgFile = new  File(picturePath);
                if(imgFile.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    //savePicSP("img1",UtlImage.bitmap2string(myBitmap));
                    imageView2.setImageBitmap(myBitmap);
                  //  img2.setImageBitmap(myBitmap);

                }
            }
            else if (requestCode == SELECT_FILE)
            {
                Uri selectedImageUri = data.getData();

                String tempPath = getPath(selectedImageUri, getActivity());
                Bitmap bm;
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
                //savePicSP("img1",UtlImage.bitmap2string(bm));
                //img1.setImageBitmap(bm);
                imageView2.setImageBitmap(bm);
            }
        }*/
    }

    private String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    private void savePicSP(String key, String image)
    {
        editor.putString(key,image).commit();
    }

    private String getPicSP(String key)
    {
        return pref.getString(key,"error");
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment
                            .getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    Toast.makeText(getContext(), "Befor", Toast.LENGTH_SHORT).show();
                    getActivity().startActivityForResult(intent, FROM_CAMERA);
                    Toast.makeText(getContext(), "After", Toast.LENGTH_SHORT).show();
                    /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, FROM_CAMERA);
                    }*/
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    getActivity().startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void proceedAfterPermission() {
        Toast.makeText(getActivity(), "We got All Permissions", Toast.LENGTH_LONG).show();
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

