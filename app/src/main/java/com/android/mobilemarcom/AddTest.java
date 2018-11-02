package com.android.mobilemarcom;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddTest extends AppCompatActivity {
    private Context context = this;
    private EditText codeTest;
    private EditText nameTest;
    private Button saveTest;
    private Button closeTest;
    private Spinner typeTest;

    private DbSqlHelper dbSqlHelper;
    private DbQueryHelper dbQueryHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);

        dbSqlHelper = new DbSqlHelper(context);
        
        typeTest = (Spinner) findViewById(R.id.typeTest); 

        codeTest = (EditText) findViewById(R.id.codeTest);
        nameTest = (EditText) findViewById(R.id.nameTest);
        
        saveTest = (Button) findViewById(R.id.saveTest);
        saveTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiInput();
            }
        });
    }
    private void validasiInput(){
        if(codeTest.getText().toString().trim().length() == 0){
            Toast.makeText(context, "Code harus diisi!", Toast.LENGTH_SHORT).show();
        }
        else if(nameTest.getText().toString().trim().length() == 0){
            Toast.makeText(context, "Name harus diisi!", Toast.LENGTH_SHORT).show();
        }
//        else if(typeTest.toString().trim().length() == 0){
//            Toast.makeText(context, "Pengarang Buku harus diisi!", Toast.LENGTH_SHORT).show();
//        }
        
        else{
            insertIntoDatabase();
        }
    }

    private void insertIntoDatabase() {
        String code = codeTest.getText().toString().trim();
        String name = nameTest.getText().toString().trim();
        String type = typeTest.toString().trim();

        boolean status = dbQueryHelper.insertBuku(code, name, type);

        if(status){
            Toast.makeText(context, "Buku berhasil disimpan!", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(context, "Buku gagal disimpan!", Toast.LENGTH_SHORT).show();
        }
    }

}
