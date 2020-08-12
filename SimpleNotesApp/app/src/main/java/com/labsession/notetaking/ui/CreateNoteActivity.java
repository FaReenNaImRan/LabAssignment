package com.labsession.notetaking.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.labsession.notetaking.R;
import com.labsession.notetaking.db.DatabaseHelper;
import com.labsession.notetaking.model.Note;

import static com.labsession.notetaking.util.Constants.FLOW_FOR;
import static com.labsession.notetaking.util.Constants.FOR_ADD;
import static com.labsession.notetaking.util.Constants.FOR_EDIT;
import static com.labsession.notetaking.util.Constants.NOTE_DATA;

public class CreateNoteActivity extends BaseActivity {


    EditText titleEditText;
    EditText bodyEditText;
    FloatingActionButton addNoteButton;
    DatabaseHelper db;
    Toolbar toolbar;
    String flowFor;
    Note noteForEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        titleEditText = findViewById(R.id.notesTitle);
        bodyEditText = findViewById(R.id.notesBody);
        addNoteButton = findViewById(R.id.addNoteButton);
        db = new DatabaseHelper(this);


        //what is in intent?
        if (getIntent() != null) {
            flowFor = getIntent().getStringExtra(FLOW_FOR);
            if (flowFor != null && flowFor.equals(FOR_EDIT)) {
                getSupportActionBar().setTitle("Edit Notes");
                noteForEdit = getIntent().getParcelableExtra(NOTE_DATA);
                if (noteForEdit != null) {
                    titleEditText.setText(noteForEdit.getTitle());
                    bodyEditText.setText(noteForEdit.getBody());
                }
            } else {
                getSupportActionBar().setTitle("Add Notes");
            }
        }


        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for validation
                if (!TextUtils.isEmpty(titleEditText.getText().toString()) && !TextUtils.isEmpty(bodyEditText.getText().toString())) {
                    if (flowFor.equals(FOR_ADD)) {
                        saveNoteToDB();
                    } else {
                        updateNote();
                    }
                    gotoActivity(MainActivity.class);
                    finish();
                } else {
                    Toast.makeText(CreateNoteActivity.this, R.string.no_field_empty_text, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void updateNote() {
        Note note = new Note();
        note.setTitle(titleEditText.getText().toString());
        note.setBody(bodyEditText.getText().toString());
        note.setNoteID(noteForEdit.getNoteID());
        db.updateNote(note);
    }

    private void saveNoteToDB() {
        //modal class for note to carry data easy
        Note note = new Note();
        note.setTitle(titleEditText.getText().toString());
        note.setBody(bodyEditText.getText().toString());
        db.insetNote(note);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //back button
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
