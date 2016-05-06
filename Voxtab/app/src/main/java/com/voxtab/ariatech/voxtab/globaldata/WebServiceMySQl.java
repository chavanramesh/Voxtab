package com.voxtab.ariatech.voxtab.globaldata;


import android.content.Context;

import com.ariatech.lib_project.WebCommunicator;
import com.voxtab.ariatech.voxtab.bean.Users;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;

public class WebServiceMySQl {



	String res = "";

	//Common Methods

	//Upload File
	Context context ;

	public WebServiceMySQl(Context context){
		this.context=context;
	}

	public  String uploadFile(String sourceFileUri, String[] params) {

		String fileName = sourceFileUri;
		String serverResponseMessage = "";
		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);
		int serverResponseCode =0;
		if (!sourceFile.isFile()) {
//            dialog.dismiss();
			return "File not Found";
		} else {
			FileInputStream fileInputStream = null;
			try {
				// open a URL connection to the Servlet

				fileInputStream = new FileInputStream(sourceFile);
				java.net.URL url = new java.net.URL(GlobalData.UPLOAD_FILE);

				// Open a HTTP  connection to  the URL
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("FilePath", fileName);
                  /* conn.setRequestProperty("userid", ""+74);
                  conn.setRequestProperty("videotype", ""+7); */


				dos = new DataOutputStream(conn.getOutputStream());
				int count =(params.length/2);
				for (int i = 0; i<params.length  ;i=i+2) {

					try {

						dos.writeBytes(twoHyphens + boundary + lineEnd);
						dos.writeBytes("Content-Disposition: form-data; name=\""+params[i]+"\"" + lineEnd);
						dos.writeBytes(lineEnd);
						dos.writeBytes("" + params[i+1]);
						dos.writeBytes(lineEnd);
						dos.writeBytes(twoHyphens + boundary + lineEnd);



					}catch (Exception e){
						GlobalData.printError(e);
					}
				}


				dos.writeBytes(twoHyphens + boundary + lineEnd);
				dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
						+ fileName + "\"" + lineEnd);

				dos.writeBytes(lineEnd);
				// create a buffer of  maximum size

				bytesAvailable = fileInputStream.available();

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				long bytes = sourceFile.length();

				long uploadBytes=0;

				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);



				while (bytesRead > 0) {

					uploadBytes= uploadBytes + bytesRead;


					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);

				}


				GlobalData.printMessage("Uploaded Bytes:" +uploadBytes);
				GlobalData.printMessage("File Bytes:" +bytes);

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();
//           serverResponseMessage = conn.getResponseMessage();


				try {
					InputStream is = null;

					is = conn.getInputStream();
					int ch;
					StringBuffer sb = new StringBuffer();
					while ((ch = is.read()) != -1) {
						sb.append((char) ch);
					}
					serverResponseMessage= sb.toString();
				}catch (Exception e){
					GlobalData.printError(e);
				}






//
//          GlobalData.printMessage("uploadFile", "HTTP Response is : "+ serverResponseMessage + ": " + serverResponseCode);

				//close the streams //

				fileInputStream.close();
				dos.flush();
				dos.close();
			} catch (Exception ex) {
//                dialog.dismiss();
				GlobalData.printError( ex);
			}
//            dialog.dismiss();
			return serverResponseMessage;

		} // End else block
	}








	// Web services
	
//	1: GetMasterUpdateResult
	public WebServiceResonse GetMasterUpdateResult( String masterjson ) throws Exception {

		//(string api_key,string language, string device_type, string current_version,  string masterjson)
		WebServiceResonse resonse = new WebServiceResonse();
		res = "";

		if(GlobalData.deviceInfo== null){
			GlobalData.deviceInfo = new DeviceInfo(context);
		}




		String[] params = new String[]{"api_key",""+GlobalData.SERVERAPIKEY,
				"language","" + GlobalData.deviceInfo.getLanguage(),
				"device_type","" + GlobalData.deviceType,
				"current_version","" +  GlobalData.deviceInfo.getCurrent_version(),
				"masterjson",""+ java.net.URLEncoder.encode( masterjson)  };

		try {
			res = WebCommunicator.setHttpGETCall(GlobalData.URL+"/"+GlobalData.webSrviceGetMasterData+"/",params);



			resonse.parseData(res);

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printMessage("====Error===" + e.toString());

		}

		return resonse;
	}


	//  2: PlaceOrder
	public WebServiceResonse PlaceOrder    ( JSONObject orderjson,JSONObject file_meta_json, JSONObject userjson,JSONObject freesev) throws Exception {

		//(string api_key,string language, string device_type, string current_version,  string masterjson)
		WebServiceResonse resonse = new WebServiceResonse();
		//orderjson ="{\"user_id\": \"1\",\"client_instruction\": \"Test client Instruction \", \"total_files\": \"2\", \"service_type_id\": \"eng\", \"trans_type_id\": \"simple\", \"delivery_opt_id\": \"STD\", \"vas_id\": \"copy-edit\", \"invoice_type_id\": \"\", \"subject_of_file\": \"Test First Order\", \"connection_type\": \"WiFi\",  \"total_duration\": \"100\", \"delivery_date\": \"2016-10-13 00:00:00\", \"total_fees\": \"100\", \"order_placed_details\": \"order place details\", \"order_complete_details\": \"order complete details\"} ";

		res = "";
		if(GlobalData.deviceInfo== null){
			GlobalData.deviceInfo = new DeviceInfo(context);
		}

		String[] params = new String[]{"api_key",""+GlobalData.SERVERAPIKEY,
				"language","eng" + GlobalData.deviceInfo.getLanguage(),
				"user_id",""+GlobalData.userSelected.getUser_id()   };
//          "orderjson","" ,
//          "file_meta_json","" ,
//          "userjson",""  };


		JSONObject jsonObject = new JSONObject();
		jsonObject.put("api_key",""+GlobalData.SERVERAPIKEY);
		jsonObject.put("language","" + GlobalData.deviceInfo.getLanguage());
		jsonObject.put("user_id",""+GlobalData.userSelected.getUser_id());
		jsonObject.put("orderjson",orderjson);
		jsonObject.put("file_meta_json",file_meta_json);
		jsonObject.put("userjson",userjson);
		jsonObject.put("freeservicejson",freesev);
		JSONObject jsonObjectData =new JSONObject();

		jsonObjectData.put("data",jsonObject);


		try {
			res = WebCommunicator.setHttpPOSTCall_2(GlobalData.URL + "/" + GlobalData.webSrvicePlaceOrder + "", jsonObjectData.toString());



			resonse.parseData(res);

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printMessage("====Error===" + e.toString());

		}

		//res
//    <string>{"status":"200","message": "Your Order with Assignment number CAPGEJ-3M is now confirmed !","assignment_no":"CAPGEJ-3M" ,
//     "file_meta_json":"[ {"order_media_id": "5", "user_id": "2", "assignment_no": "CAPGEJ-3M", "file_status": "6", "source_type": "66", "file_name": "666", "file_description": "666"},{"order_media_id": "6", "user_id": "2", "assignment_no": "CAPGEJ-3M", "file_status": "6", "source_type": "66", "file_name": "33333", "file_description": "666"}]"}
//     </string>


		return resonse;
	}


	//  3: GetOrderList
	public WebServiceResonse GetOrderList( int offset, int limit ) throws Exception {


		if(GlobalData.deviceInfo== null){
			GlobalData.deviceInfo = new DeviceInfo(context);
		}

		//(string api_key,string language, string device_type, string current_version,  string masterjson)
		WebServiceResonse resonse = new WebServiceResonse();
		res = "";
		String[] params = new String[]{"api_key",""+GlobalData.SERVERAPIKEY,
				"user_id",""+GlobalData.userSelected.getUser_id(),
				"offset",""+offset,
				"limit",""+limit };

		try {
			res = WebCommunicator.setHttpGETCall(GlobalData.URL+"/"+GlobalData.webSrviceGetOrderList+"/",params);
			resonse.parseData(res);

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printMessage("====Error===" + e.toString());

		}

		return resonse;
	}





	//  3: Login
	public WebServiceResonse Login    ( String email ,String password) throws Exception {

	WebServiceResonse resonse = new WebServiceResonse();

		res = "";

		if(GlobalData.deviceInfo== null){
			GlobalData.deviceInfo = new DeviceInfo(context);
		}


		JSONObject jsonObject = new JSONObject();
		jsonObject.put("api_key",""+GlobalData.SERVERAPIKEY);
		jsonObject.put("email","" + email);
		jsonObject.put("password",""+password);
		jsonObject.put("imei",""+GlobalData.deviceInfo.getImei());
		jsonObject.put("device_type","1");

		JSONObject jsonObjectData =new JSONObject();
		jsonObjectData.put("data",jsonObject);


		try {
			res = WebCommunicator.setHttpPOSTCall_2(GlobalData.URL + "/" + GlobalData.webSrviceLogin + "", jsonObjectData.toString());



			resonse.parseData(res);

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printMessage("====Error===" + e.toString());

		}





		return resonse;
	}


	//  5: updateorder
	public WebServiceResonse updateorder( JSONObject orderJSON) throws Exception {

		WebServiceResonse resonse = new WebServiceResonse();

		res = "";

		if(GlobalData.deviceInfo== null){
			GlobalData.deviceInfo = new DeviceInfo(context);
		}


		JSONObject jsonObject = new JSONObject();
		jsonObject.put("api_key",""+GlobalData.SERVERAPIKEY);
		jsonObject.put("language","" + GlobalData.deviceInfo.getLanguage());
		jsonObject.put("imei",""+GlobalData.deviceInfo.getImei());
		jsonObject.put("orderjson",orderJSON);

		JSONObject jsonObjectData =new JSONObject();
		jsonObjectData.put("data",jsonObject);


		try {
			res = WebCommunicator.setHttpPOSTCall_2(GlobalData.URL + "/" + GlobalData.webSrviceUpdateorder + "", jsonObjectData.toString());

			resonse.parseData(res);

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printMessage("====Error===" + e.toString());

		}





		return resonse;
	}
	//6: GetOrderDetails

	public WebServiceResonse GetOrderDetails(String assignment_no, int order_id) throws Exception {

		// api_key=abcd&user_id=1&assignment_no=CAPGEJ-1M&order_id=1

		if (GlobalData.deviceInfo == null) {
			GlobalData.deviceInfo = new DeviceInfo(context);
		}

		//(string api_key,string language, string device_type, string current_version,  string masterjson)
		WebServiceResonse resonse = new WebServiceResonse();
		res = "";


		String[] params = new String[]{"api_key", "" + GlobalData.SERVERAPIKEY,
				"user_id", "" + GlobalData.userSelected.getUser_id(),
				"assignment_no", assignment_no,
				"order_id", ""+order_id
		};

		try {
			res = WebCommunicator.setHttpGETCall(GlobalData.URL + "/" + GlobalData.webSrviceGetOrderDetail + "/", params);
			resonse.parseData(res);

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printMessage("====Error===" + e.toString());

		}

		return resonse;
	}


	//7: updateprofile

	public WebServiceResonse Updateprofile(Users userSelected) throws Exception {

		// api_key=abcd&user_id=1&assignment_no=CAPGEJ-1M&order_id=1

		if (GlobalData.deviceInfo == null) {
			GlobalData.deviceInfo = new DeviceInfo(context);
		}

		//(string api_key,string language, string device_type, string current_version,  string masterjson)
		WebServiceResonse resonse = new WebServiceResonse();
		res = "";

		JSONObject object=new JSONObject();
		object.put("user_id", "" + GlobalData.userSelected.getUser_id());
		object.put("photo", "" + userSelected.getImgBase64());
		object.put("mobile_no", "" + userSelected.getMobile_no());


		object.put("noti_setting_type", "" + GlobalData.getNotificationkey(context));
		object.put("upload_setting_type", "" + GlobalData.getUploadkey(context));


		JSONObject jsonObjectDataTemp =new JSONObject();
		jsonObjectDataTemp.put("api_key", "" + GlobalData.SERVERAPIKEY);
		jsonObjectDataTemp.put(		"language",""+GlobalData.deviceInfo.getLanguage());
		jsonObjectDataTemp.put("user_id", "" + GlobalData.userSelected.getUser_id());
		jsonObjectDataTemp.put("userjson", object);


		JSONObject jsonObjectData =new JSONObject();
		jsonObjectData.put("data",jsonObjectDataTemp);


		try {
			res = WebCommunicator.setHttpPOSTCall_2(GlobalData.URL + "/" + GlobalData.webSrviceUpdateprofile + "", jsonObjectData.toString());

			resonse.parseData(res);

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printMessage("====Error===" + e.toString());

		}

		return resonse;

//		{
//			"status": "200",
//				"user_details": [{
//			"user_id": "1",
//					"email": "demo@gmail.com",
//					"first_name": "Demo",
//					"first_name_fn": "ラーフル",
//					"last_name": "kurkute",
//					"last_name_fn": "ラーフル",
//					"photo": "IMG_2016413164649.png",
//					"gender": "",
//					"designation": "",
//					"email_2": "",
//					"mobile_no": "7709999999",
//					"extension": "",
//					"noti_setting_type": "",
//					"upload_setting_type": ""
//		}],
//			"message": "User Profile Updated Successfully"
//		}

	}


	//
	//  5: getprofile
	public WebServiceResonse Getprofile    ( JSONObject orderJSON) throws Exception {

		WebServiceResonse resonse = new WebServiceResonse();

		res = "";

		if(GlobalData.deviceInfo== null){
			GlobalData.deviceInfo = new DeviceInfo(context);
		}


		JSONObject jsonObject = new JSONObject();
		jsonObject.put("api_key",""+GlobalData.SERVERAPIKEY);
		jsonObject.put("language","" + GlobalData.deviceInfo.getLanguage());
		jsonObject.put("user_id",""+GlobalData.userSelected.getUser_id());
		jsonObject.put("imei",GlobalData.deviceInfo.getImei());

		JSONObject jsonObjectData =new JSONObject();
		jsonObjectData.put("data",jsonObject);


		try {
			res = WebCommunicator.setHttpPOSTCall_2(GlobalData.URL + "/" + GlobalData.webSrviceGetprofile + "", jsonObjectData.toString());

			resonse.parseData(res);

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printMessage("====Error===" + e.toString());

		}





		return resonse;

//		{
//			"status":"200",
//				"user_details":[
//			{
//				"user_id":"1",
//					"email":"demo@gmail.com",
//					"org_name_eng":"CapGemini",
//					"org_name_fn":"",
//					"first_name":"Demo",
//					"first_name_fn":"ラーフル",
//					"last_name":"kurkute",
//					"last_name_fn":"ラーフル",
//					"photo":"IMG_201641362711.png",
//					"gender":"",
//					"designation":"",
//					"email_2":"",
//					"mobile_no":"7709999999",
//					"extension":"",
//					"noti_setting_type":"",
//					"upload_setting_type":""
//			}
//			],
//			"message":"User Profile Updated Successfully"
//		}
	}

	//  5: getprofile
	public WebServiceResonse Getprofile    ( ) throws Exception {

		WebServiceResonse resonse = new WebServiceResonse();

		res = "";

		if(GlobalData.deviceInfo== null){
			GlobalData.deviceInfo = new DeviceInfo(context);
		}


		JSONObject jsonObject = new JSONObject();
		jsonObject.put("api_key",""+GlobalData.SERVERAPIKEY);
		jsonObject.put("language","" + GlobalData.deviceInfo.getLanguage());
		jsonObject.put("user_id",""+GlobalData.userSelected.getUser_id());
		jsonObject.put("imei",GlobalData.deviceInfo.getImei());

		JSONObject jsonObjectData =new JSONObject();
		jsonObjectData.put("data",jsonObject);


		try {
			res = WebCommunicator.setHttpPOSTCall_2(GlobalData.URL + "/" + GlobalData.webSrviceGetprofile + "", jsonObjectData.toString());

			resonse.parseData(res);

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printMessage("====Error===" + e.toString());

		}





		return resonse;

//		{
//			"status":"200",
//				"user_details":[
//			{
//				"user_id":"1",
//					"email":"demo@gmail.com",
//					"org_name_eng":"CapGemini",
//					"org_name_fn":"",
//					"first_name":"Demo",
//					"first_name_fn":"ラーフル",
//					"last_name":"kurkute",
//					"last_name_fn":"ラーフル",
//					"photo":"IMG_201641362711.png",
//					"gender":"",
//					"designation":"",
//					"email_2":"",
//					"mobile_no":"7709999999",
//					"extension":"",
//					"noti_setting_type":"",
//					"upload_setting_type":""
//			}
//			],
//			"message":"User Profile Updated Successfully"
//		}
	}


	//9:// Change Password


	public WebServiceResonse Changepassword    ( String current_pwd,String new_pwd ) throws Exception {

		WebServiceResonse resonse = new WebServiceResonse();

		res = "";

		if(GlobalData.deviceInfo== null){
			GlobalData.deviceInfo = new DeviceInfo(context);
		}


		JSONObject jsonObject = new JSONObject();
		jsonObject.put("api_key",""+GlobalData.SERVERAPIKEY);
		jsonObject.put("language","" + GlobalData.deviceInfo.getLanguage());
		jsonObject.put("user_id",""+GlobalData.userSelected.getUser_id());
		jsonObject.put("imei",GlobalData.deviceInfo.getImei());
		jsonObject.put("current_pwd",""+current_pwd);
		jsonObject.put("new_pwd",""+new_pwd);

		JSONObject jsonObjectData =new JSONObject();
		jsonObjectData.put("data",jsonObject);


		try {
			res = WebCommunicator.setHttpPOSTCall_2(GlobalData.URL + "/" + GlobalData.webSrviceChangepassword + "", jsonObjectData.toString());

			resonse.parseData(res);

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printMessage("====Error===" + e.toString());

		}





		return resonse;

//		{
//			"status":"200",
//				"user_details":[
//			{
//				"user_id":"1",
//					"email":"demo@gmail.com",
//					"org_name_eng":"CapGemini",
//					"org_name_fn":"",
//					"first_name":"Demo",
//					"first_name_fn":"ラーフル",
//					"last_name":"kurkute",
//					"last_name_fn":"ラーフル",
//					"photo":"IMG_201641362711.png",
//					"gender":"",
//					"designation":"",
//					"email_2":"",
//					"mobile_no":"7709999999",
//					"extension":"",
//					"noti_setting_type":"",
//					"upload_setting_type":""
//			}
//			],
//			"message":"User Profile Updated Successfully"
//		}
	}


	//10:// UpdateGcmkey


	public WebServiceResonse UpdateGcmKey    ( String apiKey ,int userId) throws Exception {

		WebServiceResonse resonse = new WebServiceResonse();

		res = "";

		if(GlobalData.deviceInfo== null){
			GlobalData.deviceInfo = new DeviceInfo(context);
		}

		JSONObject object=new JSONObject();
		object.put("imei",""+ GlobalData.deviceInfo.getImei());
		object.put("apikey",""+apiKey);
		object.put("device_type",""+GlobalData.deviceType);
		object.put("device_name",""+ GlobalData.deviceInfo.getDevice_name());
		object.put("device_os",""+GlobalData.deviceInfo.getDevice_os());
		object.put("user_type","1");
		object.put("user_id",""+userId);




		JSONObject jsonObject = new JSONObject();
		jsonObject.put("api_key",""+GlobalData.SERVERAPIKEY);
		jsonObject.put("language","" + GlobalData.deviceInfo.getLanguage());

		jsonObject.put("devicejson",object);


		JSONObject jsonObjectData =new JSONObject();
		jsonObjectData.put("data",jsonObject);


		try {
			res = WebCommunicator.setHttpPOSTCall_2(GlobalData.URL + "/" + GlobalData.webSrviceUpdategcmkey + "", jsonObjectData.toString());

			resonse.parseData(res);

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printMessage("====Error===" + e.toString());

		}





		return resonse;

//		{
//			"status":"200",
//				"user_details":[
//			{
//				"user_id":"1",
//					"email":"demo@gmail.com",
//					"org_name_eng":"CapGemini",
//					"org_name_fn":"",
//					"first_name":"Demo",
//					"first_name_fn":"ラーフル",
//					"last_name":"kurkute",
//					"last_name_fn":"ラーフル",
//					"photo":"IMG_201641362711.png",
//					"gender":"",
//					"designation":"",
//					"email_2":"",
//					"mobile_no":"7709999999",
//					"extension":"",
//					"noti_setting_type":"",
//					"upload_setting_type":""
//			}
//			],
//			"message":"User Profile Updated Successfully"
//		}
	}


	//  5: GEtPages
	public WebServiceResonse GetPages    ( ) throws Exception {

		WebServiceResonse resonse = new WebServiceResonse();

		res = "";

		if(GlobalData.deviceInfo== null){
			GlobalData.deviceInfo = new DeviceInfo(context);
		}


		JSONObject jsonObject = new JSONObject();
		jsonObject.put("api_key",""+GlobalData.SERVERAPIKEY);
		jsonObject.put("language","" + GlobalData.deviceInfo.getLanguage());

		jsonObject.put("imei",GlobalData.deviceInfo.getImei());

		JSONObject jsonObjectData =new JSONObject();
		jsonObjectData.put("data",jsonObject);


		try {
			res = WebCommunicator.setHttpPOSTCall_2(GlobalData.URL + "/" + GlobalData.webSrvicegetpages + "", jsonObjectData.toString());

			resonse.parseData(res);

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printMessage("====Error===" + e.toString());

		}





		return resonse;

	}


	// 10.Getorderlist for feedback
	public WebServiceResonse GetOrderListForFeedback() throws Exception {

		WebServiceResonse resonse = new WebServiceResonse();

		res = "";

		if (GlobalData.deviceInfo == null) {
			GlobalData.deviceInfo = new DeviceInfo(context);
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("api_key", "" + GlobalData.SERVERAPIKEY);
		jsonObject.put("language",""+ GlobalData.deviceInfo.getLanguage());
		jsonObject.put("user_id", "" + GlobalData.userSelected.getUser_id());
		jsonObject.put("imei", GlobalData.deviceInfo.getImei());

		JSONObject jsonObjectData = new JSONObject();
		jsonObjectData.put("data", jsonObject);


		try {
			res = WebCommunicator.setHttpPOSTCall_2(GlobalData.URL + "/" + GlobalData.webSrviceGetOrderListForFeedback + "", jsonObjectData.toString());

			resonse.parseData(res);

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printMessage("====Error===" + e.toString());

		}
		return resonse;
	}


	//Send feedback
	public WebServiceResonse SendFeedback(JSONObject feedbackjson) throws Exception {

		WebServiceResonse resonse = new WebServiceResonse();

		res = "";
		if (GlobalData.deviceInfo == null) {
			GlobalData.deviceInfo = new DeviceInfo(context);
		}



		JSONObject jsonObject = new JSONObject();
		jsonObject.put("api_key", "" + GlobalData.SERVERAPIKEY);
		jsonObject.put("language", ""+GlobalData.deviceInfo.getLanguage());
		jsonObject.put("feedbackjson", feedbackjson);


		JSONObject jsonObjectData = new JSONObject();
		jsonObjectData.put("data", jsonObject);


		try {
			res = WebCommunicator.setHttpPOSTCall_2(GlobalData.URL + "/" + GlobalData.webSrviceSendFeedback + "", jsonObjectData.toString());


			resonse.parseData(res);

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printMessage("====Error===" + e.toString());

		}

		return resonse;
	}

	//forgot password


	public WebServiceResonse ForgotPassword() throws Exception {

		WebServiceResonse resonse = new WebServiceResonse();

		res = "";

		if (GlobalData.deviceInfo == null) {
			GlobalData.deviceInfo = new DeviceInfo(context);
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("api_key", "" + GlobalData.SERVERAPIKEY);
		jsonObject.put("language",""+ GlobalData.deviceInfo.getLanguage());
		jsonObject.put("email", "" + GlobalData.userSelected.getEmail());
		jsonObject.put("imei", GlobalData.deviceInfo.getImei());

		JSONObject jsonObjectData = new JSONObject();
		jsonObjectData.put("data", jsonObject);


		try {
			res = WebCommunicator.setHttpPOSTCall_2(GlobalData.URL + "/" + GlobalData.webSrviceForgotPassword + "", jsonObjectData.toString());

			resonse.parseData(res);

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printMessage("====Error===" + e.toString());

		}
		return resonse;
	}
	public WebServiceResonse Logout() throws Exception {

		WebServiceResonse resonse = new WebServiceResonse();

		res = "";

		if (GlobalData.deviceInfo == null) {
			GlobalData.deviceInfo = new DeviceInfo(context);
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("api_key", "" + GlobalData.SERVERAPIKEY);
		jsonObject.put("language",""+ GlobalData.deviceInfo.getLanguage());
		jsonObject.put("user_id", "" + GlobalData.userSelected.getUser_id());
		jsonObject.put("imei", GlobalData.deviceInfo.getImei());

		JSONObject jsonObjectData = new JSONObject();
		jsonObjectData.put("data", jsonObject);


		try {
			res = WebCommunicator.setHttpPOSTCall_2(GlobalData.URL + "/" + GlobalData.webSrviceLogout+ "", jsonObjectData.toString());

			resonse.parseData(res);

		} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printMessage("====Error===" + e.toString());

		}
		return resonse;
	}
}
