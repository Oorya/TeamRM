package com.teamrm.teamrm.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;

/**
 * Created by Oorya on 24/07/2016.
 */
public class UtlImage
{
    //encode image
    public static String bitmap2string(Bitmap myImage)
    {
        //create holder for the stream - baos
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        //compress the image to 80 precent from original size and store it into baos
        myImage.compress(Bitmap.CompressFormat.JPEG,80,baos);
        //create array of bytes to hold the image data
        byte[] bytes=baos.toByteArray();
        //finally, convert the image with base64 to a string....
        String base64image = Base64.encodeToString(bytes, Base64.DEFAULT);
        //return the string which will represent our image
        return base64image;
    }

    //decode image
    public static Bitmap string2bitmap(String myString)
    {
        //create a string to hold our picture
        String base64image = myString;
        //decode code our string back to byte array
        byte[] imageAsBytes = Base64.decode(base64image.getBytes(), Base64.DEFAULT);
        //create a bitmap image from our byte array with offset of 0, and length of our array length
        Bitmap myBitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        //return the bitmap back to the user.....
        return myBitmap;
    }

    public static Uri fileToUri(File file, Context context)
    {
        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        myBitmap.compress(Bitmap.CompressFormat.JPEG,0,new ByteArrayOutputStream());
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), myBitmap,null, null);

        return Uri.parse(path);
    }

    public static Bitmap fileToBitmap(File file)
    {
        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        myBitmap.compress(Bitmap.CompressFormat.JPEG,0,new ByteArrayOutputStream());

        return myBitmap;
    }
}
