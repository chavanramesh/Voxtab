package com.voxtab.ariatech.voxtab.gcm;



import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.voxtab.ariatech.voxtab.HomeActivity;
import com.voxtab.ariatech.voxtab.R;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

public class NotificationMessageActivity extends Activity implements OnClickListener {

	ActionBar actionBar;
	
	
	Context con;

	Context context;
	
	
	
	Button buttonNtAtt;
	TextView textView;
	Button cancel;

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);
		GlobalData.activities.add(NotificationMessageActivity.this);
		try {

			// set Action bar
			try {
				context=NotificationMessageActivity.this;
				actionBar = getActionBar();
				actionBar.show();
				actionBar.setTitle("Message");
//				actionBar=GlobalData.setActionBar(context,actionBar);
			} catch (Exception e) {
				// TODO: handle exception
			}

			textView=(TextView)findViewById(R.id.textViewInfo);
			textView.setText("");
			
			cancel=(Button)findViewById(R.id.buttonClose);


		
			

			
			cancel.setOnClickListener(this);

			
			setData();

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}
	}

	public void setData() {

		try {
			String mesString="";
			String message="";
			int eventId=0;
			try {
				message=getIntent().getStringExtra("message");
				eventId=Integer.parseInt(getIntent().getStringExtra("eventId"));
			} catch (Exception e) {
				// TODO: handle exception
				message="";
				 eventId=0;
			}
			
			if(message.length()>0){
				 mesString=message;

				
			}else{
			
				
//				 mesString=GlobalData.selectedNotificationMessage;
			}
			
			
//			if(event.getEventId()==0){
//				event = GlobalData.selectedNotificationEvent;
//			}
			
			if(mesString.length()>0)
			textView.setText(""+mesString);
			else{
				finish();
			}

		
		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	}

	

	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		try {

			switch (arg0.getId()) {
		
			case R.id.buttonClose:

				context.startActivity(new Intent(context,HomeActivity.class));
				
				finish();

				break;

			default:
				break;
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	
	
@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
	context.startActivity(new Intent(context,HomeActivity.class));
	
	finish();
}
}
