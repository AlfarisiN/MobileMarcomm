package com.android.mobilemarcom.souvenir;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.adapters.CrudAdapter;
import com.android.mobilemarcom.utility.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CrudFragment extends Fragment {
    private Button buttonCreate;
    private RecyclerView recyclerList;
    private CrudAdapter adapterCrud;
    private List<String> listCode = new ArrayList<>();
    private List<String> listName = new ArrayList<>();
    private List<Integer> idMBC = new ArrayList<>();
    private DatabaseHelper databaseHelper;
    private Cursor cursor;
    public static CrudFragment CrudFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_crud_fragment,container,false);
        CrudFragment = this;
        recyclerList = (RecyclerView) view.findViewById(R.id.recycler_souvenir);
        buttonCreate = (Button) view.findViewById(R.id.buttonCreate);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), InputData.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void readData(){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] projection = {
                "code", "name", "type"
        };

        cursor = db.query("bootcamp", projection, null, null, null, null, null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int c = 0; c < cursor.getCount(); c++) {
                cursor.moveToPosition(c);

                String code = cursor.getString(1).toString();
                listCode.add(code);

                String nama = cursor.getString( 2);
                listName.add(nama);
            }
        }
        tampilkanListMahasiswa();
    }

    private void tampilkanListMahasiswa(){
        adapterCrud = new CrudAdapter(getContext(), listCode, listName);
    }
}
