package com.teamrm.teamrm.Type;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.teamrm.teamrm.Interfaces.GenericKeyValueTypeable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Oorya on 13/01/2017.
 */

public class EnrollmentCode{

    public static List<EnrollmentCode> enrollmentCodeList = Collections.EMPTY_LIST;

    public static final String ENROLLMENT_CODE_ID = "enrollmentCodeID";
    public static final String ENROLLMENT_CODE_STRING = "enrollmentCodeString";
    public static final String ENROLLMENT_CODE_COMPANY_ID = "enrollmentCodeCompanyId";
    public static final String IS_SENT_TO_PHONE = "isSentToPhone";
    public static final String ENROLLMENT_CODE_SENT_TO_PHONE = "enrollmentCodeSentToPhone";
    public static final String IS_SENT_TO_MAIL = "isSentToMail";
    public static final String ENROLLMENT_CODE_SENT_TO_MAIL = "enrollmentCodeSentToMail";
    
    private String enrollmentCodeID;
    private String enrollmentCodeString;
    private String enrollmentCodeCompanyId;
    private boolean isSentToPhone;
    private String enrollmentCodeSentToPhone;
    private boolean isSentToMail;
    private String enrollmentCodeSentToMail;

    public EnrollmentCode(){};

    public EnrollmentCode(@NonNull String companyID, @NonNull String enrollmentCodeString) {
        this.enrollmentCodeString = enrollmentCodeString;
        this.enrollmentCodeCompanyId = companyID;
        this.isSentToMail = false;
        this.isSentToPhone = false;
        this.enrollmentCodeSentToMail = "";
        this.enrollmentCodeSentToPhone = "";
    }

    public EnrollmentCode(String enrollmentCodeID, String enrollmentCodeString, String companyID, boolean isSentToPhone, @Nullable String enrollmentCodeSentToPhone, boolean isSentToMail, @Nullable String enrollmentCodeSentToMail) {
        this.enrollmentCodeID = enrollmentCodeID;
        this.enrollmentCodeString = enrollmentCodeString;
        this.isSentToPhone = isSentToPhone;
        this.enrollmentCodeSentToPhone = enrollmentCodeSentToPhone;
        this.isSentToMail = isSentToMail;
        this.enrollmentCodeSentToMail = enrollmentCodeSentToMail;
    }

    public String getEnrollmentCodeID() {
        return enrollmentCodeID;
    }

    public void setEnrollmentCodeID(String enrollmentCodeID) {
        this.enrollmentCodeID = enrollmentCodeID;
    }

    public String getEnrollmentCodeString() {
        return enrollmentCodeString;
    }

    public void setEnrollmentCodeString(String enrollmentCodeString) {
        this.enrollmentCodeString = enrollmentCodeString;
    }

    public String getEnrollmentCodeCompanyId() {
        return enrollmentCodeCompanyId;
    }

    public void setEnrollmentCodeCompanyId(String enrollmentCodeCompanyId) {
        this.enrollmentCodeCompanyId = enrollmentCodeCompanyId;
    }

    public boolean isSentToPhone() {
        return isSentToPhone;
    }

    public void setSentToPhone(boolean sentToPhone) {
        isSentToPhone = sentToPhone;
    }

    public String getEnrollmentCodeSentToPhone() {
        return enrollmentCodeSentToPhone;
    }

    public void setEnrollmentCodeSentToPhone(String enrollmentCodeSentToPhone) {
        this.enrollmentCodeSentToPhone = enrollmentCodeSentToPhone;
    }

    public boolean isSentToMail() {
        return isSentToMail;
    }

    public void setSentToMail(boolean sentToMail) {
        isSentToMail = sentToMail;
    }

    public String getEnrollmentCodeSentToMail() {
        return enrollmentCodeSentToMail;
    }

    public void setEnrollmentCodeSentToMail(String enrollmentCodeSentToMail) {
        this.enrollmentCodeSentToMail = enrollmentCodeSentToMail;
    }

    public static List<EnrollmentCode> getEnrollmentCodeList() {
        return enrollmentCodeList;
    }

    public static void setEnrollmentCodeList(List<EnrollmentCode> enrollmentCodeList) {
        EnrollmentCode.enrollmentCodeList = enrollmentCodeList;
    }

    @Override
    public String toString() {
        return this.enrollmentCodeID + "\n" + this.enrollmentCodeString + "\n" +
                "Mail: " + this.isSentToMail() + " : " + this.enrollmentCodeSentToMail + "\n" +
                "Phone: " + this.isSentToMail() + " : " + this.enrollmentCodeSentToPhone;
    }
}
