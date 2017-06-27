package com.teamrm.teamrm.Utility;

import android.util.Log;
import android.widget.EditText;

import java.util.List;

/**
 * Created by Oorya on 27/06/2017.
 */

public class EditTextValidation
{
    public static boolean checkEt(List<EditText> etList) {
        boolean isFocused = false, isEmpty = false;

        for (EditText et : etList) {
            if (et.getText().toString().trim().length() == 0) {
                et.setError("מלא שדה זה");
                Log.d(":::isFocused", Boolean.toString(isFocused));
                if (!isFocused) {
                    et.requestFocus();
                    isFocused = true;
                }
                isEmpty = true;
            }

        }
        return isEmpty;
    }

    public static boolean checkPhoneRegex(EditText et)
    {
        if(!et.getText().toString().trim().matches("((0[57]|[//+]?[1-9]{3}[57])([0-9]{8}))|(([0][23489]|[//+]?[1-9]{3}[23489])([0-9]{7}))"))
        {
            et.setError("מספר הטלפון אינו תקין");
            et.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }

    public static boolean checkMailRegex(EditText et)
    {
        if(!et.getText().toString().trim().matches("\\w.+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6}"))
        {
            et.setError("כתובת המייל אינה תקינה");
            et.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }
}
