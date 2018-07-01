package com.salomon.tehilah.journalapp.noteslist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.salomon.tehilah.journalapp.database.AppDatabase;
import com.salomon.tehilah.journalapp.database.NoteEntry;

import java.util.List;

/**
 * Created by Salomon KABONGO on 30-Jun-18.
 */
public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<NoteEntry>> notes;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving notes from the DataBase");
        notes = database.noteDao().loadAllNotes();
    }

    public LiveData<List<NoteEntry>> getNotes() {
        return notes;
    }
}
