

 
	package com.voxtab.ariatech.voxtab.bean;


    import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

    import org.json.JSONObject;

    public class Error_log {



 int  logid =0;
 String  logtime ="" ;
 int  username =0;
 int  dbname =0;
 String  actionmsg ="" ;
 String  errormsg ="" ;
 public void setLogid(int logid) {
        this.logid = logid;
    }
 public int getLogid() {
        return logid;
    }

public void setLogtime(String logtime) {
        this.logtime = logtime;
    }
 public String getLogtime() {
        return logtime;
    }

 public void setUsername(int username) {
        this.username = username;
    }
 public int getUsername() {
        return username;
    }

 public void setDbname(int dbname) {
        this.dbname = dbname;
    }
 public int getDbname() {
        return dbname;
    }

public void setActionmsg(String actionmsg) {
        this.actionmsg = actionmsg;
    }
 public String getActionmsg() {
        return actionmsg;
    }

public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
 public String getErrormsg() {
        return errormsg;
    }




 public void parseJSON(JSONObject jsonObject) {
 try {
if (jsonObject.has("logid")) { 
 try {
 logid= Integer.parseInt(jsonObject.getString("logid"));
 } catch (Exception e) {}
 }

if (jsonObject.has("logtime")) { 
 try {
 logtime= jsonObject.getString("logtime");
 } catch (Exception e) {}
 }

if (jsonObject.has("username")) { 
 try {
 username= Integer.parseInt(jsonObject.getString("username"));
 } catch (Exception e) {}
 }

if (jsonObject.has("dbname")) { 
 try {
 dbname= Integer.parseInt(jsonObject.getString("dbname"));
 } catch (Exception e) {}
 }

if (jsonObject.has("actionmsg")) { 
 try {
 actionmsg= jsonObject.getString("actionmsg");
 } catch (Exception e) {}
 }

if (jsonObject.has("errormsg")) { 
 try {
 errormsg= jsonObject.getString("errormsg");
 } catch (Exception e) {}
 }
} catch (Exception e) {
			// TODO: handle exception
			GlobalData.printError(e);
		}

	} 


 }//END