

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class User_device {



 int  device_id =0;
 int  user_id =0;
 String  membership_id ="" ;
 String  device_type ="" ;
 String  imei ="" ;
 String  api_key ="" ;
 String  user_type ="" ;
 String  device_name ="" ;
 String  device_os ="" ;
 String  systemdate ="" ;
 public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }
 public int getDevice_id() {
        return device_id;
    }

 public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
 public int getUser_id() {
        return user_id;
    }

public void setMembership_id(String membership_id) {
        this.membership_id = membership_id;
    }
 public String getMembership_id() {
        return membership_id;
    }

public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }
 public String getDevice_type() {
        return device_type;
    }

public void setImei(String imei) {
        this.imei = imei;
    }
 public String getImei() {
        return imei;
    }

public void setApi_key(String api_key) {
        this.api_key = api_key;
    }
 public String getApi_key() {
        return api_key;
    }

public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
 public String getUser_type() {
        return user_type;
    }

public void setDevice_name(String device_name) {
        this.device_name = device_name;
    }
 public String getDevice_name() {
        return device_name;
    }

public void setDevice_os(String device_os) {
        this.device_os = device_os;
    }
 public String getDevice_os() {
        return device_os;
    }

public void setSystemdate(String systemdate) {
        this.systemdate = systemdate;
    }
 public String getSystemdate() {
        return systemdate;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("device_id")) { 
 try {
 device_id= Integer.parseInt(jsonObject.getString("device_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("user_id")) { 
 try {
 user_id= Integer.parseInt(jsonObject.getString("user_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("membership_id")) { 
 try {
 membership_id= jsonObject.getString("membership_id");
 } catch (Exception e) {}
 }

if (jsonObject.has("device_type")) { 
 try {
 device_type= jsonObject.getString("device_type");
 } catch (Exception e) {}
 }

if (jsonObject.has("imei")) { 
 try {
 imei= jsonObject.getString("imei");
 } catch (Exception e) {}
 }

if (jsonObject.has("api_key")) { 
 try {
 api_key= jsonObject.getString("api_key");
 } catch (Exception e) {}
 }

if (jsonObject.has("user_type")) { 
 try {
 user_type= jsonObject.getString("user_type");
 } catch (Exception e) {}
 }

if (jsonObject.has("device_name")) { 
 try {
 device_name= jsonObject.getString("device_name");
 } catch (Exception e) {}
 }

if (jsonObject.has("device_os")) { 
 try {
 device_os= jsonObject.getString("device_os");
 } catch (Exception e) {}
 }

if (jsonObject.has("systemdate")) { 
 try {
 systemdate= jsonObject.getString("systemdate");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END