package com.teamrm.teamrm.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.teamrm.teamrm.Fragment.NewTicket;

import java.io.File;

/**
 * Created by Oorya on 07/12/2016.
 */

public class UtlCamera extends Activity
{
    Context context;
    Activity activity;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private String picturePath = "temp.jpg";
    private static final int SELECT_FILE = 105;
    private static final int FROM_CAMERA = 205;

    public UtlCamera(Context context, Activity activity)
    {
        this.context=context;
        this.activity=activity;
        pref = context.getSharedPreferences("strImg",MODE_PRIVATE);
        editor=pref.edit();

        //if(!getPicSP("img1").equals("error")) imageView1.setImageBitmap(UtlImage.string2bitmap(getPicSP("img1")));
        //if(!getPicSP("img2").equals("error")) imageView2.setImageBitmap(UtlImage.string2bitmap(getPicSP("img2")));
    }

    public void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment
                            .getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    Toast.makeText(context, "Befor", Toast.LENGTH_SHORT).show();
                    activity.startActivityForResult(intent, FROM_CAMERA);
                    Toast.makeText(context, "After", Toast.LENGTH_SHORT).show();
                    /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                        activity.startActivityForResult(takePictureIntent, FROM_CAMERA);
                    }*/
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    activity.startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.e("OK","OK");
            Log.w("FROM CAMERA OUTSIDE: ",requestCode+"");

            if (requestCode == FROM_CAMERA) {
                File imgFile = new  File(picturePath);

                Log.w("FROM CAMERA: ",requestCode+"");
                Log.w("FILE EXIST 1: ",imgFile.exists()+"");
                if(imgFile.exists()){
                    Log.w("FILE EXIST 2: ",imgFile.exists()+"");

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    //savePicSP("img1",UtlImage.bitmap2string(myBitmap));
                    Log.w("BITMAP: ",myBitmap+"");
                    NewTicket.imageView2.setImageBitmap(myBitmap);
                    //  img2.setImageBitmap(myBitmap);
                }
            }
            else if (requestCode == SELECT_FILE)
            {
                Uri selectedImageUri = data.getData();

                String tempPath = getPath(selectedImageUri, activity);
                Bitmap bm;
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
                //savePicSP("img1",UtlImage.bitmap2string(bm));
                //img1.setImageBitmap(bm);
                NewTicket.imageView2.setImageBitmap(bm);
            }
        }
    }

    private String getPath(Uri uri, Activity activity) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void savePicSP(String key, String image)
    {
        editor.putString(key,image).commit();
    }

    private String getPicSP(String key)
    {
        return pref.getString(key,"error");
    }

}
