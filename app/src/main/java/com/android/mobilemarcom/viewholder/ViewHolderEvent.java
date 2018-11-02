package com.android.mobilemarcom.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.event.CloseEventActivity;
import com.android.mobilemarcom.event.EventApprovalActivity;
import com.android.mobilemarcom.event.EventRequestActivity;
import com.android.mobilemarcom.model.modelevent.DataList;

/**
 * Created by Irfan Naufal Ridi on 24/10/2018.
 */

public class ViewHolderEvent extends RecyclerView.ViewHolder {

    private TextView eventListCode, eventListName, eventStartDateList, eventListStatus;
    private ImageView eventBurgerOption;
    Context context;

    public ViewHolderEvent(final View itemView) {
        super(itemView);
        context = itemView.getContext();

        eventListCode = (TextView)itemView.findViewById(R.id.eventCodeName);
        eventListName = (TextView)itemView.findViewById(R.id.eventName);
        eventStartDateList = (TextView)itemView.findViewById(R.id.eventStartDate);
        eventListStatus= (TextView)itemView.findViewById(R.id.eventStatus);
        eventBurgerOption = (ImageView)itemView.findViewById(R.id.eventBurger);



    }

    public void setModelEvent(Context context, DataList event){
        //set Code
        String code = event.getCode();
        eventListCode.setText(code);

        //set Name
        String name = event.getEventName();
        eventListName.setText(name);

        //set quantity
        String startDate = event.getStartDate();
        eventStartDateList.setText(startDate+"");

        //set Status
//        String status = event.getStatus().toString();
        eventListStatus.setText("Submited");
    }

    public void eventListEdit(DataList event){

        final String tempID = event.getId().toString();
        final String tempCode = event.getCode();
        final String tempName = event.getEventName();
        final String tempPlace = "Jakarta";
        final String tempStartDate = event.getStartDate();
        final String tempEndDate = "1-11-2222";
        final String tempBudget = "10000";
        final String tempNotes = "10000";
        final String tempAssignTo = "Regis";
        final String tempRequestedBy = "Seira";
        final String tempStatus = "Submited";
        final String tempRequestDate= "29-02-2018";

        eventBurgerOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(itemView.getContext(),eventBurgerOption);
                popupMenu.getMenuInflater()
                        .inflate(R.menu.popup_menu_event_burger,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().toString().equals("Edit") ){
                            Intent intent = new Intent(context, EventRequestActivity.class);

                            intent.putExtra("edited",true);
                            intent.putExtra("dataID",tempID);
                            intent.putExtra("dataCode",tempCode);
                            intent.putExtra("dataName",tempName);
                            intent.putExtra("dataPlace",tempPlace);
                            intent.putExtra("dataStartDate",tempStartDate);
                            intent.putExtra("dataEndDate",tempEndDate);
                            intent.putExtra("dataBudget",tempBudget);
                            intent.putExtra("dataNotes",tempNotes);

                            context.startActivity(intent);
                        }
                        else if(menuItem.getTitle().toString().equals("Approval")){
                            Intent intent = new Intent(context, EventApprovalActivity.class);

                            intent.putExtra("dataID",tempID);
                            intent.putExtra("dataCode",tempCode);
                            intent.putExtra("dataName",tempName);
                            intent.putExtra("dataPlace",tempPlace);
                            intent.putExtra("dataStartDate",tempStartDate);
                            intent.putExtra("dataEndDate",tempEndDate);
                            intent.putExtra("dataBudget",tempBudget);
                            intent.putExtra("dataNotes",tempNotes);
                            intent.putExtra("dataRequestedBy",tempRequestedBy);
                            intent.putExtra("dataRequestDate",tempRequestDate);
                            intent.putExtra("dataStatus",tempStatus);

                            context.startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(context, CloseEventActivity.class);

                            intent.putExtra("dataID",tempID);
                            intent.putExtra("dataCode",tempCode);
                            intent.putExtra("dataName",tempName);
                            intent.putExtra("dataPlace",tempPlace);
                            intent.putExtra("dataStartDate",tempStartDate);
                            intent.putExtra("dataEndDate",tempEndDate);
                            intent.putExtra("dataBudget",tempBudget);
                            intent.putExtra("dataNotes",tempNotes);
                            intent.putExtra("dataAssign",tempAssignTo);
                            intent.putExtra("dataRequestedBy",tempRequestedBy);
                            intent.putExtra("dataRequestDate",tempRequestDate);
                            intent.putExtra("dataStatus",tempStatus);

                            context.startActivity(intent);
                        }

                        return true;
                    }
                });
                popupMenu.show();

            }
        });
    }
}

