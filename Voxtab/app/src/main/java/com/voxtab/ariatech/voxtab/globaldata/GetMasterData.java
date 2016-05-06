package com.voxtab.ariatech.voxtab.globaldata;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.view.KeyEvent;

import com.ariatech.lib_project.custom.TransparentProgressDialog;
import com.voxtab.ariatech.voxtab.HomeActivity;
import com.voxtab.ariatech.voxtab.R;
import com.voxtab.ariatech.voxtab.bean.Delivery_option;
import com.voxtab.ariatech.voxtab.bean.Delivery_slot;
import com.voxtab.ariatech.voxtab.bean.Delivery_speed;
import com.voxtab.ariatech.voxtab.bean.Discount;
import com.voxtab.ariatech.voxtab.bean.Discount_type;
import com.voxtab.ariatech.voxtab.bean.File_source_type;
import com.voxtab.ariatech.voxtab.bean.File_status_type;
import com.voxtab.ariatech.voxtab.bean.Free_services;
import com.voxtab.ariatech.voxtab.bean.Holidays;
import com.voxtab.ariatech.voxtab.bean.Invoice_type;
import com.voxtab.ariatech.voxtab.bean.Local_resources;
import com.voxtab.ariatech.voxtab.bean.Output_format;
import com.voxtab.ariatech.voxtab.bean.Pages;
import com.voxtab.ariatech.voxtab.bean.Premium_type;
import com.voxtab.ariatech.voxtab.bean.Price;
import com.voxtab.ariatech.voxtab.bean.Service_type;
import com.voxtab.ariatech.voxtab.bean.Status_type;
import com.voxtab.ariatech.voxtab.bean.Tat_fee;
import com.voxtab.ariatech.voxtab.bean.TimeStamb;
import com.voxtab.ariatech.voxtab.bean.Time_slab;
import com.voxtab.ariatech.voxtab.bean.Timestamps_cal;
import com.voxtab.ariatech.voxtab.bean.Transcription_type;
import com.voxtab.ariatech.voxtab.bean.VAS_Rate;
import com.voxtab.ariatech.voxtab.bean.Vas;
import com.voxtab.ariatech.voxtab.database.DatabaseHandler;
import com.voxtab.ariatech.voxtab.database.DatabaseHandlerNew;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.LinkedList;

@SuppressLint({ "ParserError", "ParserError" })
public class GetMasterData extends AsyncTask<String, Void, String> implements
		OnKeyListener {
	Date dates[];
	int total = 0;


	WebServiceMySQl webService = new WebServiceMySQl(context);
	TransparentProgressDialog pd = null;
	boolean loadingFlag= false;

	public GetMasterData(Context context,boolean loadingFlag) {
		this.context = context;
		this.loadingFlag = loadingFlag;
	}

	boolean firstLoad = false;
	static Context context;



	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		if(loadingFlag) {
			pd = new TransparentProgressDialog(context,context.getResources().getString(R.string.loading_content));
			pd.show();
		}

		GlobalData.dataDownloadFlag = false;
	}

	@SuppressLint({ "ParserError", "ParserError", "ParserError" })
	@Override
	protected String doInBackground(String... params) {

		try {


			webService = new WebServiceMySQl(context);

			DatabaseHandlerNew db = null;
			GlobalData.printMessage("dataDownloadFlag true ");

			try {

				db = new DatabaseHandlerNew(context);

				try {
					db.open();
				}catch (Exception e){
					GlobalData.printError(e);
				}finally {
					db.close();
				}
				db.open();

				try {
					// total = db.getEventCount();


				} catch (Exception e) {

					total = 0;
				}
				GlobalData.printMessage("Index List Size:" + total);

				if (total > 0) {
					try {

						//setdataFromDB();

						// GlobalData.printMessagem.out.println("Aulist Size:"+GlobalData.allData.auList.size());

					} catch (Exception e) {

						GlobalData.printError(e);
					} finally {
						GlobalData.dataDownloadFlag = true;
					}

				} else {
					firstLoad = true;
				}

			} catch (Exception e) {

				GlobalData.printError("Backgroud Error", e);
				// GlobalData.allData.indexList = new LinkedList<MainIndex>();
			} finally {
				db.close();
			}

			if (GlobalData.isNetworkAvailable(context)) {

				SharedPreferences settings = PreferenceManager
						.getDefaultSharedPreferences(context);

				// Download Data from Server.
				getApplicationData();

				SharedPreferences.Editor editor1 = settings.edit();
				editor1.putInt("updateFlag", 0);
				editor1.putInt("firstTime", 0);
				editor1.commit();

				GlobalData.setFirstTimeDataLoading(context);

			} else {

				if (total <= 0) {
					// Toast.makeText(context, GlobalData.noConnection,
					// Toast.LENGTH_LONG).show();
				}
			}
		} catch (Exception e) {

			GlobalData.printError("", e);

		} finally {

			try {
				try {

				//	setdataFromDB();

				} catch (Exception e) {

					GlobalData.printError(e);
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}

			GlobalData.dataDownloadFlag = true;
		}

		return null;
	}




	private void getApplicationData() {
		// TODO Auto-generated method stub

		WebServiceMySQl webService = new WebServiceMySQl(context);
		DatabaseHandlerNew db = new DatabaseHandlerNew(context);

		try {
			db.open();


			JSONObject jsonObject = new JSONObject();
			jsonObject.put("delivery_slot_timestamp",""+ db.getLastModDate(Delivery_slot.class.getSimpleName()));
			jsonObject.put("delivery_option_timestamp",""+ db.getLastModDate(Delivery_option.class.getSimpleName()));
			jsonObject.put("delivery_speed_timestamp",""+ db.getLastModDate(Delivery_speed.class.getSimpleName()));
			jsonObject.put("file_source_type_timestamp",""+ db.getLastModDate(File_source_type.class.getSimpleName()));
			jsonObject.put("discount_type_timestamp",""+db.getLastModDate(Discount_type.class.getSimpleName()));
			jsonObject.put("tat_fee_timestamp",""+ db.getLastModDate(Tat_fee.class.getSimpleName()));
			jsonObject.put("time_slab_timestamp",""+ db.getLastModDate(Time_slab.class.getSimpleName()));
			jsonObject.put("transcription_type_timestamp",""+ db.getLastModDate(Transcription_type.class.getSimpleName()));
			jsonObject.put("vas_timestamp",""+ db.getLastModDate(Vas.class.getSimpleName()));
			jsonObject.put("timestamp",""+ db.getLastModDate(TimeStamb.class.getSimpleName()));
			jsonObject.put("vas_rate",""+ db.getLastModDate(VAS_Rate.class.getSimpleName()));
			jsonObject.put("file_status_type_timestamp",""+ db.getLastModDate(File_status_type.class.getSimpleName()));
			jsonObject.put("discount_timestamp",""+ db.getLastModDate(Discount.class.getSimpleName()));
			jsonObject.put("service_type_timestamp",""+ db.getLastModDate(Service_type.class.getSimpleName()));
			jsonObject.put("output_format_timestamp",""+ db.getLastModDate(Output_format.class.getSimpleName()));
			jsonObject.put("premium_type_timestamp",""+ db.getLastModDate(Premium_type.class.getSimpleName()));
			jsonObject.put("jpn_local_resources_timestamp",""+ db.getLastModDate(Local_resources.class.getSimpleName()));
			jsonObject.put("status_type_timestamp",""+ db.getLastModDate(Status_type.class.getSimpleName()));
			jsonObject.put("price_timestamp",""+db.getLastModDate(Price.class.getSimpleName()));
			jsonObject.put("invoice_type_timestamp",""+ db.getLastModDate(Invoice_type.class.getSimpleName()));
			jsonObject.put("free_services_timestamp",""+ db.getLastModDate(Free_services.class.getSimpleName()));
			jsonObject.put("holidays_timestamp",""+ db.getLastModDate(Holidays.class.getSimpleName()));
			jsonObject.put("TimesstambCalculation",""+ db.getLastModDate(Timestamps_cal.class.getSimpleName()));


			//GetMasterUpdateResult
try {



			WebServiceResonse res = webService.GetMasterUpdateResult(jsonObject.toString());

			if (res.getStatus() == 200) {

				try {

					parseDaliveryOPtio(res);
					parseDiscountType(res);
					parseDiscount(res);
					parseDelivery_slot(res);
					parseFile_status_type(res);
					parseDelivery_Speed(res);
					parseFile_source_type(res);
					parseFile_status_type(res);
					parseFree_services(res);
					parseHolidays(res);
					parsePrice_status(res);
					parseServicetype(res);
					parseStatus_type(res);
					parseTimestamp(res);
					parseTime_slab(res);
					parseTranscription_type(res);
					parseVas(res);
					parseVas_Rate(res);
					parseTimeStamp_Calculation(res);
					parsePremium_type(res);

					GlobalData.BackupDatabase(context);
//
				} catch (Exception e) {
					// TODO: handle exception
					GlobalData.printError(e);
				}

			}

}catch (Exception e){
	GlobalData.printError(e);
}


			// Update Pages Data

			try {

				WebServiceResonse res = webService.GetPages();

				if (res.getStatus() == 200) {
					parsePages(res);
				}

			}catch (Exception e){
				GlobalData.printError(e);
			}



		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		} finally {

		}

	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		try {

			if(loadingFlag) {
				if (pd != null) {
					pd.dismiss();
					pd = null;
				}

				context.startActivity(new Intent(context, HomeActivity.class));
			}

			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(context);
			SharedPreferences.Editor editor2 = settings.edit();
			editor2.putInt("newEventDownloadFlag", 1);
			editor2.commit();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void setdataFromDB() {
		DatabaseHandler db = new DatabaseHandler (context);
		try {
			db.open();
			// GlobalData.allData.eventList = db.getEvent();
			// GlobalData.allData.indexList = db.getAllDataIndex();

			// GlobalData.allData.subIndexList = db.getSubIndex();
			// GlobalData.allData.itemist = db.getItemList();
			// // GlobalData.allData.pageTypeList = db.getPageType();
			// GlobalData.allData.mediaTypeList = db.getMediaType();
			// GlobalData.allData.mediaList = db.getMedia();
			// GlobalData.allData.locationList = db.getLocation();
			// GlobalData.allData.contactList = db.getContacts();
			// GlobalData.allData.pollQiestionList = db.getPollQuestion();
			// GlobalData.allData.notificationList = db.getNotification();

			// GlobalData.printMessagem.out.println("Aulist Size:"+GlobalData.allData.auList.size());

		} catch (Exception e) {

			GlobalData.printError(e);

		} finally {

			db.close();
		}
	}

	public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
		// TODO Auto-generated method stub
		return false;
	}



	public  void parseDaliveryOPtio(WebServiceResonse res){

		// Load Data inot Database

		// TODO: 3/24/2016  Time Stamp And Vas Rate remaing table
		

		LinkedList<Delivery_option> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("del_opt_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("delivery_option");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						Delivery_option bean=new Delivery_option();
						bean.parseJSON(object);
						list.add(bean);


					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
			db.addDelivery_optionList(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}


	public  void parseDiscountType(WebServiceResonse res){

		// Load Data inot Database

		// TODO: 3/24/2016  Time Stamp And Vas Rate remaing table


		LinkedList<Discount_type> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("discount_type_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("discount_type");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						Discount_type bean=new Discount_type();
						bean.parseJSON(object);
						list.add(bean);


					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addDiscount_typeList(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}

	public  void parseDiscount(WebServiceResonse res){

		// Load Data inot Database

		// TODO: 3/24/2016  Time Stamp And Vas Rate remaing table


		LinkedList<Discount> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("discount_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("discount");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						Discount bean=new Discount();
						bean.parseJSON(object);
						list.add(bean);


					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addDiscountList(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}

	public  void parsePremium_type(WebServiceResonse res){

		// Load Data inot Database

		// TODO: 3/24/2016  Time Stamp And Vas Rate remaing table


		LinkedList<Premium_type> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("premium_type_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("premium_type");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						Premium_type bean=new Premium_type();
						bean.parseJSON(object);
						list.add(bean);


					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addPremium_typeList(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}

	public  void parseDelivery_slot(WebServiceResonse res){

		// Load Data inot Database

		LinkedList<Delivery_slot> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("del_slot_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("delivery_slot");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						Delivery_slot bean=new Delivery_slot();
						bean.parseJSON(object);
						list.add(bean);


					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addDelivery_slotList(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}




	public  void parseDelivery_Speed(WebServiceResonse res){

		LinkedList<Delivery_speed> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("del_speed_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("delivery_speed");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						Delivery_speed bean=new Delivery_speed();
						bean.parseJSON(object);
						list.add(bean);


					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addDelivery_speedList(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}


	public  void parseFile_source_type(WebServiceResonse res){

		LinkedList<File_source_type> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("file_sourcetype_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("file_source_type");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						File_source_type bean=new File_source_type();
						bean.parseJSON(object);
						list.add(bean);
					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addFile_source_typeList(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}



	public  void parseFile_status_type(WebServiceResonse res){

		LinkedList<File_status_type> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("file_status_type_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("file_status_type");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						File_status_type bean=new File_status_type();
						bean.parseJSON(object);
						list.add(bean);
					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addFile_status_typeList(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}


	public  void parseFree_services(WebServiceResonse res){

		LinkedList<Free_services> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("free_services_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("free_services");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						Free_services bean=new Free_services();
						bean.parseJSON(object);
						list.add(bean);
					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addFree_servicesList(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}


	public  void parseHolidays(WebServiceResonse res){

		LinkedList<Holidays> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("holidays_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("holidays");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						Holidays bean=new Holidays();
						bean.parseJSON(object);
						list.add(bean);
					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addHolidaysList(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}

	public  void parsePrice_status(WebServiceResonse res){

		LinkedList<Price> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("price_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("price");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						Price bean=new Price();
						bean.parseJSON(object);
						list.add(bean);
					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addPriceList(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}



	public  void parseServicetype(WebServiceResonse res){

		LinkedList<Service_type> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("servicetype_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("service_type");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						Service_type bean=new Service_type();
						bean.parseJSON(object);
						list.add(bean);
					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addService_typeList(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}


	public  void parseStatus_type(WebServiceResonse res){

		LinkedList<Status_type> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("status_type_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("status_type");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						Status_type bean=new Status_type();
						bean.parseJSON(object);
						list.add(bean);
					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addStatus_typeList(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}


	//TODO Add Table parseTimestamp
	public  void parseTimestamp(WebServiceResonse res){

		LinkedList<TimeStamb> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("timestamp_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("timestamp");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						TimeStamb bean=new TimeStamb();
						bean.parseJSON(object);
						list.add(bean);
					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addTime_stamp(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}


	public  void parseTime_slab(WebServiceResonse res){

		LinkedList<Time_slab> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("time_slab_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("time_slab");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						Time_slab bean=new Time_slab();
						bean.parseJSON(object);
						list.add(bean);
					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addTime_slabList(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}


	public  void parseTranscription_type(WebServiceResonse res){

		LinkedList<Transcription_type> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("transcription_type_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("transcription_type");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						Transcription_type bean=new Transcription_type();
						bean.parseJSON(object);
						list.add(bean);
					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addTranscription_typeList(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}


	public  void parseVas(WebServiceResonse res){

		LinkedList<Vas> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("vas_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("vas");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						Vas bean=new Vas();
						bean.parseJSON(object);
						list.add(bean);
					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addVasList(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}


	//TODO Create Table VAS Rate
	public  void parseVas_Rate(WebServiceResonse res){

		LinkedList<VAS_Rate> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("vas_rate_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("vas_rate");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						VAS_Rate bean=new VAS_Rate();
						bean.parseJSON(object);
						list.add(bean);
					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addVAS_Rate(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}



	// Authentication

	// Media List

	public String getAuthentication(String username, String password) {

		String data = "";
		try {

			// data = webService.getAuthentication(username, password);

			GlobalData.printMessage("Authentication Data:" + data);

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError("Get Index Data", e);

		}
		return data;
	}

	LinkedList<String> imageDownloadList = new LinkedList<String>();

	public JSONArray getJSONArray(JSONObject jsonObject, String statusStr,
			String arrayName) {

		JSONArray jsonArray = new JSONArray();
		try {
			if (jsonObject.has(statusStr)) {

				int status = 0;

				status = Integer.parseInt(jsonObject.getString(statusStr));

				if (status == 200) {

					if (jsonObject.has(arrayName)) {

						jsonArray = jsonObject.getJSONArray(arrayName);

					}

				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

		return jsonArray;
	}

//	// Add Speciality Data
//	public void addSpecialityData(JSONObject jsonObject, String statusStr,
//			String arrayName) {
//
//		LinkedList<Speciality> list = new LinkedList<Speciality>();
//		DataBaseHandlerNew db = new DataBaseHandlerNew(context);
//		try {
//			db.open();
//
//			JSONArray array = getJSONArray(jsonObject, statusStr, arrayName);
//
//			// Get JSON DATA
//			for (int i = 0; i < array.length(); i++) {
//				try {
//					Speciality bean = new Speciality();
//					;
//					bean.parseJSON(array.getJSONObject(i));
//					list.add(bean);
//				} catch (Exception e) {
//				}
//
//			}
//
//			// Add into Database
//
//			db.addSpecialityList(list);
//		} catch (Exception e) {
//			// TODO: handle exception
//			GlobalData.printError(e);
//		} finally {
//			db.close();
//		}
//
//	}
//
//	// Add Hospital_speciality Data
//	public void addHospital_specialityData(JSONObject jsonObject,
//			String statusStr, String arrayName) {
//
//		LinkedList<Hospital_speciality> list = new LinkedList<Hospital_speciality>();
//		DataBaseHandlerNew db = new DataBaseHandlerNew(context);
//		try {
//			db.open();
//
//			JSONArray array = getJSONArray(jsonObject, statusStr, arrayName);
//
//			// Get JSON DATA
//			for (int i = 0; i < array.length(); i++) {
//				try {
//					Hospital_speciality bean = new Hospital_speciality();
//					;
//					bean.parseJSON(array.getJSONObject(i));
//					list.add(bean);
//				} catch (Exception e) {
//				}
//
//			}
//
//			// Add into Database
//
//			db.addHospital_specialityList(list);
//		} catch (Exception e) {
//			// TODO: handle exception
//			GlobalData.printError(e);
//		} finally {
//			db.close();
//		}
//
//	}
//
//	// Add Room_Details Data
//	public void addRoom_DetailsData(JSONObject jsonObject, String statusStr,
//			String arrayName) {
//
//		LinkedList<Room_details> list = new LinkedList<Room_details>();
//		DataBaseHandlerNew db = new DataBaseHandlerNew(context);
//		try {
//			db.open();
//
//			JSONArray array = getJSONArray(jsonObject, statusStr, arrayName);
//
//			// Get JSON DATA
//			for (int i = 0; i < array.length(); i++) {
//				try {
//					Room_details bean = new Room_details();
//					;
//					bean.parseJSON(array.getJSONObject(i));
//					list.add(bean);
//				} catch (Exception e) {
//				}
//
//			}
//
//			// Add into Database
//
//			db.addRoom_detailsList(list);
//		} catch (Exception e) {
//			// TODO: handle exception
//			GlobalData.printError(e);
//		} finally {
//			db.close();
//		}
//
//	}
//
//	// Add Request_time_period Data
//	public void addRequest_time_periodData(JSONObject jsonObject,
//			String statusStr, String arrayName) {
//
//		LinkedList<Request_time_period> list = new LinkedList<Request_time_period>();
//		DataBaseHandlerNew db = new DataBaseHandlerNew(context);
//		try {
//			db.open();
//
//			JSONArray array = getJSONArray(jsonObject, statusStr, arrayName);
//
//			// Get JSON DATA
//			for (int i = 0; i < array.length(); i++) {
//				try {
//					Request_time_period bean = new Request_time_period();
//					;
//					bean.parseJSON(array.getJSONObject(i));
//					list.add(bean);
//				} catch (Exception e) {
//				}
//
//			}
//
//			// Add into Database
//
//			db.addRequest_time_periodList(list);
//		} catch (Exception e) {
//			// TODO: handle exception
//			GlobalData.printError(e);
//		} finally {
//			db.close();
//		}
//
//	}
//
//	// Add Hospital Speciality
//		public void addhosp_speciality_rel_statusData(JSONObject jsonObject,
//				String statusStr, String arrayName) {
//
//			LinkedList<Hospital_speciality> list = new LinkedList<Hospital_speciality>();
//			DataBaseHandlerNew db = new DataBaseHandlerNew(context);
//			try {
//				db.open();
//
//				JSONArray array = getJSONArray(jsonObject, statusStr, arrayName);
//
//				// Get JSON DATA
//				for (int i = 0; i < array.length(); i++) {
//					try {
//						Hospital_speciality bean = new Hospital_speciality();
//						;
//						bean.parseJSON(array.getJSONObject(i));
//						list.add(bean);
//					} catch (Exception e) {
//					}
//
//				}
//
//				// Add into Database
//
//				db.addHospital_specialityList(list);
//			} catch (Exception e) {
//				// TODO: handle exception
//				GlobalData.printError(e);
//			} finally {
//				db.close();
//			}
//
//		}

//	// Add Hospital Data
//	public void addHospitalData(JSONObject jsonObject, String statusStr,
//								String arrayName) {
//
//		LinkedList<Hospital> list = new LinkedList<Hospital>();
//		DataBaseHandlerNew db = new DataBaseHandlerNew(context);
//		try {
//			db.open();
//
//			JSONArray array = getJSONArray(jsonObject, statusStr, arrayName);
//
//			// Get JSON DATA
//			for (int i = 0; i < array.length(); i++) {
//				try {
//					Hospital bean = new Hospital();
//					;
//					bean.parseJSON(array.getJSONObject(i));
//					list.add(bean);
//				} catch (Exception e) {
//				}
//
//			}
//
//			// Add into Database
//
//			db.addHospitalList(list);
//		} catch (Exception e) {
//			// TODO: handle exception
//			GlobalData.printError(e);
//		} finally {
//			db.close();
//		}
//
//	}
//
//	// Add Procedure Data
//	public void addProcedureData(JSONObject jsonObject, String statusStr,
//								 String arrayName) {
//
//		LinkedList<Procedure> list = new LinkedList<Procedure>();
//		DataBaseHandlerNew db = new DataBaseHandlerNew(context);
//		try {
//			db.open();
//
//			JSONArray array = getJSONArray(jsonObject, statusStr, arrayName);
//
//			// Get JSON DATA
//			for (int i = 0; i < array.length(); i++) {
//				try {
//					Procedure bean = new Procedure();
//					;
//					bean.parseJSON(array.getJSONObject(i));
//					list.add(bean);
//				} catch (Exception e) {
//				}
//
//			}
//
//			// Add into Database
//
//			db.addProcedureList(list);
//		} catch (Exception e) {
//			// TODO: handle exception
//			GlobalData.printError(e);
//		} finally {
//			db.close();
//		}
//
//	}

	public  void parseTimeStamp_Calculation(WebServiceResonse res){

		LinkedList<Timestamps_cal> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();

			if(res.getJsonObject().getString("timestamb_calculation_status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("timestamb_calculation");

				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						Timestamps_cal bean=new Timestamps_cal();
						bean.parseJSON(object);
						list.add(bean);
					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.addTimestamp_calculation(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}

	public  void parsePages(WebServiceResonse res){

		LinkedList<Pages> list=new LinkedList<>();
		DatabaseHandlerNew db=new DatabaseHandlerNew(context);
		try {
			db.open();



			if(res.getJsonObject().getString("status").equals("200")){

				JSONArray array = res.getJsonObject().getJSONArray("pages");
//				HtmlEscape.unescapeHtml(escapedText);
				for (int i=0;i<array.length();i++){
					try {
						JSONObject object= array.getJSONObject(i);
						Pages bean=new Pages();
						bean.parseJSON(object);
						list.add(bean);
					}catch (Exception e){
						GlobalData.printError(e);
					}
				}

			}
			if(list.size()>0){
				db.deletePages();
				db.addPages(list);
			}


		}catch (Exception e){
			GlobalData.printError(e);
		}
	}
		
	
}
