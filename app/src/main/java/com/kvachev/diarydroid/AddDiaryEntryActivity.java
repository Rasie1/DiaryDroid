package com.kvachev.diarydroid;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

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
        ret.message = textEditor.getText().toString();//.replaceAll("{", "(").replaceAll("}", ")").replaceAll("|", "-");

        return ret;
    }


    public void addDiaryEntryOnClick(View view) {


        try {
            if (textEditor.getText().toString() == "" || // almost works
                    textEditor.getText().toString() == getString(R.string.message_cant_be_empty))
            {
                throw new UnsupportedOperationException(getString(R.string.message_cant_be_empty));
            }

            FileInputStream fis = null;
            FileOutputStream fos = null;
            ArrayList<DiaryEntry> entries = null;
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
            oos.writeObject(entries);
            oos.flush();
            oos.close();
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
