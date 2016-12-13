package com.teamrm.teamrm.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.teamrm.teamrm.Fragment.FragmentDrawer;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Oorya on 13/12/2016.
 */

public class UtlBitmapUrl extends AsyncTask<String, String, Bitmap>
{
    Context context;
    ImageView imageView;
    Bitmap myBitmap;

    public UtlBitmapUrl()
    {
        //this.context=context;
        //imageView=(ImageView)((MainActivity)context).findViewById(R.id.userAvatar);
    }
    @Override
    protected Bitmap doInBackground(String... url)
    {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url[0]).openConnection();
            myBitmap = BitmapFactory.decodeStream(connection.getInputStream());
            Log.w("Background img: ", myBitmap==null?"null":myBitmap.toString());
            connection.disconnect();

        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return myBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        FragmentDrawer.imageAvatar.setImageBitmap(getRoundedCornerBitmap(bitmap,300));
        Log.w("On post img: ", bitmap==null?"null":bitmap.toString());
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}
