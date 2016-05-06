package com.voxtab.ariatech.voxtab.beanwebservice;

import com.voxtab.ariatech.voxtab.bean.MyRecording;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import org.json.JSONObject;

/**
 * Created by MAC 2 on 4/4/2016.
 */
public class File_Meta_JSON {


    String user_id="";
    String order_id="";

    // Local recId
    String temp_media_id = "";


    String file_status="";
    String source_type="";
    String sourcelink="";
    String file_name="";
    String file_description="";
    String file_duration="";
    String file_localpath="";
    String file_size="";
    String file_upload_duration="";
    String file_up_conn_mode="";

    String order_media_id ="";
    String assignment_no="";


    String trans_file_name="";
    String file_title="";
    String trans_file_status="";
    String trans_file_up_date="";

    public String getTrans_file_name() {
        return trans_file_name;
    }

    public void setTrans_file_name(String trans_file_name) {
        this.trans_file_name = trans_file_name;
    }

    public String getFile_title() {
        return file_title;
    }

    public void setFile_title(String file_title) {
        this.file_title = file_title;
    }

    public String getTrans_file_status() {
        return trans_file_status;
    }

    public void setTrans_file_status(String trans_file_status) {
        this.trans_file_status = trans_file_status;
    }

    public String getTrans_file_up_date() {
        return trans_file_up_date;
    }

    public void setTrans_file_up_date(String trans_file_up_date) {
        this.trans_file_up_date = trans_file_up_date;
    }

    public String getAssignment_no() {
        return assignment_no;
    }

    public void setAssignment_no(String assignment_no) {
        this.assignment_no = assignment_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getFile_status() {
        return file_status;
    }

    public void setFile_status(String file_status) {
        this.file_status = file_status;
    }

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }

    public String getSourcelink() {
        return sourcelink;
    }

    public void setSourcelink(String sourcelink) {
        this.sourcelink = sourcelink;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_description() {
        return file_description;
    }

    public void setFile_description(String file_description) {
        this.file_description = file_description;
    }

    public String getFile_duration() {
        return file_duration;
    }

    public void setFile_duration(String file_duration) {
        this.file_duration = file_duration;
    }

    public String getFile_localpath() {
        return file_localpath;
    }

    public void setFile_localpath(String file_localpath) {
        this.file_localpath = file_localpath;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public String getFile_upload_duration() {
        return file_upload_duration;
    }

    public void setFile_upload_duration(String file_upload_duration) {
        this.file_upload_duration = file_upload_duration;
    }

    public String getFile_up_conn_mode() {
        return file_up_conn_mode;
    }

    public void setFile_up_conn_mode(String file_up_conn_mode) {
        this.file_up_conn_mode = file_up_conn_mode;
    }

    public String getTemp_media_id() {
        return temp_media_id;
    }

    public void setTemp_media_id(String temp_media_id) {
        this.temp_media_id = temp_media_id;
    }


    public String getOrder_media_id() {
        return order_media_id;
    }

    public void setOrder_media_id(String order_media_id) {
        this.order_media_id = order_media_id;
    }



    public void parseJSON(JSONObject jsonObject) {
        try {




            if (jsonObject.has("user_id")) {
                try {
                    user_id= (jsonObject.getString("user_id"));
                } catch (Exception e) {}
            }

            if (jsonObject.has("temp_media_id")) {
                try {
                    temp_media_id= jsonObject.getString("temp_media_id");
                } catch (Exception e) {}
            }

            if (jsonObject.has("order_id")) {
                try {
                    order_id= jsonObject.getString("order_id");
                } catch (Exception e) {}
            }




            if (jsonObject.has("file_status")) {
                try {
                    file_status= (jsonObject.getString("file_status"));
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
                    file_name= (jsonObject.getString("file_name"));
                } catch (Exception e) {}
            }

            if (jsonObject.has("file_description")) {
                try {
                    file_description= jsonObject.getString("file_description");
                } catch (Exception e) {}
            }







            if (jsonObject.has("file_localpath")) {
                try {
                    file_localpath= (jsonObject.getString("file_localpath"));
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

            if (jsonObject.has("file_upload_duration")) {
                try {
                    file_upload_duration= jsonObject.getString("file_upload_duration");
                } catch (Exception e) {}
            }



            if (jsonObject.has("order_media_id")) {
                try {
                    order_media_id= jsonObject.getString("order_media_id");
                } catch (Exception e) {}
            }


            if (jsonObject.has("trans_file_name")) {
                try {
                    trans_file_name= jsonObject.getString("trans_file_name");
                } catch (Exception e) {}
            }



            if (jsonObject.has("file_title")) {
                try {
                    file_title= jsonObject.getString("file_title");
                } catch (Exception e) {}
            }


            if (jsonObject.has("trans_file_status")) {
                try {
                    trans_file_status= jsonObject.getString("trans_file_status");
                } catch (Exception e) {}
            }


            if (jsonObject.has("trans_file_up_date")) {
                try {
                    trans_file_up_date= jsonObject.getString("trans_file_up_date");
                } catch (Exception e) {}
            }

            if (jsonObject.has("file_duration")) {
                try {
                    file_duration= jsonObject.getString("file_duration");
                } catch (Exception e) {}
            }


        } catch (Exception e) {
            // TODO: handle exception
            GlobalData.printError(e);
        }





    }

    public void parseMyrecording(MyRecording recording){

        try {

            file_name = recording.getRecName();
            file_duration = recording.getRecDuration();
            file_localpath = recording.getRecLocalPath();
            sourcelink = recording.getSourceLink();
            source_type=recording.getSourceTypeId();
            file_size=recording.getRecSize();
            assignment_no=recording.getAssignment_no();


        }catch (Exception e){
            GlobalData.printError(e);
        }
    }


}//END

