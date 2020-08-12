package com.labsession.notetaking.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.labsession.notetaking.model.Note;

import static com.labsession.notetaking.util.Constants.FLOW_FOR;
import static com.labsession.notetaking.util.Constants.FOR_EDIT;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    void gotoActivity(Class<?> intentClass) {
        Intent intent = new Intent(this, intentClass);
        startActivity(intent);

    }

    void gotoActivity(Class<?> intentClass, String intentKey1, String intentValue1) {
        Intent intent = new Intent(this, intentClass);
        intent.putExtra(intentKey1, intentValue1);
        startActivity(intent);

    }
    void gotoActivity(Class<?> intentClass, String intentKey1, String intentValue1, String intentKey2, Note intentValue2) {
        Intent intent = new Intent(this, intentClass);
        intent.putExtra(intentKey1, intentValue1);
        intent.putExtra(intentKey2, intentValue2);
        startActivity(intent);

    }

    void shareNotesToOtherApps(Note note) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, note.getTitle());
        intent.putExtra(Intent.EXTRA_TEXT, note.getBody());
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, null));
    }
}
