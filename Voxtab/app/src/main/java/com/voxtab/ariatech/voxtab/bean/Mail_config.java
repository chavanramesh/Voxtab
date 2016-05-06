

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Mail_config {



 int  config_id =0;
 int  user_id =0;
 String  purpose_name ="" ;
 String  mail_server ="" ;
 String  port ="" ;
 String  username ="" ;
 String  email ="" ;
 String  password ="" ;
 String  alt_email ="" ;
 String  mail_ssl ="" ;
 String  alert ="" ;
 String  subject ="" ;
 String  body ="" ;
 String  other_email ="" ;
 public void setConfig_id(int config_id) {
        this.config_id = config_id;
    }
 public int getConfig_id() {
        return config_id;
    }

 public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
 public int getUser_id() {
        return user_id;
    }

public void setPurpose_name(String purpose_name) {
        this.purpose_name = purpose_name;
    }
 public String getPurpose_name() {
        return purpose_name;
    }

public void setMail_server(String mail_server) {
        this.mail_server = mail_server;
    }
 public String getMail_server() {
        return mail_server;
    }

public void setPort(String port) {
        this.port = port;
    }
 public String getPort() {
        return port;
    }

public void setUsername(String username) {
        this.username = username;
    }
 public String getUsername() {
        return username;
    }

public void setEmail(String email) {
        this.email = email;
    }
 public String getEmail() {
        return email;
    }

public void setPassword(String password) {
        this.password = password;
    }
 public String getPassword() {
        return password;
    }

public void setAlt_email(String alt_email) {
        this.alt_email = alt_email;
    }
 public String getAlt_email() {
        return alt_email;
    }

public void setMail_ssl(String mail_ssl) {
        this.mail_ssl = mail_ssl;
    }
 public String getMail_ssl() {
        return mail_ssl;
    }

public void setAlert(String alert) {
        this.alert = alert;
    }
 public String getAlert() {
        return alert;
    }

public void setSubject(String subject) {
        this.subject = subject;
    }
 public String getSubject() {
        return subject;
    }

public void setBody(String body) {
        this.body = body;
    }
 public String getBody() {
        return body;
    }

public void setOther_email(String other_email) {
        this.other_email = other_email;
    }
 public String getOther_email() {
        return other_email;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("config_id")) { 
 try {
 config_id= Integer.parseInt(jsonObject.getString("config_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("user_id")) { 
 try {
 user_id= Integer.parseInt(jsonObject.getString("user_id"));
 } catch (Exception e) {}
 }

if (jsonObject.has("purpose_name")) { 
 try {
 purpose_name= jsonObject.getString("purpose_name");
 } catch (Exception e) {}
 }

if (jsonObject.has("mail_server")) { 
 try {
 mail_server= jsonObject.getString("mail_server");
 } catch (Exception e) {}
 }

if (jsonObject.has("port")) { 
 try {
 port= jsonObject.getString("port");
 } catch (Exception e) {}
 }

if (jsonObject.has("username")) { 
 try {
 username= jsonObject.getString("username");
 } catch (Exception e) {}
 }

if (jsonObject.has("email")) { 
 try {
 email= jsonObject.getString("email");
 } catch (Exception e) {}
 }

if (jsonObject.has("password")) { 
 try {
 password= jsonObject.getString("password");
 } catch (Exception e) {}
 }

if (jsonObject.has("alt_email")) { 
 try {
 alt_email= jsonObject.getString("alt_email");
 } catch (Exception e) {}
 }

if (jsonObject.has("mail_ssl")) { 
 try {
 mail_ssl= jsonObject.getString("mail_ssl");
 } catch (Exception e) {}
 }

if (jsonObject.has("alert")) { 
 try {
 alert= jsonObject.getString("alert");
 } catch (Exception e) {}
 }

if (jsonObject.has("subject")) { 
 try {
 subject= jsonObject.getString("subject");
 } catch (Exception e) {}
 }

if (jsonObject.has("body")) { 
 try {
 body= jsonObject.getString("body");
 } catch (Exception e) {}
 }

if (jsonObject.has("other_email")) { 
 try {
 other_email= jsonObject.getString("other_email");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END