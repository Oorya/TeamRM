package com.teamrm.teamrm.Type;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oorya on 13/01/2017.
 */

public class EnrollmentCode {


    @Exclude
    private static List<EnrollmentCode> enrollmentCodeList = new ArrayList<>();

    @Exclude
    public static final String ENROLLMENT_CODE_ID = "enrollmentCodeID";
    @Exclude
    public static final String ENROLLMENT_CODE_STRING = "enrollmentCodeString";
    @Exclude
    public static final String ENROLLMENT_CODE_COMPANY_ID = "enrollmentCodeCompanyId";
    @Exclude
    public static final String IS_SENT_TO_PHONE = "sentToPhone";
    //@Exclude
    //public static final String ENROLLMENT_CODE_SENT_TO_PHONE = "enrollmentCodeSentToPhone";
    @Exclude
    public static final String IS_SENT_TO_MAIL = "sentToMail";
    //@Exclude
    //public static final String ENROLLMENT_CODE_SENT_TO_MAIL = "enrollmentCodeSentToMail";
    @Exclude
    public static final String ENROLLMENT_STATUS = "enrollmentStatus";
    @Exclude
    public static final String ENROLLED_TECH_USER_ID = "enrolledTechUserID";

    @Exclude
    public static final int STATUS_ISSUED = 1000;
    @Exclude
    public static final int STATUS_PENDING = 2000;
    @Exclude
    public static final int STATUS_ACCEPTED = 3000;
    @Exclude
    public static final int STATUS_DECLINED = 4000;
    @Exclude
    public static final int STATUS_CANCELLED = 5000;
    @Exclude
    public static final int STATUS_FINALIZED = 6000;

    private String enrollmentCodeID;
    private String enrollmentCodeString;
    private String enrollmentCodeCompanyId;
    private boolean sentToPhone;
    //private String enrollmentCodeSentToPhone;
    private boolean sentToMail;
    //private String enrollmentCodeSentToMail;
    private int enrollmentStatus;
    private String enrolledTechUserID;

    public EnrollmentCode() {
    }

    public EnrollmentCode(@NonNull String companyID, @NonNull String enrollmentCodeString) {
        this.enrollmentCodeString = enrollmentCodeString;
        this.enrollmentCodeCompanyId = companyID;
        this.sentToMail = false;
        this.sentToPhone = false;
        //this.enrollmentCodeSentToMail = "";
        //this.enrollmentCodeSentToPhone = "";
        this.enrollmentStatus = STATUS_ISSUED;
        this.enrolledTechUserID = "";
    }

    public EnrollmentCode(String enrollmentCodeID, String enrollmentCodeString, String companyID,
                          boolean isSentToPhone, @Nullable String enrollmentCodeSentToPhone, boolean isSentToMail, @Nullable String enrollmentCodeSentToMail) {
        this.enrollmentCodeID = enrollmentCodeID;
        this.enrollmentCodeString = enrollmentCodeString;
        this.sentToPhone = isSentToPhone;
        //this.enrollmentCodeSentToPhone = enrollmentCodeSentToPhone;
        this.sentToMail = isSentToMail;
        //this.enrollmentCodeSentToMail = enrollmentCodeSentToMail;
    }

    public EnrollmentCode(EnrollmentCode other) {
        this.enrollmentCodeID = other.enrollmentCodeID;
        this.enrollmentCodeString = other.enrollmentCodeString;
        this.enrollmentCodeCompanyId = other.enrollmentCodeCompanyId;
        this.sentToPhone = other.sentToPhone;
        //this.enrollmentCodeSentToPhone = other.enrollmentCodeSentToPhone;
        this.sentToMail = other.sentToMail;
        //this.enrollmentCodeSentToMail = other.enrollmentCodeSentToMail;
        this.enrollmentStatus = other.enrollmentStatus;
        this.enrolledTechUserID = other.enrolledTechUserID;
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
        return sentToPhone;
    }

    public void setSentToPhone(boolean sentToPhone) {
        this.sentToPhone = sentToPhone;
    }

    /*public String getEnrollmentCodeSentToPhone() {
        return enrollmentCodeSentToPhone;
    }

    public void setEnrollmentCodeSentToPhone(String enrollmentCodeSentToPhone) {
        this.enrollmentCodeSentToPhone = enrollmentCodeSentToPhone;
    }*/

    public boolean isSentToMail() {
        return sentToMail;
    }

    public void setSentToMail(boolean sentToMail) {
        this.sentToMail = sentToMail;
    }

   /* public String getEnrollmentCodeSentToMail() {
        return enrollmentCodeSentToMail;
    }

    public void setEnrollmentCodeSentToMail(String enrollmentCodeSentToMail) {
        this.enrollmentCodeSentToMail = enrollmentCodeSentToMail;
    }*/

    public static List<EnrollmentCode> getEnrollmentCodeList() {
        return enrollmentCodeList;
    }

    public static void clearEnrollmentCodeList() {
        enrollmentCodeList.clear();
    }

    public static void setenrollmentCodeList(List<EnrollmentCode> enrollmentCodes) {
        enrollmentCodeList = enrollmentCodes;
    }

    public static void addEnrollmentCodeToList(EnrollmentCode enrollmentCode) {

        enrollmentCodeList.add(enrollmentCode);
    }

    public static void removeEnrollmentCodeFromList(EnrollmentCode enrollmentCode) {
        enrollmentCodeList.remove(enrollmentCodeList.indexOf(enrollmentCode));
    }

    public static void changeEnrollmentCodeInList(EnrollmentCode enrollmentCode) {
        enrollmentCodeList.set(enrollmentCodeList.indexOf(enrollmentCode), enrollmentCode);
    }

    public int getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public String getEnrolledTechUserID() {
        return enrolledTechUserID;
    }

    public void setEnrolledTechUserID(String enrolledTechUserID) {
        this.enrolledTechUserID = enrolledTechUserID;
    }

    @Override
    public String toString() {
        return this.enrollmentCodeID + "\n" + this.enrollmentCodeString + "\n" +
                "Sent to phone: " + Boolean.toString(this.sentToPhone) + "\n" +
                "Sent to mail: " + Boolean.toString(this.sentToMail);

                //"Mail: " + this.sentToMail() + " : " + this.enrollmentCodeSentToMail + "\n" +
                //"Phone: " + this.sentToMail() + " : " + this.enrollmentCodeSentToPhone;
    }

    @Override
    public int hashCode() {
        return this.enrollmentCodeID.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EnrollmentCode)) {
            return false;
        }
        EnrollmentCode other = (EnrollmentCode) obj;
        return enrollmentCodeID.equals(other.enrollmentCodeID);
    }

}
