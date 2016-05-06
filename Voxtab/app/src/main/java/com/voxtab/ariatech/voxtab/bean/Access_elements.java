

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Access_elements {



 int  elm_id =0;
 String  elm_name ="" ;
 String  elm_created_on ="" ;
 public void setElm_id(int elm_id) {
        this.elm_id = elm_id;
    }
 public int getElm_id() {
        return elm_id;
    }

public void setElm_name(String elm_name) {
        this.elm_name = elm_name;
    }
 public String getElm_name() {
        return elm_name;
    }

public void setElm_created_on(String elm_created_on) {
        this.elm_created_on = elm_created_on;
    }
 public String getElm_created_on() {
        return elm_created_on;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("elm_id")) { 
 try {
 elm_id= Integer.parseInt(jsonObject.getString("elm_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("elm_name")) { 
 try {
 elm_name= jsonObject.getString("elm_name");
 } catch (Exception e) {}
 }

if (jsonObject.has("elm_created_on")) { 
 try {
 elm_created_on= jsonObject.getString("elm_created_on");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END