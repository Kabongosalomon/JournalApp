package com.salomon.tehilah.journalapp;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Salomon KABONGO on 29-Jun-18.
 */
public class JournalApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        setupFirebase();
    }

    private void setupFirebase(){
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

}
