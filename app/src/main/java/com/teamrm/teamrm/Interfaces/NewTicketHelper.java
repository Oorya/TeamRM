package com.teamrm.teamrm.Interfaces;

import android.support.annotation.NonNull;

/**
 * Created by Oorya on 11/12/2016.
 */

public interface NewTicketHelper {
    public void requestResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);
}
