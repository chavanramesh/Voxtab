package com.voxtab.ariatech.voxtab.bean;

import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import org.json.JSONObject;

/**
 * Created by MAC 2 on 4/20/2016.
 */
public class Pages {

    String page_id ="";
    String page_title  ="";
    String page_content  ="";
    int  status  =0;
    String  soft_del   ="";
    String  created_date  ="";
    String  modified_date  ="";
    String show_in_menu  ="";
    String  page_link_id  ="";

    public String getPage_id() {
        return page_id;
    }

    public void setPage_id(String page_id) {
        this.page_id = page_id;
    }

    public String getPage_title() {
        return page_title;
    }

    public void setPage_title(String page_title) {
        this.page_title = page_title;
    }

    public String getPage_content() {
        return page_content;
    }

    public void setPage_content(String page_content) {
        this.page_content = page_content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSoft_del() {
        return soft_del;
    }

    public void setSoft_del(String soft_del) {
        this.soft_del = soft_del;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getShow_in_menu() {
        return show_in_menu;
    }

    public void setShow_in_menu(String show_in_menu) {
        this.show_in_menu = show_in_menu;
    }

    public String getPage_link_id() {
        return page_link_id;
    }

    public void setPage_link_id(String page_link_id) {
        this.page_link_id = page_link_id;
    }



    public void parseJSON(JSONObject jsonObject) {
        try {

            if (jsonObject.has("page_id")) {
                try {
                    page_id= jsonObject.getString("page_id");
                } catch (Exception e) {}
            }

            if (jsonObject.has("page_title")) {
                try {
                    page_title= jsonObject.getString("page_title");
                } catch (Exception e) {}
            }

            if (jsonObject.has("page_content")) {
                try {
                    page_content= jsonObject.getString("page_content");
                } catch (Exception e) {}
            }

            if (jsonObject.has("status")) {
                try {
                    status= Integer.parseInt(jsonObject.getString("status"));
                } catch (Exception e) {}
            }

            if (jsonObject.has("soft_del")) {
                try {
                    soft_del= jsonObject.getString("soft_del");
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


            if (jsonObject.has("show_in_menu")) {
                try {
                    show_in_menu= jsonObject.getString("show_in_menu");
                } catch (Exception e) {}
            }


            if (jsonObject.has("page_link_id")) {
                try {
                    page_link_id= jsonObject.getString("page_link_id");
                } catch (Exception e) {}
            }

        } catch (Exception e) {
            // TODO: handle exception
            GlobalData.printError(e);
        }

    }


}//END







