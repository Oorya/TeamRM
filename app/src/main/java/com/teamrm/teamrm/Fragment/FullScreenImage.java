package com.teamrm.teamrm.Fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamrm.teamrm.R;

import me.iwf.photopicker.widget.TouchImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FullScreenImage extends Fragment {

    private TouchImageView touchImageView;
    private Bitmap bitmap;
    public static final String FRAGMENT_TRANSACTION = "FullScreenImage";

    public FullScreenImage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_full_screen_image, container, false);
        touchImageView = (TouchImageView)view.findViewById(R.id.full_image);
        touchImageView.setImageBitmap(bitmap);
        view.findViewById(R.id.closeImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }

}
