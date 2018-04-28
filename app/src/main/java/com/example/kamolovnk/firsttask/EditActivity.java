package com.example.kamolovnk.firsttask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "EditActivity";
    DatabaseHelper mDatabaseHelper;
    private Button btnSave, btnDelete, btnBack;
    private EditText editItem;

    private String selectedName;
    private int selectedID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        btnSave = (Button)findViewById(R.id.btnSave);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnBack = (Button)findViewById(R.id.btnBack);
        editItem = (EditText)findViewById(R.id.editItem);
        mDatabaseHelper = new DatabaseHelper(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("ID", -1);

        selectedName = receivedIntent.getStringExtra("name");

        editItem.setText(selectedName);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = editItem.getText().toString();
                if(!item.equals("")){
                    mDatabaseHelper.updateName(item, selectedID, selectedName);
                    Intent saveIntent = new Intent(EditActivity.this, Login.class);
                    startActivity(saveIntent);
                    toastMessage("Your changing succesfully changed!");
                }else{
                    toastMessage("You must enter name!");
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.deleteName(selectedName,selectedID);
                Intent deleteIntent = new Intent(EditActivity.this, Login.class);
                toastMessage("Your changing succesfully deleted!!!");
                startActivity(deleteIntent);
            }
        });



    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
