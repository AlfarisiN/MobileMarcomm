package com.android.mobilemarcom.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.model.ModelSouvenirStok.RequestSouvenirStockAddItem;
import com.android.mobilemarcom.t_souvenir.SouvenirStokAddItemActivity;

public class ViewHolderSouvenirStok extends RecyclerView.ViewHolder {
    private Context context;
    private TextView t_souvenir_list_code, t_souvenir_received_date, t_souvenir_received_by;
    private ImageView image_t_souvenir_option;

    public ViewHolderSouvenirStok(View itemView) {
        super(itemView);

        t_souvenir_list_code = (TextView) itemView.findViewById(R.id.t_souvenir_list_code);
        t_souvenir_received_date = (TextView) itemView.findViewById(R.id.t_souvenir_received_date);
        t_souvenir_received_by = (TextView) itemView.findViewById(R.id.t_souvenir_received_by);
        image_t_souvenir_option = (ImageView) itemView.findViewById(R.id.image_t_souvenir_option);

    }

    public void setModelSouvenirStok(final Context context, final RequestSouvenirStockAddItem souvenirStokList){
        //set code
        String code = souvenirStokList.getCode();
        t_souvenir_list_code.setText(code);

        //set Receiveddate
        String receiveddate = souvenirStokList.getReceivedDate();
        t_souvenir_received_date.setText(receiveddate);

        //set receivedby
        String receivedby = souvenirStokList.getReceivedBy().getFirstName();
        t_souvenir_received_by.setText(receivedby);

        image_t_souvenir_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v7.widget.PopupMenu popupMenu = new android.support.v7.widget.PopupMenu(context, image_t_souvenir_option);
                popupMenu.getMenuInflater().inflate(R.menu.popup_additem, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new android.support.v7.widget.PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.addItem:
                                Intent intent = new Intent(context, SouvenirStokAddItemActivity.class);
                                intent.putExtra("id", souvenirStokList.getId());
                                context.startActivity(intent);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }
}
