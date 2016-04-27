package com.kvachev.diarydroid;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class AddDiaryEntryActivity extends AppCompatActivity {

    private final static String filename = "diary_data";
    private EditText   textEditor;
    private EditText   emailEditor;
    private DatePicker datePicker;
    private CheckBox deleteEntriesCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary_entry);

        textEditor = (EditText)findViewById(R.id.editText);
        emailEditor = (EditText)findViewById(R.id.editTextEmail);
        datePicker = (DatePicker)findViewById(R.id.datePicker);
        deleteEntriesCheckbox = (CheckBox)findViewById((R.id.checkBox));
    }

    private Calendar getDateFromDatePicker() {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar;
    }
    private DiaryEntry formDiaryEntry()
    {
        DiaryEntry ret = new DiaryEntry();
        ret.date    = getDateFromDatePicker();
        ret.message = textEditor.getText().toString();

        return ret;
    }

    public void sendEmail()
    {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{emailEditor.getText().toString()});
        i.putExtra(Intent.EXTRA_SUBJECT, "DiaryDroid");
        i.putExtra(Intent.EXTRA_TEXT   , textEditor.getText().toString());
        try {
            startActivity(Intent.createChooser(i, "Email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "@string/no_email_clients", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public void addDiaryEntryOnClick(View view) {


        try {
            if (isEmpty(textEditor))
            {
                throw new UnsupportedOperationException(getString(R.string.message_cant_be_empty));
            }

            FileInputStream fis = null;
            FileOutputStream fos = null;
            ArrayList<DiaryEntry> entries = new ArrayList<DiaryEntry>();
            try {

                fis = this.openFileInput(filename);
                ObjectInputStream ois = new ObjectInputStream(fis);
                entries = (ArrayList<DiaryEntry>) ois.readObject();

            } catch (Exception e) {
                Log.i("error", "couldn't read file");
            }
            finally {
                try {
                    if (fis != null)
                        fis.close();
                }
                catch(Exception e){}
            }

            fos = this.openFileOutput(filename, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            entries.add(formDiaryEntry());
            if (deleteEntriesCheckbox.isChecked())
                entries.clear();
            oos.writeObject(entries);
            oos.flush();
            oos.close();

            if (!isEmpty(emailEditor)) {
                sendEmail();
            }
        }
        catch (Exception e)
        {
            AlertDialog.Builder dialog  = new AlertDialog.Builder(this);
            dialog.setMessage(R.string.add_entry_error_message + "\n"
                    + e.toString() + "\n"
                    + e.getLocalizedMessage());
            dialog.setTitle(R.string.add_entry_error_message);
            dialog.setPositiveButton(R.string.add_entry_error_ok, null);
            dialog.setCancelable(true);
            dialog.create().show();
        }
    }

}
