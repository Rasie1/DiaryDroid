package com.kvachev.diarydroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.OutputStreamWriter;

public class AddDiaryEntryActivity extends AppCompatActivity {

    private final static String DIARYDATA="diary_data";
//    private EditText txtEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary_entry);

//        txtEditor=(EditText)findViewById(R.id.textbox);
    }

//    public void saveClicked(View v) {
//        try {
//            OutputStreamWriter out = new OutputStreamWriter(openFileOutput(STORETEXT, 0));
//            out.write(txtEditor.getText().toString());
//            out.close();
//            Toast
//                    .makeText(this, "The contents are saved in the file.", Toast.LENGTH_LONG)
//                    .show();
//
//        }
//        catch (Throwable t) {
//            Toast
//                    .makeText(this, "Exception: "+t.toString(), Toast.LENGTH_LONG)
//                    .show();
//        }
//    }

//    public void saveBgColorPreference()
//    {
//        RadioGroup g = (RadioGroup) findViewById(R.id.prefgroup);
//        int selected = g.getCheckedRadioButtonId();
//        RadioButton b = (RadioButton) findViewById(selected);
//        String selectedValue = (String) b.getText();
//        SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
//        SharedPreferences.Editor prefsEditor = myPrefs.edit();
//
//        prefsEditor.putString("bgcolor", selectedValue);
//        prefsEditor.commit();
//    }

//    public void setColorOnPreference()
//    {
//        mScreen = (LinearLayout) findViewById(R.id.myScreen);
//        SharedPreferences myPrefs2 = this.getSharedPreferences("myPrefs", MODE_WORLD_READABLE);
//
//        String prefName = myPrefs2.getString("bgcolor", "Blue");
//
//        if (prefName.equals("Blue"))
//            mScreen.setBackgroundColor(0xff0000ff);
//        else
//            mScreen.setBackgroundColor(0xffff0000);
//    }
}
