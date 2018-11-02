package com.android.mobilemarcom.utility;

import com.android.mobilemarcom.model.ModelSouvenir.RequestEditSouvenir;

public class TemporaryData {
    private static RequestEditSouvenir itemSouvenir;
//    private static UserList itemUser;
//    private static EventList itemEvent;

    public static RequestEditSouvenir getItemSouvenir(){ return itemSouvenir; }
    public static void setItemSouvenir(RequestEditSouvenir itemSouvenir) { TemporaryData.itemSouvenir = itemSouvenir ; }
}