package com.voxtab.ariatech.voxtab;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.voxtab.ariatech.voxtab.adapter.ChangePriceAdapter;
import com.voxtab.ariatech.voxtab.bean.ChangePrice;
import com.voxtab.ariatech.voxtab.database.DatabaseHandler;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Local User on 17-Feb-16.
 */
public class ChangePriceActivity extends AppCompatActivity {

    ListView lst_price_list;
    private Context context;
    private ChangePriceAdapter changePriceAdapter;
    private GetSchemeDetails records = null;
    public static ArrayList<ChangePrice> change_price_arraylist;
    LinkedList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_price);
        GlobalData.activities.add(ChangePriceActivity.this);
        context = this;
        init();
    }

    private void init() {
        lst_price_list = (ListView) findViewById(R.id.lst_change);

       /* ChangePrice changePrice = new ChangePrice();
        changePrice.setScheme_name("Expenses");
        changePrice.setScheme_duration("Date&Time");
        changePrice.setPrice("$20");
        changePrice.setDate_time("20Dec 2016");
        */
        setAdapaterData();

    }

    public void setAdapaterData() {
        try {
            records = new GetSchemeDetails();
            records.execute((Void) null);
        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }

    public class GetSchemeDetails extends AsyncTask<Void, Void, LinkedList> {
        @Override
        protected LinkedList doInBackground(Void... params) {
            DatabaseHandler databaseHandler = new DatabaseHandler(context);
            LinkedList list_details = new LinkedList();
            try {
                databaseHandler.open();
                list_details = databaseHandler.getSchemeDetails();

            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {
                databaseHandler.close();
            }
            return list_details;
        }

        @Override
        protected void onPostExecute(final LinkedList list_details) {
            records = null;
            change_price_arraylist = new ArrayList<>(list_details);

            if (list_details != null) {
                if (list_details.size() > 0) {
                    list = list_details;
                    changePriceAdapter = new ChangePriceAdapter(context, change_price_arraylist);
                    lst_price_list.setAdapter(changePriceAdapter);
                }
            }
        }

        @Override
        protected void onCancelled() {
            records = null;
        }

    }

}
