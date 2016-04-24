package com.kvachev.diarydroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private final static String filename = "diary_data";


    ExpandableListView expandableListView;


    @Override
    public void onResume(){
        super.onResume();
        populateList();
    }

    private void populateList()
    {

        FileInputStream fos = null;
        ArrayList<DiaryEntry> entries = null;
        DiaryEntry entry = new DiaryEntry();
        try {

            fos = this.openFileInput(filename);
            ObjectInputStream ois = new ObjectInputStream(fos);
            entries = (ArrayList<DiaryEntry>) ois.readObject();

            entry = entries.get(0);
        } catch (Exception e) {
            Log.i("error", "couldn't read file");

            entry.message = "default";
            entry.date = Calendar.getInstance();
        }
        finally {
            try {
                if (fos != null)
                    fos.close();
            }
            catch(Exception e){}
        }

//        Log.i("result", entry.message);


        HashMap<Calendar, ArrayList<String>> groups = new HashMap<Calendar, ArrayList<String>>();
        for (DiaryEntry x : entries) {
            if (!groups.containsKey(x.date)) {
                ArrayList<String> list = new ArrayList<String>();
                list.add(x.message);

                groups.put(x.date, list);
            } else {
                groups.get(x.date).add(x.message);
            }
        }

        ArrayList<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
        for (Calendar key : groups.keySet()) {
            HashMap<String, String> m = new HashMap<String, String>();
            m.put("groupName", key.getTime().toString());
            groupData.add(m);
        }

        String groupFrom[] = new String[] {"groupName"};
        int groupTo[] = new int[] {android.R.id.text1};

        ArrayList<ArrayList<Map<String, String>>> childData = new ArrayList<ArrayList<Map<String, String>>>();

        ArrayList<Map<String, String>> childDataItem = new ArrayList<Map<String, String>>();
        for (Calendar key : groups.keySet()) {
            HashMap<String, String> m = new HashMap<String, String>();
            m.put("itemName", entry.message);
            childDataItem.add(m);
        }
        childData.add(childDataItem);



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
        expandableListView.setAdapter(adapter);
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