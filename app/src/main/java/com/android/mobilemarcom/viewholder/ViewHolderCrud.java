package com.android.mobilemarcom.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.android.mobilemarcom.R;

public class ViewHolderCrud extends RecyclerView.ViewHolder {
    public TextView txtNama, txtCode, txtView;

    public ViewHolderCrud(@NonNull View itemView) {
        super(itemView);

        txtCode = (TextView) itemView.findViewById(R.id.listbccode);
        txtNama = (TextView) itemView.findViewById(R.id.listbcname);
        txtView = (TextView) itemView.findViewById(R.id.listview);

    }

    public void setCrud(Context context, String code, String nama){
        if (code != null){
            txtCode.setText(code);
        } else if (nama != null){
            txtNama.setText(nama);
        }
    }
}
