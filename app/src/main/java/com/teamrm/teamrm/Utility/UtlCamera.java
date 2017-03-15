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
import com.teamrm.teamrm.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oorya on 07/12/2016.
 */

public class UtlCamera extends Activity
{
    private static Context context;
    private Activity activity;
    private String picturePath = "", ticketID;
    private static final int SELECT_FILE = 105;
    private static final int FROM_CAMERA = 205;
    private static Uri selectedImageUri;
    private ByteArrayOutputStream baos=new ByteArrayOutputStream();

    public UtlCamera(Context context, Activity activity, String ticketID)
    {
        this.context=context;
        this.activity=activity;
        this.ticketID=ticketID;
    }

    public void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Remove", "Cancel"};

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
                }
                else if(items[item].equals("Remove"))
                {
                    switch (NewTicket.imgClick)
                    {
                        case 1:
                            NewTicket.imageView1.setImageResource(R.drawable.ic_image_black_24dp);
                            NewTicket.imgUri1 = null;
                            break;
                        case 2:
                            NewTicket.imageView2.setImageResource(R.drawable.ic_image_black_24dp);
                            NewTicket.imgUri2 = null;
                            break;
                    }
                }
                else if (items[item].equals("Cancel")) {
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
                //selectedImageUri = Uri.fromFile(imgFile);
                if(imgFile.exists()){
                    Log.w("FILE EXIST : ",imgFile.exists()+"");

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                    myBitmap.compress(Bitmap.CompressFormat.JPEG,80,baos);

                    String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), myBitmap,null, null);
                    selectedImageUri = Uri.parse(path);

                    Log.w("image uri 1 C", selectedImageUri == null?"null 1":"not null 1");

                    Log.w("image click", NewTicket.imgClick+" ==");
                    switch (NewTicket.imgClick)
                    {
                        case 1:
                            NewTicket.imageView1.setImageBitmap(myBitmap);
                            NewTicket.imgUri1 = selectedImageUri;
                            UtlFirebase.uploadFile(ticketID+"/pic1.jpg", selectedImageUri);
                            break;
                        case 2:
                            NewTicket.imageView2.setImageBitmap(myBitmap);
                            NewTicket.imgUri2 = selectedImageUri;
                            UtlFirebase.uploadFile(ticketID+"/pic2.jpg", selectedImageUri);
                            break;
                    }
                }
            }
            else if (requestCode == SELECT_FILE)
            {

                Log.w("Select file: ","Select file");

                Uri uriTemp = data.getData();
                //selectedImageUri = data.getData();
                String tempPath = getPath(uriTemp, activity);
                Bitmap bm;
                BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
                bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
                bm.compress(Bitmap.CompressFormat.JPEG,80,baos);

                String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bm,null, null);
                selectedImageUri = Uri.parse(path);

                Log.w("image uri 1 L", selectedImageUri == null?"null 1":"not null 1");
                switch (NewTicket.imgClick)
                {
                    case 1:
                        NewTicket.imageView1.setImageBitmap(bm);
                        NewTicket.imgUri1 = selectedImageUri;
                        UtlFirebase.uploadFile(ticketID+"/pic1.jpg", selectedImageUri);
                        break;
                    case 2:
                        NewTicket.imageView2.setImageBitmap(bm);
                        NewTicket.imgUri2 = selectedImageUri;
                        UtlFirebase.uploadFile(ticketID+"/pic2.jpg", selectedImageUri);
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
