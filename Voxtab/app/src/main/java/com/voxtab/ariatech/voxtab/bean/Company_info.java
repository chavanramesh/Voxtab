

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Company_info {



        String  membership_id ="";
 String  org_name_eng ="" ;
 String  org_name_fn ="" ;
 String  status ="" ;
 String  client_type ="" ;
 String  date_of_invoice ="" ;
 String  name_of_invoice ="" ;
 String  business_type ="" ;
 String  template ="" ;
 String  client_instruction ="" ;
 String  payment_instruction ="" ;
 String  modified_date ="" ;
 String  created_date ="" ;
 String  soft_del ="" ;
 public void setMembership_id(String membership_id) {
        this.membership_id = membership_id;
    }
 public String getMembership_id() {
        return membership_id;
    }

public void setOrg_name_eng(String org_name_eng) {
        this.org_name_eng = org_name_eng;
    }
 public String getOrg_name_eng() {
        return org_name_eng;
    }

public void setOrg_name_fn(String org_name_fn) {
        this.org_name_fn = org_name_fn;
    }
 public String getOrg_name_fn() {
        return org_name_fn;
    }

public void setStatus(String status) {
        this.status = status;
    }
 public String getStatus() {
        return status;
    }

public void setClient_type(String client_type) {
        this.client_type = client_type;
    }
 public String getClient_type() {
        return client_type;
    }

public void setDate_of_invoice(String date_of_invoice) {
        this.date_of_invoice = date_of_invoice;
    }
 public String getDate_of_invoice() {
        return date_of_invoice;
    }

public void setName_of_invoice(String name_of_invoice) {
        this.name_of_invoice = name_of_invoice;
    }
 public String getName_of_invoice() {
        return name_of_invoice;
    }

public void setBusiness_type(String business_type) {
        this.business_type = business_type;
    }
 public String getBusiness_type() {
        return business_type;
    }

public void setTemplate(String template) {
        this.template = template;
    }
 public String getTemplate() {
        return template;
    }

public void setClient_instruction(String client_instruction) {
        this.client_instruction = client_instruction;
    }
 public String getClient_instruction() {
        return client_instruction;
    }

public void setPayment_instruction(String payment_instruction) {
        this.payment_instruction = payment_instruction;
    }
 public String getPayment_instruction() {
        return payment_instruction;
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
if (jsonObject.has("membership_id")) { 
 try {
 membership_id= (jsonObject.getString("membership_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("org_name_eng")) { 
 try {
 org_name_eng= jsonObject.getString("org_name_eng");
 } catch (Exception e) {}
 }

if (jsonObject.has("org_name_fn")) { 
 try {
 org_name_fn= jsonObject.getString("org_name_fn");
 } catch (Exception e) {}
 }

if (jsonObject.has("status")) { 
 try {
 status= jsonObject.getString("status");
 } catch (Exception e) {}
 }

if (jsonObject.has("client_type")) { 
 try {
 client_type= jsonObject.getString("client_type");
 } catch (Exception e) {}
 }

if (jsonObject.has("date_of_invoice")) { 
 try {
 date_of_invoice= jsonObject.getString("date_of_invoice");
 } catch (Exception e) {}
 }

if (jsonObject.has("name_of_invoice")) { 
 try {
 name_of_invoice= jsonObject.getString("name_of_invoice");
 } catch (Exception e) {}
 }

if (jsonObject.has("business_type")) { 
 try {
 business_type= jsonObject.getString("business_type");
 } catch (Exception e) {}
 }

if (jsonObject.has("template")) { 
 try {
 template= jsonObject.getString("template");
 } catch (Exception e) {}
 }

if (jsonObject.has("client_instruction")) { 
 try {
 client_instruction= jsonObject.getString("client_instruction");
 } catch (Exception e) {}
 }

if (jsonObject.has("payment_instruction")) { 
 try {
 payment_instruction= jsonObject.getString("payment_instruction");
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