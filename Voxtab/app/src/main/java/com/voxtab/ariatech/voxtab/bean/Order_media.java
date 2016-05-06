

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Order_media {



 int  order_media_id =0;
 int  user_id =0;
 int  order_id =0;
 String  file_status ="" ;
 String  source_type ="" ;
 String  sourcelink ="" ;
 String  file_name ="" ;
 String  file_description ="" ;
 String  file_duration ="" ;
 String  file_localpath ="" ;
 String  file_size ="" ;
 String  file_upload_duration ="" ;
 String  file_up_conn_mode ="" ;
 String  created_date ="" ;
 String  modified_date ="" ;
 public void setOrder_media_id(int order_media_id) {
        this.order_media_id = order_media_id;
    }
 public int getOrder_media_id() {
        return order_media_id;
    }

 public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
 public int getUser_id() {
        return user_id;
    }

 public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
 public int getOrder_id() {
        return order_id;
    }

public void setFile_status(String file_status) {
        this.file_status = file_status;
    }
 public String getFile_status() {
        return file_status;
    }

public void setSource_type(String source_type) {
        this.source_type = source_type;
    }
 public String getSource_type() {
        return source_type;
    }

public void setSourcelink(String sourcelink) {
        this.sourcelink = sourcelink;
    }
 public String getSourcelink() {
        return sourcelink;
    }

public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
 public String getFile_name() {
        return file_name;
    }

public void setFile_description(String file_description) {
        this.file_description = file_description;
    }
 public String getFile_description() {
        return file_description;
    }

public void setFile_duration(String file_duration) {
        this.file_duration = file_duration;
    }
 public String getFile_duration() {
        return file_duration;
    }

public void setFile_localpath(String file_localpath) {
        this.file_localpath = file_localpath;
    }
 public String getFile_localpath() {
        return file_localpath;
    }

public void setFile_size(String file_size) {
        this.file_size = file_size;
    }
 public String getFile_size() {
        return file_size;
    }

public void setFile_upload_duration(String file_upload_duration) {
        this.file_upload_duration = file_upload_duration;
    }
 public String getFile_upload_duration() {
        return file_upload_duration;
    }

public void setFile_up_conn_mode(String file_up_conn_mode) {
        this.file_up_conn_mode = file_up_conn_mode;
    }
 public String getFile_up_conn_mode() {
        return file_up_conn_mode;
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




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("order_media_id")) { 
 try {
 order_media_id= Integer.parseInt(jsonObject.getString("order_media_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("user_id")) { 
 try {
 user_id= Integer.parseInt(jsonObject.getString("user_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("order_id")) { 
 try {
 order_id= Integer.parseInt(jsonObject.getString("order_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("file_status")) { 
 try {
 file_status= jsonObject.getString("file_status");
 } catch (Exception e) {}
 }

if (jsonObject.has("source_type")) { 
 try {
 source_type= jsonObject.getString("source_type");
 } catch (Exception e) {}
 }

if (jsonObject.has("sourcelink")) { 
 try {
 sourcelink= jsonObject.getString("sourcelink");
 } catch (Exception e) {}
 }

if (jsonObject.has("file_name")) { 
 try {
 file_name= jsonObject.getString("file_name");
 } catch (Exception e) {}
 }

if (jsonObject.has("file_description")) { 
 try {
 file_description= jsonObject.getString("file_description");
 } catch (Exception e) {}
 }

if (jsonObject.has("file_duration")) { 
 try {
 file_duration= jsonObject.getString("file_duration");
 } catch (Exception e) {}
 }

if (jsonObject.has("file_localpath")) { 
 try {
 file_localpath= jsonObject.getString("file_localpath");
 } catch (Exception e) {}
 }

if (jsonObject.has("file_size")) { 
 try {
 file_size= jsonObject.getString("file_size");
 } catch (Exception e) {}
 }

if (jsonObject.has("file_upload_duration")) { 
 try {
 file_upload_duration= jsonObject.getString("file_upload_duration");
 } catch (Exception e) {}
 }

if (jsonObject.has("file_up_conn_mode")) { 
 try {
 file_up_conn_mode= jsonObject.getString("file_up_conn_mode");
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
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END