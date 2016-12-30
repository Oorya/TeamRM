package com.teamrm.teamrm.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teamrm.teamrm.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StateActionButtons extends Fragment {

    public Button btnRight, btnLeft;

    public StateActionButtons() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_state_action_buttons, container, false);
        Button btnRight = (Button)view.findViewById(R.id.btnRight);
        Button btnLeft = (Button)view.findViewById(R.id.btnLeft);


        return view;
    }

    public Button getBtnRight() {
        return btnRight;
    }

    public Button getBtnLeft() {
        return btnLeft;
    }
}
