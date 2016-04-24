package com.kvachev.diarydroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {

    private final static String filename = "diary_data";

    String[] groups = new String[] {"Day 1", "Day 2", "Этот список пока еще не готов же"};
    String[] items0 = new String[] {"1", "2", "3", "4"};
    String[] items1 = new String[] {"112", "1232", "32"};
    String[] items2 = new String[] {"sdsdus", "123123", "asds", "fsdf"};

    ArrayList<Map<String, String>> groupData;

    ArrayList<Map<String, String>> childDataItem;

    ArrayList<ArrayList<Map<String, String>>> childData;

    Map<String, String> m;

    ExpandableListView expandableListView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        try {
//            FileInputStream fos = this.openFileInput(filename, MODE_PRIVATE);
//            final InputStreamReader osw = new InputStreamReader(fos);
//
//            osw.read()
//        }
//        catch (Exception e)
//        {
//
//        }
        FileInputStream fos = null;
        String result = "";
        DiaryEntry entry = new DiaryEntry();
        entry.message = "default";
        try {

            fos = this.openFileInput(filename);
            // json is UTF-8 by default
            ObjectInputStream ois = new ObjectInputStream(fos);
            entry = (DiaryEntry) ois.readObject();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(fos), 8);
//            StringBuilder sb = new StringBuilder();
//
//            String line = null;
//            while ((line = reader.readLine()) != null)
//            {
//                sb.append(line + "\n");
//            }
//            result = sb.toString();
        } catch (Exception e) {
            Log.i("error", "couldn't read file");
            // Oops
        }
        finally {
            try {
                if (fos != null)
                    fos.close();
            }
            catch(Exception e){}
        }

        Log.i("result", "reading string");
        Log.i("result", entry.message);

//        Log.i("readJson", "trying to read file");
//        try {
//
//            JSONObject jObject = new JSONObject(result);
//            JSONArray jArray = jObject.getJSONArray("0");
////            Log.i(j)
//
//            for (int i = 0; i < jArray.length(); i++)
//            {
//                try {
////                    JSONObject oneObject = jArray.getJSONObject(i);
//                    String oneObject = jArray.getString(i);//getJSONObject(i);
//                    Log.i("Json", oneObject.toString());
//                    // Pulling items from the array
////                    String oneObjectsItem = oneObject.getString("STRINGNAMEinTHEarray");
////                    String oneObjectsItem2 = oneObject.getString("anotherSTRINGNAMEINtheARRAY");
//                } catch (JSONException e) {
//                    Log.i("readJson", e.toString() + ": " + e.getMessage());
//                }
//            }
//        }
//        catch (Exception e) {
//            Log.i("parseJson", e.toString() + ": " + e.getMessage());
//        }


        groupData = new ArrayList<Map<String, String>>();
        for (String group : groups) {
            m = new HashMap<String, String>();
            m.put("groupName", group); // имя компании
            groupData.add(m);
        }

        String groupFrom[] = new String[] {"groupName"};
        int groupTo[] = new int[] {android.R.id.text1};

        childData = new ArrayList<ArrayList<Map<String, String>>>();

        childDataItem = new ArrayList<Map<String, String>>();
        for (String item : items0) {
            m = new HashMap<String, String>();
            m.put("itemName", entry.message); // название телефона
            childDataItem.add(m);
        }
        childData.add(childDataItem);

        childDataItem = new ArrayList<Map<String, String>>();
        for (String item : items1) {
            m = new HashMap<String, String>();
            m.put("itemName", item);
            childDataItem.add(m);
        }
        childData.add(childDataItem);

        childDataItem = new ArrayList<Map<String, String>>();
        for (String item : items2) {
            m = new HashMap<String, String>();
            m.put("itemName", item);
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

    public void openAddDiaryEntryActivity(View view)
    {
        Intent intent = new Intent(MainActivity.this, AddDiaryEntryActivity.class);
        startActivity(intent);
    }
}