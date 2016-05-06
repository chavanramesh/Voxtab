

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Order_recording {



 int  order_rec_id =0;
 int  order_id =0;
 int  recording_id =0;
 public void setOrder_rec_id(int order_rec_id) {
        this.order_rec_id = order_rec_id;
    }
 public int getOrder_rec_id() {
        return order_rec_id;
    }

 public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
 public int getOrder_id() {
        return order_id;
    }

 public void setRecording_id(int recording_id) {
        this.recording_id = recording_id;
    }
 public int getRecording_id() {
        return recording_id;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("order_rec_id")) { 
 try {
 order_rec_id= Integer.parseInt(jsonObject.getString("order_rec_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("order_id")) { 
 try {
 order_id= Integer.parseInt(jsonObject.getString("order_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("recording_id")) { 
 try {
 recording_id= Integer.parseInt(jsonObject.getString("recording_id"));
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END