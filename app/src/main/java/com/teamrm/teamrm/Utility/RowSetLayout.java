package com.teamrm.teamrm.Utility;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.teamrm.teamrm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by r00t on 24/03/2017.
 */

public class RowSetLayout extends LinearLayout {
    public static final String TAG = "RowSetLayout";
    public static final int ALTER_ODD_ROWS = 0;
    public static final int ALTER_EVEN_ROWS = 1;

    public RowSetLayout(Context context) {
        super(context);
    }

    public RowSetLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RowSetLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21) public RowSetLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void AlternateRowsBackground(RowSetLayout rowSetLayout, int backgroundResource, int alterOddOrEvenRows) {
        List<RowViewLayout> targetChildren = new ArrayList<>();
        if (rowSetLayout.getChildCount() > 0) {
            for (int count = 0; count <= rowSetLayout.getChildCount(); count += 1) {
                View child = rowSetLayout.getChildAt(count);
                if (child instanceof RowViewLayout) {
                    Log.d(TAG, child.toString());
                    try {
                        targetChildren.add((RowViewLayout) child);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            if (targetChildren.size()>1) {
                for (int cCount = 1; cCount <= targetChildren.size() - 1; cCount += 1) {
                    switch (alterOddOrEvenRows) {
                        case ALTER_EVEN_ROWS:
                            if (cCount % 2 == 0) {
                                targetChildren.get(cCount-1).setBackgroundResource(backgroundResource);
                            }
                            break;

                        case ALTER_ODD_ROWS:
                            if (cCount % 2 != 0) {
                                targetChildren.get(cCount-1).setBackgroundResource(backgroundResource);
                            }
                            break;

                    }
                }
            }
        }
    }
}
