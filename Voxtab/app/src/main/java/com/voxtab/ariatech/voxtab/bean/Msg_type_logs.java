

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Msg_type_logs {



 int  ref_id =0;
 int  log_id =0;
 int  type_id =0;
 public void setRef_id(int ref_id) {
        this.ref_id = ref_id;
    }
 public int getRef_id() {
        return ref_id;
    }

 public void setLog_id(int log_id) {
        this.log_id = log_id;
    }
 public int getLog_id() {
        return log_id;
    }

 public void setType_id(int type_id) {
        this.type_id = type_id;
    }
 public int getType_id() {
        return type_id;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("ref_id")) { 
 try {
 ref_id= Integer.parseInt(jsonObject.getString("ref_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("log_id")) { 
 try {
 log_id= Integer.parseInt(jsonObject.getString("log_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("type_id")) { 
 try {
 type_id= Integer.parseInt(jsonObject.getString("type_id"));
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END