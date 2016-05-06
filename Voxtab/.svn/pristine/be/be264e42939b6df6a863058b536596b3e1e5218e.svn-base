

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Device {



 int  deviceId =0;
 int  userId =0;
 String  IMEI ="" ;
 String  deviceModel ="" ;
 String  Key ="" ;
 int  deveicetype =0;
 public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
 public int getDeviceId() {
        return deviceId;
    }

 public void setUserId(int userId) {
        this.userId = userId;
    }
 public int getUserId() {
        return userId;
    }

public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }
 public String getIMEI() {
        return IMEI;
    }

public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }
 public String getDeviceModel() {
        return deviceModel;
    }

public void setKey(String Key) {
        this.Key = Key;
    }
 public String getKey() {
        return Key;
    }

 public void setDeveicetype(int deveicetype) {
        this.deveicetype = deveicetype;
    }
 public int getDeveicetype() {
        return deveicetype;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("deviceId")) { 
 try {
 deviceId= Integer.parseInt(jsonObject.getString("deviceId"));
 } catch (Exception e) {}
 }

if (jsonObject.has("userId")) { 
 try {
 userId= Integer.parseInt(jsonObject.getString("userId"));
 } catch (Exception e) {}
 }

if (jsonObject.has("IMEI")) { 
 try {
 IMEI= jsonObject.getString("IMEI");
 } catch (Exception e) {}
 }

if (jsonObject.has("deviceModel")) { 
 try {
 deviceModel= jsonObject.getString("deviceModel");
 } catch (Exception e) {}
 }

if (jsonObject.has("Key")) { 
 try {
 Key= jsonObject.getString("Key");
 } catch (Exception e) {}
 }

if (jsonObject.has("deveicetype")) { 
 try {
 deveicetype= Integer.parseInt(jsonObject.getString("deveicetype"));
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END