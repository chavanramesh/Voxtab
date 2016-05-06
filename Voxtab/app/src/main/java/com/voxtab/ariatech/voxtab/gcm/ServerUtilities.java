/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.voxtab.ariatech.voxtab.gcm;

import com.voxtab.ariatech.voxtab.CommonUtilities;
import com.voxtab.ariatech.voxtab.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.json.JSONObject;


import com.google.android.gcm.GCMRegistrar;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceMySQl;
import com.voxtab.ariatech.voxtab.globaldata.WebServiceResonse;

/**
 * Helper class used to communicate with the demo server.
 */
public final class ServerUtilities {

	private static final int MAX_ATTEMPTS = 5;
	private static final int BACKOFF_MILLI_SECONDS = 2000;
	private static final Random random = new Random();
	private static final String TAG = null;

	/**
	 * Register this account/device pair within the server.
	 * 
	 * @return whether the registration succeeded or not.
	 */
	@SuppressWarnings("unused")
	public static boolean register(final Context context, final String regId) {

		GlobalData.printMessage("Reg Id:" + regId);

		GlobalData.printMessage("registering device (regId = " + regId + ")");
//		String serverUrl = SERVER_URL + "/register";
		Map<String, String> params = new HashMap<String, String>();
		params.put("regId", regId);
		long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		// SharedPreferences.Editor editor = settings.edit();
		// editor.putInt("level", 1);
		// editor.commit();

		String oldRegID = settings.getString("regID", "0");
		String deviceId = settings.getString("deviceId", "0");

		if (oldRegID.equals(regId))
			return true;

		for (int i = 1; i <= MAX_ATTEMPTS; i++) {
			Log.d(TAG, "Attempt #" + i + " to register");
			try {
				String message = context.getString(R.string.server_register_error,
						MAX_ATTEMPTS);
				CommonUtilities.displayMessage(context, message);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString("regID", regId);
				editor.putInt("regIDFlag", 0);

				int appId = 0;
				try {

						try {

							TelephonyManager telephonyManager = (TelephonyManager) context
									.getSystemService(Context.TELEPHONY_SERVICE);
							String uniqueid = telephonyManager.getDeviceId().toString();

							WebServiceMySQl ws = new WebServiceMySQl(context);

							int updatedKey= settings.getInt("SETGCMKEY",0);

							if (GlobalData.userSelected.getUser_id()>0 && regId.length()>0 && updatedKey==0 ){

								WebServiceResonse updateKey = ws.UpdateGcmKey( regId, GlobalData.userSelected.getUser_id());

								if(updateKey.getStatus()==200){

									editor.putInt("SETGCMKEY", 1);
									editor.commit();
								}


							}
//							String res = ws.registerDevice(appId, 1, regId, uniqueid);

//							String res=ws.RegisterUserAppDevice("0", regId, uniqueid,"1");


//							JSONObject js=new JSONObject(res);
//
//							int redInt = Integer.parseInt(js.getString("flag"));
//							if (redInt > 0) {
//								editor.putInt("regIDFlag", 1);
//							}

						} catch (Exception e) {
							// TODO: handle exception
							GlobalData.printError(
									"Error In Adding register device webservice", e);
						}



				} catch (Exception e) {
					// TODO: handle exception
					appId = 0;
					GlobalData.printError("Error In Adding register device webservice",
							e);
				}

				editor.commit();
				return true;
			} catch (Exception e) {
				// Here we are simplifying and retrying on any error; in a real
				// application, it should retry only on unrecoverable errors
				// (like HTTP error code 503).
				Log.e(TAG, "Failed to register on attempt " + i, e);
				if (i == MAX_ATTEMPTS) {
					break;
				}
				try {
					Log.d(TAG, "Sleeping for " + backoff + " ms before retry");
					Thread.sleep(backoff);
				} catch (InterruptedException e1) {
					// Activity finished before we complete - exit.
					Log.d(TAG, "Thread interrupted: abort remaining retries!");
					Thread.currentThread().interrupt();
					return false;
				}
				// increase backoff exponentially
				backoff *= 2;
			}
		}
		

		return false;
	}

	/**
	 * Unregister this account/device pair within the server.
	 */
	public static void unregister(final Context context, final String regId) {
		Log.i(TAG, "unregistering device (regId = " + regId + ")");
//		String serverUrl = SERVER_URL + "/unregister";
		Map<String, String> params = new HashMap<String, String>();
		params.put("regId", regId);
		try {
//			post(serverUrl, params);
			GCMRegistrar.setRegisteredOnServer(context, false);
			String message = context.getString(R.string.server_unregistered);
			CommonUtilities.displayMessage(context, message);
		} catch (Exception e) {
			// At this point the device is unregistered from GCM, but still
			// registered in the server.
			// We could try to unregister again, but it is not necessary:
			// if the server tries to send a message to the device, it will get
			// a "NotRegistered" error message and should unregister the device.
			String message = context.getString(
					R.string.server_unregister_error, e.getMessage());
			CommonUtilities.displayMessage(context, message);
		}
	}

	/**
	 * Issue a POST request to the server.
	 * 
	 * @param endpoint
	 *            POST address.
	 * @param params
	 *            request parameters.
	 * 
	 * @throws IOException
	 *             propagated from POST.
	 */
	private static void post(String endpoint, Map<String, String> params)
			throws IOException {
		URL url;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url: " + endpoint);
		}
		StringBuilder bodyBuilder = new StringBuilder();
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		// constructs the POST body using the parameters
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			bodyBuilder.append(param.getKey()).append('=')
					.append(param.getValue());
			if (iterator.hasNext()) {
				bodyBuilder.append('&');
			}
		}
		String body = bodyBuilder.toString();
		Log.v(TAG, "Posting '" + body + "' to " + url);
		byte[] bytes = body.getBytes();
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setFixedLengthStreamingMode(bytes.length);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			// post the request
			OutputStream out = conn.getOutputStream();
			out.write(bytes);
			out.close();
			// handle the response
			int status = conn.getResponseCode();
			if (status != 200) {
				throw new IOException("Post failed with error code " + status);
			}
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
}
