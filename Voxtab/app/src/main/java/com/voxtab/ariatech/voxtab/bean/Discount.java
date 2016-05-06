

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Discount {


        int discount_id = 0;
        String discount_type_id = "";
        String service_type_id = "";
        String delivery_opt_id = "";
        String vas_id = "";
        String min_time = "";
        String max_time = "";
        String start_date = "";
        String end_date = "";
 String  discount ="" ;
        String status = "";
        String modified_date = "";
        String created_date = "";
        String soft_del = "";

        public void setDiscount_id(int discount_id) {
            this.discount_id = discount_id;
    }

        public int getDiscount_id() {
            return discount_id;
    }

        public void setDiscount_type_id(String discount_type_id) {
            this.discount_type_id = discount_type_id;
    }

        public String getDiscount_type_id() {
            return discount_type_id;
    }

        public void setService_type_id(String service_type_id) {
            this.service_type_id = service_type_id;
    }

        public String getService_type_id() {
            return service_type_id;
    }

        public void setDelivery_opt_id(String delivery_opt_id) {
            this.delivery_opt_id = delivery_opt_id;
    }

        public String getDelivery_opt_id() {
            return delivery_opt_id;
    }

        public void setVas_id(String vas_id) {
            this.vas_id = vas_id;
    }

        public String getVas_id() {
            return vas_id;
    }

        public void setMin_time(String min_time) {
            this.min_time = min_time;
    }

        public String getMin_time() {
            return min_time;
    }

        public void setMax_time(String max_time) {
            this.max_time = max_time;
    }

        public String getMax_time() {
            return max_time;
    }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
    }

        public String getStart_date() {
            return start_date;
    }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getEnd_date() {
            return end_date;
        }

public void setDiscount(String discount) {
        this.discount = discount;
    }
 public String getDiscount() {
        return discount;
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
     if (jsonObject.has("discount_id")) {
 try {
     discount_id = Integer.parseInt(jsonObject.getString("discount_id"));
 } catch (Exception e) {}
 }

     if (jsonObject.has("discount_type_id")) {
 try {
     discount_type_id = jsonObject.getString("discount_type_id");
 } catch (Exception e) {}
 }

     if (jsonObject.has("service_type_id")) {
 try {
     service_type_id = jsonObject.getString("service_type_id");
 } catch (Exception e) {}
 }

     if (jsonObject.has("delivery_opt_id")) {
 try {
     delivery_opt_id = jsonObject.getString("delivery_opt_id");
 } catch (Exception e) {}
 }

     if (jsonObject.has("vas_id")) {
 try {
     vas_id = jsonObject.getString("vas_id");
 } catch (Exception e) {}
 }

     if (jsonObject.has("min_time")) {
 try {
     min_time = jsonObject.getString("min_time");
 } catch (Exception e) {}
 }

     if (jsonObject.has("max_time")) {
 try {
     max_time = jsonObject.getString("max_time");
 } catch (Exception e) {}
 }

     if (jsonObject.has("start_date")) {
 try {
     start_date = jsonObject.getString("start_date");
 } catch (Exception e) {}
 }

     if (jsonObject.has("end_date")) {
         try {
             end_date = jsonObject.getString("end_date");
         } catch (Exception e) {
         }
     }

if (jsonObject.has("discount")) { 
 try {
 discount= jsonObject.getString("discount");
 } catch (Exception e) {
 }
}

     if (jsonObject.has("status")) {
         try {
             status = jsonObject.getString("status");
         } catch (Exception e) {
         }
     }

     if (jsonObject.has("modified_date")) {
         try {
             modified_date = jsonObject.getString("modified_date");
         } catch (Exception e) {
         }
     }

     if (jsonObject.has("created_date")) {
         try {
             created_date = jsonObject.getString("created_date");
         } catch (Exception e) {
         }
     }

     if (jsonObject.has("soft_del")) {
         try {
             soft_del = jsonObject.getString("soft_del");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END