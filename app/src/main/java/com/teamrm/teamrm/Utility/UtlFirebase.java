package com.teamrm.teamrm.Utility;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.teamrm.teamrm.Fragment.TicketView;
import com.teamrm.teamrm.Interfaces.ClientCallback;
import com.teamrm.teamrm.Interfaces.CompanyCallback;
import com.teamrm.teamrm.Interfaces.EnrollmentCodeCallback;
import com.teamrm.teamrm.Interfaces.EnrollmentCodeSingleCallback;
import com.teamrm.teamrm.Interfaces.EnrollmentCodesObservable;
import com.teamrm.teamrm.Interfaces.FireBaseAble;
import com.teamrm.teamrm.Interfaces.FireBaseBooleanCallback;
import com.teamrm.teamrm.Interfaces.TechniciansObservable;
import com.teamrm.teamrm.Interfaces.TicketStateObservable;
import com.teamrm.teamrm.Interfaces.WorkShiftCallback;
import com.teamrm.teamrm.Type.Admin;
import com.teamrm.teamrm.Type.Category;
import com.teamrm.teamrm.Type.Chat;
import com.teamrm.teamrm.Type.Client;
import com.teamrm.teamrm.Type.Company;
import com.teamrm.teamrm.Type.EnrollmentCode;
import com.teamrm.teamrm.Type.Product;
import com.teamrm.teamrm.Type.Region;
import com.teamrm.teamrm.Type.Technician;
import com.teamrm.teamrm.Type.Ticket;
import com.teamrm.teamrm.Type.TicketLite;
import com.teamrm.teamrm.Type.TicketState;
import com.teamrm.teamrm.Type.Users;
import com.teamrm.teamrm.Type.WorkShift;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.teamrm.teamrm.Type.TicketState.STATELISTENERTAG;
import static com.teamrm.teamrm.Utility.UserSingleton.LOGINTAG;
import static com.teamrm.teamrm.Utility.UserSingleton.TE_SEQ;


public class UtlFirebase {
    private static String ticketStateString;
    private static List<Ticket> ticketList = new ArrayList<>();
    private static List<TicketLite> ticketLiteList = new ArrayList<>();
    private static Map<Query, ValueEventListener> activeValueEventListeners = new HashMap<>(3);
    private static Map<DatabaseReference, ChildEventListener> activeChildEventListeners = new HashMap<>(3);
    private static Technician techFromFirebase;

    private static final String COMPANY_ROOT_REFERENCE_STRING = "Companies";
    private static final String COMPANY_PRODUCTS_ROOT_REFERENCE_STRING = "CompanyProducts";
    private static final String COMPANY_CATEGORIES_ROOT_REFERENCE_STRING = "CompanyCategories";
    private static final String COMPANY_REGIONS_ROOT_REFERENCE_STRING = "CompanyRegions";
    private static final String COMPANY_WORKSHIFTS_ROOT_REFERENCE_STRING = "CompanyWorkShifts";
    private static final String COMPANY_TECHNICIANS_ROOT_REFERENCE_STRING = "CompanyTechnicians";
    private static final String TECHNICIAN_ENROLLMENT_CODES_STRING = "TechnicianEnrollmentCodes";
    private static final String TICKET_ROOT_REFERENCE_STRING = "Tickets";
    private static final String TICKET_LITE_ROOT_REFERENCE_STRING = "TicketLites";
    private static final String CLIENT_TICKET_STATES_REFERENCE_STRING = "TicketStatesClient";
    private static final String COMPANY_TICKET_STATES_REFERENCE_STRING = "TicketStatesCompany";
    private static final String USERS_ROOT_REFERENCE_STRING = "Users";
    private static final String CLIENT_COMPANIES_ROOT_REFERENCE_STRING = "ClientCompanies";

    private static final DatabaseReference GLOBAL_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference();
    private static final DatabaseReference COMPANY_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference(COMPANY_ROOT_REFERENCE_STRING);
    private static final DatabaseReference COMPANY_PRODUCTS_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference(COMPANY_PRODUCTS_ROOT_REFERENCE_STRING);
    private static final DatabaseReference COMPANY_CATEGORIES_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference(COMPANY_CATEGORIES_ROOT_REFERENCE_STRING);
    private static final DatabaseReference COMPANY_REGIONS_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference(COMPANY_REGIONS_ROOT_REFERENCE_STRING);
    private static final DatabaseReference COMPANY_WORKSHIFTS_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference(COMPANY_WORKSHIFTS_ROOT_REFERENCE_STRING);
    private static final DatabaseReference COMPANY_TECHNICIANS_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference(COMPANY_TECHNICIANS_ROOT_REFERENCE_STRING);
    private static final DatabaseReference TECHNICIAN_ENROLLMENT_CODES_REFERENCE = FirebaseDatabase.getInstance().getReference(TECHNICIAN_ENROLLMENT_CODES_STRING);
    private static final DatabaseReference TICKET_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference(TICKET_ROOT_REFERENCE_STRING);
    private static final DatabaseReference TICKET_LITE_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference(TICKET_LITE_ROOT_REFERENCE_STRING);
    private static final DatabaseReference CLIENT_TICKET_STATES_REFERENCE = FirebaseDatabase.getInstance().getReference(CLIENT_TICKET_STATES_REFERENCE_STRING);
    private static final DatabaseReference COMPANY_TICKET_STATES_REFERENCE = FirebaseDatabase.getInstance().getReference(COMPANY_TICKET_STATES_REFERENCE_STRING);
    private static final DatabaseReference USERS_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference(USERS_ROOT_REFERENCE_STRING);
    private static final DatabaseReference CLIENT_COMPANIES_ROOT_REFERENCE = FirebaseDatabase.getInstance().getReference(CLIENT_COMPANIES_ROOT_REFERENCE_STRING);
    private static final StorageReference STORAGE_REFERENCE = FirebaseStorage.getInstance().getReferenceFromUrl("gs://teamrm-b1c06.appspot.com");

    final static String TAG = ":::UtlFirebase:::";

    public UtlFirebase() {
    }


///////////////////////////// User /////////////////////////////

    public static void loginUser(final FirebaseUser firebaseUser, final FireBaseAble fbHelper) {
        Log.d(LOGINTAG, "Stage 5, check if in firebase exists " + firebaseUser.getEmail());
        final Query queryUserExists = USERS_ROOT_REFERENCE.orderByChild(Users.USER_EMAIL).equalTo(firebaseUser.getEmail());
        queryUserExists.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Log.d(LOGINTAG, "Stage 6a, no such user " + firebaseUser.getEmail() + " exists, creating one");
                    Client clientToAdd = new Client(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail(), "", "");
                    UtlFirebase.addClient(clientToAdd);
                    fbHelper.resultUser(clientToAdd);
                } else {
                    for (final DataSnapshot item : dataSnapshot.getChildren()) {
                        if (item.child(Users.USER_STATUS).exists()) {
                            switch (item.child(Users.USER_STATUS).getValue().toString()) {
                                case Users.STATUS_CLIENT:
                                    Log.d(LOGINTAG, "Stage 6b, found Client");
                                    fbHelper.resultUser(item.getValue(Client.class));                              // push current dataSnapshot as Client to the UserSingleton
                                    break;

                                case Users.STATUS_TECH:
                                    Log.d(LOGINTAG, "Stage 6b, found Tech");
                                    Query techRef = COMPANY_TECHNICIANS_ROOT_REFERENCE                              //
                                            .child(dataSnapshot.child(Users.ASSIGNED_COMPANY_ID).getValue().toString())   // get Technician from the corresponding path
                                            .child(dataSnapshot.child(Users.USER_ID).getValue().toString());             //
                                    techRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                techFromFirebase = item.getValue(Technician.class);                 // acquire Technician from the new dataSnapshot
                                            } else {
                                                fbHelper.resultUser(item.getValue(Client.class));
                                                Log.e(LOGINTAG, "Stage 6b, Tech validation error");
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            toastTheError(databaseError);
                                        }
                                    });

                                    fbHelper.resultUser(techFromFirebase);                                          // push acquired Technician to the UserSingleton
                                    break;
                                case Users.STATUS_ADMIN:
                                    Log.d(LOGINTAG, "Stage 6b, found Admin");
                                    if (item.child(Users.USER_IS_ADMIN).getValue().equals(true)) {                             // check flag isAdmin
                                        fbHelper.resultUser(item.getValue(Admin.class));
                                    } else {
                                        fbHelper.resultUser(item.getValue(Client.class));
                                        Log.e(LOGINTAG, "Stage 6b, Admin validation error");
                                    }
                                    break;
                                default:
                                    fbHelper.resultUser(item.getValue(Client.class));                       // push current dataSnapshot as Client to the UserSingleton
                                    break;
                            }
                        } else {
                            Log.d(LOGINTAG, "Stage 6: Singleton undefined! fallback, logging in as Client!");
                            fbHelper.resultUser(item.getValue(Client.class));                       // push current dataSnapshot as Client to the UserSingleton
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        });
    }


    private static void addClient(final Users user) {
        Log.d(LOGINTAG, "Stage 6a, adding user " + user.getUserEmail());
        USERS_ROOT_REFERENCE.child(user.getUserID()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                String addedUser = "Added user:\n";
                addedUser += user.getUserEmail() + "\n";
                addedUser += user.getUserNameString();
                toastSuccessOrError(addedUser, databaseError);
            }

        });
        Log.d("SET USER::", user.toString());
    }


    public static void getUserByEmail(final String email, Object object) {

        final FireBaseAble fireBaseAble = (FireBaseAble) object;

        Query query = USERS_ROOT_REFERENCE.orderByChild(Users.USER_EMAIL).equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Users user = item.getValue(Users.class);
                    fireBaseAble.resultUser(user);
                    Log.e("ON DATA CHANGE ", user == null ? "NULL" : "NOT NULL");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void getClientByID(String clientID, final ClientCallback clientCallback) {
        USERS_ROOT_REFERENCE.child(clientID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clientCallback.clientCallback(dataSnapshot.getValue(Client.class));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        });
    }

    public static void removeUser(String userID) {
        USERS_ROOT_REFERENCE.child(userID).removeValue();
    }

    public static void updateUser(String userId, final Map<String, Object> updates) {
        USERS_ROOT_REFERENCE.child(userId).updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                toastSuccessOrError(updates.toString(), databaseError);
            }
        });
    }

    public static void registerUserAsAdmin(final String userID, String userStatus) {
        Map updates = new HashMap();
        if (userStatus.equals(Users.STATUS_ADMIN)) {
            updates.clear();
            updates.put(Users.USER_STATUS, Users.STATUS_ADMIN);
            updates.put(Users.USER_IS_ADMIN, true);
            USERS_ROOT_REFERENCE.child(userID).updateChildren(updates, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    toastSuccessOrError("User set as admin", databaseError);
                }
            });
        }
    }

    public static void setAssignedCompanyID(String userID, String companyID) {
        USERS_ROOT_REFERENCE.child(userID).child(Users.ASSIGNED_COMPANY_ID).setValue(companyID);
    }

    public static void setUserImg(String userID, String imgPath) {
        USERS_ROOT_REFERENCE.child(userID).child(Users.USER_IMG_PATH).setValue(imgPath);
    }

///////////////////////////// Technician -> EnrollmentCode /////////////////////////////

    public static void addEnrollmentCode() {
        EnrollmentCode enrollmentCode = new EnrollmentCode(UserSingleton.getInstance().getAssignedCompanyID(), UUID.randomUUID().toString().substring(0, 7).toUpperCase());
        DatabaseReference ref = TECHNICIAN_ENROLLMENT_CODES_REFERENCE.push();
        enrollmentCode.setEnrollmentCodeID(ref.getKey());
        ref.setValue(enrollmentCode, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.d(TE_SEQ, "Stage 01+02");
                toastSuccessOrError("Enrollment code added successfully", databaseError);
            }
        });
    }

    public static void getEnrollmentCodeByString(String enrollmentCodeString, final EnrollmentCodeSingleCallback ecCallback) {
        Query query = TECHNICIAN_ENROLLMENT_CODES_REFERENCE.orderByChild(EnrollmentCode.ENROLLMENT_CODE_STRING).equalTo(enrollmentCodeString);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 1) {
                    ecCallback.enrollmentCodeCallback(dataSnapshot.getValue(EnrollmentCode.class));
                } else {
                    new NiceToast(getAppContext(), "Enrollment code problem, please ask for another code", NiceToast.NICETOAST_ERROR, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        });
    }

    public static void getEnrollmentCodes(String companyID, final EnrollmentCodeCallback enrollmentCodeCallback) {
        final List<EnrollmentCode> enrollmentCodeList = new ArrayList<>();
        TECHNICIAN_ENROLLMENT_CODES_REFERENCE.orderByChild(EnrollmentCode.ENROLLMENT_CODE_COMPANY_ID).equalTo(companyID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                enrollmentCodeList.clear();
                for (DataSnapshot enrollmentcode : dataSnapshot.getChildren()) {
                    enrollmentCodeList.add(enrollmentcode.getValue(EnrollmentCode.class));
                }
                enrollmentCodeCallback.enrollmentCodeCallback((ArrayList<EnrollmentCode>) enrollmentCodeCallback);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public static void checkEnrollmentCodes(final String companyID, final FireBaseBooleanCallback fbCallback) {
        TECHNICIAN_ENROLLMENT_CODES_REFERENCE.orderByChild(EnrollmentCode.ENROLLMENT_CODE_COMPANY_ID).equalTo(companyID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    fbCallback.booleanCallback(true);
                } else {
                    fbCallback.booleanCallback(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fbCallback.booleanCallback(false);
            }
        });
    }

    public static void enrollmentCodeListener(String companyID, final EnrollmentCodesObservable enrollmentCodesObservable) {
        Query query = TECHNICIAN_ENROLLMENT_CODES_REFERENCE.orderByChild(EnrollmentCode.ENROLLMENT_CODE_COMPANY_ID).equalTo(companyID);
        if (!EnrollmentCode.getEnrollmentCodeList().isEmpty()) {
            EnrollmentCode.setenrollmentCodeList(Collections.EMPTY_LIST);
        }

        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                enrollmentCodesObservable.onEnrollmentCodeAdded(dataSnapshot.getValue(EnrollmentCode.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                enrollmentCodesObservable.onEnrollmentCodeChanged(dataSnapshot.getValue(EnrollmentCode.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                enrollmentCodesObservable.onEnrollmentCodeRemoved(dataSnapshot.getValue(EnrollmentCode.class));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                // irrelevant
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        };

        query.addChildEventListener(listener);
        activeChildEventListeners.put(query.getRef(), listener);
    }

    public static void updateEnrollmentCode(String enrollmentCodeID, HashMap<String, Object> updateFields) {
        Map updates = new HashMap();
        /*for (Map.Entry<String, String> field : updateFields.entrySet()) {
            updates.put(TICKET_ROOT_REFERENCE_STRING + "/" + ticketID + "/" + field.getKey(), field.getValue());
            updates.put(TICKET_LITE_ROOT_REFERENCE_STRING + "/" + ticketID + "/" + field.getKey(), field.getValue());
        }*/
        //Log.d("updateTicket", updates.toString());
        TECHNICIAN_ENROLLMENT_CODES_REFERENCE.child(enrollmentCodeID).updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                toastSuccessOrError("update successful", databaseError);
            }
        });
    }

    public static void removeEnrollmentCode(EnrollmentCode enrollmentCode) {
        TECHNICIAN_ENROLLMENT_CODES_REFERENCE.child(enrollmentCode.getEnrollmentCodeID()).removeValue();
    }

///////////////////////////// Technician -> Technician /////////////////////////////

    public static void registerAsTechnician(final String enrollmentCodeString, final FireBaseBooleanCallback resultCallback) {
        getEnrollmentCodeByString(enrollmentCodeString, new EnrollmentCodeSingleCallback() {
            @Override
            public void enrollmentCodeCallback(EnrollmentCode ecObject) {
                Map<String, Object> updates = new HashMap<>();
                updates.put(USERS_ROOT_REFERENCE_STRING + "/" + (UserSingleton.getInstance().getUserID()) + "/" + (Users.USER_STATUS), Users.STATUS_PENDING_TECH);
                updates.put(TECHNICIAN_ENROLLMENT_CODES_STRING + "/" + ecObject.getEnrollmentCodeID() + "/" + EnrollmentCode.ENROLLMENT_STATUS, EnrollmentCode.STATUS_PENDING);
                updates.put(TECHNICIAN_ENROLLMENT_CODES_STRING + "/" + ecObject.getEnrollmentCodeID() + "/" + EnrollmentCode.ENROLLED_TECH_USER_ID, UserSingleton.getInstance().getUserID());

                GLOBAL_ROOT_REFERENCE.updateChildren(updates, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        toastSuccessOrError("Successfully registered as Technician", databaseError);
                        resultCallback.booleanCallback(true);
                    }
                });
                /*DatabaseReference companyTechRef = COMPANY_TECHNICIANS_ROOT_REFERENCE.child(ecObject.getEnrollmentCodeCompanyId()); // reference the company from EnrollmentCode
                Users myUser = UserSingleton.getInstance();
                companyTechRef.child(myUser.getUserID()).setValue(new Technician(enrollmentCodeString, ecObject.getEnrollmentCodeCompanyId(), UserSingleton.getLoadedUserObject())); //
                TECHNICIAN_ENROLLMENT_CODES_REFERENCE.child(ecObject.getEnrollmentCodeID()).removeValue(); //*/
            }
        });
    }

   /* public static void getCompanyTechnicians(String companyID, TechnicianCallback technicianCallback) {
        final List<Technician> technicianList = new ArrayList<>();
        getEnrollmentCodes();
        if ()
        COMPANY_TECHNICIANS_ROOT_REFERENCE.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                enrollmentCodeList.clear();
                for (DataSnapshot enrollmentcode : dataSnapshot.getChildren()) {
                    enrollmentCodeList.add(enrollmentcode.getValue(EnrollmentCode.class));
                }
                enrollmentCodeCallback.enrollmentCodeCallback((ArrayList<EnrollmentCode>) enrollmentCodeCallback);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }*/

    public static void getCompanyTechniciansForEdit(String companyID, final TechniciansObservable techniciansObservable) {
        final List<Technician> techicianList = new ArrayList<>();
        DatabaseReference ref = COMPANY_TECHNICIANS_ROOT_REFERENCE.child(companyID);

        ChildEventListener cListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                techniciansObservable.onTechnicianAdded(dataSnapshot.getValue(Technician.class));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                techniciansObservable.onTechnicianChanged(dataSnapshot.getValue(Technician.class));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                techniciansObservable.onTechnicianRemoved(dataSnapshot.getValue(Technician.class));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        };

        ref.addChildEventListener(cListener);
        activeChildEventListeners.put(ref, cListener);
    }

    public void updateTechnician(String companyID, String technicianID, HashMap<String, Object> updates) {
        COMPANY_TECHNICIANS_ROOT_REFERENCE.child(companyID).child(technicianID).updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                toastSuccessOrError("Successfully updated technician", databaseError);
            }
        });
    }

    /*public static void addTechnician(String companyID, final Technician technician) {
        USERS_ROOT_REFERENCE.child(user.getUserID()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                String addedUser = "Added user:\n";
                addedUser += user.getUserEmail() + "\n";
                addedUser += user.getUserNameString();
                toastSuccessOrError(addedUser, databaseError);
            }

        });
        Log.d("SET USER::", user.toString());
        COMPANY_TECHNICIANS_ROOT_REFERENCE.child(companyID).child(technician.getUserID()).setValue(technician, new
                DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        String addedUser = "Added tech:\n";
                        addedUser += technician.getUserEmail() + "\n";
                        addedUser += technician.getUserNameString();
                        toastSuccessOrError(addedUser, databaseError);
                    }
                });
    }

    }*/

///////////////////////////// Ticket /////////////////////////////


    public static void ticketStateListener(final TicketStateObservable ticketStateObserver) {
        DatabaseReference stateRef = null;

        switch (UserSingleton.getLoadedUserType()) {
            case "Client":
                stateRef = CLIENT_TICKET_STATES_REFERENCE.child(UserSingleton.getInstance().getUserID());
                break;

            case "Admin":
                stateRef = COMPANY_TICKET_STATES_REFERENCE.child(UserSingleton.getInstance().getAssignedCompanyID());
                break;

            case "Technician":
                stateRef = COMPANY_TICKET_STATES_REFERENCE.child(UserSingleton.getInstance().getAssignedCompanyID());
                break;

            case "undefined":
                Log.e(TAG, "StateListener:::UserSingleton undefined");
                break;

            default:
                Log.e(TAG, "StateListener:::UserSingleton undefined");
                break;
        }


        ChildEventListener stateListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot ticketState, String s) {
                Log.d(STATELISTENERTAG, "Ticketstate from FireBase: " + ticketState.toString());
                ticketStateObserver.onTicketAdded(new TicketState(ticketState.getKey(), ticketState.getValue().toString()));
            }

            @Override
            public void onChildChanged(DataSnapshot ticketState, String s) {
                Log.d(STATELISTENERTAG, "Ticketstate changed in FireBase: " + ticketState.toString());
                ticketStateObserver.onTicketStateChanged(new TicketState(ticketState.getKey(), ticketState.getValue().toString()));
            }

            @Override
            public void onChildRemoved(DataSnapshot ticketState) {
                Log.d(STATELISTENERTAG, "Ticketstate removed from FireBase: " + ticketState.toString());
                ticketStateObserver.onTicketRemoved(new TicketState(ticketState.getKey(), ticketState.getValue().toString()));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                //irrelevant
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        };

        try {
            stateRef.addChildEventListener(stateListener);
            activeChildEventListeners.put(stateRef, stateListener);
        } catch (Exception e) {
            Log.e(STATELISTENERTAG, "Could not attach stateListener");
            e.printStackTrace();
        }
    }

    public static void addTicket(final Ticket ticket) {
        Map updates = new HashMap();
        updates.put(TICKET_ROOT_REFERENCE_STRING + "/" + ticket.getTicketID(), ticket);
        updates.put(TICKET_LITE_ROOT_REFERENCE_STRING + "/" + ticket.getTicketID(), new TicketLite(ticket));
        if (ticket.getClientID() != null) {
            updates.put(CLIENT_TICKET_STATES_REFERENCE_STRING + "/" + ticket.getClientID() + "/" + ticket.getTicketID(), ticket.getTicketStateString());
        }
        updates.put(COMPANY_TICKET_STATES_REFERENCE_STRING + "/" + ticket.getCompanyID() + "/" + ticket.getTicketID(), ticket.getTicketStateString());
        GLOBAL_ROOT_REFERENCE.updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                toastSuccessOrError("Added ticket " + ticket.getTicketNumber(), databaseError);
            }
        });
    }

    public static void updateTicketStateString(Ticket ticket, String ticketStateString) {
        Map updates = new HashMap();
        updates.put(TICKET_ROOT_REFERENCE_STRING + "/" + ticket.getTicketID() + "/" + Ticket.TICKET_STATE_STRING, ticketStateString);
        updates.put(TICKET_LITE_ROOT_REFERENCE_STRING + "/" + ticket.getTicketID() + "/" + Ticket.TICKET_STATE_STRING, ticketStateString);
        if (ticket.getClientID() != null) {
            updates.put(CLIENT_TICKET_STATES_REFERENCE_STRING + "/" + UserSingleton.getInstance().getUserID() + "/" + ticket.getTicketID(), ticketStateString);
        }
        updates.put(COMPANY_TICKET_STATES_REFERENCE_STRING + "/" + ticket.getCompanyID() + "/" + ticket.getTicketID(), ticketStateString);
        GLOBAL_ROOT_REFERENCE.updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    toastTheError(databaseError);
                }
            }
        });
    }


    public static void getAllTickets(final FireBaseAble fbHelper) {
        switch (UserSingleton.getLoadedUserType()) {
            case "Client":
                getAllClientTickets(UserSingleton.getInstance().getUserID(), fbHelper);
                break;

            case "Admin":
                getAllCompanyTickets(UserSingleton.getInstance().getAssignedCompanyID(), fbHelper);
                break;

            case "Technician":
                getAllCompanyTickets(UserSingleton.getInstance().getAssignedCompanyID(), fbHelper);
                break;

            case "undefined":
                Log.e(TAG, "getAllTickets:::UserSingleton undefined");
                break;

            default:
                Log.e(TAG, "getAllTickets:::UserSingleton undefined");
                break;
        }

    }

    public static void getAllTicketLites(final FireBaseAble fbHelper) {

        switch (UserSingleton.getLoadedUserType()) {
            case "Client":
                getAllClientTicketLites(UserSingleton.getInstance().getUserID(), fbHelper);
                break;

            case "Admin":
                getAllCompanyTicketLites(UserSingleton.getInstance().getAssignedCompanyID(), fbHelper);
                break;

            case "Technician":
                getAllCompanyTicketLites(UserSingleton.getInstance().getAssignedCompanyID(), fbHelper);
                break;

            case "undefined":
                Log.e(TAG, "getAllTicketlites:::UserSingleton undefined");
                break;

            default:
                Log.e(TAG, "getAllTicketlites:::UserSingleton undefined");
                break;
        }

    }

    private static void getAllClientTickets(String clientID, final FireBaseAble fbHelper) {
        Log.d("getAllClientTickets", ":::called:::");
        Query query = TICKET_ROOT_REFERENCE.orderByChild(Ticket.CLIENT_ID).equalTo(clientID);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Ticket retrievedTicket = item.getValue(Ticket.class);
                    ticketList.add(retrievedTicket);
                }
                fbHelper.ticketListCallback(ticketList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        };
        query.addValueEventListener(listener);
        activeValueEventListeners.put(query, listener);
    }

    private static void getAllClientTicketLites(String clientID, final FireBaseAble fbHelper) {
        Query query = TICKET_LITE_ROOT_REFERENCE.orderByChild(Ticket.CLIENT_ID).equalTo(clientID);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketLiteList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    TicketLite retrievedTicket = item.getValue(TicketLite.class);
                    ticketLiteList.add(retrievedTicket);
                }
                fbHelper.ticketLiteListCallback(ticketLiteList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        };
        query.addValueEventListener(listener);
        activeValueEventListeners.put(query, listener);
    }


    public static void updateTicket(String ticketID, HashMap<String, String> updateFields) {
        Map updates = new HashMap();
        for (Map.Entry<String, String> field : updateFields.entrySet()) {
            updates.put(TICKET_ROOT_REFERENCE_STRING + "/" + ticketID + "/" + field.getKey(), field.getValue());
            updates.put(TICKET_LITE_ROOT_REFERENCE_STRING + "/" + ticketID + "/" + field.getKey(), field.getValue());


        }
        //Log.d("updateTicket", updates.toString());
        GLOBAL_ROOT_REFERENCE.updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                toastSuccessOrError("update successful", databaseError);
            }
        });
    }


    private static void getAllCompanyTickets(String companyID, final FireBaseAble fbHelper) {
        Query query = TICKET_ROOT_REFERENCE.orderByChild(Ticket.COMPANY_ID).equalTo(companyID);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Ticket retrievedTicket = item.getValue(Ticket.class);
                    ticketList.add(retrievedTicket);
                }
                fbHelper.ticketListCallback(ticketList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        };
        query.addValueEventListener(listener);
        activeValueEventListeners.put(query, listener);
    }

    private static void getAllCompanyTicketLites(String companyID, final FireBaseAble fbHelper) {
        Query query = TICKET_LITE_ROOT_REFERENCE.orderByChild(Ticket.COMPANY_ID).equalTo(companyID);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketLiteList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    TicketLite retrievedTicket = item.getValue(TicketLite.class);
                    ticketLiteList.add(retrievedTicket);
                }
                fbHelper.ticketLiteListCallback(ticketLiteList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        };
        query.addValueEventListener(listener);
        activeValueEventListeners.put(query, listener);
    }

    public static void getAllTechTickets(String companyID, String techID, final FireBaseAble fbHelper) {
        Query query = TICKET_ROOT_REFERENCE.orderByChild(Ticket.COMPANY_ID).equalTo(companyID).orderByChild(Ticket.TECH_ID).equalTo(techID);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Ticket retrievedTicket = item.getValue(Ticket.class);
                    ticketList.add(retrievedTicket);
                }
                fbHelper.ticketListCallback(ticketList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        };
        query.addValueEventListener(listener);
        activeValueEventListeners.put(query, listener);
    }

    public static void getAllTechTicketLites(String companyID, String techID, final FireBaseAble fbHelper) {
        Query query = TICKET_LITE_ROOT_REFERENCE.orderByChild(Ticket.COMPANY_ID).equalTo(companyID).orderByChild(Ticket.TECH_ID).equalTo(techID);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketLiteList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    TicketLite retrievedTicket = item.getValue(TicketLite.class);
                    ticketLiteList.add(retrievedTicket);
                }
                fbHelper.ticketLiteListCallback(ticketLiteList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        };
        query.addValueEventListener(listener);
        activeValueEventListeners.put(query, listener);
    }

    public static String getTicketStateString(String ticketID) {
        TICKET_ROOT_REFERENCE.child(ticketID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ticketStateString = dataSnapshot.child(Ticket.TICKET_STATE_STRING).getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        });

        return ticketStateString;
    }

    public static void updateTicketPresentation(String ticketID, int status) {
        Map updates = new HashMap();
        updates.put(TICKET_ROOT_REFERENCE_STRING + "/" + ticketID + "/" + Ticket.TICKET_PRESENTATION, status);
        updates.put(TICKET_LITE_ROOT_REFERENCE_STRING + "/" + ticketID + "/" + Ticket.TICKET_PRESENTATION, status);
        GLOBAL_ROOT_REFERENCE.updateChildren(updates, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    toastTheError(databaseError);
                }
            }
        });

    }

    public static void removeTicket(Ticket ticket) {
        Map updates = new HashMap();
        updates.put(TICKET_ROOT_REFERENCE_STRING + "/" + ticket.getTicketID(), null);
        updates.put(TICKET_LITE_ROOT_REFERENCE_STRING + "/" + ticket.getTicketID(), null);
        updates.put(CLIENT_TICKET_STATES_REFERENCE_STRING + "/" + ticket.getClientID() + "/" + ticket.getTicketID(), null);
        updates.put(COMPANY_TICKET_STATES_REFERENCE_STRING + "/" + ticket.getCompanyID() + "/" + ticket.getTicketID(), null);
    }

    public static void removeTicketLite(TicketLite ticketLite) {
        Map updates = new HashMap();
        updates.put(TICKET_ROOT_REFERENCE_STRING + "/" + ticketLite.getTicketID(), null);
        updates.put(TICKET_LITE_ROOT_REFERENCE_STRING + "/" + ticketLite.getTicketID(), null);
        updates.put(CLIENT_TICKET_STATES_REFERENCE_STRING + "/" + ticketLite.getClientID() + "/" + ticketLite.getTicketID(), null);
        updates.put(COMPANY_TICKET_STATES_REFERENCE_STRING + "/" + ticketLite.getCompanyID() + "/" + ticketLite.getTicketID(), null);
    }

    public static void removeMultipleTickets(List<String> ticketsIDList) {
        for (int counter = 0; counter < ticketsIDList.size(); counter++) {
            TICKET_ROOT_REFERENCE.child(ticketsIDList.get(counter)).removeValue();
        }
    }

    public static void removeAllTickets() {
        TICKET_ROOT_REFERENCE.removeValue();
        TICKET_LITE_ROOT_REFERENCE.removeValue();
        CLIENT_TICKET_STATES_REFERENCE.removeValue();
        COMPANY_TICKET_STATES_REFERENCE.removeValue();
    }

    public static void getTicketByKey(final String key, final FireBaseAble fbHelper) {
        Query query = TICKET_ROOT_REFERENCE.orderByKey().equalTo(key);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    fbHelper.resultTicket(item.getValue(Ticket.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        });
    }

///////////////////////////// Chat /////////////////////////////

    public static List<Chat> getChatByTicketID(String ticketID) {
        final List<Chat> chatList = new ArrayList<Chat>();
        //creating a reference to the database
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Chat");
        //DataSnapshot data = new DataSnapshot(myRef);
        Query query = myRef.child(ticketID);
        ValueEventListener listener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Chat retrievedChat = item.getValue(Chat.class);
                    chatList.add(retrievedChat);
                }
                //Log.e("LIST SIZE: ", chatList.size() + "");

                //Need to notify for current list adapter
                //ChatTicket.chatAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }

        };
        query.addValueEventListener(listener);
        activeValueEventListeners.put(query, listener);
        return chatList;
    }

    public static void removeChat(String ticketID) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Chat");
        myRef.child(ticketID).removeValue();
    }

///////////////////////////// Company /////////////////////////////

    public static List<Company> getAllCompanies(final FireBaseAble fbHelper) {

        final List<Company> companyList = new ArrayList<>();

        COMPANY_ROOT_REFERENCE.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                companyList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    Company retrieveCompany = item.getValue(Company.class);
                    companyList.add(retrieveCompany);
                }
                fbHelper.companyListCallback(companyList);
                //Log.e("LIST SIZE COMPANIES: ", companyList.size() + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        });
        //Log.e("ALL", "COMPANIES LIST");
        return companyList;
    }

    public static void getAllClientCompanies(final FireBaseAble fbHelper) {
        final List<Company> clientCompanies = new ArrayList<>();
        Query query = CLIENT_COMPANIES_ROOT_REFERENCE.orderByChild(UserSingleton.getInstance().getUserID());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot clientCompany : dataSnapshot.getChildren()) {
                    clientCompanies.add((Company) clientCompany.getValue());
                }
                fbHelper.companyListCallback(clientCompanies);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        });
    }

    public static void addCompany(final @NonNull Company company) {
        COMPANY_ROOT_REFERENCE.child(company.getCompanyId()).setValue(company, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                DatabaseReference userRef = USERS_ROOT_REFERENCE.child(UserSingleton.getInstance().getUserID());
                Map update = new HashMap();
                update.put(Users.ASSIGNED_COMPANY_ID, company.getCompanyId());
                userRef.updateChildren(update, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        new NiceToast(getAppContext(), "Created company " + company.getCompanyName() + "for Admin" + UserSingleton.getInstance().getUserEmail(), NiceToast.NICETOAST_INFORMATION, Toast.LENGTH_LONG);
                    }
                });
            }
        });
    }

    public static void getCompanyByID(String companyid, final CompanyCallback companyCallback) {
        COMPANY_ROOT_REFERENCE.child(companyid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                companyCallback.companyCallback(dataSnapshot.getValue(Company.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                toastTheError(databaseError);
            }
        });
    }

///////////////////////////// Product /////////////////////////////

    public static void addProduct(String companyID, String productName) {
        COMPANY_PRODUCTS_ROOT_REFERENCE.child(companyID).push().setValue(productName, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //Log.w("Firebase util ", "productName key " + databaseReference.getKey());
                if (databaseError != null) {
                    toastTheError(databaseError);
                }
            }
        });
    }

    public static void getProducts(String companyID, final FireBaseAble fbHelper) {
        final List<Product> productList = new ArrayList<>();
        COMPANY_PRODUCTS_ROOT_REFERENCE.child(companyID).orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    productList.add(new Product(item.getKey(), (String) item.getValue()));

                }
                fbHelper.productListCallback(productList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public static void getProductsForEdit(String companyID, final FireBaseAble fbHelper) {

        final List<Product> productList = new ArrayList<>();
        Query query = COMPANY_PRODUCTS_ROOT_REFERENCE.child(companyID).orderByValue();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!productList.isEmpty()) {
                    productList.clear();
                }
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    productList.add(new Product(item.getKey(), (String) item.getValue()));
                }
                fbHelper.productListCallback(productList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        query.addValueEventListener(listener);
        activeValueEventListeners.put(query, listener);
    }

    public static void updateProduct(String companyID, Product product, final String productUpdatedName) {
        COMPANY_PRODUCTS_ROOT_REFERENCE.child(companyID).child(product.getItemKey()).setValue(productUpdatedName, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //Log.d(TAG, "Updated productName " + productUpdatedName);
                if (databaseError != null) {
                    toastTheError(databaseError);
                }
            }
        });
    }

    public static void removeProduct(String companyID, Product product) {
        COMPANY_PRODUCTS_ROOT_REFERENCE.child(companyID).child(product.getItemKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    toastTheError(databaseError);
                }
            }
        });
    }

///////////////////////////// Category /////////////////////////////

    public static void addCategory(String companyID, String categoryName) {
        COMPANY_CATEGORIES_ROOT_REFERENCE.child(companyID).push().setValue(categoryName, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //Log.w("Firebase util ", "category key " + databaseReference.getKey());

            }
        });
    }

    public static List<Category> getCategories(String companyID, final FireBaseAble fbHelper) {
        final List<Category> categoryList = new ArrayList<>();
        COMPANY_CATEGORIES_ROOT_REFERENCE.child(companyID).orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                categoryList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    categoryList.add(new Category(item.getKey(), (String) item.getValue()));
                }
                fbHelper.categoryListCallback(categoryList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return categoryList;
    }

    public static void getCategoriesForEdit(String companyID, final FireBaseAble fbHelper) {

        final List<Category> categoryList = new ArrayList<>();
        Query query = COMPANY_CATEGORIES_ROOT_REFERENCE.child(companyID).orderByValue();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!categoryList.isEmpty()) {
                    categoryList.clear();
                }
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    categoryList.add(new Category(item.getKey(), (String) item.getValue()));
                }
                fbHelper.categoryListCallback(categoryList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        query.addValueEventListener(listener);
        activeValueEventListeners.put(query, listener);

    }

    public static void updateCategory(String companyID, Category category, String categoryUpdatedName) {
        COMPANY_CATEGORIES_ROOT_REFERENCE.child(companyID).child(category.getItemKey()).setValue(categoryUpdatedName, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    toastTheError(databaseError);
                }
            }
        });
    }

    public static void removeCategory(String companyID, Category category) {
        COMPANY_CATEGORIES_ROOT_REFERENCE.child(companyID).child(category.getItemKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    toastTheError(databaseError);
                }
            }
        });
    }

///////////////////////////// Region /////////////////////////////

    public static void addRegion(String companyID, String regionName) {
        COMPANY_REGIONS_ROOT_REFERENCE.child(companyID).push().setValue(regionName, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                //Log.w("Firebase util ", "region key " + databaseReference.getKey());
                if (databaseError != null) {
                    toastTheError(databaseError);
                }
            }
        });
    }

    public static List<Region> getRegions(String companyID, final FireBaseAble fbHelper) {
        final List<Region> regionList = new ArrayList<>();
        COMPANY_REGIONS_ROOT_REFERENCE.child(companyID).orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                regionList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    regionList.add(new Region(item.getKey(), (String) item.getValue()));
                }
                fbHelper.regionListCallback(regionList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return regionList;
    }

    public static void getRegionsForEdit(String companyID, final FireBaseAble fbHelper) {
        final List<Region> regionList = new ArrayList<>();
        Query query = COMPANY_REGIONS_ROOT_REFERENCE.child(companyID).orderByValue();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!regionList.isEmpty()) {
                    regionList.clear();
                }
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    regionList.add(new Region(item.getKey(), (String) item.getValue()));
                }
                fbHelper.regionListCallback(regionList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        query.addValueEventListener(listener);
        activeValueEventListeners.put(query, listener);
    }

    public static void updateRegion(String companyID, Region region, String regionUpdatedName) {
        COMPANY_REGIONS_ROOT_REFERENCE.child(companyID).child(region.getItemKey()).setValue(regionUpdatedName, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    toastTheError(databaseError);
                }
            }
        });
    }

    public static void removeRegion(String companyID, Region region) {
        COMPANY_REGIONS_ROOT_REFERENCE.child(companyID).child(region.getItemKey()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    toastTheError(databaseError);
                }
            }
        });
    }

    ///////////////////////////// WorkShift /////////////////////////////

    public static void addWorkShift(String companyID, WorkShift workShift) {
        DatabaseReference ref = COMPANY_WORKSHIFTS_ROOT_REFERENCE.child(companyID).push();
        workShift.setWorkShiftID(ref.getKey());
        ref.setValue(workShift, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    toastTheError(databaseError);
                }
            }
        });
    }

    public static void getWorkShifts(String companyID, final WorkShiftCallback workShiftCallback) {
        final List<WorkShift> workShiftList = new ArrayList<>();
        COMPANY_WORKSHIFTS_ROOT_REFERENCE.child(companyID).orderByValue().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                workShiftList.clear();
                for (DataSnapshot workShift : dataSnapshot.getChildren()) {
                    workShiftList.add(workShift.getValue(WorkShift.class));
                }
                workShiftCallback.workShiftFireBaseCallback((ArrayList<WorkShift>) workShiftList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    public static void getWorkShiftsForEdit(String companyID, final WorkShiftCallback workShiftCallback) {
        final List<WorkShift> workShiftList = new ArrayList<>();
        Query query = COMPANY_WORKSHIFTS_ROOT_REFERENCE.child(companyID).orderByValue();
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!workShiftList.isEmpty()) {
                    workShiftList.clear();
                }
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    workShiftList.add(item.getValue(WorkShift.class));
                }
                Log.d(TAG, "@getWorkShiftsForEdit: workShiftList before callback:" + workShiftList.size());
                workShiftCallback.workShiftFireBaseCallback((ArrayList<WorkShift>) workShiftList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        query.addValueEventListener(listener);
        activeValueEventListeners.put(query, listener);
    }

    public static void updateWorkShift(String companyID, WorkShift workShift) {
        COMPANY_WORKSHIFTS_ROOT_REFERENCE.child(companyID).child(workShift.getWorkShiftID()).setValue(workShift, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    toastTheError(databaseError);
                }
            }
        });
    }

    public static void removeWorkShift(String companyID, WorkShift workShift) {
        COMPANY_WORKSHIFTS_ROOT_REFERENCE.child(companyID).child(workShift.getWorkShiftID()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    toastTheError(databaseError);
                }
            }
        });
    }

///////////////////////////////// Storage /////////////////////////////

    //this method will upload the file
    public static void uploadFile(String path, Uri uriPic) {

        //if there is a file to upload
        if (uriPic != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(getAppContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            STORAGE_REFERENCE.child(path).putFile(uriPic)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successful
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            new NiceToast(getAppContext(), "File uploaded!", NiceToast.NICETOAST_INFORMATION, Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successful
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            new NiceToast(getAppContext(), "Could not upload file :(\n" + exception.getMessage(), NiceToast.NICETOAST_ERROR, Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            @SuppressWarnings("VisibleForTests")
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }

        //if there is not any file
        else {
            //you can display an error toast
        }
    }

    public static void downloadFile(String path, final int imgNum) {
        final long ONE_MEGABYTE = 1024 * 1024;
        STORAGE_REFERENCE.child(path).getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                switch (imgNum) {
                    case 1:
                        TicketView.img1.setImageBitmap(bitmap);
                        break;
                    case 2:
                        TicketView.img2.setImageBitmap(bitmap);
                        break;
                    default:
                        TicketView.img1.setImageBitmap(bitmap);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {

            }
        });
    }

///////////////////////////////// Generic methods /////////////////////////////

    public static Context getAppContext() {
        return App.getInstance().getApplicationContext();
    }

    public static void removeActiveListeners() {
        for (Map.Entry<Query, ValueEventListener> entry : activeValueEventListeners.entrySet()) {
            Query query = entry.getKey();
            ValueEventListener listener = entry.getValue();
            Log.d(TAG, "Removing active ValueEventListener " + listener + " @ " + query.getRef().toString());
            query.removeEventListener(listener);
        }
        for (Map.Entry<DatabaseReference, ChildEventListener> entry : activeChildEventListeners.entrySet()) {
            DatabaseReference ref = entry.getKey();
            ChildEventListener listener = entry.getValue();
            Log.d(TAG, "Removing active ChildEventListener " + listener + " @ " + ref.toString());
            ref.removeEventListener(listener);
        }
        if (!activeValueEventListeners.isEmpty()) {
            activeValueEventListeners.clear();
        }
        if (!activeChildEventListeners.isEmpty()) {
            activeChildEventListeners.clear();
        }
    }

    static void toastTheError(DatabaseError dbError) {
        new NiceToast(getAppContext(), "FireBase failed with error \n" + dbError.getCode() + "\n" + dbError.getMessage(), NiceToast.NICETOAST_ERROR, Toast.LENGTH_LONG).show();
    }

    static void toastSuccessOrError(String positiveMessage, @Nullable DatabaseError dbError) {
        if (dbError == null) {
            new NiceToast(getAppContext(), positiveMessage, NiceToast.NICETOAST_INFORMATION, Toast.LENGTH_SHORT).show();
        } else {
            new NiceToast(getAppContext(), "FireBase failed with error \n" + dbError.getCode() + "\n" + dbError.getMessage(), NiceToast.NICETOAST_ERROR, Toast.LENGTH_LONG).show();
        }
    }
}