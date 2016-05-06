

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Feedback {


 int  feedback_id =0;
 int  user_id =0;
 int  order_id =0;
 String  comments ="" ;
 String  created_date ="" ;
 String  feedback_type ="" ;
 String  assignment_number ="" ;
 String  filename ="" ;
 public void setFeedback_id(int feedback_id) {
        this.feedback_id = feedback_id;
    }
 public int getFeedback_id() {
        return feedback_id;
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

public void setFeed_txt(String feed_txt) {
        this.comments = feed_txt;
    }
 public String getFeed_txt() {
        return comments;
    }

public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
 public String getCreated_date() {
        return created_date;
    }

public void setFeedback_type(String feedback_type) {
        this.feedback_type = feedback_type;
    }
 public String getFeedback_type() {
        return feedback_type;
    }

public void setAssignment_no(String assignment_no) {
        this.assignment_number = assignment_no;
    }
 public String getAssignment_no() {
        return assignment_number;
    }

public void setFiles_nams(String files_nams) {
        this.filename = files_nams;
    }
 public String getFiles_nams() {
        return filename;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("feedback_id")) { 
 try {
 feedback_id= Integer.parseInt(jsonObject.getString("feedback_id"));
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

if (jsonObject.has("feed_txt")) { 
 try {
 comments= jsonObject.getString("feed_txt");
 } catch (Exception e) {}
 }

if (jsonObject.has("created_date")) { 
 try {
 created_date= jsonObject.getString("created_date");
 } catch (Exception e) {}
 }

if (jsonObject.has("feedback_type")) { 
 try {
 feedback_type= jsonObject.getString("feedback_type");
 } catch (Exception e) {}
 }

if (jsonObject.has("assignment_no")) { 
 try {
 assignment_number= jsonObject.getString("assignment_no");
 } catch (Exception e) {}
 }

if (jsonObject.has("files_nams")) { 
 try {
 filename= jsonObject.getString("files_nams");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END