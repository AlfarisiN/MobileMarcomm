package com.android.mobilemarcom.viewholder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.model.souvenir_request.DataListSouvenirRequest;

public class ViewJolderSouvenirGetItem extends RecyclerView.ViewHolder {
    private Context context;
    private TextView souvenir_name,souvenir_quantity;


    public ViewJolderSouvenirGetItem(@NonNull View itemView) {
        super(itemView);
        souvenir_name = (TextView) itemView.findViewById(R.id.souvenir_name);
        souvenir_quantity = (TextView) itemView.findViewById(R.id.souvenir_quantity);
    }

    public void setModelSouvenirReqItem(final Context context, final DataListSouvenirRequest dataList) {
        souvenir_name.setText(dataList.getSouvenir().getName().toString());
        souvenir_quantity.setText(dataList.getQty().toString());

    }
}
