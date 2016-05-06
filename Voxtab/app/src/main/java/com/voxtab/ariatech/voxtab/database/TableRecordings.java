package com.voxtab.ariatech.voxtab.database;

/**
 * Created by AtechM_02 on 12/11/2015.
 */

import android.content.Context;
import android.provider.BaseColumns;

// Referenced classes of package in.ariatech.society.database:
//            DatabaseHandler

public class TableRecordings {
    public static abstract class DataComplaint
            implements BaseColumns {
        // Recording
        public static final String Recording_KEY_recId = "recId";
        public static final String Recording_KEY_serverId = "serverId";
        public static final String Recording_KEY_userId = "userId";
        public static final String Recording_KEY_upMasterId = "upMasterId";
        public static final String Recording_KEY_sourceTypeId = "sourceTypeId";
        public static final String Recording_KEY_sourceLink = "sourceLink";
        public static final String Recording_KEY_recName = "recName";
        public static final String Recording_KEY_recDesc = "recDesc";
        public static final String Recording_KEY_recDurationfloat = "recDurationfloat";
        public static final String Recording_KEY_recLocalPath = "recLocalPath";
        public static final String Recording_KEY_recSize = "recSize";
        public static final String Recording_KEY_recUploadDuration = "recUploadDuration";
        public static final String Recording_KEY_uploadingConnectionMode = "uploadingConnectionMode";
        public static final String Recording_KEY_createdDate = "createdDate";
        public static final String TABLE = "MYRECORDINGS";


    }

    public static final String SQL = "CREATE TABLE 'MYRECORDINGS' ( 'recId' INTEGER , 'serverId' INTEGER, 'userId' INTEGER, 'upMasterId' INTEGER, 'sourceTypeId' INTEGER,'sourceLink' TEXT, 'recName' TEXT, 'recDesc' TEXT, 'recDurationfloat' TEXT,  'recLocalPath' TEXT, 'recSize' INTEGER, 'recUploadDuration'  INTEGER,'uploadingConnectionMode' TEXT, 'createdDate' TEXT, PRIMARY KEY(recId));";

    Context c;

    public TableRecordings(Context context) {
        c = context;
    }


  /*  public void insertAll(LinkedList linkedlist) {
        for (int i = 0; i < linkedlist.size(); i++) {
            insert((MyRecording) linkedlist.get(i));
        }

    }



    public LinkedList selectAllComplaint() {
        LinkedList linkedlist = (new DatabaseHandler(c)).select("select * from MYRECORDINGS");
        LinkedList linkedlist1 = new LinkedList();
        for (int i = 0; i < linkedlist.size(); i++) {
            MyRecording myRecording = new MyRecording();
            MyRecording.setContentValues((ContentValues) linkedlist.get(i));
            linkedlist1.add(myRecording);
        }

        return linkedlist1;
    }*/


  /*  public LinkedList<OrderRecordings> getOrderRecordingsList(int orderRecId) {
        String sql = "select *  from OrderRecordings where orderRecId=" + orderRecId;
        Cursor c = ourDatabase.rawQuery(sql, null);
        LinkedList<OrderRecordings> list = new LinkedList<OrderRecordings>();
        int i0 = c.getColumnIndex(OrderRecordings_KEY_orderRecId);
        int i1 = c.getColumnIndex(OrderRecordings_KEY_orderId);
        int i2 = c.getColumnIndex(OrderRecordings_KEY_recId);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            try {
                OrderRecordings bean = new OrderRecordings();
                bean.setOrderRecId(c.getInt(i0));
                bean.setOrderId(c.getInt(i1));
                bean.setRecId(c.getInt(i2));

                list.add(bean);
            } catch (Exception e) {
                GlobalData.printError("OrderRecordings Read:", e);
            }
        }
        c.close();
        return list;
    }*/
}
