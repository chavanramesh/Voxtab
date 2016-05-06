

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Order_transaction {



 int  order_trasnaction_id =0;
 int  order_id =0;
 int  rec_id =0;
 String  status_id ="" ;
 String  trans_date ="" ;
 public void setOrder_trasnaction_id(int order_trasnaction_id) {
        this.order_trasnaction_id = order_trasnaction_id;
    }
 public int getOrder_trasnaction_id() {
        return order_trasnaction_id;
    }

 public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
 public int getOrder_id() {
        return order_id;
    }

 public void setRec_id(int rec_id) {
        this.rec_id = rec_id;
    }
 public int getRec_id() {
        return rec_id;
    }

public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }
 public String getStatus_id() {
        return status_id;
    }

public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }
 public String getTrans_date() {
        return trans_date;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("order_trasnaction_id")) { 
 try {
 order_trasnaction_id= Integer.parseInt(jsonObject.getString("order_trasnaction_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("order_id")) { 
 try {
 order_id= Integer.parseInt(jsonObject.getString("order_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("rec_id")) { 
 try {
 rec_id= Integer.parseInt(jsonObject.getString("rec_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("status_id")) { 
 try {
 status_id= jsonObject.getString("status_id");
 } catch (Exception e) {}
 }

if (jsonObject.has("trans_date")) { 
 try {
 trans_date= jsonObject.getString("trans_date");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END