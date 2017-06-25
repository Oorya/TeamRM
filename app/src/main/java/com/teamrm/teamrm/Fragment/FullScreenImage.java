package com.teamrm.teamrm.Fragment;


import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamrm.teamrm.Interfaces.FireBaseBooleanCallback;
import com.teamrm.teamrm.R;
import com.teamrm.teamrm.Utility.UtlFirebase;
import com.teamrm.teamrm.Utility.UtlImage;

import me.iwf.photopicker.widget.TouchImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FullScreenImage extends Fragment {

    private Bitmap bitmap;
    public static final String FRAGMENT_TRANSACTION = "FullScreenImage";

    public FullScreenImage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final Bundle bundle = this.getArguments();
        View view = inflater.inflate(R.layout.fragment_full_screen_image, container, false);
        final TouchImageView touchImageView = (TouchImageView) view.findViewById(R.id.full_image);
        touchImageView.setImageBitmap(bitmap);
        view.findViewById(R.id.closeImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        view.findViewById(R.id.rotate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bitmap bitmapOrg = ((BitmapDrawable) touchImageView.getDrawable()).getBitmap();
                if (bitmap != null) {
                    Matrix matrix = new Matrix();

                    matrix.postRotate(90);

                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

                    Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                    touchImageView.setImageBitmap(rotatedBitmap);

                    UtlFirebase.uploadFileByte(bundle.getString("ticketId") + "/pic" + bundle.getInt("imgClicked") + ".jpg", UtlImage.bitmapToByte(rotatedBitmap), getContext(), new FireBaseBooleanCallback() {
                        @Override
                        public void booleanCallback(boolean isTrue) {

                        }
                    });
                }
            }
        });

        return view;
    }

    public void setBitmap(Bitmap mBitmap) {
        Log.d(":::Setting bitmap", "bitmap size + " + mBitmap.getByteCount());
        this.bitmap = mBitmap;
    }
}
