package com.kvachev.diarydroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends Activity {

    private final static String filename = "diary_data";


    ExpandableListView expandableListView;

    TextView noEntriesText;


    @Override
    public void onResume(){
        super.onResume();
        populateList();
    }

    private void populateList()
    {
        FileInputStream fos = null;
        ArrayList<DiaryEntry> entries = new ArrayList<DiaryEntry>();
        try {

            fos = this.openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(fos);
            entries = (ArrayList<DiaryEntry>) ois.readObject();

        } catch (Exception e) {
            Log.i("error", "couldn't read file");
            Log.i("error", e.toString());
        }
        finally {
            try {
                if (fos != null)
                    fos.close();
            }
            catch(Exception e){}
        }
        TreeMap<String, ArrayList<String>> groups = new TreeMap<String, ArrayList<String>>();
        for (DiaryEntry x : entries) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy MMM dd");
            String key = format.format(x.date.getTime());

            if (!groups.containsKey(key)) {
                ArrayList<String> list = new ArrayList<String>();
                list.add(x.message);

                groups.put(key, list);
            } else {
                groups.get(key).add(x.message);
            }
        }

        ArrayList<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        for (String key : groups.keySet()) {
            TreeMap<String, String> m = new TreeMap<String, String>();
            m.put("groupName", key);
            groupData.add(m);
        }

        String groupFrom[] = new String[] {"groupName"};
        int groupTo[] = new int[] {android.R.id.text1};

        ArrayList<ArrayList<Map<String, String>>> childData = new ArrayList<ArrayList<Map<String, String>>>();

        for (String key : groups.keySet()) {
            ArrayList<Map<String, String>> childDataItem = new ArrayList<Map<String, String>>();
            for (String msg : groups.get(key))
            {
                TreeMap<String, String> m = new TreeMap<String, String>();
                m.put("itemName", msg);
                childDataItem.add(m);
            }
            childData.add(childDataItem);
        }



        String childFrom[] = new String[] {"itemName"};
        int childTo[] = new int[] {android.R.id.text1};

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this,
                groupData,
                android.R.layout.simple_expandable_list_item_1,
                groupFrom,
                groupTo,
                childData,
                android.R.layout.simple_list_item_1,
                childFrom,
                childTo);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        noEntriesText = (TextView) findViewById(R.id.textView);


        expandableListView.setAdapter(adapter);

        if (!entries.isEmpty())
            noEntriesText.setVisibility(View.GONE);
        else
            noEntriesText.setVisibility(View.VISIBLE);

    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateList();
    }

    public void openAddDiaryEntryActivity(View view)
    {
        Intent intent = new Intent(MainActivity.this, AddDiaryEntryActivity.class);
        startActivity(intent);
    }
}