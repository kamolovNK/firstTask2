package com.example.kamolovnk.firsttask;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";

    DatabaseHelper mDatabaseHelper;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        listView = (ListView)findViewById(R.id.listView);
        mDatabaseHelper = new DatabaseHelper(this);

        populateListView();

    }


    private void populateListView(){
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1));
        }
        final ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);



       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
               String name = adapterView.getItemAtPosition(position).toString();
               Log.d(TAG, "onItemClick: You clicked on " + name);

               Cursor data = mDatabaseHelper.getItemID(name);
               int itemID = -1;

               while (data.moveToNext()){
                   itemID = data.getInt(0);
               }

               if(itemID > -1){
                   Log.d(TAG, "onItemClick: The ID is: " + itemID);
                   Intent editIntent = new Intent(Login.this, EditActivity.class);
                   editIntent.putExtra("ID", itemID);
                   editIntent.putExtra("name", name);
                   startActivity(editIntent);
               }else{
                   toastMessage("No ID associated with that name.");
               }
           }
       });

    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
