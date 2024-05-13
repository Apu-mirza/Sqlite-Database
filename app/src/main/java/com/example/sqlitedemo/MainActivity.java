package com.example.sqlitedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nameEdit, ageEdit, genderEdit, idEdit;
    private Button saveButton, showButton, updateButton, deleteButton;

    DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        nameEdit = (EditText) findViewById(R.id.nameEditId);
        ageEdit = (EditText) findViewById(R.id.ageEditId);
        genderEdit = (EditText) findViewById(R.id.genderEditId);
        idEdit = (EditText) findViewById(R.id.idEditId);
        saveButton = (Button) findViewById(R.id.buttonId);
        showButton = (Button) findViewById(R.id.displayButtonId);
        updateButton = (Button) findViewById(R.id.updateButtonId);
        deleteButton = (Button) findViewById(R.id.deleteButtonId);

        saveButton.setOnClickListener(this);
        showButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);


    }

    @Override
      public void onClick(View view) {

        String name = nameEdit.getText().toString();
        String age = ageEdit.getText().toString();
        String gender = genderEdit.getText().toString();
        String id = idEdit.getText().toString();

        if (view.getId() == R.id.buttonId){
            long rowId = databaseHelper.insertData(name,age,gender);

            if(rowId==-1){
                Toast.makeText(getApplicationContext(),"Unsuccessfull",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getApplicationContext(),"Row "+rowId+" is successfully inserted",Toast.LENGTH_SHORT).show();
            }

        }
        else if(view.getId()==R.id.displayButtonId){
            Cursor cursor = databaseHelper.displayAllData();

            if(cursor.getCount()==0){
                showData("Error","No data found");

            }
            StringBuffer stringBuffer = new StringBuffer();
            while(cursor.moveToNext()){
                stringBuffer.append("ID: "+cursor.getString(0)+"\n");
                stringBuffer.append("Name: "+cursor.getString(1)+"\n");
                stringBuffer.append("Age: "+cursor.getString(2)+"\n");
                stringBuffer.append("Gender: "+cursor.getString(3)+"\n\n\n");

            }
            showData("Resultset",stringBuffer.toString());
        }
        else if(view.getId()==R.id.updateButtonId){

            Boolean isUpdated = databaseHelper.updateData(id,name,age,gender);

            if(isUpdated==true){
                Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"Update Unsuccessfull",Toast.LENGTH_SHORT).show();
            }
        }
        else if(view.getId()==R.id.deleteButtonId){
             int value = databaseHelper.deleteData(id);
             if(value>0){
                 Toast.makeText(getApplicationContext(),"Data successfully Deleted",Toast.LENGTH_SHORT).show();
             }
             else {
                 Toast.makeText(getApplicationContext(),"Delete Unsuccessfull",Toast.LENGTH_SHORT).show();
             }
        }

    }
    public void showData(String title, String message){
       AlertDialog.Builder builder = new AlertDialog.Builder(this);
       builder.setTitle(title);
       builder.setMessage(message);
       builder.setCancelable(true);
       builder.show();
    }
}