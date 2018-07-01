package com.salomon.tehilah.journalapp.noteslist;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.salomon.tehilah.journalapp.database.AppDatabase;

/**
 * Created by Salomon KABONGO on 30-Jun-18.
 */
public class AddNoteViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mNoteId;


    public AddNoteViewModelFactory(AppDatabase database, int noteId) {
        mDb = database;
        mNoteId = noteId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddNoteViewModel(mDb, mNoteId);
    }
}
