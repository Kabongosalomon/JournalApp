package com.salomon.tehilah.journalapp.domain;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Salomon KABONGO on 27-Jun-18.
 */
public class FirebaseHelper {
    private DatabaseReference dataReference;
    private final static String SEPARATOR = "___";
    private final static String NOTES_PATH = "notes";
    private final static String USERS_PATH = "users";


    private static class SingletonHolder {
        private static final FirebaseHelper INSTANCE = new FirebaseHelper();
    }


    public static FirebaseHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public FirebaseHelper() {
        dataReference = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDataReference() {
        return dataReference;
    }

    public String getAuthUserEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = null;
        if (user != null) {
            email = user.getEmail();
        }
        return email;
    }


    public DatabaseReference getUserReference(String email) {
        DatabaseReference userReference = null;
        if (email != null) {
            String emailKey = email.replace(".", "_");
            userReference = dataReference.getRoot().child(USERS_PATH).child(emailKey);
        }
        return userReference;
    }

    public DatabaseReference getMyUserReference() {
        return getUserReference(getAuthUserEmail());
    }

    public DatabaseReference getNotesReference(String email) {
        return getUserReference(email).child(NOTES_PATH);
    }


}
