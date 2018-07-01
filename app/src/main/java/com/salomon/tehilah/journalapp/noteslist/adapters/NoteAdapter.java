package com.salomon.tehilah.journalapp.noteslist.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.salomon.tehilah.journalapp.R;
import com.salomon.tehilah.journalapp.database.NoteEntry;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

    // Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;

    // Class variables for the List that holds task data and the Context
    private List<NoteEntry> mNoteEntries;
    private Context mContext;
    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    /**
     * Constructor for the TaskAdapter that initializes the Context.
     *
     * @param context  the current Context
     * @param listener the ItemClickListener
     */
    public NoteAdapter(Context context, ItemClickListener listener) {
        mContext = context;
        mItemClickListener = listener;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new NoteViewHolder that holds the view for each task
     */
    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the note_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.note_layout, parent, false);

        return new NoteViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        // Determine the values of the wanted data
        NoteEntry noteEntry = mNoteEntries.get(position);
        String title = noteEntry.getTitle();
        String updatedAt = dateFormat.format(noteEntry.getUpdatedAt());

        //Set values
        holder.noteTitleView.setText(title);
        holder.updatedAtView.setText(updatedAt);

    }


    /**
     * Returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        if (mNoteEntries == null) {
            return 0;
        }
        return mNoteEntries.size();
    }

    public List<NoteEntry> getNotes() {
        return mNoteEntries;
    }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setNotes(List<NoteEntry> noteEntries) {
        mNoteEntries = noteEntries;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    // Inner class for creating ViewHolders
    class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView noteTitleView;
        TextView updatedAtView;


        /**
         * Constructor for the TaskViewHolders.
         *
         * @param itemView The view inflated in onCreateViewHolder
         */
        public NoteViewHolder(View itemView) {
            super(itemView);

            noteTitleView = itemView.findViewById(R.id.tv_noteTitle);
            updatedAtView = itemView.findViewById(R.id.tv_noteUpdatedAt);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = mNoteEntries.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}

