package com.kvachev.diarydroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {

    private final static String filename = "diary_data";

    String[] groups = new String[] {"Day 1", "Day 2", "Этот список пока еще не готов же"};
    String[] phonesHTC = new String[] {"Sensation", "Desire", "Wildfire", "Hero"};
    String[] phonesSams = new String[] {"Galaxy S II", "Galaxy Nexus", "Wave"};
    String[] phonesLG = new String[] {"Optimus", "Optimus Link", "Optimus Black", "Optimus One"};

    ArrayList<Map<String, String>> groupData;

    ArrayList<Map<String, String>> childDataItem;

    ArrayList<ArrayList<Map<String, String>>> childData;

    Map<String, String> m;

    ExpandableListView expandableListView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // заполняем коллекцию групп из массива с названиями групп
        groupData = new ArrayList<Map<String, String>>();
        for (String group : groups) {
            // заполняем список аттрибутов для каждой группы
            m = new HashMap<String, String>();
            m.put("groupName", group); // имя компании
            groupData.add(m);
        }

        // список аттрибутов групп для чтения
        String groupFrom[] = new String[] {"groupName"};
        // список ID view-элементов, в которые будет помещены аттрибуты групп
        int groupTo[] = new int[] {android.R.id.text1};

        DiaryEntry entry = new DiaryEntry();
        entry.message = "lol no";

        try {

            FileInputStream fis = getApplicationContext().openFileInput(filename);
            ObjectInputStream is = new ObjectInputStream(fis);
            entry = (DiaryEntry) is.readObject();
            is.close();
            fis.close();
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


        // создаем коллекцию для коллекций элементов
        childData = new ArrayList<ArrayList<Map<String, String>>>();

        // создаем коллекцию элементов для первой группы
        childDataItem = new ArrayList<Map<String, String>>();
        // заполняем список аттрибутов для каждого элемента
        for (String phone : phonesHTC) {
            m = new HashMap<String, String>();
            m.put("phoneName", entry.message); // название телефона
            childDataItem.add(m);
        }
        // добавляем в коллекцию коллекций
        childData.add(childDataItem);

        // создаем коллекцию элементов для второй группы
        childDataItem = new ArrayList<Map<String, String>>();
        for (String phone : phonesSams) {
            m = new HashMap<String, String>();
            m.put("phoneName", phone);
            childDataItem.add(m);
        }
        childData.add(childDataItem);

        // создаем коллекцию элементов для третьей группы
        childDataItem = new ArrayList<Map<String, String>>();
        for (String phone : phonesLG) {
            m = new HashMap<String, String>();
            m.put("phoneName", phone);
            childDataItem.add(m);
        }
        childData.add(childDataItem);

        // список аттрибутов элементов для чтения
        String childFrom[] = new String[] {"phoneName"};
        // список ID view-элементов, в которые будет помещены аттрибуты элементов
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