package com.android.mobilemarcom.employee;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.mobilemarcom.R;

import java.util.List;

public class DialogAutoComplete extends AlertDialog {
    private Context context;
    private List<String> stringList;

    private TextView titleDialog;
    private EditText filter;
    private ListView listDataView;
    private ImageView closeDialog;

    private AutoCompleteAdapter autoCompleteAdapter;
    private OnAutoCompleteResult dialogResult;

    public DialogAutoComplete(Context context, List<String> stringList) {
        super(context);
        this.context = context;
        this.stringList = stringList;

        initView();
    }

    private void initView() {
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_auto_complete, null);
        titleDialog = (TextView) view.findViewById(R.id.titleDialog);
        titleDialog.setText("Pilih Jurusan");

        filter = (EditText) view.findViewById(R.id.filter);
        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                autoCompleteAdapter.getFilter().filter(s);
                autoCompleteAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listDataView = (ListView) view.findViewById(R.id.listDataView);
        autoCompleteAdapter = new AutoCompleteAdapter(context, stringList);
        listDataView.setAdapter(autoCompleteAdapter);
        listDataView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) listDataView.getItemAtPosition(position);
                if(dialogResult != null){
                    dialogResult.finish(selected);
                }
                dismiss();
            }
        });

        closeDialog = (ImageView) view.findViewById(R.id.closeDialog);
        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        setView(view);
        show();
    }
    public interface OnAutoCompleteResult{
        void finish(String result);

    }
    public void setDialogResult(OnAutoCompleteResult dialogResult){
        this.dialogResult = dialogResult;
    }
}
