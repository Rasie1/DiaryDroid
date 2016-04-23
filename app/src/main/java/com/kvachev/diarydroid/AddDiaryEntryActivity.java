package com.kvachev.diarydroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.Date;

public class AddDiaryEntryActivity extends AppCompatActivity {

    private final static String filename = "diary_data";
    private EditText   textEditor;
    private DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary_entry);

        textEditor = (EditText)findViewById(R.id.editText);
        datePicker = (DatePicker)findViewById(R.id.datePicker);

    }

    private Date getDateFromDatePicker() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
    private DiaryEntry formDiaryEntry()
    {
        DiaryEntry ret = new DiaryEntry();
        ret.date    = getDateFromDatePicker();
        ret.message = textEditor.getText().toString();

        return ret;
    }

    public void addDiaryEntryOnClick(View view) {
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            DiaryEntry diaryEntry = formDiaryEntry();
            os.writeObject(diaryEntry);
            os.close();
            fos.close();
        }
        catch (Exception e)
        {
            AlertDialog.Builder dialog  = new AlertDialog.Builder(this);
            dialog.setMessage(R.string.add_entry_error_message + "\n"
                    + e.toString() + ": "
                    + e.getLocalizedMessage());
            dialog.setTitle(R.string.add_entry_error_title);
            dialog.setPositiveButton(R.string.add_entry_error_ok, null);
            dialog.setCancelable(true);
            dialog.create().show();
        }
    }

}
