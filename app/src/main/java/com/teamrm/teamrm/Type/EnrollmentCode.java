package com.teamrm.teamrm.Type;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TabHost;

import com.google.firebase.database.Exclude;

import java.util.Collections;
import java.util.List;

/**
 * Created by Oorya on 13/01/2017.
 */

public class EnrollmentCode{

    @Exclude private static List<EnrollmentCode> enrollmentCodeList = Collections.EMPTY_LIST;

    @Exclude public static final String ENROLLMENT_CODE_ID = "enrollmentCodeID";
    @Exclude public static final String ENROLLMENT_CODE_STRING = "enrollmentCodeString";
    @Exclude public static final String ENROLLMENT_CODE_COMPANY_ID = "enrollmentCodeCompanyId";
    @Exclude public static final String IS_SENT_TO_PHONE = "isSentToPhone";
    @Exclude public static final String ENROLLMENT_CODE_SENT_TO_PHONE = "enrollmentCodeSentToPhone";
    @Exclude public static final String IS_SENT_TO_MAIL = "isSentToMail";
    @Exclude public static final String ENROLLMENT_CODE_SENT_TO_MAIL = "enrollmentCodeSentToMail";
    @Exclude public static final String IS_ACCEPTED = "isPending";
    @Exclude public static final String ENROLLED_TECH_USER_ID = "enrolledTechUserID";

    @Exclude private static final int ENROLLMENT_ISSUED = 66379;
    @Exclude private static final int ENROLLMENT_PENDING = 75236;
    @Exclude private static final int ENROLLMENT_ACCEPTED = 62344;
    @Exclude private static final int ENROLLMENT_DECLINED = 98223;
    @Exclude private static final int ENROLLMENT_FINALIZED = 84863;

    private String enrollmentCodeID;
    private String enrollmentCodeString;
    private String enrollmentCodeCompanyId;
    private boolean isSentToPhone;
    private String enrollmentCodeSentToPhone;
    private boolean isSentToMail;
    private String enrollmentCodeSentToMail;
    private int enrollmentStatus;
    private String enrolledTechUserID;

    public EnrollmentCode(){}

    public EnrollmentCode(@NonNull String companyID, @NonNull String enrollmentCodeString) {
        this.enrollmentCodeString = enrollmentCodeString;
        this.enrollmentCodeCompanyId = companyID;
        this.isSentToMail = false;
        this.isSentToPhone = false;
        this.enrollmentCodeSentToMail = "";
        this.enrollmentCodeSentToPhone = "";
        this.enrollmentStatus = ENROLLMENT_ISSUED;
        this.enrolledTechUserID = "";
    }

    public EnrollmentCode(String enrollmentCodeID, String enrollmentCodeString, String companyID,
                          boolean isSentToPhone, @Nullable String enrollmentCodeSentToPhone, boolean isSentToMail, @Nullable String enrollmentCodeSentToMail) {
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

    public static void setenrollmentCodeList(List<EnrollmentCode> enrollmentCodes) {
        enrollmentCodeList = enrollmentCodes;
    }

    public static void addEnrollmentCodeToList(EnrollmentCode enrollmentCode) {
        enrollmentCodeList.add(enrollmentCode);
    }

    public static void removeEnrollmentCodeFromList(EnrollmentCode enrollmentCode) {
        //TODO:add method
    }

    public static void changeEnrollmentCodeInList(EnrollmentCode enrollmentCode) {
        //TODO:add method
    }

    @Override
    public String toString() {
        return this.enrollmentCodeID + "\n" + this.enrollmentCodeString + "\n" +
                "Mail: " + this.isSentToMail() + " : " + this.enrollmentCodeSentToMail + "\n" +
                "Phone: " + this.isSentToMail() + " : " + this.enrollmentCodeSentToPhone;
    }

    @Override
    public int hashCode() {
        return this.enrollmentCodeID.hashCode();
    }
}