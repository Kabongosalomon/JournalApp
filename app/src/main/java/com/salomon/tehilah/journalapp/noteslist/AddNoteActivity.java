package com.salomon.tehilah.journalapp.noteslist;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.salomon.tehilah.journalapp.R;
import com.salomon.tehilah.journalapp.database.AppDatabase;
import com.salomon.tehilah.journalapp.database.NoteEntry;
import com.salomon.tehilah.journalapp.domain.AppExecutors;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNoteActivity extends AppCompatActivity {

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_NOTE_ID = "extraNoteId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_NOTE_ID = "instanceNoteId";

    // Constant for default note id to be used when not in update mode
    private static final int DEFAULT_NOTE_ID = -1;
    // Constant for logging
    private static final String TAG = AddNoteActivity.class.getSimpleName();

    // Fields for views

    EditText mtexAddNotesTitle;
    EditText mtexAddNotesDescription;
    Button mButton;


    private int mNoteId = DEFAULT_NOTE_ID;

    // Member variable for the Database
    private AppDatabase mDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        ButterKnife.bind(this);

        initViews();

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_NOTE_ID)) {
            mNoteId = savedInstanceState.getInt(INSTANCE_NOTE_ID, DEFAULT_NOTE_ID);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_NOTE_ID)) {
            mButton.setText(R.string.update_button);
            if (mNoteId == DEFAULT_NOTE_ID) {
                // populate the UI
                mNoteId = intent.getIntExtra(EXTRA_NOTE_ID, DEFAULT_NOTE_ID);

                AddNoteViewModelFactory factory = new AddNoteViewModelFactory(mDb, mNoteId);

                // for that use the factory created above AddTaskViewModel
                final AddNoteViewModel viewModel
                        = ViewModelProviders.of(this, factory).get(AddNoteViewModel.class);

                viewModel.getNote().observe(this, new Observer<NoteEntry>() {
                    @Override
                    public void onChanged(@Nullable NoteEntry noteEntry) {
                        viewModel.getNote().removeObserver(this);
                        populateUI(noteEntry);
                    }
                });
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_NOTE_ID, mNoteId);
        super.onSaveInstanceState(outState);
    }

    /**
     * initViews is called from onCreate to init the member variable views
     */
    private void initViews() {
        mtexAddNotesTitle = findViewById(R.id.editTextNoteTitle);
        mtexAddNotesDescription = findViewById(R.id.editTextNoteContent);
        mButton = findViewById(R.id.saveButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });
    }

    /**
     * populateUI would be called to populate the UI when in update mode
     *
     * @param note the taskEntry to populate the UI
     */
    private void populateUI(NoteEntry note) {
        if (note == null) {
            return;
        }
        mtexAddNotesTitle.setText(note.getTitle());
        mtexAddNotesDescription.setText(note.getDescription());

    }

    /**
     * onSaveButtonClicked is called when the "save" button is clicked.
     * It retrieves user input and inserts that new note data into the underlying database.
     */
    public void onSaveButtonClicked() {
        String title = mtexAddNotesTitle.getText().toString();
        String description = mtexAddNotesDescription.getText().toString();

        Date date = new Date();

        final NoteEntry note = new NoteEntry(title, description, date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mNoteId == DEFAULT_NOTE_ID) {
                    // insert new task
                    mDb.noteDao().insertNote(note);
                } else {
                    //update task
                    note.setId(mNoteId);
                    mDb.noteDao().updateNote(note);
                }
                finish();
            }
        });
    }
}

