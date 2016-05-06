

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Core_user {



 int  user_id =0;
 int  group_id =0;
 int  cat_id =0;
 String  username ="" ;
 String  last_name ="" ;
 String  password ="" ;
 String  first_name ="" ;
 String  join_date ="" ;
 String  photo ="" ;
 String  status ="" ;
 String  app_user_status ="" ;
 String  email ="" ;
 String  gender ="" ;
 String  mobile ="" ;
 String  auth_key ="" ;
 String  reg_date ="" ;
 String  modified_date ="" ;
 String  user_type ="" ;
 String  session_key ="" ;
 String  dob ="" ;
 String  city ="" ;
 public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
 public int getUser_id() {
        return user_id;
    }

 public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }
 public int getGroup_id() {
        return group_id;
    }

 public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }
 public int getCat_id() {
        return cat_id;
    }

public void setUsername(String username) {
        this.username = username;
    }
 public String getUsername() {
        return username;
    }

public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
 public String getLast_name() {
        return last_name;
    }

public void setPassword(String password) {
        this.password = password;
    }
 public String getPassword() {
        return password;
    }

public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
 public String getFirst_name() {
        return first_name;
    }

public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }
 public String getJoin_date() {
        return join_date;
    }

public void setPhoto(String photo) {
        this.photo = photo;
    }
 public String getPhoto() {
        return photo;
    }

public void setStatus(String status) {
        this.status = status;
    }
 public String getStatus() {
        return status;
    }

public void setApp_user_status(String app_user_status) {
        this.app_user_status = app_user_status;
    }
 public String getApp_user_status() {
        return app_user_status;
    }

public void setEmail(String email) {
        this.email = email;
    }
 public String getEmail() {
        return email;
    }

public void setGender(String gender) {
        this.gender = gender;
    }
 public String getGender() {
        return gender;
    }

public void setMobile(String mobile) {
        this.mobile = mobile;
    }
 public String getMobile() {
        return mobile;
    }

public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }
 public String getAuth_key() {
        return auth_key;
    }

public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }
 public String getReg_date() {
        return reg_date;
    }

public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }
 public String getModified_date() {
        return modified_date;
    }

public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
 public String getUser_type() {
        return user_type;
    }

public void setSession_key(String session_key) {
        this.session_key = session_key;
    }
 public String getSession_key() {
        return session_key;
    }

public void setDob(String dob) {
        this.dob = dob;
    }
 public String getDob() {
        return dob;
    }

public void setCity(String city) {
        this.city = city;
    }
 public String getCity() {
        return city;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("user_id")) { 
 try {
 user_id= Integer.parseInt(jsonObject.getString("user_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("group_id")) { 
 try {
 group_id= Integer.parseInt(jsonObject.getString("group_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("cat_id")) { 
 try {
 cat_id= Integer.parseInt(jsonObject.getString("cat_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("username")) { 
 try {
 username= jsonObject.getString("username");
 } catch (Exception e) {}
 }

if (jsonObject.has("last_name")) { 
 try {
 last_name= jsonObject.getString("last_name");
 } catch (Exception e) {}
 }

if (jsonObject.has("password")) { 
 try {
 password= jsonObject.getString("password");
 } catch (Exception e) {}
 }

if (jsonObject.has("first_name")) { 
 try {
 first_name= jsonObject.getString("first_name");
 } catch (Exception e) {}
 }

if (jsonObject.has("join_date")) { 
 try {
 join_date= jsonObject.getString("join_date");
 } catch (Exception e) {}
 }

if (jsonObject.has("photo")) { 
 try {
 photo= jsonObject.getString("photo");
 } catch (Exception e) {}
 }

if (jsonObject.has("status")) { 
 try {
 status= jsonObject.getString("status");
 } catch (Exception e) {}
 }

if (jsonObject.has("app_user_status")) { 
 try {
 app_user_status= jsonObject.getString("app_user_status");
 } catch (Exception e) {}
 }

if (jsonObject.has("email")) { 
 try {
 email= jsonObject.getString("email");
 } catch (Exception e) {}
 }

if (jsonObject.has("gender")) { 
 try {
 gender= jsonObject.getString("gender");
 } catch (Exception e) {}
 }

if (jsonObject.has("mobile")) { 
 try {
 mobile= jsonObject.getString("mobile");
 } catch (Exception e) {}
 }

if (jsonObject.has("auth_key")) { 
 try {
 auth_key= jsonObject.getString("auth_key");
 } catch (Exception e) {}
 }

if (jsonObject.has("reg_date")) { 
 try {
 reg_date= jsonObject.getString("reg_date");
 } catch (Exception e) {}
 }

if (jsonObject.has("modified_date")) { 
 try {
 modified_date= jsonObject.getString("modified_date");
 } catch (Exception e) {}
 }

if (jsonObject.has("user_type")) { 
 try {
 user_type= jsonObject.getString("user_type");
 } catch (Exception e) {}
 }

if (jsonObject.has("session_key")) { 
 try {
 session_key= jsonObject.getString("session_key");
 } catch (Exception e) {}
 }

if (jsonObject.has("dob")) { 
 try {
 dob= jsonObject.getString("dob");
 } catch (Exception e) {}
 }

if (jsonObject.has("city")) { 
 try {
 city= jsonObject.getString("city");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END