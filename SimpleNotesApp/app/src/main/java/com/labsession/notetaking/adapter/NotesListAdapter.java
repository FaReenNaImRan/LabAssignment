package com.labsession.notetaking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.labsession.notetaking.R;
import com.labsession.notetaking.model.Note;

import java.util.List;

public class NotesListAdapter extends ArrayAdapter<Note> {
    private Context ctx;
    private List<Note> notesList;

    public NotesListAdapter(@NonNull Context context, int resource, @NonNull List<Note> objects) {
        super(context, R.layout.item_notes_list, objects);
        ctx = context;
        notesList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.item_notes_list, null,true);

        TextView textView = view.findViewById(R.id.notesTitle);
        textView.setText(notesList.get(position).getTitle());
        return view;
    }
}
