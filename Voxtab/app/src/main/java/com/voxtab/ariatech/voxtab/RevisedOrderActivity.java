package com.voxtab.ariatech.voxtab;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

/**
 * Created by Local User on 17-Feb-16.
 */
public class RevisedOrderActivity extends AppCompatActivity {

    private Context context;
    private Toolbar toolbar;
    TextView lbl_details;
    Button btn_confirm, btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_revised_delivery_schedule);
        GlobalData.activities.add(RevisedOrderActivity.this);
        toolbar = GlobalData.initToolBarMenu(this, true, true);
        context = this;
        init();

    }

    private void init() {
        lbl_details = (TextView) findViewById(R.id.lbl_details);
        SpannableString details = new SpannableString("Details");
        details.setSpan(new UnderlineSpan(), 0, details.length(), 0);
        lbl_details.setText(details);

        btn_confirm = (Button) findViewById(R.id.btn_complete_order);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm_order(v);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              cancel();
                                          }
                                      }
        );


    }

    private void confirm_order(View v) {
    }

    private void cancel() {

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.cancel_alert))
                .setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"))
                .setNegativeButton(R.string.no, null)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(RevisedOrderActivity.this, MyRecordingsActivity.class);
                        startActivity(intent);
                    }
                }).create().show();
    }


}
