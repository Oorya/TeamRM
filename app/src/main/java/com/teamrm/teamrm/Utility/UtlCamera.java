package com.teamrm.teamrm.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.teamrm.teamrm.Fragment.NewTicket;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oorya on 07/12/2016.
 */

public class UtlCamera extends Activity
{
    Context context;
    Activity activity;
    private String picturePath = "";
    private static final int SELECT_FILE = 105;
    private static final int FROM_CAMERA = 205;
    ByteArrayOutputStream baos=new ByteArrayOutputStream();

    public UtlCamera(Context context, Activity activity)
    {
        this.context=context;
        this.activity=activity;
    }

    public void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Log.e("Camera", "fireCam: start");
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    String imageFileName = timeStamp + ".jpg";
                    File storageDir = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES);
                    picturePath = storageDir.getAbsolutePath() + "/" + imageFileName;
                    File file = new File(picturePath);
                    Uri outputFileUri = Uri.fromFile(file);
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    Log.e("Camera", "fireCam: intent");
                    activity.startActivityForResult(cameraIntent, FROM_CAMERA);
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
        if (resultCode == RESULT_OK)
        {
            Log.e("OK","OK");
            Log.w("FROM CAMERA OUTSIDE: ",requestCode+"");

            if (requestCode == FROM_CAMERA)
            {
                File imgFile = new  File(picturePath);

                if(imgFile.exists()){
                    Log.w("FILE EXIST : ",imgFile.exists()+"");

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                    myBitmap.compress(Bitmap.CompressFormat.JPEG,80,baos);
                    //savePicSP("img1",UtlImage.bitmap2string(myBitmap));
                    Log.w("image click", NewTicket.imgClick+" ==");
                    switch (NewTicket.imgClick)
                    {
                        case 1:
                            NewTicket.imageView1.setImageBitmap(myBitmap);
                            NewTicket.img1 = myBitmap;
                            break;
                        case 2:
                            NewTicket.imageView2.setImageBitmap(myBitmap);
                            NewTicket.img2 = myBitmap;
                            break;
                    }
                }
            }
            else if (requestCode == SELECT_FILE)
            {
                Log.w("Select file: ","Select file");
                Uri selectedImageUri = data.getData();

                String tempPath = getPath(selectedImageUri, activity);
                Bitmap bm;
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
                bm.compress(Bitmap.CompressFormat.JPEG,80,baos);
                //savePicSP("img1",UtlImage.bitmap2string(bm));
                switch (NewTicket.imgClick)
                {
                    case 1:
                        NewTicket.imageView1.setImageBitmap(bm);
                        NewTicket.img1 = bm;
                        break;
                    case 2:
                        NewTicket.imageView2.setImageBitmap(bm);
                        NewTicket.img2 = bm;
                        break;
                }
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
}
