package com.android.mobilemarcom.souvenir;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.utility.DatabaseHelper;

public class InputData extends Activity {
    private AutoCompleteTextView autoCompleteCrud;
    private EditText edtName, edtCode;
    private Button btnSaveCrud, btnCancelCrud;
    private DatabaseHelper databaseHelper;
    private Context context = this;
    String[] namaBootcamp = {"Java Script", "Android", "Java", "PHP"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);
        databaseHelper = new DatabaseHelper(context);

        edtCode = (EditText) findViewById(R.id.codeCrud);
        edtName = (EditText) findViewById(R.id.nameCrud);
        autoCompleteCrud = (AutoCompleteTextView) findViewById(R.id.autoCompleteCrud);
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, namaBootcamp);
        autoCompleteCrud.setAdapter(adapter);
        autoCompleteCrud.setThreshold(1);

        btnSaveCrud = (Button) findViewById(R.id.buttonSaveCrud);
        btnSaveCrud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasi();
            }
        });
        btnCancelCrud = (Button) findViewById(R.id.buttonCancelCrud);
        btnCancelCrud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void validasi(){
        if(edtCode.getText().toString().trim().length() == 0){
            Toast.makeText(context, "Code belum diisi!", Toast.LENGTH_SHORT).show();
        }
        else if(edtName.getText().toString().trim().length() == 0){
            Toast.makeText(context, "Nama belum diisi!", Toast.LENGTH_SHORT).show();
        } else {
            saveDb();
        }
    }
    private void saveDb(){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("code", edtCode.getText().toString().trim());
        content.put("name", edtName.getText().toString().trim());
        content.put("type", autoCompleteCrud.getText().toString().trim());

        db.insert("bootcamp", null, content);
        Toast.makeText(context, "Input Sukses", Toast.LENGTH_SHORT).show();
        finish();
    }
}
