

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Holidays {


        int holiday_id = 0;
        String holiday_date = "";
        String status = "";
        String soft_del = "";
        String created_date = "";
        String modified_date = "";

        int year=0;
        int month =0;
        int day=0;


        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public void setHoliday_id(int holiday_id) {
            this.holiday_id = holiday_id;
    }

        public int getHoliday_id() {
            return holiday_id;
    }

        public String getHoliday_date() {
            return holiday_date;
        }

        public void setHoliday_date(String holiday_date) {
            this.holiday_date = holiday_date;
        }

        public void setStatus(String status) {
            this.status = status;
    }

        public String getStatus() {
            return status;
    }

        public void setSoft_del(String soft_del) {
            this.soft_del = soft_del;
        }

        public String getSoft_del() {
            return soft_del;
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
     if (jsonObject.has("holiday_id")) {
 try {
     holiday_id = Integer.parseInt(jsonObject.getString("holiday_id"));
 } catch (Exception e) {}
 }

     if (jsonObject.has("holiday_date")) {
 try {
     holiday_date = jsonObject.getString("holiday_date");
 } catch (Exception e) {}
 }

     if (jsonObject.has("status")) {
 try {
     status = jsonObject.getString("status");
 } catch (Exception e) {
 }
     }

     if (jsonObject.has("soft_del")) {
         try {
             soft_del = jsonObject.getString("soft_del");
         } catch (Exception e) {
         }
     }

     if (jsonObject.has("created_date")) {
         try {
             created_date = jsonObject.getString("created_date");
         } catch (Exception e) {
         }
     }

     if (jsonObject.has("modified_date")) {
         try {
             modified_date = jsonObject.getString("modified_date");
 } catch (Exception e) {}
 }


     if (jsonObject.has("year")) {
         try {
             year = Integer.parseInt(jsonObject.getString("year"));
         } catch (Exception e) {}
     }

     if (jsonObject.has("month")) {
         try {
             month = Integer.parseInt(jsonObject.getString("month"));
         } catch (Exception e) {}
     }

     if (jsonObject.has("day")) {
         try {
             day = Integer.parseInt(jsonObject.getString("day"));
         } catch (Exception e) {}
     }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END