package com.labsession.notetaking.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.labsession.notetaking.R;
import com.labsession.notetaking.model.Note;

public class DetailsActivity extends BaseActivity {

    Note selectedNote;
    TextView title,body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        title=(TextView)findViewById(R.id.textTitle);
        body=(TextView)findViewById(R.id.textBody);

        if (getIntent() != null && getIntent().getExtras() != null && getIntent().getExtras().containsKey("data")) {
            selectedNote = (Note) getIntent().getParcelableExtra("data");


         title.setText(selectedNote.getTitle());
         body.setText(selectedNote.getBody());

        }
    }
}
