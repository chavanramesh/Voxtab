

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Invoice_type {



 String  invoice_type_id ="" ;
 String  invoice_txt ="" ;
 String  created_date ="" ;
 String  modified_date ="" ;
 String  soft_del ="" ;
public void setInvoice_type_id(String invoice_type_id) {
        this.invoice_type_id = invoice_type_id;
    }
 public String getInvoice_type_id() {
        return invoice_type_id;
    }

public void setInvoice_txt(String invoice_txt) {
        this.invoice_txt = invoice_txt;
    }
 public String getInvoice_txt() {
        return invoice_txt;
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
if (jsonObject.has("invoice_type_id")) { 
 try {
 invoice_type_id= jsonObject.getString("invoice_type_id");
 } catch (Exception e) {}
 }

if (jsonObject.has("invoice_txt")) { 
 try {
 invoice_txt= jsonObject.getString("invoice_txt");
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