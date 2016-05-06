

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Output_format {



 String  output_format_id ="" ;
 String  output_format_txt ="" ;
 String  status ="" ;
 String  modified_date ="" ;
 String  created_date ="" ;
 String  soft_del ="" ;
public void setOutput_format_id(String output_format_id) {
        this.output_format_id = output_format_id;
    }
 public String getOutput_format_id() {
        return output_format_id;
    }

public void setOutput_format_txt(String output_format_txt) {
        this.output_format_txt = output_format_txt;
    }
 public String getOutput_format_txt() {
        return output_format_txt;
    }

public void setStatus(String status) {
        this.status = status;
    }
 public String getStatus() {
        return status;
    }

public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }
 public String getModified_date() {
        return modified_date;
    }

public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
 public String getCreated_date() {
        return created_date;
    }

public void setSoft_del(String soft_del) {
        this.soft_del = soft_del;
    }
 public String getSoft_del() {
        return soft_del;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("output_format_id")) { 
 try {
 output_format_id= jsonObject.getString("output_format_id");
 } catch (Exception e) {}
 }

if (jsonObject.has("output_format_txt")) { 
 try {
 output_format_txt= jsonObject.getString("output_format_txt");
 } catch (Exception e) {}
 }

if (jsonObject.has("status")) { 
 try {
 status= jsonObject.getString("status");
 } catch (Exception e) {}
 }

if (jsonObject.has("modified_date")) { 
 try {
 modified_date= jsonObject.getString("modified_date");
 } catch (Exception e) {}
 }

if (jsonObject.has("created_date")) { 
 try {
 created_date= jsonObject.getString("created_date");
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