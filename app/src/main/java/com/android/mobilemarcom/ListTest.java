package com.android.mobilemarcom;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class ListTest extends AppCompatActivity {
    private Context context = this;
    private Button create;
    private RecyclerView recyclerTest;

    private DbQueryHelper queryHelper;
    private DbSqlHelper sqlHelper;

    private ListAdapter bukuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_test);

        recyclerTest = (RecyclerView) findViewById(R.id.recyclerTest);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        recyclerTest.setLayoutManager(layoutManager);

        sqlHelper = new DbSqlHelper(context);
        queryHelper = new DbQueryHelper(sqlHelper);

        create = (Button) findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AddTest.class);
                startActivity(i);
            }
        });
        tampilkanList();
    }
    private void tampilkanList(){
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

    }

}
