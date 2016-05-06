

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Vas {



 String  vas_id ="" ;
 String  vas_text ="" ;
 String  default_set ="" ;
 String  service_flag ="" ;
 String  transcription_flag ="" ;
 String  timeslab_flag ="" ;
 String  status ="" ;
 String  created_date ="" ;
 String  modified_date ="" ;
 String  soft_del ="" ;
public void setVas_id(String vas_id) {
        this.vas_id = vas_id;
    }
 public String getVas_id() {
        return vas_id;
    }

public void setVas_text(String vas_text) {
        this.vas_text = vas_text;
    }
 public String getVas_text() {
        return vas_text;
    }

public void setDefault_set(String default_set) {
        this.default_set = default_set;
    }
 public String getDefault_set() {
        return default_set;
    }

public void setService_flag(String service_flag) {
        this.service_flag = service_flag;
    }
 public String getService_flag() {
        return service_flag;
    }

public void setTranscription_flag(String transcription_flag) {
        this.transcription_flag = transcription_flag;
    }
 public String getTranscription_flag() {
        return transcription_flag;
    }

public void setTimeslab_flag(String timeslab_flag) {
        this.timeslab_flag = timeslab_flag;
    }
 public String getTimeslab_flag() {
        return timeslab_flag;
    }

public void setStatus(String status) {
        this.status = status;
    }
 public String getStatus() {
        return status;
    }

public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
 public String getCreated_date() {
        return created_date;
    }

public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }
 public String getModified_date() {
        return modified_date;
    }

public void setSoft_del(String soft_del) {
        this.soft_del = soft_del;
    }
 public String getSoft_del() {
        return soft_del;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("vas_id")) { 
 try {
 vas_id= jsonObject.getString("vas_id");
 } catch (Exception e) {}
 }

if (jsonObject.has("vas_text")) { 
 try {
 vas_text= jsonObject.getString("vas_text");
 } catch (Exception e) {}
 }

if (jsonObject.has("default_set")) { 
 try {
 default_set= jsonObject.getString("default_set");
 } catch (Exception e) {}
 }

if (jsonObject.has("service_flag")) { 
 try {
 service_flag= jsonObject.getString("service_flag");
 } catch (Exception e) {}
 }

if (jsonObject.has("transcription_flag")) { 
 try {
 transcription_flag= jsonObject.getString("transcription_flag");
 } catch (Exception e) {}
 }

if (jsonObject.has("timeslab_flag")) { 
 try {
 timeslab_flag= jsonObject.getString("timeslab_flag");
 } catch (Exception e) {}
 }

if (jsonObject.has("status")) { 
 try {
 status= jsonObject.getString("status");
 } catch (Exception e) {}
 }

if (jsonObject.has("created_date")) { 
 try {
 created_date= jsonObject.getString("created_date");
 } catch (Exception e) {}
 }

if (jsonObject.has("modified_date")) { 
 try {
 modified_date= jsonObject.getString("modified_date");
 } catch (Exception e) {}
 }

if (jsonObject.has("soft_del")) { 
 try {
 soft_del= jsonObject.getString("soft_del");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END