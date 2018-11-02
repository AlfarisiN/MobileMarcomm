package com.android.mobilemarcom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

public class ViewHolderList extends RecyclerView.ViewHolder {
    private TextView codeTest;
    private TextView nameTest;
    private Spinner typeTest;

    public ViewHolderList(View itemView) {
        super(itemView);

        codeTest = (TextView) itemView.findViewById(R.id.codeTest);
        nameTest = (TextView) itemView.findViewById(R.id.nameTest);
        typeTest = (Spinner) itemView.findViewById(R.id.typeTest);
    }

    public void setData(Context context, Header header){
        if(header != null){
            codeTest.setText(header.getCode());

            nameTest.setText(header.getName());
            typeTest.setAdapter(header.getType());
        }
    }
}

