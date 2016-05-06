

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class File_status_type {



 String  file_status_msg ="" ;
 String  file_status ="" ;
 String  is_active ="" ;
 int  sort_order =0;
 String  soft_del ="" ;
 String  modified_date ="" ;
 String  created_date ="" ;
public void setFile_status_msg(String file_status_msg) {
        this.file_status_msg = file_status_msg;
    }
 public String getFile_status_msg() {
        return file_status_msg;
    }

public void setFile_status(String file_status) {
        this.file_status = file_status;
    }
 public String getFile_status() {
        return file_status;
    }

public void setIs_active(String is_active) {
        this.is_active = is_active;
    }
 public String getIs_active() {
        return is_active;
    }

 public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }
 public int getSort_order() {
        return sort_order;
    }

public void setSoft_del(String soft_del) {
        this.soft_del = soft_del;
    }
 public String getSoft_del() {
        return soft_del;
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




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("file_status_msg")) { 
 try {
 file_status_msg= jsonObject.getString("file_status_msg");
 } catch (Exception e) {}
 }

if (jsonObject.has("file_status")) { 
 try {
 file_status= jsonObject.getString("file_status");
 } catch (Exception e) {}
 }

if (jsonObject.has("is_active")) { 
 try {
 is_active= jsonObject.getString("is_active");
 } catch (Exception e) {}
 }

if (jsonObject.has("sort_order")) { 
 try {
 sort_order= Integer.parseInt(jsonObject.getString("sort_order"));
 } catch (Exception e) {}
 }

if (jsonObject.has("soft_del")) { 
 try {
 soft_del= jsonObject.getString("soft_del");
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
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END