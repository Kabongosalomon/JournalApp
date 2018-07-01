package com.salomon.tehilah.journalapp.noteslist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.salomon.tehilah.journalapp.database.AppDatabase;
import com.salomon.tehilah.journalapp.database.NoteEntry;

/**
 * Created by Salomon KABONGO on 30-Jun-18.
 */
public class AddNoteViewModel extends ViewModel {

    private LiveData<NoteEntry> note;

    // Note: The constructor should receive the database and the taskId
    public AddNoteViewModel(AppDatabase database, int noteId) {
        note = database.noteDao().loadNoteById(noteId);
    }

    public LiveData<NoteEntry> getNote() {
        return note;
    }
}
