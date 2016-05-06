package com.ariatech.lib_project;

import android.os.StrictMode;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class WebCommunicator {

	private static final int TIMEOUT = 15000;
	private static final String ERR_NETWORK_SLOW_OR_NO_CONNECTION = "{\"Msg\":\"No response from server, Please try again or try after some time\",\"flag\":\"0\"}";
//	private static final String ERR_NETWORK_SLOW_OR_NO_CONNECTION = "{\"message\":\"No response from server, Please try again or try after some time\",\"status\":\"fail\"}";

	/**
	 * this method is use for web HTTP POST call
	 * 
	 * @param url
	 *            - url of web service
	 * @param data
	 *            - provide array of String contains key at odd places and value
	 *            at even places in array one after another. example
	 *            ["key1","value1","key2","value2"....]
	 * @return it return data which is received from web service (mainly json
	 *         data but depends on server and return type).
	 */

	public static String setHttpPOSTCall(String url, String... data) {

		try {
			BufferedReader in = null;
			StringBuffer buffer = new StringBuffer("");
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT);

			HttpClient client = new DefaultHttpClient(httpParameters);
			URI website = new URI(url);

			HttpPost request = new HttpPost(website);
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();

			for (int i = 0; i < data.length; i += 2) {
				parameters.add(new BasicNameValuePair(data[i], data[i + 1]));

				System.out.println("Web service call param = " + data[i]  + " value = " + data[i + 1]);

			}

			request.setEntity(new UrlEncodedFormEntity(parameters));
			HttpResponse response = client.execute(request);

			StatusLine sl = response.getStatusLine();
			int statusCode = sl.getStatusCode();

			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			String l;

			while ((l = in.readLine()) != null) {
				buffer.append(l);
			}

			return buffer.toString();
		} catch (UnsupportedEncodingException e) {
			return ERR_NETWORK_SLOW_OR_NO_CONNECTION;
		} catch (ClientProtocolException e) {
			return ERR_NETWORK_SLOW_OR_NO_CONNECTION;
		} catch (IllegalStateException e) {
			return ERR_NETWORK_SLOW_OR_NO_CONNECTION;
		} catch (URISyntaxException e) {
			return ERR_NETWORK_SLOW_OR_NO_CONNECTION;
		} catch (IOException e) {
			return ERR_NETWORK_SLOW_OR_NO_CONNECTION;
		}
	}

	/**
	 * this method is use for web HTTP GET call
	 * 
	 * @param url
	 *            - url of web service
	 * @param data
	 *            - provide array of String contains key at odd places and value
	 *            at even places in array one after another. example
	 *            ["key1","value1","key2","value2"....]
	 * @return it return data which is received from web service (mainly json
	 *         data but depends on server and return type).
	 */

	public static String setHttpGETCall(String url, String... data)
			throws Exception {

		String result = "";
		try {

		InputStream inputStream = null;

		StringBuffer urlStr = new StringBuffer();

		// create HttpClient
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT);

		HttpClient httpclient = new DefaultHttpClient(httpParameters);

		urlStr.append(url);

		for (int i = 0; i < data.length; i += 2) {
			if (i == 0) {
				urlStr.append("?" + data[i] + "=" + data[i + 1]);
			} else {
				urlStr.append("&" + data[i] + "=" + data[i + 1]);
			}
		}

			String urlS=urlStr.toString();

			String urlStemp=urlS.replace("\"", "\\\"");

			urlStemp= (urlStemp);

		// make GET request to the given URL
		HttpResponse httpResponse = httpclient.execute(new HttpGet(urlStemp));

		// receive response as inputStream
		inputStream = httpResponse.getEntity().getContent();

		// convert inputstream to string
		if (inputStream != null) {
			result = convertInputStreamToString(inputStream);
		} else {
			result = null;
		}



			result = result.replace("<string xmlns=\"http://schemas.microsoft.com/2003/10/Serialization/\">","");
			result = result.replace("</string>","");
			result = result.replace("&#xD;","");

			result = decryptData(result);
		}catch (Exception e){
			e.printStackTrace();
		}
		return result;
	}



	public static String setHttpPOSTCall_2(String url, String json) {

		String result = "";
		try {
			BufferedReader in = null;
			StringBuffer buffer = new StringBuffer("");
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParameters, TIMEOUT);

			HttpClient client = new DefaultHttpClient(httpParameters);
			URI website = new URI(url);

			HttpPost request = new HttpPost(website);
			request.setHeader("Accept", "application/json");
			request.setHeader("Content-type", "application/json");

//       List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//
//       for (int i = 0; i < data.length; i += 2) {
//          parameters.add(new BasicNameValuePair(data[i], data[i + 1]));
//
//          System.out.println("Web service call param = " + data[i]  + " value = " + data[i + 1]);
//
//       }

			request.setEntity(new StringEntity(json, "UTF8"));



			request.setHeader("Content-type", "application/json");


			HttpResponse response = client.execute(request);

			StatusLine sl = response.getStatusLine();
			int statusCode = sl.getStatusCode();

			in = new BufferedReader(new InputStreamReader(response.getEntity()
					.getContent()));
			String l;

			while ((l = in.readLine()) != null) {
				buffer.append(l);
			}

			result = buffer.toString();

			result = decryptData(result);
		} catch (UnsupportedEncodingException e) {
			result = ERR_NETWORK_SLOW_OR_NO_CONNECTION;
		} catch (ClientProtocolException e) {
			result = ERR_NETWORK_SLOW_OR_NO_CONNECTION;
		} catch (IllegalStateException e) {
			result = ERR_NETWORK_SLOW_OR_NO_CONNECTION;
		} catch (URISyntaxException e) {
			result = ERR_NETWORK_SLOW_OR_NO_CONNECTION;
		} catch (IOException e) {
			result = ERR_NETWORK_SLOW_OR_NO_CONNECTION;
		}

		result = result.replace("<string xmlns=\"http://schemas.microsoft.com/2003/10/Serialization/\">","");
		result = result.replace("</string>","");
		result = result.replace("&#xD;","");
		result=result.replace("\\","");
		result=result.replace("u000du000a","");

		if(result.length() > 3) {

			if(result.indexOf("\"") == 0) {
				result = result.substring(1,result.length()-1);
			}

			if(result.indexOf("\"") == result.length()-1) {
				result = result.substring(0,result.length()-2);
			}
		}


		return result;
	}
	// convert inputstream to String
	private static String convertInputStreamToString(InputStream inputStream)
			throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

	/**
	 * 
	 * @param url
	 *            - url of SOAP call exa: PHP - URL =
	 *            "http://ariatechpl.com/magento/wservice/service.php?wsdl"; ASP
	 *            - URL =
	 *            "http://apps.atechmobility.com/CRM/MicrosoftCRMService.asmx";
	 *            JAVA = URL =
	 *            "http://ariatechpl.com/magento/wservice/service?WSDL";
	 * @param soap
	 *            - exa: PHP: SOAP =
	 *            "http://ariatechpl.com/magento/wservice/service.php/"; ASP:
	 *            SOAP = "http://tempuri.org/"; JAVA: SOAP =
	 *            "http://ariatechpl.com/";
	 * @param nameSpace
	 *            exa: PHP: NAMESPACE =
	 *            "http://ariatechpl.com/magento/wservice/service.php/"; ASP :
	 *            NAMESPACE = "http://tempuri.org/"; JAVA: NAMESPACE =
	 *            "http://ariatechpl.com/";
	 * @param data
	 *            - provide array of String contains key at odd places and value
	 *            at even places in array one after another. example
	 *            ["key1","value1","key2","value2"....]
	 * @param method
	 *            - web method name.
	 * @return it return data which is received from web service (mainly json
	 *         data but depends on server and return type).
	 * @throws Exception
	 */

	public static String setWebServiceSOAPCall(String url, String soap,
			String nameSpace, String method, String... data) {

		String action = soap + method;
		String res;

		SoapObject request = new SoapObject(nameSpace, method);
		try {

			for (int i = 0; i < data.length; i += 2) {
				PropertyInfo obj1 = new PropertyInfo();
				obj1.setName(data[i]);
				obj1.setValue(data[i + 1]);
				obj1.setType(String.class);
				request.addProperty(obj1);

			}

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = true;
			envelope.setOutputSoapObject(request);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(url,
					TIMEOUT);
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);

			androidHttpTransport.call(action, envelope);
			// System.out.println("5:");
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
			res  = response.toString();

		} catch (Exception e) {
			// TODO: handle exception
			CommonUtil.printError(e);
			return ERR_NETWORK_SLOW_OR_NO_CONNECTION;
		}
		return res;
	}

	public static String decryptData(String data) {
		// {"'", "&rsquo;"},
		// {"&#39;", "&rsquo;"}

		try {

			data = data.replace("U 002B", "+");
			data = data.replace("U+002B", "+");
			data = data.replace("U+0021", "!");
			data = data.replace("U+0022", "\"");
			data = data.replace("U+0023", "#");
			data = data.replace("U+0024", "$");
			data = data.replace("U+0025", "%");
			data = data.replace("U+0026", "&");
			data = data.replace("U+0027", "'");
			data = data.replace("U+002F", "/");

			data = data.replace("U 0021", "!");
			data = data.replace("U 0022", "\"");
			data = data.replace("U 0023", "#");
			data = data.replace("U 0024", "$");
			data = data.replace("U 0025", "%");
			data = data.replace("U 0026", "&");
			data = data.replace("U 0027", "'");
			data = data.replace("U 002F", "/");

		} catch (Exception e) {

		}

		return data;
		// {,},
		// {"\"","U+0022"},
		// {"#","U+0023"},
		// {"$","U+0024"},
		//
		// {"%","U+0025"},
		// {"&","U+0026"},
		// {"'","U+0027"},
		// {"/","U+002F"}

	}
}
