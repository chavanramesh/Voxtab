package com.voxtab.ariatech.voxtab.globaldata;

import org.json.JSONObject;

public class WebServiceResonse {
	
	
	int status= 0;
	String version="";
	 
	String message = "";
	
	JSONObject jsonObject = new JSONObject();
	
	
	// Error
	boolean isError = true;
	String errorMessage = "";
	
	

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}
	
	
	
	
	// Parse JSON Data
	
	public void parseData(String json){
		
		try {
			
			
			jsonObject = new JSONObject(json);
			
			//  Parse status 
			if(jsonObject.has("status")){
				
				try {
					status = Integer.parseInt(jsonObject.getString("status"));
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
		//  Parse Message 
					if(jsonObject.has("message")){
						
						try {
							message = jsonObject.getString("message");
						} catch (Exception e) {
							// TODO: handle exception
						}
						
					}
			
			// Check web service has error
		
			if (status == 200 ||status == 201 ||status == 202 ){
			
				isError = false;
			}
					
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
	}
	
	// Get error Message
	/*
	200	OK
	201	Created
	202	Accepted (Request accepted, and queued for execution)
	400	Bad request
	401	Authentication failure
	403	Forbidden
	404	Resource not found
	405	Method Not Allowed
	409	Conflict
	412	Precondition Failed
	413	Request Entity Too Large
	500	Internal Server Error
	501	Exception Occured
	503	Service Unavailable*/
	public void getErrorMessage(){
		
		errorMessage = "";
		switch (status) {
		
		case 0:

			errorMessage = "Internal error: Webservice calling issue:";
			break;
		case 200:

			errorMessage = "OK";
			break;
		case 201:

			errorMessage = "Created";
			break;
		case 202:

			errorMessage = "Accepted";
			break;
		case 400:

			errorMessage = "Bad request";
			break;
		case 401:

			errorMessage = "Authentication failure";
			break;
		case 403:

			errorMessage = "Forbidden";
			break;
		case 404:

			errorMessage = "Resource not found";
			break;
		case 405:

			errorMessage = "Method Not Allowed";
			break;
		case 409:

			errorMessage = "Conflict";
			break;
		case 412:

			errorMessage = "Precondition Failed";
			break;
		case 413:

			errorMessage = "Request Entity Too Large";
			break;
		case 500:

			errorMessage = "Internal Server Error";
			
			break;
		case 501:

			errorMessage = "Exception Occured";
		case 503:

			errorMessage = "Service Unavailable";

		default:
			break;
		}
		
		
	}

	
	
	
	
			 
			 
	

}
